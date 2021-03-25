package cn.yjh.controller;

import cn.yjh.pojo.User;
import cn.yjh.service.UserService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private static UserService userService;


    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Resource
    private BeanFactory beanFactory;
    @Resource
    private ApplicationContext ac;

    @RequestMapping("/login/{username}")
    public User login(@PathVariable("username") String username){
        Object userController = beanFactory.getBean("userController");
        userController = ac.getBean("userController");
        return userService.getUser(username);
    }
}
