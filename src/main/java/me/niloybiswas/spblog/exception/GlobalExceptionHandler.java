package me.niloybiswas.spblog.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import me.niloybiswas.spblog.dto.common.ApiResponseDTO;

/**
 * This is a global exception handler
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseDTO> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		
		String message = ex.getMessage();
		ApiResponseDTO apiResponseDTO = new ApiResponseDTO(message, false);
		return new ResponseEntity<>(apiResponseDTO, HttpStatus.NOT_FOUND);
		
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		
		Map<String, String> res = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			res.put(fieldName, message);
		});
		
		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		
		Map<String, String> res = new HashMap<>();
		res.put("message", ex.getMessage().toString().split(":")[0].strip());
//		System.out.println(ex.getMessage());
		
		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		
		Map<String, String> res = new HashMap<>();
		res.put("message", "there is a mismatch in data type");
//		res.put("message", ex.getMessage().split(":")[0].strip());
		
		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		
	}
	

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Map<String, String>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
		
		Map<String, String> res = new HashMap<>();
//		res.put("message", "please check your payload data");
		res.put("message", ex.getMessage().split(":")[0].strip());
		
		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		
		Map<String, String> res = new HashMap<>();
//		res.put("message", "violated database constraint");
		res.put("message", "something is missing, violated db constraint");
//		res.put("message", ex.getMessage().split(":")[0].strip());
		
		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		
	}

	///* Query Parameter exception for wrong information
	//PropertyReferenceException
	@ExceptionHandler(PropertyReferenceException.class)
	public ResponseEntity<Map<String, String>> handlePropertyReferenceException(PropertyReferenceException ex) {
		Map<String, String> res = new HashMap<>();
		res.put("message", ex.getMessage().split(":")[0].strip());

		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Map<String, Object>> handleBadCredentialsException(BadCredentialsException ex) {
		Map<String, Object> res = new HashMap<>();
		res.put("message", ex.getMessage().split(":")[0].strip());

		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InternalAuthenticationServiceException.class)
	public ResponseEntity<Map<String, Object>> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException ex) {
		Map<String, Object> res = new HashMap<>();
		res.put("message", ex.getMessage().split(":")[0].strip());

		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
	}
}
