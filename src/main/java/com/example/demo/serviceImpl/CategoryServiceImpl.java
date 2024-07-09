package com.example.demo.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ResponseMessage;
import com.example.demo.entity.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public ResponseEntity<?> saveCategory(Category category) {
		Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
		if (existingCategory.isPresent()) {
			ResponseMessage responseMessage = new ResponseMessage("Category Saved Successfully");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
		}

		@SuppressWarnings("unused")
		Category savedCategory = categoryRepository.save(category);
		ResponseMessage responseMessage = new ResponseMessage("Category Saved Successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
	}

	@Override
	public ResponseEntity<?> getAllCategory() {
		List<Category> categories = categoryRepository.findAll();
		return ResponseEntity.ok(categories);
	}

	@Override
	public ResponseEntity<?> getCategoryById(Long id) {
		Optional<Category> category = categoryRepository.findById(id);
		if (category.isPresent()) {
			return ResponseEntity.ok(category.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found with id: " + id);
		}
	}

	@Override
	public ResponseEntity<?> updateCategory(Category category) {
		if (category.getId() == null) {
			return ResponseEntity.badRequest().body("Please provide id for updating category");
		}

		Optional<Category> existingCategory = categoryRepository.findById(category.getId());
		if (existingCategory.isPresent()) {
			categoryRepository.save(category);
			return ResponseEntity.ok("Category updated successfully");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found with id: " + category.getId());
		}
	}

	@Override
	public ResponseEntity<?> deleteCategoryId(Long id) {
		Optional<Category> categoryOptional = categoryRepository.findById(id);
		if (categoryOptional.isPresent()) {
			categoryRepository.deleteById(id);
			return ResponseEntity.ok("Category deleted successfully");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found with id: " + id);
		}
	}
}
