package com.winchannel.extension.dozer;

import org.dozer.CustomConverter;

/**
 * 字符串枚举转换类
 * @author lidongbo
 * 2010-12-8
 */
public class StringEnumConverter implements CustomConverter {

	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		if (sourceFieldValue == null||"".equals(sourceFieldValue)) {
			return null;
	    }
		if(destinationClass.isEnum() 
				&& String.class.isAssignableFrom(sourceClass)){
			return string2Enum(destinationClass, (String)sourceFieldValue);
		}
		if(sourceClass.isEnum() 
				&& String.class.isAssignableFrom(destinationClass)){
			return enum2String((Enum<?>)sourceFieldValue);
		}
		return null;
	}

	/**字符串转枚举*/
	public static Enum<?> string2Enum(Class<?> destinationClass,String sourceFieldValue){
		Enum<?>[] enumers = (Enum[]) destinationClass.getEnumConstants();
		for (Enum<?> enumer : enumers) {
			if(enumer.name().equals(sourceFieldValue)){
				return enumer;
			}
		}
		return null;
	}
	/**枚举转字符串*/
	private String enum2String(Enum<?> sourceFieldValue){
		return sourceFieldValue.name();
	}
	
}
