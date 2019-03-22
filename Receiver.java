/**
 * NAME: SHAYNE MUJURU
 * UCID: 30029552
 * TITLE: CPSC501 A3 : ObjectSerializer class
 * DESCRIPTION : serializes objects and has methods which allow that to be done
 * 
 * JDOM output: http://www.jdom.org/docs/apidocs.1.1/org/jdom/output/XMLOutputter.html
 * Sample code attained from stackoverflow
 **/
 
import java.util.IdentityHashMap;

import org.jdom2.Element;
import java.lang.reflect.*;
import org.jdom2.*;
import org.jdom2.output.*;
import java.util.*;
import java.io.*;
import java.util.Scanner;
import java.net.*;
import org.jdom2.input.*;

public class Receiver extends Thread {

     private static Socket socket;
     private static ServerSocket serverSocket;


     public Receiver(int port){
        try{
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(600000);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

     //Setting up network connection
    public static void main (String[] args ) {
        try {
            System.out.println();
            //int port = Integer.parseInt(args[0]);
            int port = 8000;
            //serverSocket = new ServerSocket(port);
            //serverSocket.setSoTimeout(600000);

            Thread serverThread = new Receiver(port);
            serverThread.start();
        }catch(Exception e) { e.printStackTrace();}   
    }

    //Overriding pre-existing method
    public void run(){

        while(true){
            try{
                System.out.println("Receiver (Server) running on " + serverSocket.getLocalPort());

                socket = serverSocket.accept();
                System.out.println("Receiver (Server) connected to " + socket.getRemoteSocketAddress());

                File file =  new File("receivedFile.xml");

                //io streams
                InputStream inputStream = socket.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                //receive file from Sender client as byte array
                byte[] fileBytes = new byte[2048 * 2048];
                int bytesRead = 0;
                while((bytesRead =  inputStream.read(fileBytes)) > 0){
                    fileOutputStream.write(fileBytes, 0, bytesRead);
                    break;
                }
                System.out.println("received file!");

                System.out.println("Building objects");
                //deserialize XML document
                SAXBuilder saxBuilder = new SAXBuilder();
                Document document = (Document) saxBuilder.build(file);
                Object obj = Deserializer.deserialize(document);

                //visualize object (via reflective Inspector class)
                System.out.println("----------------------------------------\n");
                Inspector inspectorGadget = new Inspector();
                inspectorGadget.inspect(obj, false);
                System.out.println("----------------------------------------------");
                System.out.println();

                //close socket
                socket.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}