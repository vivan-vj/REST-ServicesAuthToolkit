package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseObj {
	private String status;
	private String errorMessage;
	private String message;
}
