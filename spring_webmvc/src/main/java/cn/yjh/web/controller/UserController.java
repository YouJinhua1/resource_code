package cn.yjh.web.controller;

import com.alibaba.fastjson.JSONObject;
import cn.yjh.anotation.*;
import cn.yjh.web.service.UserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    // http://localhost:8080/spring_web/user/hello.do?id=1&name=zhangsan
    @ResponseBody
    @RequestMapping("/hello.do")
    public String hello(@RequestParam("id") Integer id,
                        @RequestParam("name") String name) throws IOException {
        return service.sayHello(id,name);
    }

    @RequestMapping("/hello2.do")
    public Object hello2(@RequestParam("id") Integer id,
                         @RequestParam("name") String name) throws IOException {
        Map<String,Object> datas = new HashMap<>();
        datas.put("id",id);
        datas.put("name",name);
        return JSONObject.toJSONString(datas);
    }
}
