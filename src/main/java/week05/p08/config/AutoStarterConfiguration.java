package week05.p08.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import week05.p08.model.School;
import week05.p08.model.Student;
import week05.p08.model.XClass;
import week05.p10.hikari.HikariConfiguration;

import java.util.List;

@Configuration
@Import(HikariConfiguration.class)
@ConditionalOnProperty(prefix = "school",value = "enabled",havingValue = "true")
@PropertySource("classpath:application.properties")
public class AutoStarterConfiguration {


    @Bean
    public School school(XClass xClass){
        return new School(List.of(xClass));
    }

    @Bean
    public XClass xClass(Student student){
        return new XClass(List.of(student));
    }

    @Bean
    public Student student(){
        return new Student();
    }
}
