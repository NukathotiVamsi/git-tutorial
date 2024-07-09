package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class ResponseMessage {
	@SuppressWarnings("unused")
	private String message;

	public ResponseMessage(String message) {
		this.message = message;
	}
}
