package cn.yjh.spring_3.servlet;



import cn.yjh.spring_3.core.ApplicationContext;
import cn.yjh.spring_3.core.ConfigurationConstant;
import cn.yjh.spring_3.core.ContextLoader;
import cn.yjh.spring_3.core.IOCContainer;
import cn.yjh.spring_3.request.RequestMappingResolver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // 加载配置文件
        ContextLoader.loadConfig(config.getInitParameter(ConfigurationConstant.CONTEXT_CONFIG_LOCATION));
        // 初始化ioc容器
        IOCContainer.init();
        // 依赖注入
        IOCContainer.autowired();

        RequestMappingResolver.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.excute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.excute(req, resp);
    }

    protected void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //将当前线程中request和response对象存储到ThreadLocal中，以便在Controller类中使用
        ApplicationContext.setWebContext(request,response);
    }

}
