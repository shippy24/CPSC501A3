/**
 * NAME: SHAYNE MUJURU
 * UCID: 30029552
 * TITLE: CPSC501 A3 : CollectionObject class
 * DESCRIPTION : Simple CollectionObject class with field that is a collection of other objects
 **/
import java.lang.reflect.*;
import org.jdom2.*;
import org.jdom2.output.*;
import java.util.*;
import java.io.*;
import java.util.Scanner;

public class CollectionObject {

    private Vector<Object> objectCollectionField = null;

    public CollectionObject () {

    }

    public CollectionObject (Vector<Object> paramCollection) {
        objectCollectionField = paramCollection;
    }
 }