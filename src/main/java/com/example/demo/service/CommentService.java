package com.example.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.dto.CommentDTO;
import com.example.demo.entity.Comment;

public interface CommentService {
	ResponseEntity<?> createComments(CommentDTO commentDTO);

	ResponseEntity<?> getCommentsById(Long commentId);

	ResponseEntity<List<?>> getAllComments();

	ResponseEntity<?> updateComments(Comment comment);

	ResponseEntity<?> deleteComments(Long commentId);

	// List<Comment> getCommentsByPostId(Long postId);
}
