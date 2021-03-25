package cn.yjh.spring_mybatis;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-18 21:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MapperBeanDefinitionRegistrar.class)
public @interface MapperScans {
    String[] value() default {};
}
