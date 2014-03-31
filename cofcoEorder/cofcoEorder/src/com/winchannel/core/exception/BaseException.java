package com.winchannel.core.exception;

/**
 * 业务异常基类. 
 */
@SuppressWarnings("serial")
public class BaseException extends Exception {
	
	private String bundle;
	private String key;
	private Object[] values;
	private String exCode;

	public BaseException() {
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
	
	public BaseException(String bundle, String key, Object... values) {
		this.bundle = bundle;
		this.key = key;
		this.values = values;
	}
	
	public BaseException(Throwable cause, String bundle, String key, Object... values) {
		super(cause);
		this.bundle = bundle;
		this.key = key;
		this.values = values;
	}
	
	public String getBundle() {
		return bundle;
	}

	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}
	
	public String getExCode() {
		return exCode;
	}

	public void setExCode(String exCode) {
		this.exCode = exCode;
	}

}
