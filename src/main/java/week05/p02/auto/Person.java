package week05.p02.auto;


import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
@ToString
public class Person {



    private String name;

    @Autowired
    private School school;
}
