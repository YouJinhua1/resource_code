package cn.yjh.service;

import cn.yjh.annotation.MyComponentScan;
import cn.yjh.controller.UserController;
import cn.yjh.pojo.User;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
//@MyComponentScan(value = "com.yy",basePackages = "com.yy",basePackageClasses={UserController.class},basePackageClassesw={User.class})
public class UserService {
    @Resource
    private UserController userController;
    public User getUser(String username) {
        return new User(username,18,"男");
    }
}
