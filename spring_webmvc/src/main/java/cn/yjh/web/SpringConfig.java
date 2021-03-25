package cn.yjh.web;

import cn.yjh.anotation.Bean;
import cn.yjh.anotation.boot.Configuration;
import cn.yjh.anotation.boot.Value;

import java.util.concurrent.*;

@Configuration
public class SpringConfig {

    @Value(value="${spring.threadPool.corePoolSize}",key="")
    private int corePoolSize;

    @Value("${spring.threadPool.maxPoolSize}")
    private int maxPoolSize;

    @Value("${spring.threadPool.keepAliveSeconds}")
    private long keepAliveSeconds;

    @Value("${spring.threadPool.rejectedExecutionHandler}")
    private String rejectedExecutionHandler;


    @Bean("taskExecutor")
    public Executor getThreadPool(){
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveSeconds, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory());
    }
}
