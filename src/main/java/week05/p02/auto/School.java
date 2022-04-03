package week05.p02.auto;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
@ToString(exclude = {"person"})
public class School {

    private String name;

    @Autowired
    private Person person;

    @Value("week.school.location:china")
    private String location;

}
