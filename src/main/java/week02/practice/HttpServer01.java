package week02.practice;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer01 {

    public static void main(String args[]) throws IOException{

        ServerSocket serverSocket=new ServerSocket(8081);
        while (true){
            try{
                Socket socket=serverSocket.accept();
                service(socket);
            }catch (IOException ex){

            }
        }

    }

    private static void service(Socket socket){
        try{
            PrintWriter printWriter=new PrintWriter(socket.getOutputStream(),true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body="hello nio1";
            printWriter.println("Conteng-Length:"+body.getBytes().length);
            printWriter.println();
            printWriter.println(body);
            printWriter.close();
            socket.close();

        }catch (IOException ex){

        }

    }
}
