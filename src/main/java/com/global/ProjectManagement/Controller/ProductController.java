package com.global.ProjectManagement.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.global.ProjectManagement.DTOs.ProductDTO;
import com.global.ProjectManagement.Dto.ProductDto;
import com.global.ProjectManagement.Dto.ProductSizeUpdateDto;
import com.global.ProjectManagement.Entity.Product;
import com.global.ProjectManagement.Mappar.ProductMapper;
import com.global.ProjectManagement.Services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v2")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;
	private final ProductMapper mapper;

	@GetMapping("/productpaginate/findallbycategoryname")
	public ResponseEntity<?> findAllByCategoryName(@RequestParam String categoryName,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(productService.getProductsByCategoryName(categoryName, page, size));
	}

	@GetMapping("/productpaginate/findallbycategorytypename")
	public ResponseEntity<?> findAllByCategoryTypeName(@RequestParam String categoryTypeName,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(productService.getProductsByCategoryTypeName(categoryTypeName, page, size));
	}

	@GetMapping("/admin/findimagesbyproductid")
	public ResponseEntity<?> getImagesByProductId(@RequestParam Long id) {
		return ResponseEntity.ok(productService.getImagesByProductId(id));
	}

	@GetMapping("/admin/findByNameSearch/{name}")
	public ResponseEntity<?> findByNameSearch(@PathVariable String name) {
		return ResponseEntity.ok(productService.findByNameSearch(name));
	}

	@GetMapping("/product/newarrivals")
	public ResponseEntity<?> getNewArrivals(@RequestParam String categoryName) {
		return ResponseEntity.ok(productService.getNewArrivals(categoryName));
	}

	@GetMapping("/admin/productpaginate/findall")
	public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(productService.getPaginatedfindAll(page, size));
	}

	@PostMapping("/admin/product/insert")
	public ResponseEntity<?> insertProduct(@RequestParam("product") String productJson,
			@RequestParam("productSizes") String productSizesJson,
			@RequestParam("colorImages") MultipartFile[] colorImages) {

		try {

			Product savedProduct = productService.insertProductWithDetials(productJson, productSizesJson, colorImages);
			ProductDto Dto = mapper.map(savedProduct);
			// Return the saved product with HTTP 201 Created status
			return ResponseEntity.status(HttpStatus.CREATED).body(Dto);

		} catch (JsonProcessingException e) {
			return ResponseEntity.badRequest().body(null); // Invalid JSON format
		}
	}
	

	@GetMapping("/product/findbyname/{name}")
	public ResponseEntity<?> findByName(@PathVariable String name) {
		List<ProductDTO> product = productService.findProductsByName(name);
//		ProductDto returnDto = mapper.map(category);
		return ResponseEntity.ok(product);
	}

	@PutMapping("/admin/updateproduct")
	public ResponseEntity<?> updateProduct(@RequestParam Long id, @RequestBody ProductDto productDto) {
		Product updatedProduct = productService.updateProduct(id, productDto);
		ProductDto Dto = mapper.map(updatedProduct);
		return ResponseEntity.status(HttpStatus.CREATED).body(Dto);
	}

	@PutMapping("/admin/update-stock-multiple")
	public ResponseEntity<Map<String, String>> updateStockForMultipleSizes(@RequestParam Long productId,
			@RequestBody List<ProductSizeUpdateDto> sizeUpdates) {
		productService.updateStockForMultipleSizes(productId, sizeUpdates);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Stock updated successfully for multiple sizes.");
		return ResponseEntity.ok(response);
	}

	@PostMapping("/admin/addnewImages")
	public ResponseEntity<String> addNewImages(@RequestParam Long productId,
			@RequestParam("images") MultipartFile[] images) {
		productService.addNewImages(productId, images);
		return ResponseEntity.ok("Images added successfully.");
	}

	@DeleteMapping("/admin/deleteImagesByProductId/{productId}")
	public ResponseEntity<?> deleteImagesByProductId(@PathVariable Long productId) {
		productService.deleteImagesByProductId(productId);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Images deleted successfully for product ID: " + productId);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/admin/deleteImagesById/{id}")
	public ResponseEntity<?> deleteImagesById(@PathVariable Long id) {
		productService.deleteImagesById(id);
		Map<String, String> response = new HashMap<>();
		response.put("message", "Image deleted successfully for ID: " + id);
		return ResponseEntity.ok(response);
	}
}
