package com.example.demo.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


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

import com.example.demo.Excel.ExcelExporterr;
import com.example.demo.dto.PostDTO;

import com.example.demo.entity.Post;
import com.example.demo.repository.CommentsRepository;

import com.example.demo.service.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/posts")
//@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
public class PostController {

	@Autowired
	private PostService postService;
	@Autowired
	private CommentsRepository commentRepository;

	@PostMapping("/create")
	public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
		return postService.savePost(postDTO);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getByID(@PathVariable long id) {
		return postService.getByID(id);

	}

	@GetMapping()
	public ResponseEntity<?> getAll(@RequestBody PostDTO postdto) {
		return postService.getAll();

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> updatePost(@RequestBody Post post) {
		return postService.updatePost(post);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteId(@PathVariable long id) {
		return postService.deleteId(id);
	}

	@GetMapping("/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=post" + currentDateTime + ".csv";
		response.setHeader(headerKey, headerValue);

		List<Post> posts = postService.listAll();

		ExcelExporterr excelExporter = new ExcelExporterr(posts, commentRepository);

		excelExporter.export(response, "export");
	}

}