package week05.p02.code;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import week05.p02.auto.Person;
import week05.p02.auto.School;

public class CodeInjectionMain {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");

        Apple apple = context.getBean(Apple.class);
        System.out.println(apple);


    }
}
