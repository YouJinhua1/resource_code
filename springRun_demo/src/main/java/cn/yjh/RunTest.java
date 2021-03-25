package cn.yjh;

import cn.yjh.bean.User;
import cn.yjh.config.ApplicationConfig;
import cn.yjh.dao.UserMapper;
import cn.yjh.service.Root;
import cn.yjh.spring_mybatis.MapperFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;

@Lazy@MapperScan
public class RunTest {

    @MyService(value = "ni")
    class test{

    }
    public static void main(String[] args) throws Exception {
        //runByAnnotationApplicationContext();

        //runByXmlApplicationContext();

        //createBeanByBeanFactory();

        Annotation annotation = AnnotatedElementUtils.findMergedAnnotation(test.class, Component.class);
        System.out.println(annotation);

//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("cn.yjh");
//        String[] beanDefinition = applicationContext.getBeanDefinitionNames();
//        UserMapper userMapper = applicationContext.getBean(UserMapper.class);
//        userMapper.query();
       // MapperFactoryBean bean = applicationContext.getBean(MapperFactoryBean.class);
//        bean.getObject();
//        bean.

    }

    private static void createBeanByBeanFactory() {

        // 1. 直接注册一个 bean
/*
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        beanFactory.registerSingleton("user",new User());
*/

        // 2. 直接注册一个 bean
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        //AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        AbstractBeanDefinition beanDefinition = new GenericBeanDefinition();

        beanDefinition.setBeanClass(User.class);

        beanDefinition.setScope("prototype");

        beanFactory.registerBeanDefinition("user",beanDefinition);


        User user = beanFactory.getBean("user", User.class);
        System.out.println(user);

    }

    public static void runByAnnotationApplicationContext(){
        // 1. 配置扫描的包路径
/*
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.yjh");
*/

        // 2. 指定配置类：ApplicationConfig 并在配置类上指定扫描的包路径
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        // 3. 直接手动注册 bean 到容器中
/*
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.registerBean(User.class);

        applicationContext.refresh();

*/

        // 4. 通过 BeanDefinition 手动注册 bean 到容器中
       /* AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();

        beanDefinition.setBeanClass(ApplicationConfig.class);

        applicationContext.registerBeanDefinition("applicationConfig",beanDefinition);

        applicationContext.refresh();*/


        User user = applicationContext.getBean("user", User.class);
        System.out.println(user);
    }

    public static void runByXmlApplicationContext(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
        User user = applicationContext.getBean("user", User.class);
        System.out.println(user);
    }
}
