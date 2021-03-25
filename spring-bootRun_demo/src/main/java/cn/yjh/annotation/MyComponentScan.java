package cn.yjh.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-09 11:13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ComponentScan
public @interface MyComponentScan {

    @AliasFor(value = "basePackages",annotation = ComponentScan.class)
    String[] value() default {};

    @AliasFor(value = "value",annotation = ComponentScan.class)
    String[] basePackages() default {};

    @AliasFor(annotation = ComponentScan.class)
    Class<?>[] basePackageClasses() default {};

    @AliasFor(value = "basePackageClasses",annotation = ComponentScan.class)
    Class<?>[] basePackageClassesw() default {};
}
