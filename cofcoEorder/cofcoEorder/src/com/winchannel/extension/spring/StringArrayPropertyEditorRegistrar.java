package com.winchannel.extension.spring;

import java.beans.PropertyEditor;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
/**
 * 实现Spting提供的接口实现类
 * 解析字符串成String数组注册器
 * @author lidongbo
 * */
public class StringArrayPropertyEditorRegistrar  implements PropertyEditorRegistrar{
	
	private PropertyEditor stringArrayPropertyEditor;

    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(String[].class, getStringArrayPropertyEditor());
    }

	public PropertyEditor getStringArrayPropertyEditor() {
		return stringArrayPropertyEditor;
	}

	public void setStringArrayPropertyEditor(
			PropertyEditor stringArrayPropertyEditor) {
		this.stringArrayPropertyEditor = stringArrayPropertyEditor;
	}

}
