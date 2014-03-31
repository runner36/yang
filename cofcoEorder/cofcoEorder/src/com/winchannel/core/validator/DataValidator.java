package com.winchannel.core.validator;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import com.winchannel.core.bean.DataField;
import com.winchannel.core.utils.StringUtils;

/**
 * @author xianghui
 *
 */
public interface DataValidator {
	
	public Object validate(String value) throws Exception;
}

abstract class AbstractValidator implements DataValidator {
	
	protected String name;
	protected int length;
	protected boolean required;
	protected Pattern mask;
	protected String value;
	protected Object result;
	protected boolean valueIsBlank;

	public AbstractValidator(DataField field) {
		this.name = field.getTitle();
		this.length = field.getLength();
		this.required = field.isRequired();
		if (StringUtils.isNotBlank(field.getMask())) {
			this.mask = Pattern.compile(field.getMask());
		}
	}
	
	protected boolean validateRequired() {
		return !(required && valueIsBlank);
	}
	
	protected boolean validateLength() {
		return !(!valueIsBlank && value.getBytes().length > length);
	}
	
	protected boolean validateMask() {
		return !(!valueIsBlank && mask != null && !mask.matcher(value).matches());
	}
	
	public Object validate(String value) throws Exception {
		this.result = null;
		this.value = value;
		this.valueIsBlank = StringUtils.isBlank(value);
		
		if (!validateRequired()) {
			throw new Exception("Data Validate Failure : " + name + " is required");
		}
		if (!validateFormat()) {
			throw new Exception("Data validate failure : " + name + " is not a valid format");
		}
		if (!validateLength()) {
			throw new Exception("Data Validate Failure : " + name + " maximum length is " + length);
		}
		if (!validateMask()) {
			throw new Exception("Data Validate Failure : " + name + " Can not be a regular check");
		}
		return (result == null ? value : result);
	}
	
	protected abstract boolean validateFormat();
}

class DateValidator extends AbstractValidator {
	
	private SimpleDateFormat dateFormat;
	private static SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy.MM.dd");
	private static SimpleDateFormat dateFormat4 = new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat dateFormat5 = new SimpleDateFormat("yyyyMMdd");
	
	public DateValidator(DataField field) {
		super(field);
		if (StringUtils.isNotBlank(field.getFormat())) {
			dateFormat = new SimpleDateFormat(field.getFormat());
		}
	}
	
	protected boolean validateFormat() {
		if (!valueIsBlank) {
			try {
				result = dateFormat.parse(value);
				return true;
			}
			catch (Exception e) {
			}
			try {
				result = dateFormat1.parse(value);
				return true;
			}
			catch (Exception e) {
			}
			try {
				result = dateFormat2.parse(value);
				return true;
			}
			catch (Exception e) {
			}
			try {
				result = dateFormat3.parse(value);
				return true;
			}
			catch (Exception e) {
			}
			try {
				result = dateFormat4.parse(value);
				return true;
			}
			catch (Exception e) {
			}
			if (value.length() == 8) {
				try {
					result = dateFormat5.parse(value);
					return true;
				}
				catch (Exception e) {
				}
			}
			return false;
		}
		return true;
	}
}

class NumberValidator extends AbstractValidator {
	
	private DecimalFormat numberFormat;

	public NumberValidator(DataField field) {
		super(field);
		String format = field.getFormat();
		if (StringUtils.isBlank(format)) {
			format = "0.0";
		}
		numberFormat = new DecimalFormat(field.getFormat());
	}
	
	protected boolean validateFormat() {
		if (!valueIsBlank) {
			try {
				value = numberFormat.format(new Double(value));
				if (value.indexOf(".") != -1) {
					result = new Double(value.replace(",", ""));
				}
				else {
					result = new Long(value.replace(",", ""));
				}
				return true;
			}
				catch (Exception e) {
			}
			return false;
		}
		return true;
	}
	
}

class CharValidator extends AbstractValidator {
	
	public CharValidator(DataField field) {
		super(field);
	}
	
	protected boolean validateFormat() {
		return true;
	}
}
