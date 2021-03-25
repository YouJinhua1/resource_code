package cn.yjh.anotation.boot;


/*import com.yjh.anotation.AliasFor;*/

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigurationProperties {

    /*@AliasFor("prefix")*/
    String value() default "";

    String prefix() default "";


    boolean ignoreInvalidFields() default false;

    boolean ignoreUnknownFields() default true;
}

