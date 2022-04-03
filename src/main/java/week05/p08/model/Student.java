package week05.p08.model;


import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ToString
@ConfigurationProperties(prefix = "student")
public class Student implements InitializingBean {
    private Integer id;
    private String name;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("student info:"+this);
    }
}
