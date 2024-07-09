package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Comment;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByPostId(Long postId);

	List<Comment> findTopLevelCommentsByPostId(Long postId);

}
