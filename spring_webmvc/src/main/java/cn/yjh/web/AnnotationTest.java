package cn.yjh.web;

import cn.yjh.anotation.boot.ConfigurationProperties;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class AnnotationTest {
    public static void main(String[] args) {
        Annotation[] annotations = RedisProperties.class.getAnnotations();
        for(Annotation an :annotations){
            System.out.println(an.annotationType()+"---"+an.toString());
        }
        Map<String,String> map = new HashMap<>();
    }
    @ConfigurationProperties(prefix = "spring",value = "123456")
    class RedisProperties{

    }

}
