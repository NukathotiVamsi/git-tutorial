package com.example.demo.dto;

public class PostDTO {
	private Long id;
	private String title;
	private String postDescription;
	private Long categoryId;
	private String createDate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPostDescription() {
		return postDescription;
	}

	public void setPostDescription(String postDescription) {
		this.postDescription = postDescription;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createdDate) {
		this.createDate = createdDate;
	}

}
