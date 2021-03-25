package cn.yjh.spring_3.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @description: 数据源
 * @author: You Jinhua
 * @create: 2021-01-18 19:19
 */
public class DataSource {

    private static final Logger logger= LoggerFactory.getLogger(DataSource.class);
    private String DB_TYPE;
    private String DB_NAME;
    private String HOST;
    private String PORT;
    private String USERNAME;
    private String PASSWORD;

    DataSource(String dataSourcePath){
        InputStream in = null;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(dataSourcePath);
            Properties prop = new Properties();
            prop.load(in);
            DB_TYPE = prop.getProperty("datasource.dbtype");
            HOST = prop.getProperty("datasource.host");
            PORT = prop.getProperty("datasource.port");
            DB_NAME = prop.getProperty("datasource.dbname");
            USERNAME = prop.getProperty("datasource.username");
            PASSWORD = prop.getProperty("datasource.password");
        } catch (Exception e) {
            logger.error("程序异常：",e);
        }finally {
            try {
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                logger.error("程序异常：",e);
            }
        }
    }

    public String getDB_TYPE() {
        return DB_TYPE;
    }

    public void setDB_TYPE(String DB_TYPE) {
        this.DB_TYPE = DB_TYPE;
    }

    public String getDB_NAME() {
        return DB_NAME;
    }

    public void setDB_NAME(String DB_NAME) {
        this.DB_NAME = DB_NAME;
    }

    public String getHOST() {
        return HOST;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    public String getPORT() {
        return PORT;
    }

    public void setPORT(String PORT) {
        this.PORT = PORT;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
}
