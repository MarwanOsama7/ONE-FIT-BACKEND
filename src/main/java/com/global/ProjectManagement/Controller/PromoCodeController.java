package com.global.ProjectManagement.Controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.ProjectManagement.DTOs.PromoCodeDTO;
import com.global.ProjectManagement.Entity.PromoCode;
import com.global.ProjectManagement.Mappar.PromoCodeMapper;
import com.global.ProjectManagement.Response.PromoCodeResponse;
import com.global.ProjectManagement.Services.PromoCodeServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v2")
@RequiredArgsConstructor
public class PromoCodeController {

	private final PromoCodeServices service;
	private final PromoCodeMapper mapper;

	@GetMapping("/admin/promocodepaginate/findall")
	public ResponseEntity<?> getPromoCode(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(service.getPaginatedfindAll(page, size));
	}

	@PostMapping("/admin/promoCode/insert")
	public ResponseEntity<?> insert(@RequestBody PromoCodeDTO dto) {
		PromoCode promo = mapper.unmap(dto);
		PromoCode savedPromo = service.insert(promo);
		PromoCodeDTO returnDto = mapper.map(savedPromo);
		return ResponseEntity.ok(returnDto);
	}

	@GetMapping("/admin/validate/{code}")
	public ResponseEntity<?> validatePromoCode(@PathVariable String code) {
		Optional<PromoCode> promoCode = service.validatePromoCode(code);
		if (promoCode.isPresent()) {
//			service.usePromoCode(code);
			return ResponseEntity.ok("Promo code used successfully");
		}
		return ResponseEntity.badRequest().body("Invalid or expired promo code");
	}

	@PostMapping("/usepromoCode/{code}")
	public ResponseEntity<?> usePromoCode(@PathVariable String code) {
		Optional<PromoCode> promoCode = service.validatePromoCode(code);
		if (promoCode.isPresent()) {
			service.usePromoCode(code);
			double discount = promoCode.get().getPromoDiscount();
			PromoCodeResponse response = new PromoCodeResponse("Promo code used successfully", discount);
			return ResponseEntity.ok(response);
		}
		PromoCodeResponse errorResponse = new PromoCodeResponse("Invalid or expired promo code", 0.0);
		return ResponseEntity.badRequest().body(errorResponse);
	}
	
	@DeleteMapping("/admin/promocode/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		try {
			service.deleteById(id);
			return ResponseEntity.noContent().build(); // Return 204 No Content on successful deletion
		} catch (Exception e) {
			return ResponseEntity.notFound().build(); // Return 404 Not Found if the ID doesn't exist
		}
	}
}
