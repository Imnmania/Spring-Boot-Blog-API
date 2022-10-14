package me.niloybiswas.spblog.services.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.niloybiswas.spblog.entities.Category;
import me.niloybiswas.spblog.exceptions.ResourceNotFoundException;
import me.niloybiswas.spblog.payloads.CategoryDTO;
import me.niloybiswas.spblog.repositories.CategoryRepo;
import me.niloybiswas.spblog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		
		Category category = modelMapper.map(categoryDTO, Category.class);
		Category newCategory = categoryRepo.save(category);
		return modelMapper.map(newCategory, CategoryDTO.class);
		
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, BigInteger categoryId) {
		
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));
		
		category.setCategoryTitle(categoryDTO.getCategoryTitle());
		category.setCategoryDescription(categoryDTO.getCategoryDescription());
		Category updatedCategory = categoryRepo.save(category);
		
		return modelMapper.map(updatedCategory, CategoryDTO.class);
		
	}

	@Override
	public void deleteCategory(BigInteger categoryId) {
		
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));
		categoryRepo.delete(category);
		
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		
		List<Category> categoryList = categoryRepo.findAll();
		
		List<CategoryDTO> categoryDTOList = categoryList.stream()
				.map(category -> modelMapper.map(category, CategoryDTO.class))
				.collect(Collectors.toList());
		
		return categoryDTOList;
		
	}

	@Override
	public CategoryDTO getCategoryById(BigInteger categoryId) {
		
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));
		
		return modelMapper.map(category, CategoryDTO.class);
		
	}

}
