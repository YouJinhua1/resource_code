package cn.yjh.spring_3.db;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @description: JDBC连接池
 * @author: You Jinhua
 * @create: 2021-01-18 13:32
 */
public enum DBConnPoolFactory {

    DB_CONN_POOL;
    private static final Logger logger= LoggerFactory.getLogger(DBConnPoolFactory.class);
    private List<DBConn> POOL;
    private ThreadLocal<DBConn> threadLocal = new ThreadLocal<>();
    private static int maxSize;
    private static int minSize;
    private static int keepAlive;
    static{
        InputStream in = null;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
            Properties prop = new Properties();
            prop.load(in);
            maxSize = Integer.parseInt(prop.getProperty("connection.pool.maxSize"));
            minSize = Integer.parseInt(prop.getProperty("connection.pool.minSize"));
            keepAlive = Integer.parseInt(prop.getProperty("connection.pool.keepAlive"));
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

    private void initDBConnPool(){
        logger.info("============初始化连接池，初始连接数为：" + minSize + " ============");
        POOL = new ArrayList<>();
        for(int i = 0 ; i < minSize ; i++){
            DBConn dbConn = createDBConnection(false);
            POOL.add(dbConn);
        }
        logger.info("============连接池初始化结束，当前连接数：" + minSize + " ============");
    }

    public synchronized DBConn getConnection(){
        DBConn dbConn = null;
        if(POOL != null){
            dbConn = threadLocal.get();
            if(dbConn == null){
                for(DBConn dbConn1 : POOL){
                    if(!dbConn1.getIsUse()){
                        dbConn1.setIsUse(true);
                        threadLocal.set(dbConn1);
                        return  dbConn1;
                    }
                }
                if(POOL.size() < maxSize){
                    dbConn = createDBConnection(true);
                    POOL.add(dbConn);
                    threadLocal.set(dbConn);
                    return dbConn;
                }else{
                    return null;
                }
            }else{
                return dbConn;
            }
        }else{
            initDBConnPool();
            dbConn = POOL.get(0);
            dbConn.setIsUse(true);
            threadLocal.set(dbConn);
            return  dbConn;
        }
    }

    private DBConn createDBConnection(boolean isUse){
        logger.info("----------------创建连接----------------");
        DBConn dbConn = new DBConn(isUse);
        return dbConn;
    }


}
