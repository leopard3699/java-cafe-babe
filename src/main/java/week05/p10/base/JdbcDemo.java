package week05.p10.base;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StopWatch;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class JdbcDemo {

    private ThreadLocal<StopWatch> stopWatchThreadLocal = new ThreadLocal<>();


    private Connection connection;

    public void createConnection(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到pg驱动");
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:postgresql:postgres", "postgres", "pg1234");
        } catch (SQLException e) {
            System.out.println("数据库连接异常："+e.getSQLState()+","+e.getMessage());
            e.printStackTrace();
        }
    }
    /**
     * 删除
     * @param sql 给定的sql
     * @return
     * @throws SQLException
     */
    public boolean insertOrUpdate(String sql) throws SQLException {

        try{
            connection.createStatement().executeUpdate(sql);
            return true;
        }catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    /**
     * 根据输入的sql返回查询结果
     * @param sql 输入sql
     * @return 查询结果
     * @throws SQLException
     */
    public List<Map<String, Object>> query(String sql) throws SQLException {

        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        List<Map<String,Object>> result=new ArrayList<>();
        while(resultSet.next()){
            int columnSize=resultSet.getMetaData().getColumnCount();
            Map<String,Object> columnData=new HashMap<>();
            for (int i = 1; i <= columnSize; i++) {
                columnData.put(resultSet.getMetaData().getColumnName(i),resultSet.getObject(i));
            }
            result.add(columnData);
        }
        return result;
    }

    public static void main(String[] args) throws SQLException {

        JdbcDemo jdbcDemo=new JdbcDemo();
        StopWatch sw=new StopWatch("jdbc");

        sw.start("task0");
        jdbcDemo.createConnection();
        sw.stop();

        sw.start("task1");
        for (int i = 0; i < 10_000; i++) {
            jdbcDemo.insertOrUpdate("insert into TEST values(\'apollo\',50)");
        }
        List<Map<String, Object>> query = jdbcDemo.query("select * from test");
        System.out.println(query);
        sw.stop();

        sw.start("task2");
        jdbcDemo.insertOrUpdate("delete from TEST where name = \'apollo\'");
        query = jdbcDemo.query("select * from test");
        System.out.println(query);
        sw.stop();

        System.out.println(sw.prettyPrint());
    }
}
