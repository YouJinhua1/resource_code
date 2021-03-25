import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-03-16 11:27
 */
@Root(value = "com.yjh",basePackages = "base.yjh",a="aa",b={})
@RestController("/user")
public class AnnotationUtilsTest {
	public static void main(String[] args) {
		long l = System.currentTimeMillis();
		Root an = AnnotatedElementUtils.getMergedAnnotation(AnnotationUtilsTest.class, Root.class);
		System.out.println("查找耗时：" + (System.currentTimeMillis() - l) + "ms,查找结果为：" + an);
	}


}
