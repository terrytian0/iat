package com.terry.iat.dao.common;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author houyin.tian
 */
public class DataSourceExchange implements MethodBeforeAdvice, AfterReturningAdvice,BeforeAdvice{



    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        //这里DataSource是自定义的注解，不是java里的DataSource接口
        if (method.isAnnotationPresent(DataSource.class)) {
            DataSource datasource = method.getAnnotation(DataSource.class);
            DataSourceContextHolder.setDataSourceType(datasource.value());
        } else {
            Class[] classes =  target.getClass().getInterfaces();
            for(Class cls :classes){
                for(Annotation annotation:cls.getAnnotations()){
                    if(annotation instanceof DataSource){
                        String dbsourceName=annotation.annotationType().getMethod("value").invoke(annotation).toString();
                        DataSourceContextHolder.setDataSourceType(dbsourceName);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        DataSourceContextHolder.clearDataSourceType();
    }
}