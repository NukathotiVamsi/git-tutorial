package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/Category")
	public ResponseEntity<?> saveCategory(@RequestBody Category category) {
		return categoryService.saveCategory(category);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getByID(@PathVariable long id) {
		return categoryService.getCategoryById(id);
	}

	@GetMapping()
	public ResponseEntity<?> getAll(@RequestBody Category category) {
		return categoryService.getAllCategory();

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateCategory(@RequestBody Category category) {
		return categoryService.updateCategory(category);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteId(@PathVariable long id) {
		return categoryService.deleteCategoryId(id);
	}

}
