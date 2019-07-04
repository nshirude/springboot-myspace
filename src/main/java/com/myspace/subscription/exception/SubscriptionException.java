package com.myspace.subscription.exception;

import com.myspace.subscription.util.ErrorCode;

public class SubscriptionException extends RuntimeException {

	private final String errorCode;

	private final String errorMessage;

	private final int httpStatus;
	
	public SubscriptionException(Throwable e, ErrorCode serverError) {
		this.errorCode = serverError.getErrorCode();
		this.errorMessage = serverError.getErrorDescription();
		this.httpStatus = serverError.getHttpStatus();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	private static final long serialVersionUID = 1L;



}
