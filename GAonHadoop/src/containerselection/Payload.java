package containerselection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class Payload implements Serializable{
	private  List<ShipContainer> shipContainer;
	
    public Payload(String file, int dataSize)
    		throws FileNotFoundException,IOException{
    	shipContainer = new ArrayList<ShipContainer>();
    	readData(file);
    }
    
    
    public List<ShipContainer> getShipContainer() {
		return shipContainer;
	}


	private void readData(String str)
    		throws FileNotFoundException,IOException{
    	BufferedReader br = new BufferedReader(new FileReader(str));
    	String line;
    	
    	while((line=br.readLine()) != null){
    		StringTokenizer st = new StringTokenizer(line,",");
    		 int id =0;
    		 double weight=0;
    		 int count =0;
    		 while(st.hasMoreTokens()){
    			 if (count == 0){
    				 id = Integer.parseInt(st.nextToken());
    			 }
    			 else if (count == 1){
    				 weight = Double.parseDouble(st.nextToken());
    			 }
    			 else{
    				 throw new IOException("Count should 0 or 1. check file for extra spaces in lines");
    			 }
    			 count++;
    		 }
    		 shipContainer.add(new ShipContainer(id,weight));
    	}
    	
    }
}
