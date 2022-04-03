package week05.p02.xml;


import org.springframework.context.support.ClassPathXmlApplicationContext;


public class XMLInjectionMain {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");

        Bike bike = context.getBean(Bike.class);
        System.out.println(bike);

        Car car = context.getBean(Car.class);
        System.out.println(car);


    }
}
