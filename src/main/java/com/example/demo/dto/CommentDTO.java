package com.example.demo.dto;

import java.util.Date;

public class CommentDTO {
	private String comment;
	private Date commentedDate;
	private Long postId;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCommentedDate() {
		return commentedDate;
	}

	public void setCommentedDate(Date commentedDate) {
		this.commentedDate = commentedDate;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

}
