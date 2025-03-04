package com.global.ProjectManagement.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.global.ProjectManagement.Base.Exception.RecordNotFoundException;
import com.global.ProjectManagement.Dto.CustomerDto;
import com.global.ProjectManagement.Dto.GetItemDto;
import com.global.ProjectManagement.Dto.GroupedOrderDto;
import com.global.ProjectManagement.Dto.ItemDto;
import com.global.ProjectManagement.Dto.OrderDto;
import com.global.ProjectManagement.Entity.Color;
import com.global.ProjectManagement.Entity.Customers;
import com.global.ProjectManagement.Entity.OrderItems;
import com.global.ProjectManagement.Entity.Orders;
import com.global.ProjectManagement.Entity.Product;
import com.global.ProjectManagement.Entity.ProductSize;
import com.global.ProjectManagement.Entity.Size;
import com.global.ProjectManagement.Mappar.ItemsMapper;
import com.global.ProjectManagement.Repository.ColorRepository;
import com.global.ProjectManagement.Repository.CustomerRepository;
import com.global.ProjectManagement.Repository.OrderItemsRepository;
import com.global.ProjectManagement.Repository.OrdersRepository;
import com.global.ProjectManagement.Repository.ProductRepository;
import com.global.ProjectManagement.Repository.ProductSizeRepository;
import com.global.ProjectManagement.Repository.SizeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderRequestServices {

	private final CustomerRepository customerRepository;
	private final OrdersRepository ordersRepository;
	private final OrderItemsRepository itemsRepository;
	private final ProductSizeRepository productSizeRepository;
	private final ProductRepository productRepository;
	private final SizeRepository sizeRepository;
	private final ColorRepository colorRepository;

	@CacheEvict(value = { "getOrdersProcessAndPending", "getOrdersCompleteAndReturned", "findOrder", "CountNewOrders",
			"countAllOrders" }, key = "#root.methodName", allEntries = true)
	@Transactional
	public Orders createOrder(CustomerDto customerDTO, OrderDto OrderDTO, List<ItemDto> itemsDTO) {
		Customers customers = processCustomer(customerDTO);
		return processOrderAndItems(OrderDTO, itemsDTO, customers);
	}

	private Orders processOrderAndItems(OrderDto orderDTO, List<ItemDto> itemsDTO, Customers customers) {
		Orders orders = Orders.builder().cityOfOrder(orderDTO.getCityOfOrder()).code(orderDTO.getCode())
				.note(orderDTO.getNote()).status(-1).totalPrice(orderDTO.getTotalPrice())
				.totalQuantity(orderDTO.getTotalQuantity()).customers(customers).promoCode(orderDTO.getPromoCode())
				.build();

		Orders savedOrders = ordersRepository.save(orders);

		List<OrderItems> items = itemsDTO.stream().map(itemsDto -> {
			OrderItems orderItems = OrderItems.builder().colorName(itemsDto.getColorName()).img(itemsDto.getImg())
					.name(itemsDto.getName()).orders(savedOrders).price(itemsDto.getPrice())
					.quantity(itemsDto.getQuantity()).size(itemsDto.getSize()).build();

			return orderItems;
		}).filter(item -> {
			// Filter out items that do not have necessary data
			return item.getName() != null && !item.getName().isEmpty() && item.getColorName() != null
					&& !item.getColorName().isEmpty() && item.getSize() != null && !item.getSize().isEmpty()
					&& item.getPrice() != 0 && item.getPrice() > 0 && item.getQuantity() != 0 && item.getQuantity() > 0;
		}).toList();

		if (!items.isEmpty()) {
			// Iterate over the itemsList to reduce stock for each valid item
			for (OrderItems item : items) {
				try {
					reduceStock(item.getName(), item.getSize(), item.getColorName(), item.getQuantity());
				} catch (RuntimeException e) {
					throw new RuntimeException("Failed to process order due to stock issues: " + e.getMessage());
				}
			}

			itemsRepository.saveAll(items);
		} else {
			throw new RuntimeException("No valid items to save.");
		}
		// Set items back to request order (in case you need to return them)
		savedOrders.setItems(items);
		return savedOrders;
	}

	private Customers processCustomer(CustomerDto customerDTO) {
		Customers customer = customerRepository.findByEmail(customerDTO.getEmail()).orElseGet(() -> {
			Customers newcustomer = Customers.builder().username(customerDTO.getUsername())
					.email(customerDTO.getEmail()).phoneNumber(customerDTO.getPhoneNumber()).city(customerDTO.getCity())
					.address(customerDTO.getAddress()).phoneNumberOptional(customerDTO.getPhoneNumberOptional())
					.build();
			return customerRepository.save(newcustomer);
		});

		boolean clientChanged = false;
		if (!customer.getPhoneNumber().equals(customerDTO.getPhoneNumber())) {
			customer.setPhoneNumber(customerDTO.getPhoneNumber());
			clientChanged = true;
		}
		if (customerDTO.getPhoneNumberOptional() != null
				&& !customerDTO.getPhoneNumberOptional().equals(customer.getPhoneNumberOptional())) {
			customer.setPhoneNumberOptional(customerDTO.getPhoneNumberOptional());
			clientChanged = true;
		}
		if (!customer.getAddress().equals(customerDTO.getAddress())) {
			customer.setAddress(customerDTO.getAddress());
			clientChanged = true;
		}
		if (!customer.getCity().equals(customerDTO.getCity())) {
			customer.setCity(customerDTO.getCity());
			clientChanged = true;
		}

		// Save the client only if there were changes
		if (clientChanged) {
			customerRepository.save(customer);
		}

		return customer;
	}

	@Transactional
	private void reduceStock(String productName, String sizeValue, String colorName, int quantity) {
		// Additional Consideration 3: Avoid deadlocks by ensuring a consistent order of
		// locking

		// Find the product
		Product product = productRepository.findByName(productName)
				.orElseThrow(() -> new RuntimeException("Product not found"));

		// Find the size
		Size size = sizeRepository.findByAttibutevalue(sizeValue)
				.orElseThrow(() -> new RuntimeException("Size not found"));

		// Find the color
		Color color = colorRepository.findByAttibutename(colorName)
				.orElseThrow(() -> new RuntimeException("Color not found"));

		// Lock the ProductSize with PESSIMISTIC_WRITE to prevent overselling
		ProductSize productSize = productSizeRepository.findByProductIdAndSizeIdAndColorId(product.getId(),
				size.getId(), color.getId());

		// Update the stock quantity
		if (productSize == null) {
			throw new RecordNotFoundException("this record not found ");
		} else {
			int updatedStock = productSize.getStockQuantity() - quantity;
			if (updatedStock < 0) {
				throw new RuntimeException("Insufficient stock for the requested product size and color");
			}
			productSize.setStockQuantity(updatedStock);
		}

		// Save the updated ProductSize
		productSizeRepository.save(productSize);
	}

	@Transactional
	@CacheEvict(value = { "getOrdersProcessAndPending", "getOrdersCompleteAndReturned", "findOrder", "CountNewOrders",
			"countAllOrders" }, key = "#root.methodName", allEntries = true)
	public void updateRequestOrderStatus(Long id, int status) {
		int rowsAffected = ordersRepository.updateStatusById(id, status);
		if (rowsAffected == 0) {
			throw new RecordNotFoundException("RequestOrder with id " + id + " not found");
		}
	}

	@Cacheable(value = "getOrdersProcessAndPending", key = "#root.methodName")
	public Page<GroupedOrderDto> getAllOrdersByStatusProcessAndPending(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<OrderItems> items = itemsRepository.findAllByOrderByRequestOrderId(pageable);

		// Date and time formatters
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // User-friendly date format
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a"); // 12-hour format with AM/PM

		// Grouping by requestOrder
		Map<Orders, List<GetItemDto>> groupedItems = items.stream().collect(Collectors.groupingBy(OrderItems::getOrders,
				Collectors.mapping(ItemsMapper.INSTANCE::map, Collectors.toList())));

		// Convert the map to a list of GroupedItemDto, filter by status -1
		List<GroupedOrderDto> groupedDtos = groupedItems.entrySet().stream()
				.filter(entry -> entry.getKey().getStatus() == -1 || entry.getKey().getStatus() == 0) // Filter by
																										// status
																										// Process and
																										// pending
				.sorted(Comparator.comparing(entry -> entry.getKey().getCreatedDate())) // Sort by createdDate
				.map(entry -> {
					// Parse the createdDate
					LocalDateTime createdDateTime = entry.getKey().getCreatedDate();
					String createdDate = createdDateTime.format(dateFormatter); // Format the date
					String createdTime = createdDateTime.format(timeFormatter); // Format the time

					// Create and return the GroupedItemDto
					return new GroupedOrderDto(entry.getKey().getId(), entry.getKey().getCode(),
							entry.getKey().getTotalPrice(), entry.getKey().getTotalQuantity(),
							entry.getKey().getStatus(), createdDate, // Pass the separated date
							createdTime, // Pass the separated time
							entry.getKey().getCustomers().getUsername(), entry.getKey().getCustomers().getPhoneNumber(),
							entry.getKey().getCustomers().getAddress(), entry.getKey().getCityOfOrder(),
							entry.getKey().getPromoCode(), entry.getValue());
				}).collect(Collectors.toList());

		// Sort the grouped DTOs by status ID if needed (though all are -1)
		groupedDtos.sort(Comparator.comparing(GroupedOrderDto::getStatus).reversed());

		return new PageImpl<>(groupedDtos, pageable, items.getTotalElements());
	}

	@Cacheable(value = "getOrdersCompleteAndReturned", key = "#root.methodName")
	public Page<GroupedOrderDto> getAllOrdersByStatusCompleteAndReturned(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<OrderItems> items = itemsRepository.findAllByOrderByRequestOrderId(pageable);

		// Date and time formatters
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // User-friendly date format
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a"); // 12-hour format with AM/PM

		// Grouping by requestOrder
		Map<Orders, List<GetItemDto>> groupedItems = items.stream().collect(Collectors.groupingBy(OrderItems::getOrders,
				Collectors.mapping(ItemsMapper.INSTANCE::map, Collectors.toList())));

		// Convert the map to a list of GroupedItemDto, filter by status -1
		List<GroupedOrderDto> groupedDtos = groupedItems.entrySet().stream()
				.filter(entry -> entry.getKey().getStatus() == 1 || entry.getKey().getStatus() == 2) // Filter by status
																										// complete and
																										// returned
				.sorted(Comparator.comparing(entry -> entry.getKey().getCreatedDate())) // Sort by createdDate
				.map(entry -> {
					// Parse the createdDate
					LocalDateTime createdDateTime = entry.getKey().getCreatedDate();
					String createdDate = createdDateTime.format(dateFormatter); // Format the date
					String createdTime = createdDateTime.format(timeFormatter); // Format the time

					// Create and return the GroupedItemDto
					return new GroupedOrderDto(entry.getKey().getId(), entry.getKey().getCode(),
							entry.getKey().getTotalPrice(), entry.getKey().getTotalQuantity(),
							entry.getKey().getStatus(), createdDate, // Pass the separated date
							createdTime, // Pass the separated time
							entry.getKey().getCustomers().getUsername(), entry.getKey().getCustomers().getPhoneNumber(),
							entry.getKey().getCustomers().getAddress(), entry.getKey().getCityOfOrder(),
							entry.getKey().getPromoCode(), entry.getValue());
				}).collect(Collectors.toList());

		// Sort the grouped DTOs by status ID if needed (though all are -1)
		groupedDtos.sort(Comparator.comparing(GroupedOrderDto::getStatus).reversed());

		return new PageImpl<>(groupedDtos, pageable, items.getTotalElements());
	}

//	@Cacheable(value = "findOrder", key = "#root.methodName")
	public List<GroupedOrderDto> findByCodeOrPhoneNumber(String searchTerm) {
		List<Orders> requestOrders = itemsRepository.findByCodeOrPhoneNumber(searchTerm);

		// Grouping items by Order
		Map<Orders, List<GetItemDto>> groupedItems = requestOrders.stream()
				.filter(ro -> ro.getStatus() == -1 || ro.getStatus() == 0) // Filter by status
				.collect(Collectors.groupingBy(Function.identity(), Collectors.flatMapping(ro -> ro.getItems().stream(),
						Collectors.mapping(ItemsMapper.INSTANCE::map, Collectors.toList()))));

		// Convert the map to a list of GroupedItemDto
		return groupedItems.entrySet().stream().filter(entry -> entry.getKey().getStatus() == -1) // Filter by status -1
																									// only
				.map(entry -> {
					// Parse the createdDate
					LocalDateTime createdDateTime = entry.getKey().getCreatedDate();
					String createdDate = createdDateTime.toLocalDate().toString(); // Extract date
					String createdTime = createdDateTime.toLocalTime().toString(); // Extract time

					// Create and return the GroupedItemDto
					return new GroupedOrderDto(entry.getKey().getId(), entry.getKey().getCode(),
							entry.getKey().getTotalPrice(), entry.getKey().getTotalQuantity(),
							entry.getKey().getStatus(), createdDate, // Pass the separated date
							createdTime, // Pass the separated time
							entry.getKey().getCustomers().getUsername(), entry.getKey().getCustomers().getPhoneNumber(),
							entry.getKey().getCustomers().getAddress(), entry.getKey().getCityOfOrder(),
							entry.getKey().getPromoCode(), entry.getValue());
				}).collect(Collectors.toList());

	}

	@Cacheable(value = "CountNewOrders", key = "#root.methodName")
	public long CountNewOrders() {
		return ordersRepository.CountNewOrders();
	}

	@Cacheable(value = "countAllOrders", key = "#root.methodName")
	public long countAllOrders() {
		return ordersRepository.countAllOrders();
	}

}
