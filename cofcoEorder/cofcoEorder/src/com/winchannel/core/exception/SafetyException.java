package com.winchannel.core.exception;

public class SafetyException extends BaseException {
	
	public static final String EXCODE_USER_PASSWORD_ERROR = "01";
	public static final String EXCODE_USER_LOCKED = "02";
	public static final String EXCODE_LOGIN_NUM_LOCKED = "03";
	public static final String EXCODE_PASSWORD_EXPIRED = "04";
	public static final String EXCODE_FIRST_LOGIN = "05";
	public static final String EXCODE_NO_ROLE = "06";
	
	public SafetyException() {
	}

	public SafetyException(String message) {
		super(message);
	}

	public SafetyException(String message, Throwable cause) {
		super(message, cause);
	}

	public SafetyException(Throwable cause) {
		super(cause);
	}
	
	public SafetyException(String bundle, String key, Object... values) {
		super(bundle, key, values);
	}
	
	public SafetyException(Throwable cause, String bundle, String key, Object... values) {
		super(cause, bundle, key, values);
	}
	
}
