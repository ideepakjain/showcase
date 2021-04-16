package com.example.showcase.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

 
@AllArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {
	
	private String message;
	 
	 private List<T> dataList;
 
	 
 
}
