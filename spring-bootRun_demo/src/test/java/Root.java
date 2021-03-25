import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-03-19 12:26
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Root_1
public @interface  Root {

    @AliasFor(annotation = Root_1.class)
    String[] value() default {};

    @AliasFor(annotation = Root_1.class)
    String[] basePackages() default {};

    @AliasFor("b")
    String[] a() default {};


    String[] b() default {};


    String c() default "";

}
