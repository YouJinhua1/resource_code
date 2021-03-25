package cn.yjh.spring_3.prop;

import cn.yjh.spring_3.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public abstract class PropertyLoadFactory {

    private static final Logger logger = LoggerFactory.getLogger(PropertyLoadFactory.class);

    private void template(){

    }

    public static void loadConfig(String location){
        if(location.startsWith("classpath:")){
            location=location.replace("classpath:","");
        }

        InputStream is = PropertyLoadFactory.class.getClassLoader().getResourceAsStream(location);
        try {
            ApplicationContext.properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("读取配置文件失败..........");
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
