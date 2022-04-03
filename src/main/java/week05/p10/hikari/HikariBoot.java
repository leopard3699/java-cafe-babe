package week05.p10.hikari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import week05.p08.config.AutoStarterBoot;

@SpringBootApplication
@ComponentScan("week05")
@Import(HikariConfiguration.class)
public class HikariBoot {

    public static void main(String[] args) {
        SpringApplication.run(AutoStarterBoot.class, args);
    }
}
