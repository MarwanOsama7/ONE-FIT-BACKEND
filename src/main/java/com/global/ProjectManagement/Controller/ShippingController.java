package com.global.ProjectManagement.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.ProjectManagement.Dto.ShippingDto;
import com.global.ProjectManagement.Entity.Shipping;
import com.global.ProjectManagement.Mappar.ShippingMapper;
import com.global.ProjectManagement.Services.ShippingServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v2")
@RequiredArgsConstructor
public class ShippingController {

	private final ShippingServices shippingServices;
	private final ShippingMapper shippingMapper;

	@GetMapping("/admin/shipping/findbyid/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Shipping shipping = shippingServices.findById(id);
		ShippingDto returnDto = shippingMapper.map(shipping);
		return ResponseEntity.ok(returnDto);
	}

	@GetMapping("/user/shipping/findall")
	public ResponseEntity<?> findAllshipping() {
		List<Shipping> shipping = shippingServices.findAll();
		List<ShippingDto> returnDto = shippingMapper.map(shipping);
		return ResponseEntity.ok(returnDto);
	}

	@GetMapping("/admin/shippingpaginate/findall")
	public ResponseEntity<?> findAllshippingByPaginated(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(shippingServices.getPaginatedfindAll(page, size));
	}

	@PostMapping("/admin/shipping/insert")
	public ResponseEntity<?> insertShipping(@RequestBody ShippingDto dto) {
		Shipping shipping = shippingMapper.unmap(dto);
		Shipping savedshipping = shippingServices.insert(shipping);
		ShippingDto returnDto = shippingMapper.map(savedshipping);
		return ResponseEntity.ok(returnDto);
	}

	@PutMapping("/admin/shipping/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ShippingDto dto) {
		// Call the service to handle the update process
		ShippingDto updatedShipping = shippingServices.updateShipping(id, dto);

		// Return the updated DTO
		if (updatedShipping == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(updatedShipping);
	}

	@DeleteMapping("/admin/shipping/deletebyid/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		shippingServices.deleteById(id);
		return ResponseEntity.ok("Record deleted successfully");
	}
}
