package week05.p02.code;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanInjectionConfiguration {

    @Bean
    public Apple apple(){
        return new Apple("12","fuzhou");
    }
}
