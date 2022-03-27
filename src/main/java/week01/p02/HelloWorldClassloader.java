package week01.p02;

import java.io.*;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HelloWorldClassloader extends ClassLoader {

    public static void main(String args[]) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

        HelloWorldClassloader helloWorldClassloader=new HelloWorldClassloader();
        Class<?> hello = helloWorldClassloader.findClass("Hello");
        Object object=hello.getDeclaredConstructor().newInstance();
        Method method=hello.getMethod("hello");
        method.invoke(object);
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException{

        byte[] bytes =getFromFile();
        return defineClass(name,bytes,0,bytes.length);

    }

    /**
     * 以字节方式从文件解析内容，并进行反向解析255-x
     * @return class原始byte
     */
    public byte[] getFromFile(){

        File file=new File("src/main/java/week01/p02/Hello.xlass");

        InputStream in;
        List<Integer> fileContent= new ArrayList<>();
        int tempType=0;
        try{
            in = new FileInputStream(file);
            while((tempType=in.read())!=-1){
                fileContent.add(255-tempType);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] result = new byte[fileContent.size()];
        for (int i = 0; i < fileContent.size(); i++) {
            result[i] = fileContent.get(i).byteValue();
        }

        return result;
    }
}
