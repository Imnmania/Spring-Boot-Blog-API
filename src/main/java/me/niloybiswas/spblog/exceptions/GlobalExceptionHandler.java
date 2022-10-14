package me.niloybiswas.spblog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import me.niloybiswas.spblog.payloads.ApiResponseDTO;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseDTO> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		
		String message = ex.getMessage();
		ApiResponseDTO apiResponseDTO = new ApiResponseDTO(message, false);
		return new ResponseEntity<ApiResponseDTO>(apiResponseDTO, HttpStatus.NOT_FOUND);
		
	}

}
