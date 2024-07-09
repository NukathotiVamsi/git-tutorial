package com.example.demo.serviceImpl;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.ResponseMessage;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.repository.CommentsRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.CommentService;

@Service
public class CommentsServiceImpl implements CommentService {
	@Autowired
	private CommentsRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public ResponseEntity<?> createComments(CommentDTO commentDTO) {
		Optional<Post> postOptional = postRepository.findById(commentDTO.getPostId());
		if (postOptional.isPresent()) {
			Post post = postOptional.get();
			Comment comment = new Comment();
			comment.setPost(post);
			comment.setComment(commentDTO.getComment());
			LocalDate date = LocalDate.now();
			comment.setCommentedDate(date);

			commentRepository.save(comment);
			ResponseMessage responseMessage = new ResponseMessage("Comment Saved Successfully");
			return ResponseEntity.ok(responseMessage);
		} else {
			ResponseMessage responseMessage = new ResponseMessage("Post not found");
			return ResponseEntity.badRequest().body(responseMessage);
		}
	}

//	public List<Comment> getCommentsByPostId(Long postId) {
//		return commentRepository.findByPostId(postId);
//
//	}

	public ResponseEntity<?> getCommentsById(Long commentId) {
		Optional<Comment> optionalComment = commentRepository.findById(commentId);
		if (optionalComment.isPresent()) {
			return ResponseEntity.ok(optionalComment.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	public ResponseEntity<List<?>> getAllComments() {

		return new ResponseEntity<>(commentRepository.findAll(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> updateComments(Comment comment) {

		Comment existingComments = commentRepository.findById(comment.getId()).get();
		existingComments.setComment(comment.getComment());
		existingComments.setCommentedDate(comment.getCommentedDate());
		existingComments.setPost(comment.getPost());
		commentRepository.save(existingComments);
		return new ResponseEntity<>("updated successfully", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteComments(Long commentId) {

		commentRepository.deleteById(commentId);
		return new ResponseEntity<>("deleted successfully", HttpStatus.OK);
	}

}
