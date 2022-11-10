package me.niloybiswas.spblog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.niloybiswas.spblog.dto.common.ApiResponseDTO;
import me.niloybiswas.spblog.dto.category.CategoryDTO;
import me.niloybiswas.spblog.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	// create
	@PostMapping("/create")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO newCategoryDTO = categoryService.createCategory(categoryDTO);
		return new ResponseEntity<>(newCategoryDTO, HttpStatus.CREATED);
	}
	
	// update
	@PutMapping("/update/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable(name = "id") Long categoryId) {
		CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
		return new ResponseEntity<>(updatedCategoryDTO, HttpStatus.OK);
	}
	
	// delete
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponseDTO> deleteCategory(@PathVariable(name = "id") Long categoryId){
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<>(new ApiResponseDTO("category deleted successfully!", true), HttpStatus.OK);
	}
	
	// get all
	@GetMapping("/getAll")
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
	}
	
	
	// get by id
	@GetMapping("/getById/{id}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable(name = "id") Long categoryId){
		return new ResponseEntity<>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
	}

}
