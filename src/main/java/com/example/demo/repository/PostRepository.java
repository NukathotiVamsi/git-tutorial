package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	Optional<Post> findByTitle(String title);

	void save(List<Post> postDTOList);

}