package com.yangjunshuai.yang;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestWebContext {
    ApplicationContext context;
    @Before
    public void loadSpringContext(){
        context = new ClassPathXmlApplicationContext("/ApplicationContext.xml");
    }
    
    @Test
    public void test() {
    	context.getBean("appBean").toString();
//        AutoLoadBean autoLoadBean = (AutoLoadBean)context.getBean("AutoLoadBean");
//        Assert.assertNotNull( autoLoadBean );
//        autoLoadBean.showTest();
    }

}