package cn.yjh.spring_3.db.annotation;

import cn.yjh.spring_3.db.FieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:  Ù–‘”≥…‰◊¢Ω‚
 * @author: You Jinhua
 * @create: 2021-01-19 21:33
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Column {
    String value() default "";
    boolean isPK() default false;
    FieldType type();
}
