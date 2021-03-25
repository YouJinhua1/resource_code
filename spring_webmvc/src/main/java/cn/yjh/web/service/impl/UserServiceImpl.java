package cn.yjh.web.service.impl;

import cn.yjh.anotation.Service;
import cn.yjh.web.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public String sayHello(Integer id, String name) {
        return "hello " + id + " : " + name ;
    }
}
