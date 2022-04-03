package week05.p10.high;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.sql.*;
import java.util.*;

@Data
@NoArgsConstructor
public class JdbcHighDemo {

    private ThreadLocal<StopWatch> stopWatchThreadLocal = new ThreadLocal<>();


    private Connection connection;
    private PreparedStatement preparedStatement;

    public void createConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到pg驱动");
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:postgresql:postgres", "postgres", "pg1234");
        } catch (SQLException e) {
            System.out.println("数据库连接异常：" + e.getSQLState() + "," + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 删除
     *
     * @param sql 给定的sql
     * @return
     * @throws SQLException
     */
    public boolean insertOrUpdate(String sql) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * batch update
     *
     * @param sqlList sql清单
     * @return
     * @throws SQLException
     */
    public boolean batchUpdate(List<String> sqlList) throws SQLException {
        try {
            if (!CollectionUtils.isEmpty(sqlList)) {
                Statement statement = connection.createStatement();
                sqlList.forEach(sql -> {
                    try {
                        statement.addBatch(sql);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                statement.executeBatch();
                statement.clearBatch();
                return true;
            } else {
                return false;
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * batch update higer
     *
     * @param sql        待执行sql
     * @param valuesList sql清单
     * @return
     * @throws SQLException
     */
    public boolean batchUpdateHigher(String sql, List<String> valuesList) throws SQLException {
        try {
            if (!CollectionUtils.isEmpty(valuesList)) {
                preparedStatement = connection.prepareStatement(sql);
                valuesList.forEach(value -> {
                    try {
                        preparedStatement.setString(1, value);
                        preparedStatement.addBatch();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                preparedStatement.executeBatch();
                preparedStatement.clearBatch();
                return true;
            } else {
                return false;
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 根据输入的sql返回查询结果
     *
     * @param sql 输入sql
     * @return 查询结果
     * @throws SQLException
     */
    public List<Map<String, Object>> query(String sql) throws SQLException {
        preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Map<String, Object>> result = new ArrayList<>();
        while (resultSet.next()) {
            int columnSize = resultSet.getMetaData().getColumnCount();
            Map<String, Object> columnData = new HashMap<>();
            for (int i = 1; i <= columnSize; i++) {
                columnData.put(resultSet.getMetaData().getColumnName(i), resultSet.getObject(i));
            }
            result.add(columnData);
        }
        return result;
    }

    public static void main(String[] args) throws SQLException {

        JdbcHighDemo jdbcDemo = new JdbcHighDemo();
        StopWatch sw = new StopWatch("jdbc");

        sw.start("task0");
        jdbcDemo.createConnection();
        sw.stop();


        sw.start("task1");
        jdbcDemo.getConnection().setAutoCommit(false);
        for (int i = 0; i < 10_000; i++) {
            jdbcDemo.insertOrUpdate("insert into TEST values(\'apollo\',50)");
        }
        List<Map<String, Object>> query = jdbcDemo.query("select * from test");
        System.out.println(query);
        sw.stop();


        sw.start("task3");
        jdbcDemo.getConnection().setAutoCommit(false);
        List<String> sqlList = new ArrayList<>();
        for (int i = 0; i < 10_000; i++) {
            sqlList.add("insert into TEST values(\'apollo\',"+i+")");
        }
        jdbcDemo.batchUpdate(sqlList);
        jdbcDemo.getConnection().commit();
        sw.stop();

        sw.start("task4");
        jdbcDemo.getConnection().setAutoCommit(false);
        List<String> valueList = new ArrayList<>();
        for (int i = 0; i < 10_000; i++) {
            valueList.add("apollo");
        }
        jdbcDemo.batchUpdateHigher("insert into TEST values(?,50)", valueList);
        jdbcDemo.getConnection().commit();
        sw.stop();

        sw.start("task5");
        jdbcDemo.insertOrUpdate("delete from TEST where name = \'apollo\'");
        query = jdbcDemo.query("select * from test");
        System.out.println(query);
        jdbcDemo.getConnection().commit();
        sw.stop();

        System.out.println(sw.prettyPrint());
    }
}
