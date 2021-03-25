package cn.yjh.web.service;

import cn.yjh.anotation.Service;

@Service
public class LoginServiceImpl implements LoginService {
    int a = 0;

    @Override
    public boolean Login(String username, String password) {
        System.out.println("service层：" + username + " : " + password);
        return false;
    }
}
