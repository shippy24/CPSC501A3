/**
 * NAME: SHAYNE MUJURU
 * UCID: 30029552
 * TITLE: CPSC501 A3 : OBJECT CREATOR
 * DESCRIPTION : Driver of Sender side of program
 **/

 import java.lang.reflect.*;
 import org.jdom2.*;
 import java.util.Scanner;
 import java.io.*;
 import java.util.ArrayList;
 import java.net.*;

 public class Sender {

    private static ArrayList<Object> objectList;

    public static void main (String[] args) {
        //Initialization
        ObjectCreator objCreator = new ObjectCreator();
        ObjectSerializer serializer = null;
        Scanner in = new Scanner(System.in);

        Object obj = null;
        String objects = null;

        //User selection options
        
        System.out.println("Pick one or multiple options, seperated by a space for type of objects to create:\n" +
        "1. Primitives\n" +
        "2. References\n" +
        "3. Array of Primitives\n" +
        "4. Array of Object References\n" +
        "5. Collections\n");

        //Receive user input
        objects = in.nextLine();

        //Split input by using space as regex
        String[] objectArray = objects.split(" ");

        for (String object : objectArray) {
            switch (object) {
                case "1" : {
                    objectList.add(objCreator.createPrimitiveObject());
                    break;
                }
                case "2" : {
                    objectList.add(objCreator.createReferenceObject());
                    break;
                }
                case "3" : {
                    objectList.add(objCreator.createPrimitiveArrayObject());
                    break;
                }
                case "4" : {
                    objectList.add(objCreator.createReferenceArrayObject());
                    break;
                }
                case "5" : {
                    objectList.add(objCreator.createCollectionObject());    
                    break;
                    }
                default :
                    System.out.println("Choice out of accepted range");
                    break;
            }
        }

        //Serialize objects 
        serializer = new ObjectSerializer(objectList);
        //Send objects
        sendFile("localhost", 8000, serializer.getSerializedFile());
    }
    
    //Sending file 
    //Code attained from tutorialspoint and modified using stackoverflow additions
    private static void sendFile(String host, int port, File file){
        try{
            System.out.println("Connecting to " + host + " on port: " + port);

            Socket socket = new Socket(host, port);
            System.out.println("Sender connected to " + socket.getRemoteSocketAddress());

            OutputStream outputStream = socket.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(file);

            //send file as byte array stream
            byte[] fileBytes = new byte[2048 * 2048];
            int bytesRead = 0;
            while((bytesRead = fileInputStream.read(fileBytes))> 0){
                outputStream.write(fileBytes, 0, bytesRead);
            }

            //close streams/sockets
            fileInputStream.close();
            outputStream.close();
            socket.close();

            System.out.println("File sent!");
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
 }