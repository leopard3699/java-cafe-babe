package week05.p08.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class School implements InitializingBean {

    public List<XClass> xClasses;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("school info:"+this);
    }
}
