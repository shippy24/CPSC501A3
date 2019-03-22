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

 public class Sender {

    public static void main (String[] args) {

        //Initialization
        //ObjectCreator objCreator = new ObjectCreator();
        //Serializer serializer = new Serializer();
        //XMLOutputter xmlOut = new XMLOutputter();
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
                case "1" :
                case "2" :
                case "3" :
                case "4" :
                case "5" :
                    {
                        //Call createObject
                        //objCreator.createObject(object);
                        break;
                    }
                default :
                    System.out.println("Choice out of accepted range");
                    break;
            }
        }

        //Serialize objects 

        //Send objects
    }
 }