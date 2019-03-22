/**
 * NAME: SHAYNE MUJURU
 * UCID: 30029552
 * TITLE: CPSC501 A3 : ObjectSerializer class
 * DESCRIPTION : serializes objects and has methods which allow that to be done
 * 
 * JDOM output: http://www.jdom.org/docs/apidocs.1.1/org/jdom/output/XMLOutputter.html
 * Sample code attained from stackoverflow
 * Code for testing from github
 **/

import org.jdom2.Element;
import java.lang.reflect.*;
import org.jdom2.*;
import org.jdom2.output.*;
import java.util.*;
import java.io.*;
import java.util.Scanner;


public class Deserializer {

 
     //Deserialize document and build object from from document contents
     
    public static Object deserialize(Document document){
        //get root element and list of nested object elements
        Element rootElement = document.getRootElement();
        List objList = rootElement.getChildren();
        HashMap objMap =  new HashMap();


        //Object to be instantiated via deserialization
        Object obj = null;

        try{

            //create instances of each object in object list
            createObjectInstances(objList, objMap);
            setFieldValues(objList, objMap);
            obj = objMap.get("0");

        }
        catch(Exception e){
            e.printStackTrace();
        }

        //return deserialized object
        return obj;
    }

    //Deserialize a content element into an object
    
    private static Object deserializeContentElement(Class classType, Element contentElement, HashMap objMap){
        Object contentObject;

        String contentType = contentElement.getName();

        if(contentType.equals("reference")){
            contentObject = objMap.get(contentElement.getText());
        }
        else if(contentType.equals("value"))
            contentObject = deserializeFieldValue(classType, contentElement);
        else
            contentObject = contentElement.getText();


        return contentObject;
    }
    
     //Deserialize value from field element content
    private static Object deserializeFieldValue(Class fieldType, Element valueElement){

        Object valueObject = null;

        if(fieldType.equals(int.class))
            valueObject = Integer.valueOf(valueElement.getText());
        else if(fieldType.equals(byte.class))
            valueObject = Byte.valueOf(valueElement.getText());
        else if(fieldType.equals(short.class))
            valueObject = Short.valueOf(valueElement.getText());
        else if(fieldType.equals(long.class))
            valueObject = Long.valueOf(valueElement.getText());
        else if(fieldType.equals(float.class))
            valueObject = Float.valueOf(valueElement.getText());
        else if(fieldType.equals(double.class))
            valueObject = Double.valueOf(valueElement.getText());
        else if(fieldType.equals(boolean.class)){

            String boolString = valueElement.getText();

            if(boolString.equals("true"))
                valueObject = Boolean.TRUE;
            else
                valueObject = Boolean.FALSE;
        }

        return valueObject;
    }

    
    


    
    //set values for fields using field element attributes from Document
    
    private static void setFieldValues(List objList, HashMap objMap){
        for(int i =0; i < objList.size(); i++){
            try{
                Element objElement = (Element) objList.get(i);

                Object objInstance =  objMap.get(objElement.getAttributeValue("id"));


                //get list of all children of object element (fields if non-array, elements if array)
                List objChildrenList = objElement.getChildren();


                // if array object, set value of each element
                // if non-array object, assign values to all fields/instance variables
                Class objClass = objInstance.getClass();
                System.out.println(objClass.getName());
                if(objClass.isArray()){

                    //set values for each array element
                    Class arrayType =  objClass.getComponentType();
                    for(int j= 0; j < objChildrenList.size(); j++){
                        Element arrayContentElement = (Element) objChildrenList.get(j);

                        Object arrayContent = deserializeContentElement(arrayType, arrayContentElement, objMap);

                        Array.set(objInstance, j, arrayContent);

                    }
                }
                else{
                    //non-array object, assign values to all fields
                    for(int j = 0; j < objChildrenList.size(); j++){
                        Element fieldElement = (Element) objChildrenList.get(j);

                      
                        Class declaringClass =  Class.forName(fieldElement.getAttributeValue("declaringclass"));
                        String fieldName = fieldElement.getAttributeValue("name");
                        Field field = declaringClass.getDeclaredField(fieldName);

                        if(!Modifier.isPublic(field.getModifiers())){
                            field.setAccessible(true);

                     
                            Field modifiersField = Field.class.getDeclaredField("modifiers");
                            modifiersField.setAccessible(true);
                            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                        }

                        //check field element content for value/reference and set accordingly
                        Class fieldType = field.getType();
                        Element fieldContentElement = (Element) fieldElement.getChildren().get(0);

                        Object fieldContent = deserializeContentElement(fieldType, fieldContentElement, objMap);

                        field.set(objInstance, fieldContent);
                    }

                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    //Create instances for all object elements from Document
     
    private static void createObjectInstances(List objList, HashMap objMap){
        for(int i =0; i < objList.size(); i++){
            try{
                Element objElement = (Element) objList.get(i);

                //create uninitialized instance using element attribute
                Class objClass =  Class.forName(objElement.getAttributeValue("class"));
                //System.out.println(objClass.getName());

                //check for class type then create new instance
                Object objInstance;
                if(objClass.isArray()){

                    //get length (via element attributes) and component type of array object instantiation
                    int arrayLength = Integer.parseInt(objElement.getAttributeValue("length"));
                    Class arrayType = objClass.getComponentType();

                    objInstance = Array.newInstance(arrayType, arrayLength);

                }
                else{
                   
                    Constructor constructor =  objClass.getConstructor(null);
                    //check constructor modifiers, just in case
                    if(!Modifier.isPublic(constructor.getModifiers())){
                        constructor.setAccessible(true);
                    }

                    objInstance = constructor.newInstance(null);
                }

                //associate the new instance with the object's unique id (element attribute)
                String objId = objElement.getAttributeValue("id");
                objMap.put(objId, objInstance);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

    }
}