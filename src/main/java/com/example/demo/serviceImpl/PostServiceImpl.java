package com.example.demo.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PostDTO;

import com.example.demo.dto.ResponseMessage;
import com.example.demo.entity.Category;

import com.example.demo.entity.Post;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CommentsRepository;
import com.example.demo.repository.PostRepository;

import com.example.demo.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@SuppressWarnings("unused")
	@Autowired
	private CommentsRepository commentRepository;

	@Override
	public ResponseEntity<?> savePost(PostDTO postDTO) {

		Optional<Post> existingTitle = postRepository.findByTitle(postDTO.getTitle());
		if (existingTitle.isPresent()) {
			ResponseMessage responseMessage = new ResponseMessage(
					"Post with title '" + postDTO.getTitle() + "' already exists");
			return ResponseEntity.ok(responseMessage);

		}

		Optional<Category> categoryOptional = categoryRepository.findById(postDTO.getCategoryId());
		if (!categoryOptional.isPresent()) {
			ResponseMessage responseMessage = new ResponseMessage(
					"Category With ID " + postDTO.getCategoryId() + " Not Found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
		}

		Post post = new Post();
		post.setTitle(postDTO.getTitle());
		post.setPostDescription(postDTO.getPostDescription());

		post.setCategory(categoryOptional.get());

		@SuppressWarnings("unused")
		Post savedPost = postRepository.save(post);
		ResponseMessage responseMessage = new ResponseMessage("Post Saved Successfully");
		return ResponseEntity.ok(responseMessage);

	}

	public ResponseEntity<?> getByID(Long id) {
		Optional<Post> postOptional = postRepository.findById(id);
		if (postOptional.isPresent()) {
			return ResponseEntity.ok(postOptional.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with id: " + id);
		}
	}
//	@Override
//	public ResponseEntity<?> getPostIdByComment(Long postId) {
//		List<Comment> comments = commentRepository.findByPostId(postId);
//
//		if (comments != null && !comments.isEmpty()) {
//			return new ResponseEntity<>(comments, HttpStatus.OK);
//		} else {
//			ResponseMessage responseMessage = new ResponseMessage("No Comments found for post with id:" + postId);
//			return ResponseEntity.ok(responseMessage);
//		}
//	}

	@Override
	public ResponseEntity<?> getAll() {
		List<Post> posts = postRepository.findAll();
		return ResponseEntity.ok(posts);
	}

	public ResponseEntity<?> getByID(long id) {
		Optional<Post> postOptional = postRepository.findById(id);
		if (postOptional.isPresent()) {
			return ResponseEntity.ok(postOptional.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with id: " + id);
		}
	}


	@Override
	public ResponseEntity<?> updatePost(Post post) {
		Optional<Post> existingPostOptional = postRepository.findById(post.getId());
		if (!existingPostOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with id: " + post.getId());
		}

		Post existingPost = existingPostOptional.get();
		existingPost.setTitle(post.getTitle());
		existingPost.setPostDescription(post.getPostDescription());
		existingPost.setCategory(post.getCategory());

		Post updatedPost = postRepository.save(existingPost);

		return ResponseEntity.ok(updatedPost);
	}

	@Override
	public ResponseEntity<?> deleteId(long id) {

		Optional<Post> postOptional = postRepository.findById(id);
		if (!postOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found with id: " + id);
		}

		postRepository.deleteById(id);

		return ResponseEntity.ok("Post deleted successfully");
	}

	@Override
	public List<Post> listAll() {

		return postRepository.findAll();
	}

}
