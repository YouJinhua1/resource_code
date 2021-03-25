package cn.yjh.annotation;

import cn.yjh.service.UserService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotatedElementUtils;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-09 11:17
 */
public class AnnoTest {
    public static void main(String[] args) {
        Class<UserService> classz = UserService.class;
        long l = System.currentTimeMillis();
        ComponentScan annotation = AnnotatedElementUtils.findMergedAnnotation(classz, ComponentScan.class);
        System.out.println("查找耗时："+(System.currentTimeMillis()-l)+ "ms,查找结果为："+annotation);

    }
}
