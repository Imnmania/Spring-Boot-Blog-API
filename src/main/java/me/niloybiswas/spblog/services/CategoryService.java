package me.niloybiswas.spblog.services;

import java.math.BigInteger;
import java.util.List;
import me.niloybiswas.spblog.payloads.CategoryDTO;

public interface CategoryService {

	// Create
	CategoryDTO createCategory(CategoryDTO categoryDTO);
	
	// Update
	CategoryDTO updateCategory(CategoryDTO categoryDTO, BigInteger categoryId);
	
	// Delete
	void deleteCategory(BigInteger categoryId);
	
	// Get All
	List<CategoryDTO> getAllCategories();
	
	// Get by ID
	CategoryDTO getCategoryById(BigInteger categoryId);
	
}
