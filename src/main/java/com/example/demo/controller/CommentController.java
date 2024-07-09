package com.example.demo.controller;

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

import com.example.demo.dto.CommentDTO;
import com.example.demo.entity.Comment;
import com.example.demo.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestBody CommentDTO comment) {
        return commentService.createComments(comment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable("id") Long commentId) {
        return commentService.getCommentsById(commentId);
    }

    @GetMapping
    public ResponseEntity<List<?>> getAllComments() {
        return commentService.getAllComments();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable("id") Long commentId, @RequestBody Comment comment) {
        return commentService.updateComments(comment);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long commentId) {
        return commentService.deleteComments(commentId);
    }
}
