package com.winchannel.core.service;

import com.winchannel.core.exception.BaseException;

@SuppressWarnings("serial")
public class ServiceException extends BaseException {
	
	public ServiceException() {
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
	
	public ServiceException(String bundle, String key, Object... values) {
		super(bundle, key, values);
	}
	
	public ServiceException(Throwable cause, String bundle, String key, Object... values) {
		super(cause, bundle, key, values);
	}

}
