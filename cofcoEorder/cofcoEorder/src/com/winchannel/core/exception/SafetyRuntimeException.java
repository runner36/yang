package com.winchannel.core.exception;

public class SafetyRuntimeException extends BusinessException {
	
	public static final String EXCODE_PASSWORD_NO_CHANGE = "07";
	public static final String EXCODE_PASSWORD_LENGTH = "08";
	public static final String EXCODE_PASSWORD_LOWER = "09";
	public static final String EXCODE_PASSWORD_UPPER = "10";
	public static final String EXCODE_PASSWORD_DIGIT = "11";
	public static final String EXCODE_PASSWORD_SPECIAL = "12";
	
	
	public SafetyRuntimeException() {
	}

	public SafetyRuntimeException(String message) {
		super(message);
	}

	public SafetyRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SafetyRuntimeException(Throwable cause) {
		super(cause);
	}
	
	public SafetyRuntimeException(String bundle, String key, Object... values) {
		super(bundle, key, values);
	}
	
	public SafetyRuntimeException(Throwable cause, String bundle, String key, Object... values) {
		super(cause, bundle, key, values);
	}
	
}
