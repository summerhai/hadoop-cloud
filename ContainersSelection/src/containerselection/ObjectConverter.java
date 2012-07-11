package containerselection;

import org.jgap.IChromosome;

public class ObjectConverter {
	public static byte[] toBytes(IChromosome object){
		  java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
		  try{
			  java.io.ObjectOutputStream oos = new  java.io.ObjectOutputStream(baos);
			  oos.writeObject(object);
		  }
		  catch(java.io.IOException ioe){
			  System.err.println(ioe.getMessage());
		  }
		return baos.toByteArray();
	 }
	
	 public static IChromosome toObject(byte[] bytes){
		 IChromosome object = null;
		  try{
		   object = (IChromosome)new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(bytes)).readObject();
		  }
		  catch(java.io.IOException ioe){
		   //System.err.println(ioe.getMessage());
			  ioe.printStackTrace();
		  }
		  catch(java.lang.ClassNotFoundException cnfe){
		   System.err.println(cnfe.getMessage());
		  }
		  return object;
		 }
}
