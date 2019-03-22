/**
 * NAME: SHAYNE MUJURU
 * UCID: 30029552
 * TITLE: CPSC501 A3 : OBJECT CREATOR
 * DESCRIPTION : 
 * 
 * This part of your system will create arbitrary objects under control of the user. 
 * Allow the user to create one or more objects from a selection of objects using some sort of text-based menu system or GUI. 
 * You must demonstrate that your system can handle the following kinds of objects:
 * 
 *      A simple object with only primitives for instance variables. 
 * The user of your program must also beable to set the values for these fields.
 *      An object that contains references to other objects. 
 * Of course, these other objects must also be created at the same time, and their primitive instance variables must be settable by the user. 
 * Your program must also be able to deal with circular references (i.e. objects connected in a graph).
 *      An object that contains an array of primitives. 
 * Allow the user to set the values for the array elements to arbitrary values.
 *      An object that contains an array of object references. 
 * The other objects must also be created at the same time.
 *      An object that uses an instance of one of Java’s collection classes to refer to several other objects. These objects, too, must be created at the same time.
 */

import java.lang.reflect.*;
import org.jdom2.*;
import org.jdom2.output.*;
import java.util.*;
import java.io.*;
import java.util.Scanner;


public class ObjectCreator {

    private static Scanner in = new Scanner(System.in);

    //Creates and returns an instance of a simple primitive object
    public PrimitiveObject createPrimitiveObject() {
        System.out.println("Creating Primitive Object");
        PrimitiveObject primObj = null;
        System.out.println("DEBUG : PrimitiveObject(int, float, boolean");

        try {
            System.out.print("Setting primitive instance variables");

            //Integer primitive
            System.out.println("Enter value for integer field");
            handleInput(1);
            int intParam = in.nextInt();

            //Float
            System.out.println("Enter value of float field");
            handleInput(2);
            float floatParam = in.nextFloat();

            primObj = new PrimitiveObject(intParam, floatParam);

        }catch(Exception e ) { e.printStackTrace(); }
        
        return primObj;
    }
    
    //Creates and returns an instance of a reference object
    public ReferenceObject createReferenceObject() {
        System.out.println("Creating Reference Object");
        ReferenceObject refObj = null;

        //Creation of other objects at same time
        PrimitiveObject primObj = createPrimitiveObject();
        refObj = new ReferenceObject(primObj);

        return refObj;
    } 

    
    //Creates and returns a PrimitiveArray object 
    public PrimitiveArrayObject createPrimitiveArrayObject() {
        System.out.println("Creating PrimitiveArray Object");
        PrimitiveArrayObject primArrayObject = null;
        
        //Prompt user for array length 
        System.out.println("Enter size of array:");
        handleInput(1);
        int arrayLength = in.nextInt();

        //Allow user to set the values of the array elements
        String[] paramArray = new String[arrayLength];
        System.out.println("Press enter after every complete entry");

        for (int i = 0; i < paramArray.length; i++) {
            System.out.printf("Enter value for index %d:\n", i);
            paramArray[i] = in.nextLine();
        }

        primArrayObject = new PrimitiveArrayObject(paramArray);
        return primArrayObject;
    }  
    
    //Creates and returns a ReferenceArray Object
    public ReferenceArrayObject createReferenceArrayObject() {
        System.out.println("Creating ReferenceArray Object");
        ReferenceArrayObject refArrayObject = null;

        //Prompt user for array length 
        System.out.println("Enter size of array:");
        handleInput(1);
        int arrayLength = in.nextInt();

        Object[] paramArray = new PrimitiveArrayObject[arrayLength];

        for (int i = 0; i < paramArray.length; i++) {
            //Creation of primitive objects at same time
            paramArray[i] = createPrimitiveObject();
        }

        refArrayObject = new ReferenceArrayObject(paramArray);
        return refArrayObject;

    }
    /*
    //Creates and returns a Collections Object
    public CollectionObject createCollectionObject() {


    }
*/
    public static void handleInput(int mode) {
        switch (mode) {
            case 1 : {
                while (!in.hasNextInt()) {
                    in.next();
                    System.out.println("Invalid value for int field");
                }
                break;
            }
            case 2 : {
                while (!in.hasNextFloat()) {
                    in.next();
                    System.out.println("Invalid value for float field");
                }
                break;
            }
        } 
    }
    
 }