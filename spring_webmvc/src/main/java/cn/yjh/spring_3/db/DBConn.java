package cn.yjh.spring_3.db;

import cn.yjh.spring_3.db.annotation.Column;
import cn.yjh.spring_3.db.annotation.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 自定义的JDBC连接
 * @author: You Jinhua
 * @create: 2021-01-18 16:44
 */
public class DBConn {

    private static final Logger logger = LoggerFactory.getLogger(DBConn.class);
    private boolean isUse;
    private Connection conn;
    private DataSource dataSource;
    private SQLExcutor excutor;

    public DBConn(boolean isUse) {
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



    public <T> List<T> query(String sql, Class<T> clazz) throws SQLException {
        Statement statement = conn.createStatement();
        // 4. 执行语句
        ResultSet rs = statement.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        String name = rsmd.getColumnName(1);
        List<T> resultList = new ArrayList<>();
        // 5. 处理结果
        while (rs.next()) {
            T bean = null;
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

    public <T> int insertObject(T t) {
        Class<T> clazz = (Class<T>) t.getClass();
        String sql = null;
        try {
            sql = builSQL("insert", t, clazz);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            paramsPrepared(ps, t, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public <T> int updateObject(T t) throws IllegalAccessException {
        return 0;
    }

    private <T> void paramsPrepared(PreparedStatement ps, T t, Class<T> clazz) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        int index = 1;
        for (Field field : fields) {
            field.setAccessible(true);
            Object o = field.get(t);
            if (o == null) {
                continue;
            }
            Class<?> type = field.getType();
            FieldType fieldType = FieldType.getFiledType(type.getTypeName());
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                fieldType = column.type();
            }
            setFieldValue(ps, index, fieldType, o);
            index++;
        }

    }

    private void setFieldValue(PreparedStatement ps, int index, FieldType fieldType, Object o) throws SQLException {
        switch (fieldType) {
            case STRING:
                ps.setString(index, (String) o);
                break;
            case DOUBLE:
                ps.setDouble(index, (Double) o);
                break;
            case FLOAT:
                ps.setFloat(index, (Float) o);
                break;
            case INTEGER:
                ps.setInt(index, (Integer) o);
                break;
            case BOOLEAN:
                ps.setBoolean(index, (Boolean) o);
                break;
            case BYTE:
                ps.setByte(index, (Byte) o);
                break;
            case SHORT:
                ps.setShort(index, (Short) o);
                break;
            case DATE:
                ps.setDate(index, (java.sql.Date) o);
                break;
        }
    }


    public int delete(String sql) {
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
        Field[] fields = clazz.getDeclaredFields();
        E e = clazz.newInstance();
        for (Field field : fields) {
            String fieldName = field.getName();
            Class<?> type = field.getType();
            FieldType fieldType = FieldType.getFiledType(type.getTypeName());
            if (field.isAnnotationPresent(Column.class)) {
                Column anno = field.getAnnotation(Column.class);
                fieldName = anno.value();
                if (anno.type() != null) {
                    fieldType = anno.type();
                }
            }
            try {
                setBeanField(rs, e, field, fieldName, fieldType);
            } catch (Exception ex) {
                logger.info("设置字段：" + fieldName + " 失败", ex);
            }

        }
        return e;
    }

    private <E> void setBeanField(ResultSet rs, E e, Field field, String fieldName, FieldType fieldType) throws Exception {
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

    private <T> String builSQL(String sqlType, T t, Class<T> clazz) throws IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();
        String tableName = getTableName(clazz);
        StringBuilder sqlBuilder = new StringBuilder();
        if ("insert".equals(sqlType)) {
            StringBuilder tableFieldNameBuilder = new StringBuilder();
            StringBuilder tableFieldValueBuilder = new StringBuilder();
            for (Field field : fields) {
                field.setAccessible(true);
                Object o = field.get(t);
                if (o == null) {
                    continue;
                }
                String fieldName = field.getName();
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    fieldName = column.value();
                }
                tableFieldNameBuilder.append("," + fieldName);
                tableFieldValueBuilder.append(",?");

            }
            String tableFieldNames = tableFieldNameBuilder.toString().substring(1, tableFieldNameBuilder.length());
            String tableFieldValues = tableFieldValueBuilder.toString().substring(1, tableFieldValueBuilder.length());
            sqlBuilder.append("insert into " + tableName + " ( " + tableFieldNames + " ) values ( " + tableFieldValues + " )");
        } else {
            sqlBuilder.append("update " + tableName + " set ");
            for(Field field : fields){
                field.setAccessible(true);
                Object o = field.get(t);
                if(o == null){
                    continue;
                }
                String fieldName = field.getName();
                if(field.isAnnotationPresent(Column.class)){
                    Column column = field.getAnnotation(Column.class);
                    fieldName = column.value();
                    boolean pk = column.isPK();

                }
              /*  tableFieldNameBuilder.append(","+fieldName);
                tableFieldValueBuilder.append(",?");*/

            }
        }
        return sqlBuilder.toString();
    }

    private String getTableName(Class<?> clazz) {
        String tableName = clazz.getSimpleName();
        if (clazz.isAnnotationPresent(Table.class)) {
            Table anno = clazz.getAnnotation(Table.class);
            tableName = anno.value();
        }
        return tableName;
    }

    public static void main(String[] args) throws Exception {
        /*String sql = "SELECT t1.Table_Name as tableName,t1.Column_Name as columnName,t1.DATA_TYPE || '(' || t1.DATA_LENGTH || ')' as dataType,t2.Comments as Comments,t1.Data_Default  as defaultValue FROM cols t1 LEFT JOIN user_col_comments t2 ON t1.Table_name = t2.Table_name AND t1.Column_Name = t2.Column_Name WHERE t1.Table_Name = 'ES_DOC_MAIN'";
        String sql1 = "select * from es_doc_main";
        DBConn dbConn = DBConnPoolFactory.DB_CONN_POOL.getConnection();
        ES_DOC_MAIN es_doc_main = new ES_DOC_MAIN();
        es_doc_main.setDocCode("12332");
        es_doc_main.setDocFlag("true");
        dbConn.updateObject(es_doc_main);
        List<ES_DOC_MAIN> query = dbConn.query(sql1, ES_DOC_MAIN.class);
        System.out.println(query);*/
        System.out.println(Thread.currentThread().getName()+"=="+Thread.currentThread().getId());
        int i =2;
        i=i++;

        /*int a = i++;3
        System.out.println(a);3*/
        System.out.println(i);

       /* String sql1 = """
                update es_doc_main set doccode='G0071' 
                where docid='301'
                """;*/
        String sql2 = "update es_doc_main set numpages='6' where docid='301'";
        DBConn connection = DBConnPoolFactory.DB_CONN_POOL.getConnection();
       /* connection.testTransaction(sql1,sql2);*/


    }

    public int testTransaction(String sql1,String sql2) {
        System.out.println(Thread.currentThread().getName()+"=="+Thread.currentThread().getId());

        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        String callName=stack[2].getClassName();
        test4();
        int fetchRowNum = 0;
        try {
            // 关闭自动事务提交
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql1);
            fetchRowNum = ps.executeUpdate(sql1);

            PreparedStatement ps2 = conn.prepareStatement(sql2);
            fetchRowNum = ps2.executeUpdate(sql2);
            // 手动提交
            conn.rollback();
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

    private void test4() {

        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        System.out.println(Thread.currentThread().getName()+"=="+Thread.currentThread().getId());
        String callName=stack[2].getClassName();
    }


}
