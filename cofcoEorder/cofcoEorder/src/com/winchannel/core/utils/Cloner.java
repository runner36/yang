package com.winchannel.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;

import com.winchannel.core.utils.SpringContext;

public class Cloner {

	private static final String BEAN_NAME = "cloner";

	/**
	 *  
	 * @param <T>                     目标对象类型
	 * @param sourceObject		                源对象
	 * @param destinationObjectClass  目标对象类
	 * @return                        目标对象
	 */
	public <T> T mapper(Object sourceObject,Class<T> destinationObjectClass){ 
		return getMapper().map(sourceObject, destinationObjectClass);
	}
	
	
	/***
	 * 把一个List内的所有对象转换为目标对象
	 * @param <FROM>
	 * @param <TO>
	 * @param sourceObjects            源对象
	 * @param destinationClass         目标类型
	 * @return
	 */
	public <From,To> List<To> deepClone(List<From> sourceObjects
			,Class<To> destinationClass){ 
		List<To> destinations=new ArrayList<To>();
		for (From from : sourceObjects) {
			destinations.add(mapper(from, destinationClass));
		}
		return destinations;
	}
	
	/***
	 * 克隆List内的所有对象
	 * @param sourceObjects            源对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> deepClone(List<T> sourceObjects){ 
		List<T> destinations=new ArrayList<T>();
		for (T from : sourceObjects) {
			destinations.add((T) mapper(from, from.getClass()));
		}
		return destinations;
	}
	
	public static Cloner getInstance(){
		return (Cloner) SpringContext.getBean(BEAN_NAME);
	}
	
	private Mapper mapper;
	
	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	private Mapper getMapper() {
		return mapper;
	}
	
}
