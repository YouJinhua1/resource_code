import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-03-19 12:29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Root_2
public @interface Root_1 {

    @AliasFor(annotation = Root_2.class)
    String[] value() default {};

    //@AliasFor(value = "value",annotation = Root_2.class)
    String[] basePackages() default {};

}
