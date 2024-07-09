package com.example.demo.service;

import org.springframework.http.ResponseEntity;


import com.example.demo.entity.Category;

public interface CategoryService {

	ResponseEntity<?> saveCategory(Category category);

	ResponseEntity<?> getAllCategory();

	ResponseEntity<?> getCategoryById(Long id);

	ResponseEntity<?> updateCategory(Category category);

	ResponseEntity<?> deleteCategoryId(Long id);

}
