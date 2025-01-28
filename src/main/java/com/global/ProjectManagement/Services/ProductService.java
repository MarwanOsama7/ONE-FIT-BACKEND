package com.global.ProjectManagement.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.global.ProjectManagement.Base.Exception.DuplicateProductException;
import com.global.ProjectManagement.Base.Exception.InvalidFileNameException;
import com.global.ProjectManagement.Base.Exception.RecordNotFoundException;
import com.global.ProjectManagement.Base.Services.BaseServices;
import com.global.ProjectManagement.DTOs.ColorGroupedSizesDTO;
import com.global.ProjectManagement.DTOs.ProductDTO;
import com.global.ProjectManagement.DTOs.ProductSizeDTO;
import com.global.ProjectManagement.Dto.ImageDataDto;
import com.global.ProjectManagement.Dto.NewArrivalDto;
import com.global.ProjectManagement.Dto.ProductDto;
import com.global.ProjectManagement.Dto.ProductSizeDto;
import com.global.ProjectManagement.Dto.ProductSizeUpdateDto;
import com.global.ProjectManagement.Dto.SizeDto;
import com.global.ProjectManagement.Entity.Color;
import com.global.ProjectManagement.Entity.Product;
import com.global.ProjectManagement.Entity.ProductImage;
import com.global.ProjectManagement.Entity.ProductSize;
import com.global.ProjectManagement.Entity.Size;
import com.global.ProjectManagement.Repository.ColorRepository;
import com.global.ProjectManagement.Repository.ProductImageRepository;
import com.global.ProjectManagement.Repository.ProductRepository;
import com.global.ProjectManagement.Repository.ProductSizeRepository;
import com.global.ProjectManagement.Repository.SizeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService extends BaseServices<Product, Long> {
	private final LogService logService;
	private final ProductRepository productRepository;
	private final UploadFileService uploadFileService;
	private final ProductImageRepository imageRepository;
	private final ColorRepository colorRepository;
	private final SizeRepository sizeRepository;
	private final ProductSizeRepository productSizeRepository;
	private final ProductImageRepository productImageRepository;
//	private final ProductMapper productMapper;

	@Cacheable(value = "getProductsByCategoryName", key = "#categoryName + '_' + #page + '_' + #size")
	public Page<ProductDto> getProductsByCategoryName(String categoryName, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> productPage = productRepository.findByCategoryName(categoryName, pageable);

		// Map each product to a ProductDto
		List<ProductDto> productDtoList = productPage.getContent().stream().map(product -> {
			// Map images to DTOs
			Set<ImageDataDto> images = product.getImages().stream()
					.map(image -> new ImageDataDto(image.getId(), image.getImageUrl(),
							image.getColor() != null ? image.getColor().getId() : null))
					.sorted(Comparator.comparing((ImageDataDto image) -> image.getColorId(),
							Comparator.nullsLast(Long::compareTo)).thenComparing(ImageDataDto::getId))
					.collect(Collectors.toCollection(LinkedHashSet::new));

			// Map product to DTO
			return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getDiscount(),
					product.getPriceAfterdiscount(), product.getCategoryType().getName(), images);
		}).collect(Collectors.toList());

		// Return a Page object with ProductDto
		return new PageImpl<>(productDtoList, pageable, productPage.getTotalElements());
	}

	@Cacheable(value = "getProductsByCategoryTypeName", key = "#categoryTypeName + '_' + #page + '_' + #size")
	public Page<ProductDto> getProductsByCategoryTypeName(String categoryTypeName, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> productPage = productRepository.findByCategoryTypeName(categoryTypeName, pageable);

		// Map each product to a ProductDto
		List<ProductDto> productDtoList = productPage.getContent().stream().map(product -> {
			// Map images to DTOs
			Set<ImageDataDto> images = product.getImages().stream()
					.map(image -> new ImageDataDto(image.getId(), image.getImageUrl(),
							image.getColor() != null ? image.getColor().getId() : null))
					.sorted(Comparator.comparing((ImageDataDto image) -> image.getColorId(),
							Comparator.nullsLast(Long::compareTo)).thenComparing(ImageDataDto::getId))
					.collect(Collectors.toCollection(LinkedHashSet::new));

			// Map product to DTO
			return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getDiscount(),
					product.getPriceAfterdiscount(), product.getCategoryType().getName(), images);
		}).collect(Collectors.toList());

		// Return a Page object with ProductDto
		return new PageImpl<>(productDtoList, pageable, productPage.getTotalElements());
	}
