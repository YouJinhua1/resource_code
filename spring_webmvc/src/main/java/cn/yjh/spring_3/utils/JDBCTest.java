package cn.yjh.spring_3.utils;
import java.sql.*;

public class JDBCTest {


        public static Connection getConnection(){
            Connection conn=null;

            try {
                String url="jdbc:oracle:thin:@localhost:1521:orcl";
                String user="easyscan02";
                String password="root";

                Class.forName("oracle.jdbc.driver.OracleDriver");//加载数据驱动
                conn = DriverManager.getConnection(url, user, password);// 连接数据库

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("加载数据库驱动失败");
            }catch(Exception e){
                e.printStackTrace();
                System.out.println("连接数据库失败");
            }
            return conn;
        }
        public static void close(Connection conn, PreparedStatement ps, ResultSet rs){
            try {
                if(rs!=null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if(ps!=null){
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    public static void main(String[] args) throws SQLException {
        Connection connection = JDBCTest.getConnection();
        Statement statement = connection.createStatement();

        //4.执行语句
        ResultSet rs=statement.executeQuery("select * from es_doc_main");
        //5.处理结果
        while(rs.next()){
            System.out.print(rs.getObject(1)+"\t"+rs.getObject(2)+"\t"+rs.getObject(3)+"\t");
        }
        //6.释放资源,资源rs、st、conn的释放顺序与创建顺序相反
        rs.close();
        statement.close();
        connection.close();
    }

}
