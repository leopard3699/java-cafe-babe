package week05.p02.auto;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AutoInjectionMain {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");

        School school = context.getBean(School.class);
        school.setName("清华");

        System.out.println(school);
        System.out.println(school.getPerson());

        Person person = context.getBean(Person.class);
        person.setName("张三");

        System.out.println(person);
        System.out.println(school.getPerson());


    }
}
