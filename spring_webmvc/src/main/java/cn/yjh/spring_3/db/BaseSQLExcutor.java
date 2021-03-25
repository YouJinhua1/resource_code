package cn.yjh.spring_3.db;

import cn.yjh.spring_3.db.annotation.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: JDBC基本操作类
 * @author: You Jinhua
 * @create: 2021-01-21 10:35
 */
public class BaseSQLExcutor implements SQLExcutor{

    private static final Logger logger = LoggerFactory.getLogger(DBConn.class);
    private boolean isUse;
    private Connection conn;
    private DataSource dataSource;

    public BaseSQLExcutor(boolean isUse) {
        this.isUse = isUse;
        try {
            String url = "jdbc:oracle:thin:@123.207.138.89:1521:XE";
            String user = "ccbimage";
            String password = "ccbimage";
            Class.forName("oracle.jdbc.driver.OracleDriver");//加载数据驱动
            conn = DriverManager.getConnection(url, user, password);// 连接数据库
        } catch (ClassNotFoundException e) {
            logger.error("加载数据库驱动失败{}", e);
        } catch (Exception e) {
            logger.error("连接数据库失败{}", e);
        }
    }

    public boolean getIsUse() {
        return isUse;
    }

    public void setIsUse(boolean use) {
        isUse = use;
    }

    public List<List<String>> excuteSQL(String sql) throws SQLException {
        Statement statement = conn.createStatement();
        // 4. 执行语句
        ResultSet rs = statement.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        String name = rsmd.getColumnName(1);
        List<List<String>> result = new ArrayList<>();
        // 5. 处理结果
        while (rs.next()) {
            List<String> row = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                row.add(rs.getString(i));
            }

            result.add(row);
        }
        //6.释放资源,资源rs、st、conn的释放顺序与创建顺序相反
        rs.close();
        statement.close();
        isUse = false;
        return result;
    }

    /*public <E>  List<Map<String,String>> excuteSQL(String sql,Class<E> clazz) throws SQLException {
        Statement statement = conn.createStatement();
        // 4. 执行语句
        ResultSet rs=statement.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        String name = rsmd.getColumnName(1);
        List<Map<String,String>> result= new ArrayList<>();
        // 5. 处理结果
        while(rs.next()){
            Map<String,String> row = new HashMap<>();
            for(int i=1;i<=count;i++){
                System.out.println(rs.getString("tableName"));
                System.out.println(rs.getString("TABLENAME"));
                System.out.println(rs.getString("tablename"));
                row.put(rsmd.getColumnName(i),rs.getString(i));
            }

            result.add(row);
        }
        //6.释放资源,资源rs、st、conn的释放顺序与创建顺序相反
        rs.close();
        statement.close();
        isUse = false;
        return result;
    }*/

    public <E> List<E> query(String sql, Class<E> clazz) throws SQLException {
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
    }

    public <E> List<E> insert(String sql, Class<E> clazz) throws SQLException {
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
    }

    public  int updateObject(String sql) {
        Statement statement = null;
        try {
            statement = conn.prepareStatement(sql);
            // 4. 执行语句
            ResultSet rs = null;
            try {
                rs = statement.executeQuery(sql);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            rs.close();
            statement.close();
            isUse = false;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public  int delete(String sql) {
        int fetchRowNum = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            // 关闭自动事务提交
            conn.setAutoCommit(false);
            fetchRowNum = ps.executeUpdate(sql);
            // 手动提交
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return fetchRowNum;
    }

    private <E> E convertBean(ResultSet rs, Class<E> clazz) throws Exception {
        java.lang.reflect.Field[] fields = clazz.getDeclaredFields();
        E e = clazz.newInstance();
        for (java.lang.reflect.Field field : fields) {
            String fieldName = field.getName();
            Class<?> type = field.getType();
            FieldType fieldType = FieldType.getFiledType(type.getTypeName());
            if (field.isAnnotationPresent(Column.class)) {
                Column anno = field.getAnnotation(Column.class);
                fieldName = anno.value();
                fieldType = anno.type();
            }
            try{
                setBeanFiled(rs, e, field, fieldName, fieldType);
            }catch (Exception ex){
                logger.info("设置字段："+fieldName+" 失败",ex);
            }

        }
        return e;
    }

    private <E> void setBeanFiled(ResultSet rs, E e, Field field, String fieldName, FieldType fieldType) throws Exception {
        field.setAccessible(true);
        switch (fieldType) {
            case STRING:
                field.set(e, rs.getString(fieldName));
                break;
            case INTEGER:
                field.set(e, rs.getInt(fieldName));
                break;
            case DOUBLE:
                field.set(e, rs.getDouble(fieldName));
                break;
            case FLOAT:
                field.set(e, rs.getFloat(fieldName));
                break;
            case BOOLEAN:
                field.set(e, rs.getBoolean(fieldName));
                break;
            case LONG:
                field.set(e, rs.getLong(fieldName));
                break;
            case BYTE:
                field.set(e, rs.getByte(fieldName));
                break;
            case SHORT:
                field.set(e, rs.getShort(fieldName));
                break;
            case DATE:
                field.set(e, rs.getDate(fieldName));
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        String sql = "SELECT t1.Table_Name as tableName,t1.Column_Name as columnName,t1.DATA_TYPE || '(' || t1.DATA_LENGTH || ')' as dataType,t2.Comments as Comments,t1.Data_Default  as defaultValue FROM cols t1 LEFT JOIN user_col_comments t2 ON t1.Table_name = t2.Table_name AND t1.Column_Name = t2.Column_Name WHERE t1.Table_Name = 'ES_DOC_MAIN'";
        String sql1 = "delete from es_doc_main_p where doccode in ('G123','999999')";
        DBConn dbConn = DBConnPoolFactory.DB_CONN_POOL.getConnection();
        dbConn.delete(sql1);

    }

}
