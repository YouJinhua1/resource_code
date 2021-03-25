package cn.yjh.spring_3.request;

import com.alibaba.fastjson.JSONObject;
import cn.yjh.anotation.Controller;
import cn.yjh.anotation.RequestMapping;
import cn.yjh.anotation.RequestParam;
import cn.yjh.anotation.ResponseBody;
import cn.yjh.spring_2.View;
import cn.yjh.spring_3.core.*;
import cn.yjh.web.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 处理器映射器
 */
public class RequestMappingResolver {
    private static final Logger logger= LoggerFactory.getLogger(RequestMappingResolver.class);
    public static void init(){
        Map<String, Object> ioc= IOCContainer.getContainer();
        for (Object value: ioc.values()) {
            Class<?> clazz = value.getClass();
            if (!clazz.isAnnotationPresent(Controller.class)) {
                continue;
            }
            String baseUrl = "";
            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping url = clazz.getAnnotation(RequestMapping.class);
                if (!url.value().equals("")) {
                    baseUrl += url.value();
                }
            }

            Method[] methods = clazz.getDeclaredMethods();
            for (Method method: methods) {
                if (!method.isAnnotationPresent(RequestMapping.class)) {
                    continue;
                }
                RequestMapping url = method.getAnnotation(RequestMapping.class);
                Map requestMap = RequestMap.getMapping();
                if(requestMap.containsKey(baseUrl + url.value())){
                    throw new RuntimeException("RequestMapping："+baseUrl + url.value()+"重复了！");
                }
                RequestHandler handler = new RequestHandler(value,method,baseUrl+url.value());
                requestMap.put(baseUrl + url.value(),handler);
            }
        }
    }

    public static void handlerRequest(){
        HttpServletRequest request=ApplicationContext.getRequest();
        HttpServletResponse response=ApplicationContext.getResponse();
        //获取请求url

        String lasturl = request.getRequestURI();
        lasturl = lasturl.replace(request.getContextPath(),"");
        //获取要使用的类
        RequestHandler handler = RequestMap.getHandler(lasturl);
        Method method = handler.getMethod();
        //绑定请求参数到具体方法
        Class<?>[] types = method.getParameterTypes();
        Object[] params = new Object[types.length];
        int index = 0;
        Annotation[][] annotations = method.getParameterAnnotations();
        for (Annotation[] an1: annotations) {
            for (Annotation an: an1) {
                if (an instanceof RequestParam) {
                    String paramName = ((RequestParam)an).value();
                    Class<?> type = types[index];
                    if (type == Integer.class)
                        params[index] = Integer.parseInt(request.getParameter(paramName));
                    else
                        params[index] = request.getParameter(paramName);
                }
                index++;
            }
        }
        try {
            Object o = method.invoke(handler.getController(),params);
            if(method.isAnnotationPresent(ResponseBody.class)){
                response.setContentType("application/json");
                response.setCharacterEncoding(ConfigurationConstant.CHARACTER_ENCODING);
                String result=JSONObject.toJSONString(o);
                response.getWriter().write(result);
            }else{
                if(o.toString().startsWith("redirect:")){
                    //使用重定向方式
                    response.sendRedirect(request.getContextPath() + o.toString());
                }else if(o.toString().startsWith("forward:")){
                    //使用转发方式
                    request.getRequestDispatcher(o.toString()).forward(request, response);
                }else{
                    //默认没指明跳转方式，也使用转发的方式
                    if(o instanceof View){
                        View view = (View) o;
                        if("forward".equals(view.getDispathAction())){
                            request.getRequestDispatcher(view.getUrl()).forward(request, response);
                        }else{
                            response.sendRedirect(request.getContextPath() + view.getUrl());
                        }
                    } else{
                        request.getRequestDispatcher(o.toString()).forward(request, response);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ServletException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Class<UserController> userControllerClass = UserController.class;
        Method[] declaredMethods = userControllerClass.getDeclaredMethods();
        for(Method method:declaredMethods){
            Annotation[] annotations = method.getAnnotations();
            System.out.println(annotations.length);
            for(Annotation an :annotations){
                System.out.println(an.toString());

            }

            if(method.isAnnotationPresent(ResponseBody.class)){
                System.out.println(method.getName() +": true");
            }else{
                System.out.println(method.getName() +": false");
            }

        }
    }
}
