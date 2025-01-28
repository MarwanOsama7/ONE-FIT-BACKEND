package com.global.ProjectManagement.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.global.ProjectManagement.Dto.ColorDto;
import com.global.ProjectManagement.Dto.SizeReturnDto;
import com.global.ProjectManagement.Entity.Color;
import com.global.ProjectManagement.Entity.Size;
import com.global.ProjectManagement.Mappar.ColorMapper;
import com.global.ProjectManagement.Mappar.SizeMapper;
import com.global.ProjectManagement.Services.ColorServices;
import com.global.ProjectManagement.Services.SizeServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v2")
@RequiredArgsConstructor
public class AttributeController {

	private final ColorServices colorService;
	private final ColorMapper colorMapper;
	private final SizeServices sizeService;
	private final SizeMapper sizeMapper;

	@PostMapping("/admin/color/insert")
	public ResponseEntity<?> insertColor(@RequestBody ColorDto dto) {
		Color color = colorMapper.unmap(dto);
		Color savedcolor = colorService.insert(color);
		ColorDto returnDto = colorMapper.map(savedcolor);
		return ResponseEntity.ok(returnDto);
	}

	@PostMapping("/admin/size/insert")
	public ResponseEntity<?> insertSize(@RequestBody SizeReturnDto dto) {
		Size size = sizeMapper.unmap(dto);
		Size savedsize = sizeService.insert(size);
		SizeReturnDto returnDto = sizeMapper.map(savedsize);
		return ResponseEntity.ok(returnDto);
	}

//	@GetMapping("/admin/color/findall")
//	public ResponseEntity<?> findAllColor() {
//		List<Color> color = colorService.findAll();
//		List<ColorDto> returnDto = colorMapper.map(color);
//		return ResponseEntity.ok(returnDto);
//	}
//
//	@GetMapping("/admin/size/findall")
//	public ResponseEntity<?> findAllSizes() {
//		List<Size> size = sizeService.findAll();
//		List<SizeReturnDto> returnDto = sizeMapper.map(size);
//		return ResponseEntity.ok(returnDto);
//	}

	@GetMapping("/admin/colorpaginate/findall")
	public ResponseEntity<?> findAllColorByPaginated(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(colorService.getPaginatedfindAll(page, size));
	}

	@GetMapping("/admin/sizepaginate/findall")
	public ResponseEntity<?> findAllSizeByPaginated(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return ResponseEntity.ok(sizeService.getPaginatedfindAll(page, size));
	}

	@GetMapping("/admin/findcoloridsandnames")
	public ResponseEntity<?> findColorIdsAndNames() {
		return ResponseEntity.ok(colorService.findColorIdsAndNames());
	}

	@GetMapping("/admin/findsizeidsandnames")
	public ResponseEntity<?> findSizeIdsAndNames() {
		return ResponseEntity.ok(sizeService.findSizeIdsAndNames());
	}

	@DeleteMapping("/admin/color/deletebyid/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) {
		colorService.deleteById(id);
		return ResponseEntity.ok("Record deleted successfully");
	}

	@DeleteMapping("/admin/size/deletebyid/{id}")
	public ResponseEntity<String> deleteByIdSize(@PathVariable Long id) {
		sizeService.deleteById(id);
		return ResponseEntity.ok("Record deleted successfully");
	}
}
