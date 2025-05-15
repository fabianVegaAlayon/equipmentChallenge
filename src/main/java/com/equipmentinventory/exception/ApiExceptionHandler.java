package com.equipmentinventory.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.equipmentinventory.common.StandarizeApiExceptionResponse;



@RestControllerAdvice
public class ApiExceptionHandler {

	

	//definimos la clase de errores que manejará
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<?> handleUnknownHostException(NoSuchElementException ex){
		StandarizeApiExceptionResponse standarizeApiExceptionResponse = new StandarizeApiExceptionResponse("BUSINESS", "Validation Error", HttpStatus.NOT_FOUND.toString(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standarizeApiExceptionResponse);
	}

	
	
	
	
}
