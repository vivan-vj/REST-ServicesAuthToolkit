package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestObj {
	private Integer id;
	private String name;
	private String department;
}
