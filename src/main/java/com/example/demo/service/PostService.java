package com.example.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.dto.PostDTO;

import com.example.demo.entity.Post;

public interface PostService {
	ResponseEntity<?> savePost(PostDTO postdto);

	ResponseEntity<?> getAll();

	ResponseEntity<?> getByID(long id);

	ResponseEntity<?> updatePost(Post post);

	ResponseEntity<?> deleteId(long id);

	List<Post> listAll();

	// ResponseEntity<?> getCommentsById(Long commentId);;

	//public ResponseEntity<?> getPostIdByComment(Long postId);

	//public ResponseEntity<?> getPostAndComments(Long postId);
}
