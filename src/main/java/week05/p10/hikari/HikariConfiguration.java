package week05.p10.hikari;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class HikariConfiguration {

    @Bean
    public DataSource dataSource() throws SQLException {
        HikariConfig config=new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(5);
        config.setUsername("postgres");
        config.setPassword("pg1234");
        HikariDataSource dataSource=new HikariDataSource(config);
        return dataSource;
    }

    @Bean
    public HikariRunner hikariRunner(JdbcTemplate jdbcTemplate){
        return new HikariRunner(jdbcTemplate);
    }
}
