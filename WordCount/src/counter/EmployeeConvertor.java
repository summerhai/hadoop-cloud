package counter;

public class EmployeeConvertor {
	 /** Converts an object to an array of bytes. 
	  * @param object the object to convert.
	  * @return the associated byte array.
	  */
	 public static byte[] toBytes(Employee employee){
	  java.io.ByteArrayOutputStream baos = new
	  java.io.ByteArrayOutputStream();
	  try{
	   java.io.ObjectOutputStream oos = new
	   java.io.ObjectOutputStream(baos);
	   oos.writeObject(employee);
	  }catch(java.io.IOException ioe){
	   System.err.println(ioe.getMessage());
	  }
	  return baos.toByteArray();
	 }

	 /** Converts an array of bytes back to its constituent object.
	  * The input array is assumed to have been created from the original object.
	  * @param bytes the byte array to convert.
	  * @return the associated object.
	  */
	 public static Employee toEmployee(byte[] bytes){
	  Employee employee = null;
	  try{
		  employee = (Employee)new java.io.ObjectInputStream(new
	     java.io.ByteArrayInputStream(bytes)).readObject();
	  }catch(java.io.IOException ioe){
	   System.err.println(ioe.getMessage());
	  }catch(java.lang.ClassNotFoundException cnfe){
	   System.err.println(cnfe.getMessage());
	  }
	  return employee;
	 } 
	}
