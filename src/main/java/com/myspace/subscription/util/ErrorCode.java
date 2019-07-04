package com.myspace.subscription.util;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
	
	VALIDATION_ERROR("VALIDATION_ERROR", "",
			HttpStatus.BAD_REQUEST.value()),
	SERVERL_ERROR("SERVERL_ERROR",
			"Unable to process request",
			HttpStatus.INTERNAL_SERVER_ERROR.value()),
  	BUSINESS_ERROR("BUSINESS_ERROR","",590),
	DATA_ERROR("DATA_ERROR","",500);


	/** The error code. */
	private String errorCode;

	/** The error description. */
	private String errorDescription;

	/** The http status. */
	private int httpStatus;

	/**
	 * Gets the http status.
	 *
	 * @return the http status
	 */
	public int getHttpStatus() {
		return httpStatus;
	}

	/**
	 * Instantiates a new orion error code.
	 *
	 * @param errorCode
	 *            the error code
	 * @param errorDescription
	 *            the error description
	 * @param httpStatus
	 *            the http status
	 */
	ErrorCode(String errorCode, String errorDescription, int httpStatus) {
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
		this.httpStatus = httpStatus;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Gets the error description.
	 *
	 * @return the error description
	 */
	public String getErrorDescription() {
		return errorDescription;
	}


}
