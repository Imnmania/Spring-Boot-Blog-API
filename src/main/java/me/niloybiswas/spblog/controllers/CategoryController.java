package me.niloybiswas.spblog.controllers;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.niloybiswas.spblog.payloads.ApiResponseDTO;
import me.niloybiswas.spblog.payloads.CategoryDTO;
import me.niloybiswas.spblog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	
	// create
	@PostMapping("/create")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		
		CategoryDTO newCategoryDTO = categoryService.createCategory(categoryDTO);
		return new ResponseEntity<CategoryDTO>(newCategoryDTO, HttpStatus.CREATED);
		
	}
	
	// update
	@PutMapping("/update/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable(name = "id") BigInteger categoryId) {
		
		CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
		return new ResponseEntity<CategoryDTO>(updatedCategoryDTO, HttpStatus.OK);
		
	}
	
	// delete
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponseDTO> deleteCategory(@PathVariable(name = "id") BigInteger categoryId){
		
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponseDTO>(new ApiResponseDTO("category deleted successfully!", true), HttpStatus.OK);
		
	}
	
	// get all
	@GetMapping("/getAll")
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		
		return new ResponseEntity<List<CategoryDTO>>(categoryService.getAllCategories(), HttpStatus.OK);
		
	}
	
	
	// get by id
	@GetMapping("/getById/{id}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable(name = "id") BigInteger categoryId){
		
		return new ResponseEntity<CategoryDTO>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
		
	}

}
