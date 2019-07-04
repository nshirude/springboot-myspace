package com.myspace.subscription.exception;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.myspace.subscription.dto.SubscriptionError;
import com.myspace.subscription.util.Constants;
import com.myspace.subscription.util.ErrorCode;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


	@ResponseBody
	@ExceptionHandler(SubscriptionNotFoundException.class)	
	public ResponseEntity<SubscriptionError> handleSubscriptionNotFoundException(SubscriptionException ex) {
		logger.info("Inside Method handleSubscriptionException "+ex.getStackTrace());
		SubscriptionError error = new SubscriptionError();
		error.setCode(ex.getErrorCode());
		error.setMessage(ex.getLocalizedMessage());
		return new ResponseEntity<>(error,HttpStatus.valueOf(ex.getHttpStatus()));
	}
	
	@ResponseBody
	@ExceptionHandler(SubscriptionException.class)	
	public ResponseEntity<SubscriptionError> handleSubscriptionException(SubscriptionException ex) {
		logger.info("Inside Method handleSubscriptionException "+ex.getStackTrace());
		SubscriptionError error = new SubscriptionError();
		error.setCode(ex.getErrorCode());
		error.setMessage(ex.getErrorMessage());
		return new ResponseEntity<>(error,HttpStatus.valueOf(ex.getHttpStatus()));
	}
	
	
	@ResponseBody
	@ExceptionHandler(ConstraintViolationException.class)	
	public ResponseEntity<SubscriptionError> handleConstraintExcepiton(ConstraintViolationException ex) {
		logger.info("Inside Method handleConstraintExcepiton "+ex.getStackTrace());
		SubscriptionError error = new SubscriptionError();
		error.setCode(ErrorCode.VALIDATION_ERROR.getErrorCode());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	@ResponseBody
	@ExceptionHandler(Exception.class)	
	public ResponseEntity<SubscriptionError> handleGenericExceptionHandler(Exception ex) {
		logger.info("Inside Method handleGenericExceptionHandler "+ex.getStackTrace());
		SubscriptionError error = new SubscriptionError();
		error.setCode("");
		error.setMessage(ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info("Inside Method handleHttpMessageNotReadable "+ex.getStackTrace());
		JsonMappingException jme = (JsonMappingException) ex.getCause();
        SubscriptionError errorDetails = new SubscriptionError();
        errorDetails.setCode(Constants.FAILURE_MSG);
        errorDetails.setMessage(jme.getPath().get(0).getFieldName() + " invalid");
        return new ResponseEntity<>(errorDetails, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
    	logger.info("Inside Method handleHttpRequestMethodNotSupported "+ex.getStackTrace());
        StringBuilder builder = new StringBuilder();
        builder.append(". Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
        String supported = builder.toString();
        SubscriptionError errorDetails = new SubscriptionError();
        errorDetails.setCode(Constants.FAILURE_MSG);
        errorDetails.setMessage(ex.getLocalizedMessage() + supported);
        return new ResponseEntity<>(errorDetails, headers, status);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
    	logger.info("Inside Method handleMethodArgumentTypeMismatch "+ex.getStackTrace());
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        SubscriptionError errorDetails = new SubscriptionError();
        errorDetails.setCode(Constants.FAILURE_MSG);
        errorDetails.setMessage(error);
        return new ResponseEntity<>(errorDetails, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

	

}
