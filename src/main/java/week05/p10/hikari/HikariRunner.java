package week05.p10.hikari;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class HikariRunner implements ApplicationRunner {

    JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args)  {
        List<Map<String, Object>> query = jdbcTemplate.queryForList("select * from test");
        System.out.println(query);
    }
}
