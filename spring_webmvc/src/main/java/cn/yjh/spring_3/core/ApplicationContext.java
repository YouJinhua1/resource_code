package cn.yjh.spring_3.core;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Properties;

/**
 * web上下文，通过上下文能够获取：
 *      request，response，session，servletContext
 */
public class ApplicationContext {

    // 保存全局配置文件中的配置信息
    public static Properties properties=new Properties();
    public static ThreadLocal<WebContext> webContext = new ThreadLocal<WebContext>();

    public static void setWebContext(HttpServletRequest request,HttpServletResponse response){
        webContext.set(new WebContext(request,response));
    }

    public static HttpServletRequest getRequest(){
        return webContext.get().request;
    }

    public static HttpServletResponse getResponse(){
        return webContext.get().response;
    }

    public static HttpSession getSession(){
        return webContext.get().request.getSession();
    }

    public static ServletContext getServletContext(){
        return webContext.get().request.getSession().getServletContext();
    }

    public static Properties getpropConfig(){
        return properties;
    }

    static class WebContext{
        private HttpServletRequest request;
        private HttpServletResponse response;

        public WebContext(HttpServletRequest request, HttpServletResponse response) {
            this.request = request;
            this.response = response;
        }
    }
}
