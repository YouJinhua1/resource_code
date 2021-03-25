package cn.yjh.spring_3.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: ±íÓ³Éä×¢½â
 * @author: You Jinhua
 * @create: 2021-01-21 09:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Table {
    String value() default "";
}
