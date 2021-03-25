package cn.yjh.spring_mybatis;

import org.apache.ibatis.annotations.Select;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-18 21:04
 */
public class MapperProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if("query".equals(method.getName())){
            System.out.println(method.getAnnotation(Select.class).value()[0]);
        }

        return null;
    }
}
