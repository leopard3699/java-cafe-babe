package week05.p08.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class XClass {

    public List<Student> students;
}
