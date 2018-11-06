package util;

//requires object to implement java.io.Serializable
public class ObjectIO
{
    public static void WriteObjectToFile(String filepath, Object serObj) 
    {
        try {
            java.io.FileOutputStream fileOut = new java.io.FileOutputStream(filepath);
            java.io.ObjectOutputStream objectOut = new java.io.ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Object ReadObjectFromFile(String filepath) {
        try {
            java.io.FileInputStream fileIn = new java.io.FileInputStream(filepath);
            java.io.ObjectInputStream objectIn = new java.io.ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();
            System.out.println("The Object has been read from the file");
            objectIn.close();
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}