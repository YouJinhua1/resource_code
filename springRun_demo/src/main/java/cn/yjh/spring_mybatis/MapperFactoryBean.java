package cn.yjh.spring_mybatis;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-18 21:00
 */

public class MapperFactoryBean<T> implements FactoryBean {

    private Class<T> mapperInterface;

    public MapperFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object getObject(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{mapperInterface},new MapperProxy());
    }

    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
    }
}
