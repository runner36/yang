package com.winchannel.core.validator;

import com.winchannel.core.bean.DataField;

/**
 * @author xianghui
 *
 */
public class ValidatorFactory {
	
	public static DataValidator createValidator(DataField field) {
		DataValidator v = null;
		if ("char".equals(field.getType())) {
			v = new CharValidator(field);
		}
		else if ("date".equals(field.getType())) {
			v = new DateValidator(field);
		}
		else if ("number".equals(field.getType())) {
			v = new NumberValidator(field);
		}
		return v;
	}

}
