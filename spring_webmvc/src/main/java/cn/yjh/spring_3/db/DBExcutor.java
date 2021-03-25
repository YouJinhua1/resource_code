package cn.yjh.spring_3.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: JDBC基本操作接口
 * @author: You Jinhua
 * @create: 2021-01-21 10:37
 */
public interface DBExcutor {

    /*default <E> List<E> query(String sql, Class<E> clazz) throws SQLException {
        Statement statement = conn.createStatement();
        // 4. 执行语句
        ResultSet rs = statement.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        String name = rsmd.getColumnName(1);
        List<E> resultList = new ArrayList<>();
        // 5. 处理结果
        while (rs.next()) {
            E bean = null;
            try {
                bean = convertBean(rs, clazz);
                resultList.add(bean);
            } catch (Exception e) {
                logger.error("实体转换异常：", e);
            }
        }
        rs.close();
        statement.close();
        isUse = false;
        return resultList;
    }*/
}
