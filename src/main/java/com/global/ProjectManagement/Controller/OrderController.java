package com.global.ProjectManagement.Controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.ProjectManagement.Dto.GroupedOrderDto;
import com.global.ProjectManagement.Dto.PurchaseDto;
import com.global.ProjectManagement.Entity.Orders;
import com.global.ProjectManagement.Services.OrderRequestServices;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v2")
@RequiredArgsConstructor
public class OrderController {

	private final OrderRequestServices requestServices;

	@PostMapping("/user/purchase-requests")
//	@PreAuthorize(value = "user")
	public ResponseEntity<?> createPurchaseRequest(@RequestBody PurchaseDto purchaseRequestDto) {
		Orders requestOrder = requestServices.createOrder(purchaseRequestDto.getClient(),
				purchaseRequestDto.getRequestOrder(), purchaseRequestDto.getItems());
		return new ResponseEntity<>(requestOrder, HttpStatus.CREATED);
	}

	@PutMapping("/admin/{id}/status")
	public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam int status) {
		try {
			requestServices.updateRequestOrderStatus(id, status);
			return ResponseEntity.ok(new StatusUpdateResponse("Status updated successfully."));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.status(500)
					.body(new StatusUpdateResponse("An error occurred while updating the status."));
		}
	}

	static class StatusUpdateResponse {
		private String message;

		public StatusUpdateResponse(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	@GetMapping("/admin/findByCodeOrPhoneNumber/{searchTerm}")
	public ResponseEntity<?> findByCodeOrPhoneNumber(@PathVariable String searchTerm) {
		return ResponseEntity.ok(requestServices.findByCodeOrPhoneNumber(searchTerm));
	}

	@GetMapping("/admin/getallitems/status-process-pending")
	public ResponseEntity<?> getAllOrdersByStatusProcessAndPending(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Page<GroupedOrderDto> items = requestServices.getAllOrdersByStatusProcessAndPending(page, size);
		return ResponseEntity.ok(items);
	}

	@GetMapping("/admin/getallitems/status-complete-return")
	public ResponseEntity<Page<?>> getAllOrdersByStatusCompleteAndReturned(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Page<GroupedOrderDto> items = requestServices.getAllOrdersByStatusCompleteAndReturned(page, size);
		return ResponseEntity.ok(items);
	}

	@GetMapping("/countneworders/count")
	public long CountNewOrders() {
		return requestServices.CountNewOrders();
	}

	@GetMapping("/countallorders/count")
	public long countAllOrders() {
		return requestServices.countAllOrders();
	}
}
