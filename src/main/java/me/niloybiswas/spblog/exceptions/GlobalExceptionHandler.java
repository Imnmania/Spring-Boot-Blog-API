package me.niloybiswas.spblog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import me.niloybiswas.spblog.payloads.ApiResponseDTO;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseDTO> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		
		String message = ex.getMessage();
		ApiResponseDTO apiResponseDTO = new ApiResponseDTO(message, false);
		return new ResponseEntity<ApiResponseDTO>(apiResponseDTO, HttpStatus.NOT_FOUND);
		
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		
		Map<String, String> res = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			res.put(fieldName, message);
		});
		
		return new ResponseEntity<Map<String, String>>(res, HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		
		Map<String, String> res = new HashMap<>();
		res.put("message", ex.getMessage().toString().split(":")[1].strip());
//		System.out.println(ex.getMessage());
		
		return new ResponseEntity<Map<String,String>>(res, HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		
		Map<String, String> res = new HashMap<>();
		res.put("message", "there is a mismatch in data type");
//		res.put("message", ex.getMessage().split(":")[0].strip());
		
		return new ResponseEntity<Map<String,String>>(res, HttpStatus.BAD_REQUEST);
		
	}
	

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String, String>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
		
		Map<String, String> res = new HashMap<>();
//		res.put("message", "there is a mismatch in data type");
		res.put("message", ex.getMessage().split(":")[0].strip());
		
		return new ResponseEntity<Map<String,String>>(res, HttpStatus.BAD_REQUEST);
		
	}
}
