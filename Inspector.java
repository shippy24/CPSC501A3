/**
    NAME : SHAYNE MUJURU
    UCID : 30029552
    TITLE: CPSC501 AII : INSPECTOR CLASS 
    DESCRIPTION : Goal is to create a reflective object inspector 
                    that does a complete introspection of 
                    an object at runtime.

    Uses and edits code from Jordan Kidney ObjectInspector.java for field
**/ 

import java.lang.reflect.*;
import java.util.*;

public class Inspector {

    public void inspect(Object obj, boolean recursive) {

        //attaining class object which will be used to get declaring class
        Class objClass = obj.getClass();
        String declClass = objClass.getName();
        System.out.println("--Declaring Class: " + declClass);

        //immediate super class
        String superClass = objClass.getSuperclass().getName();
        System.out.println("--Super Class: " + superClass);

        //Interfaces the class implements
        traverseInterfaces(obj, objClass, recursive);
        System.out.println();

        //attaining methods for current object and super class 
        System.out.println("\n-----  Base class methods --------");
        inspectMethods(objClass);

        //attaining the constructors defined
        System.out.println("\n-----  Base class Constructors --------");
        inspectConstructors(objClass);

        //attaining fields
        System.out.println("\n-----  Base class Fields --------");
        inspectFields(obj, objClass, recursive);

        //attaining info from traversing super class
        System.out.println("\n-----  Traversing Super Classes --------");
        traverseSuperMethods(obj, objClass, recursive);

        //Handling arrays
        handleArray(obj, objClass);
        
    }

    public void handleArray(Object obj, Class objClass) {
        try {
        //Check if array
            if (objClass.isArray()) {
            //Print out info about array if array
                System.out.println("---Array of Type: " + objClass.getComponentType());
                System.out.println("---Array of length: " + Array.getLength(obj));
                for (int i = 0; i < Array.getLength(obj); i++) {
                    System.out.println("Element " + i + ": " + Array.get(obj, i));
                }
            }
        }catch (Exception e ) { System.out.println(e.getMessage()); }
    }

    public void traverseSuperMethods(Object obj, Class objClass, boolean recursive){
        System.out.println("\n-----  Base Super Class --------");
        inspectMethods(objClass.getSuperclass());
        inspectConstructors(objClass.getSuperclass());
        inspectFields(obj, objClass, recursive);
        Class currentSuperClass = objClass.getSuperclass();

        if (currentSuperClass.getSuperclass() != null)
            System.out.println("\n-----  Recursive Super Classes --------");

        while (currentSuperClass.getSuperclass() != null) {
            Class nextSuperClass = currentSuperClass.getSuperclass();
            System.out.println("\n----- " +  nextSuperClass.getName() + " --------");
            inspectMethods(nextSuperClass);
            inspectConstructors(nextSuperClass);
            inspectFields(obj, nextSuperClass, recursive);
            currentSuperClass = nextSuperClass;
        }

        System.out.println("\n-----   End of super class traversal    -----\n");
    }

    public void traverseInterfaces(Object obj, Class objClass, boolean recursive){
        Class[] interfaces = objClass.getInterfaces();
        //Print the name of each of the interfaces
        for (Class interFace : interfaces) {
            System.out.println("\n--Interface : " + interFace.getName());

            //Get interface methods
            if (interFace.getMethods().length != (new Method[] {}).length){
                System.out.println("\n-----  Interface methods --------");
                inspectMethods(interFace);
            }

            //Get interface constructors
            if (interFace.getConstructors().length != (new Constructor[] {}).length){
                System.out.println("\n-----  Interface constructors --------");
                inspectConstructors(interFace);
            }

            //Get interface FIELDS
            if (interFace.getConstructors().length != (new Constructor[] {}).length){
                System.out.println("\n-----  Interface fields--------");
                inspectFields(obj, interFace, recursive);
            }

            if (interFace.getInterfaces().length != (new Method[] {}).length) {
                System.out.println("\n-----  Recursive Super Interfaces --------");
            }

            while (interFace.getInterfaces().length != (new Method[] {}).length) {
                for (Class recInterface : interFace.getInterfaces()) {
                    traverseInterfaces(obj, recInterface, recursive);
                }
            }
            System.out.println("\n----- End of Interface traversal  -----\n");
        }

    }

    public void inspectMethods(Class objClass) {
        //Get all the declared methods
        Method[] declMethods = objClass.getDeclaredMethods();

        Class[] exceptions;
        Class[] params;

        //Print info about the methods
        for (Method method : declMethods) {
            System.out.println("--- Method: " + method.getName());

            //Get exceptions
            exceptions = method.getExceptionTypes();
            for (Class exception : exceptions) {
                System.out.println("---     Exception : " + exception.getName());
            }

            //Get parameter types
            params = method.getParameterTypes();
            for (Class param : params) {
                System.out.println("---     Parameter : " + param.getName());
            }

            //Get Return type
            System.out.println("---     Return type : " + method.getReturnType().getName());

            //Get Modifiers
            System.out.println("---     Modifiers : " + method.getModifiers() + "\n");
            
            System.out.println();
        }
    }

    public void inspectConstructors(Class objClass) {
        //All declared constructos 
        Constructor[] constructors = objClass.getConstructors();
        Class[] params;

        //Print all info about constructors
        for (Constructor constructor : constructors) {
            System.out.println("--- Constructor: " + constructor.getName());
            
            //Get parameters 
            params = constructor.getParameterTypes();
            for (Class param : params) {
                System.out.println("---     Parameter: " + param.getName());
            }

            //get Modifiers
            System.out.println("---     Modifiers : " + constructor.getModifiers() + "\n");

        }

    }

    public void inspectFields(Object obj, Class objClass, boolean recursive) {
        //Get fields
        Field[] declFields = objClass.getDeclaredFields();

        try {

            for (Field field : declFields){
                //Print field info
                System.out.println("---     Field: " + field.getName());
                System.out.println("---     Type: " + field.getType().getName());
                System.out.println("---     Modifier: " + field.getModifiers());

                //Allow full access to all fields
                field.setAccessible(true);
                //Get field value
                System.out.println("---     Value: " + field.get(obj) + "\n");
            }

            //Recursive Inspection
            if (recursive) {
                System.out.println("---  Recursive Inspection ---");
                Vector<Field> objectsToInspect = new Vector();

                for (Field field : declFields) {
                    if(! field.getType().isPrimitive() ) 
		                objectsToInspect.addElement( field );
                }

                for (Field field : objectsToInspect) {
                    inspect(field.get(obj), recursive);
                }
            }
        }
        catch (Exception e) { System.out.println(e.getMessage()); }

    }
}