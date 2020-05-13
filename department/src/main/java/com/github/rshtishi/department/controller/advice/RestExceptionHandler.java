package com.github.rshtishi.department.controller.advice;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.rshtishi.department.dto.ErrorDetail;
import com.github.rshtishi.department.exception.ResourceNotFoundException;


@Order(Ordered.HIGHEST_PRECEDENCE) 
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetail> handleResourceNotFoundException(ResourceNotFoundException exception,
			HttpServletRequest request) {
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(Instant.now().getEpochSecond());
		errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
		errorDetail.setTitle("Resource Not Found");
		errorDetail.setDetail(exception.getMessage());
		errorDetail.setDeveloperMessage(exception.getClass().getName());
		return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
	}

//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<ErrorDetail> handleValidationError(MethodArgumentNotValidException exception,
//			HttpServletRequest request) {
//		ErrorDetail errorDetail = new ErrorDetail();
//		errorDetail.setTimeStamp(Instant.now().getEpochSecond());
//		errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
//		String requestPath = (String) request.getAttribute("javax.servlet.error.request_uri");
//		if (requestPath == null) {
//			requestPath = request.getRequestURI();
//		}
//		errorDetail.setTitle("Validation Failed");
//		errorDetail.setDetail("Input validation failed");
//		errorDetail.setDeveloperMessage(exception.getClass().getName());
//		LOGGER.info("Field: x ");
//		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
//		for(FieldError fe: fieldErrors) {
//			LOGGER.info("Field: xx ");
////			List<ValidationError> validationErrorList = errorDetail.getErrors().get(fe.getField());
////			if(validationErrorList==null) {
////				validationErrorList = new ArrayList<ValidationError>();
////				errorDetail.getErrors().put(fe.getField(), validationErrorList);
////			}
////			System.out.println(fe.getField()+" -> "+validationErrorList);
////			ValidationError validationError = new ValidationError();
////			validationError.setCode(fe.getCode());
////			validationError.setMessage(fe.getDefaultMessage());
////			validationErrorList.add(validationError);
////			System.out.println(fe.getField()+" -> "+validationError);
//			
//		}
//		return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
//	}

}
