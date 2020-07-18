package com.github.rshtishi.payroll.employee.rest.advice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.rshtishi.payroll.employee.entity.ErrorDetail;
import com.github.rshtishi.payroll.employee.entity.ValidationError;
import com.github.rshtishi.payroll.employee.exception.ResourceNotFoundException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetail> handleResourceNotFoundException(ResourceNotFoundException exception,
			HttpServletRequest request) {
		LOGGER.info("Entered in ResourceNotFoundException handler.");;
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(Instant.now().getEpochSecond());
		errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
		errorDetail.setTitle("Burimi nuk ekziston");
		errorDetail.setDetail("Departamenti nuk ekziston");
		errorDetail.setDeveloperMessage(exception.getClass().getName());
		return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetail> handleValidationError(MethodArgumentNotValidException exception,
			HttpServletRequest request){
		ErrorDetail errorDetail = new ErrorDetail();
		errorDetail.setTimeStamp(Instant.now().getEpochSecond());
		errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
		errorDetail.setTitle("Kontrolli deshtoi");
		errorDetail.setDetail("Kontrolli i dhenave hyrese deshtoi");
		errorDetail.setDeveloperMessage(exception.getClass().getName());
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		for(FieldError fe: fieldErrors) {
			List<ValidationError> validationErrorList = errorDetail.getErrors().get(fe.getField());
			if(validationErrorList==null) {
				validationErrorList = new ArrayList<ValidationError>();
				errorDetail.getErrors().put(fe.getField(), validationErrorList);
			}
			ValidationError validationError = new ValidationError();
			validationError.setCode(fe.getCode());
			validationError.setMessage(fe.getDefaultMessage());
			validationErrorList.add(validationError);	
		}
		return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);	
	}

}
