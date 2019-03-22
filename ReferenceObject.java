/**
 * NAME: SHAYNE MUJURU
 * UCID: 30029552
 * TITLE: CPSC501 A3 : ReferenceObject class
 * DESCRIPTION : Simple ReferenceObject class with field that refers to another class
 **/

 public class ReferenceObject {

    private PrimitiveObject referenceObjectField;

    public ReferenceObject() {

    }

    public ReferenceObject(PrimitiveObject primObj) {
        referenceObjectField = primObj;
    }
 }