//	public Product findByName(String name) {
//		Optional<Product> entity = productRepository.findByName(name);
//
//		if (entity.isPresent()) {
//			Product product = entity.get();
//			// this Comparator.nullsLast(Long::compareTo) when return null بحطه في اخر
//			// الينكد لسيت
//			Set<ProductImage> sortedImages = product.getImages().stream()
//					.sorted(Comparator
//							.comparing((ProductImage image) -> image.getColorId(), Comparator.nullsLast(Long::compareTo))
//							.thenComparing(ProductImage::getId))
//					.collect(Collectors.toCollection(LinkedHashSet::new));
//
//			product.setImages(sortedImages); // Set the sorted images
//			return entity.orElse(null);
//		} else {
//			throw new RecordNotFoundException("this record with name : " + name + "  Not Found");
//		}
//	}

	@Cacheable(value = "findProductsByName", key = "#name")
	public List<ProductDTO> findProductsByName(String name) {
		List<Product> products = productRepository.findByNameContainingIgnoreCase(name);

		return products.stream().map(product -> {
			// Map and sort images
			Set<ImageDataDto> images = product.getImages().stream()
					.map(image -> new ImageDataDto(image.getId(), image.getImageUrl(),
							image.getColor().getAttibutename(),
							image.getColor() != null ? image.getColor().getId() : null))
					.sorted(Comparator.comparing((ImageDataDto image) -> image.getColorId(),
							Comparator.nullsLast(Long::compareTo)).thenComparing(ImageDataDto::getId))
					.collect(Collectors.toCollection(LinkedHashSet::new));

			// Group product sizes by colorName

			Map<String, List<ProductSizeDTO>> sizesByColor = product.getProductSizes().stream()
					.collect(Collectors.groupingBy(size -> size.getColor().getAttibutename(), Collectors.mapping(
							size -> new ProductSizeDTO(size.getSize().getAttibutevalue(), size.getStockQuantity()),
							Collectors.toList())));

			// Transform sizesByColorName to the desired structure
			List<ColorGroupedSizesDTO> groupedSizes = sizesByColor.entrySet().stream().map(entry -> {
				String colorName = entry.getKey();
				List<ProductSizeDTO> sizeDTOs = entry.getValue();
				sizeDTOs.sort(Comparator.comparing(ProductSizeDTO::getSizeValue));

				// Fetch colorId from the first item in the list (assuming all items for a color
				// have the same colorId)
				Long colorId = product.getProductSizes().stream()
						.filter(size -> size.getColor().getAttibutename().equals(colorName)).findFirst()
						.map(size -> size.getColor().getId()).orElse(null); // Handle case where colorId might be
																			// missing or invalid

				return new ColorGroupedSizesDTO(colorId, colorName, sizeDTOs); // Include colorId here
			}).collect(Collectors.toList());

			return new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getPrice(),
					product.getDiscount(), product.getPriceAfterdiscount(), images, groupedSizes);
		}).collect(Collectors.toList());
	}

	@Cacheable(value = "getProductPaginatedfindAll", key = "#product + '_' + #page + '_' + #size")
	public Page<ProductDto> getPaginatedfindAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> productPage = productRepository.findAll(pageable);
		productPage.getContent().forEach(product -> Hibernate.initialize(product.getImages()));
		List<ProductDto> productDtoList = productPage.getContent().stream().map(product -> {
			return new ProductDto(product.getName(), product.getGender(), product.getPrice(), product.getDiscount(),
					product.getPriceAfterdiscount());

		}).collect(Collectors.toList());
		return new PageImpl<>(productDtoList, pageable, productPage.getTotalElements());
	}

	public List<ImageDataDto> getImagesByProductId(Long productId) {
		List<ProductImage> images = productImageRepository.findByProductId(productId);

		return images.stream().map(ps -> new ImageDataDto(ps.getId(), ps.getImageUrl(), ps.getColor().getId()))
				.collect(Collectors.toList());
	}

