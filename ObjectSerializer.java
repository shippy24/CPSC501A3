/**
 * NAME: SHAYNE MUJURU
 * UCID: 30029552
 * TITLE: CPSC501 A3 : ObjectSerializer class
 * DESCRIPTION : serializes objects
 * 
 * JDOM output: http://www.jdom.org/docs/apidocs.1.1/org/jdom/output/XMLOutputter.html
 **/

import java.lang.reflect.*;
import org.jdom2.*;
import org.jdom2.output.*;

import java.util.*;
import java.io.*;
import java.util.Scanner;



public class ObjectSerializer extends Serializer {

    private File serializedFile = null;

    public ObjectSerializer (ArrayList<Object> objectList) {
        for (Object obj : objectList) {
            try {
                Class objClass = obj.getClass();

                System.out.println("Serializing object");
                Document document = serialize(obj);

                System.out.println("File creation");
                serializedFile = makeXMLFile(document);
            }catch(Exception e) {e.printStackTrace();}
        }
    }

    public File makeXMLFile(Document doc) {
        File file = new File("outFile.xml");
        XMLOutputter xmlOutputter = new XMLOutputter();
        xmlOutputter.setFormat(Format.getPrettyFormat());

        try{
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            xmlOutputter.output(doc, bufferWriter);

            bufferWriter.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return file;
    }

    public File getSerializedFile() {
        return serializedFile;
    }
}