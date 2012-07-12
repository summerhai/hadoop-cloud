package containerselection;

import org.jgap.IChromosome;
import org.jgap.Population;

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
			  ioe.printStackTrace();
		  }
		  catch(java.lang.ClassNotFoundException cnfe){
		   System.err.println(cnfe.getMessage());
		  }
		  return object;
		 }
	 
		public static byte[] toPopBytes(Population object){
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
		
		 public static Population toPopObject(byte[] bytes){
			 Population object = null;
			  try{
			   object = (Population)new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(bytes)).readObject();
			  }
			  catch(java.io.IOException ioe){
				  ioe.printStackTrace();
			  }
			  catch(java.lang.ClassNotFoundException cnfe){
			   System.err.println(cnfe.getMessage());
			  }
			  return object;
		}
}
