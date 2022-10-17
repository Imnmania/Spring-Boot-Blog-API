package me.niloybiswas.spblog.service;

import java.math.BigInteger;
import java.util.List;

import me.niloybiswas.spblog.dto.CategoryDTO;

public interface CategoryService {

	// Create
	CategoryDTO createCategory(CategoryDTO categoryDTO);
	
	// Update
	CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
	
	// Delete
	void deleteCategory(Long categoryId);
	
	// Get All
	List<CategoryDTO> getAllCategories();
	
	// Get by ID
	CategoryDTO getCategoryById(Long categoryId);
	
}