//	@Cacheable(value = "findByNameSearch", key = "#name")
	public List<Product> findByNameSearch(String name) {
		return productRepository.findByNameContaining(name);
	}

	@Cacheable(value = "getNewArrivalsProducts", key = "#CategoryName")
	public List<NewArrivalDto> getNewArrivals(String CategoryName) {
		Set<Product> products = productRepository.findAllByCategoryNameOrderByCreatedDateDesc(CategoryName);

		return products.stream().sorted(Comparator.comparing(Product::getId)).map(product -> {

			Set<ImageDataDto> images = product.getImages().stream()
					.map(image -> new ImageDataDto(image.getId(), image.getImageUrl(),
							image.getColor() != null ? image.getColor().getId() : null))
					.sorted(Comparator.comparing((ImageDataDto image) -> image.getColorId(),
							Comparator.nullsLast(Long::compareTo)).thenComparing(ImageDataDto::getId))
					.collect(Collectors.toCollection(LinkedHashSet::new));

			return new NewArrivalDto(product.getId(), product.getName(), product.getPrice(), product.getDiscount(),
					product.getPriceAfterdiscount(), images);
		}).collect(Collectors.toList());
	}

	// Helper to get the current username (example implementation)
	private String getCurrentUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@CacheEvict(value = { "getProductsByCategoryTypeName", "getProductsByCategoryName", "getNewArrivalsProducts",
			"findProductByName", "getProductPaginatedfindAll" }, key = "#root.methodName", allEntries = true)
	public Product insertProductWithDetials(String productJson, String productSizesJson, MultipartFile[] images)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		Product product = objectMapper.readValue(productJson, Product.class);
		List<ProductSizeDto> productSize = objectMapper.readValue(productSizesJson,
				new TypeReference<List<ProductSizeDto>>() {
				});

		// Log the start of the method
		logService.logAction("CREATE_PRODUCT", "INFO", "Starting product creation process", getCurrentUsername(), null,
				product.getName(), null);

		// Check for duplicate product
		Optional<Product> existingProduct = productRepository.findByNameAndCategory(product.getName());
		if (existingProduct.isPresent()) {
			logService.logError("CREATE_PRODUCT", "Duplicate product found with name '" + product.getName()
					+ "' and category '" + product.getCategory().getName() + "'", getCurrentUsername(), null);
			throw new DuplicateProductException("Product with name '" + product.getName() + "' and category '"
					+ product.getCategory().getName() + "' already exists.");
		}

		Product savedProduct = productRepository.save(product);
		logService.logAction("CREATE_PRODUCT", "INFO", "Product saved successfully", getCurrentUsername(),
				savedProduct.getId(), savedProduct.getName(), null);

		processAndInsertSizes(productSize, savedProduct);
		processAndInsertImages(images, savedProduct);
		return savedProduct;
	}

	private void processAndInsertSizes(List<ProductSizeDto> productSize, Product product) {
		// Save product sizes
		for (ProductSizeDto dto : productSize) {
			Color color = colorRepository.findById(dto.getColorId())
					.orElseThrow(() -> new RecordNotFoundException("Color not found with id " + dto.getColorId()));

			for (SizeDto sizeDto : dto.getSizes()) {
				Size size = sizeRepository.findById(sizeDto.getSizeId()).orElseThrow(
						() -> new RecordNotFoundException("Size not found with id " + sizeDto.getSizeId()));
				productSizeRepository.save(ProductSize.builder().product(product).color(color).size(size)
						.stockQuantity(sizeDto.getStockQuantity()).build());
			}
		}

		logService.logAction("SAVE_PRODUCT_SIZES", "INFO", "Product sizes saved successfully", getCurrentUsername(),
				product.getId(), product.getName(), null);
	}

	private void processAndInsertImages(MultipartFile[] Images, Product product) {
		Map<Long, List<MultipartFile>> imageMap = new HashMap<>();
		for (MultipartFile image : Images) {
			String imageName = image.getOriginalFilename();
			if (imageName == null || !imageName.matches("\\d+_.+")) {
				logService.logError("PROCESS_IMAGES", "Invalid filename: " + imageName, getCurrentUsername(), null);
				throw new InvalidFileNameException(
						"Invalid filename: " + imageName + " please modify the filename to be Colorid_nameofimage");
			}
			String[] parts = imageName.split("_");
			Long colorid = Long.parseLong(parts[0]);

			imageMap.computeIfAbsent(colorid, k -> new ArrayList<>()).add(image);
		}

		logService.logAction("PROCESS_IMAGES", "INFO", "Color images processed successfully", getCurrentUsername(),
				product.getId(), product.getName(), null);

		// Process and save images
		imageMap.forEach((colorId, images) -> {
			Color color = colorRepository.findById(colorId).orElseThrow(() -> new RuntimeException("Color not found"));

			for (MultipartFile image : images) {
				try {
					// Call storeFile instead of upload
					Map<String, String> storeResult = uploadFileService.storeFile(image, product.getId(),
							"onefit/" + colorId, null);
					String filePath = storeResult.get("filePath");

					// Save image data
					ProductImage imageData = ProductImage.builder().name(image.getName()).imageUrl(filePath)
							.color(color).product(product).build();

					imageRepository.save(imageData);

				} catch (Exception e) {
					logService.logError("PROCESS_IMAGES", "Error storing file: " + e.getMessage(), getCurrentUsername(),
							null);
					throw new RuntimeException("Error storing file", e);
				}
			}
		});
		logService.logAction("SAVE_IMAGES", "INFO", "Images saved successfully", getCurrentUsername(), product.getId(),
				product.getName(), null);
	}

	@CacheEvict(value = { "getProductsByCategoryTypeName", "getProductsByCategoryName", "getNewArrivalsProducts",
			"findProductByName", "getProductPaginatedfindAll" }, key = "#root.methodName", allEntries = true)
	@Transactional
	public Product updateProduct(Long id, ProductDto productDto) {
		Product existingProduct = productRepository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Product not found with id: " + id));

		existingProduct.setName(productDto.getName());
		existingProduct.setPrice(productDto.getPrice());
		existingProduct.setDiscount(productDto.getDiscount());
		existingProduct.setGender(productDto.getGender());
		existingProduct.setDescription(productDto.getDescription());

		return productRepository.save(existingProduct);
	}

	@CacheEvict(value = { "getProductsByCategoryTypeName", "getProductsByCategoryName", "getNewArrivalsProducts",
			"findProductById", "getProductPaginatedfindAll" }, key = "#root.methodName", allEntries = true)
	@Transactional
	public void updateStockForMultipleSizes(Long productId, List<ProductSizeUpdateDto> sizeUpdates) {
		// Fetch product once
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("Product not found for id: " + productId));

		// Fetch all sizes and colors used in the updates
		Set<Long> sizeIds = sizeUpdates.stream().map(ProductSizeUpdateDto::getSizeId).collect(Collectors.toSet());
		Set<Long> colorIds = sizeUpdates.stream().map(ProductSizeUpdateDto::getColorId).collect(Collectors.toSet());

		List<Size> sizes = sizeRepository.findAllById(sizeIds);
		Map<Long, Size> sizeMap = sizes.stream().collect(Collectors.toMap(Size::getId, size -> size));

		List<Color> colors = colorRepository.findAllById(colorIds);
		Map<Long, Color> colorMap = colors.stream().collect(Collectors.toMap(Color::getId, color -> color));

		// Fetch existing ProductSize entities once using the new method
		List<ProductSize> existingProductSizes = productSizeRepository.findByProductIdAndSizeIdInAndColorIdIn(productId,
				new ArrayList<>(sizeIds), new ArrayList<>(colorIds));

		Map<String, ProductSize> productSizeMap = existingProductSizes.stream()
				.collect(Collectors.toMap(ps -> ps.getSize().getId() + "-" + ps.getColor().getId(), ps -> ps));

		// Update or create ProductSize
		for (ProductSizeUpdateDto sizeUpdate : sizeUpdates) {
			String key = sizeUpdate.getSizeId() + "-" + sizeUpdate.getColorId();
			ProductSize productSize = productSizeMap.get(key);

			if (productSize != null) {
				productSize.setStockQuantity(sizeUpdate.getStockQuantity());
				logService.logAction("Updated Stock of ProductSizes", "INFO", "", getCurrentUsername(),
						productSize.getId(), product.getName(), "Updated for Product have ID  : " + product.getId());
			} else {
				// Create new ProductSize
				ProductSize newProductSize = new ProductSize();
				newProductSize.setProduct(product);
				newProductSize.setSize(sizeMap.get(sizeUpdate.getSizeId()));
				newProductSize.setColor(colorMap.get(sizeUpdate.getColorId()));
				newProductSize.setStockQuantity(sizeUpdate.getStockQuantity());
				productSizeRepository.save(newProductSize);
			}
		}

	}

	@Transactional
	public void addNewImages(Long productId, MultipartFile[] newImages) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new RecordNotFoundException("Product not found with id " + productId));

		for (MultipartFile image : newImages) {
			String fileName = image.getOriginalFilename();
			if (fileName == null || !fileName.matches("\\d+_.+")) {
				throw new InvalidFileNameException(
						"Invalid filename: " + fileName + ". Filename must be in format 'colorId_imageName'");
			}

			String[] parts = fileName.split("_");
			Long colorId = Long.parseLong(parts[0]); // Assumes filename starts with colorId_
			Color color = colorRepository.findById(colorId)
					.orElseThrow(() -> new RuntimeException("Color not found with id " + colorId));

			// Upload the image and get the file path
			Map<String, String> uploadResult = uploadFileService.storeFile(image, productId, "BriskShoes/" + colorId,
					null);
			String imageUrl = uploadResult.get("filePath");

			// Create new ImageData and associate it with the product
			ProductImage newImageData = ProductImage.builder().name(image.getOriginalFilename()).imageUrl(imageUrl)
					.color(color).product(product).build();

			// Save the new image
			productImageRepository.save(newImageData);
		}
	}

	@CacheEvict(value = { "getProductsByCategoryTypeName", "getProductsByCategoryName", "getNewArrivalsProducts",
			"findProductByName", "getProductPaginatedfindAll" }, key = "#root.methodName", allEntries = true)
	@Transactional
	public void deleteImagesByProductId(Long productId) {
		// Fetch the images by product ID
		List<ProductImage> images = productImageRepository.findByProductId(productId);

		if (!images.isEmpty()) {
			// Delete the images from the database
			productImageRepository.deleteByProductId(productId);

			// Remove images from the file system
			for (ProductImage image : images) {
				String imageUrl = image.getImageUrl();
				deleteFile(imageUrl); // Method to remove the file from storage
			}
		}
	}

	@CacheEvict(value = { "getProductsByCategoryTypeName", "getProductsByCategoryName", "getNewArrivalsProducts",
			"findProductByName", "getProductPaginatedfindAll" }, key = "#root.methodName", allEntries = true)
	@Transactional
	public void deleteImagesById(Long id) {
		// Fetch the images by product ID
		Optional<ProductImage> images = productImageRepository.findById(id);

		if (!images.isEmpty()) {
			// Delete the images from the database
			productImageRepository.deleteById(id);
			String imageUrl = images.get().getImageUrl();
			deleteFile(imageUrl);
		} else {
			throw new RecordNotFoundException("the id not found");
		}
	}

	private void deleteFile(String imageUrl) {
		try {
			// Extract the file path from the image URL
			String basePath = uploadFileService.getBasePath();
			String relativeFilePath = imageUrl.replace("http://92.113.27.179/uploads/", ""); // Get the relative path
																								// from the URL
			Path filePath = Paths.get(basePath, relativeFilePath);

			// Delete the file from the file system
			Files.deleteIfExists(filePath);
		} catch (IOException e) {
			throw new RuntimeException("Error deleting file from storage: " + imageUrl, e);
		}
	}
}
