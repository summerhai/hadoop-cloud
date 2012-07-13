/*package containerselection;

import org.jgap.Chromosome;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.impl.FixedBinaryGene;


public class ContainerSelectionMain {
	private org.jgap.Configuration conf;
	private Genotype population;
	
	public ContainerSelectionMain() 
			throws Exception {
		configureJGAP();
	}
	
	private void configureJGAP() 
	throws Exception{
		conf = new org.jgap.impl.DefaultConfiguration();
		conf.setPreservFittestIndividual(true);
	    conf.setKeepPopulationSizeConstant(true);
	    conf.setFitnessFunction(createFitnessFunction());
	    Gene sampleGene = new FixedBinaryGene(conf,1);
	    IChromosome sampleChromosome = new Chromosome(conf, sampleGene, 10000);
	    conf.setSampleChromosome(sampleChromosome);
	    //sampleChromosome
	    // Hi Vu, sampleChromosome is the object you need to test for serialization
	    
	    // Initial Population size
	    conf.setPopulationSize(1);
	    population = Genotype.randomInitialGenotype(conf);
	 // Just printing the Population gene wise
	    int[] bits;
	    for (int pop=0; pop < population.getPopulation().size();pop++){
	    	
		    for(int i=0;i<sampleChromosome.size();i++){
		    	bits = (int[])sampleChromosome.getGene(i).getAllele();
		    	System.out.println("Genes: "+i+" "+bits[0]);
		    }
	    }
	    
	    //evolution ... Dummy now
	    long startTime = System.currentTimeMillis();
	    population.evolve();
	    long endTime = System.currentTimeMillis();
	    System.out.println("Total evolution time: " + ( endTime - startTime));
	    // Display the best solution we found.
	    // -----------------------------------
	    IChromosome bestSolutionSoFar = population.getFittestChromosome();
	    double v1 = bestSolutionSoFar.getFitnessValue();
	    System.out.println("The best solution has a fitness value of " +
	                       bestSolutionSoFar.getFitnessValue());

	}
	
	private FitnessFunction createFitnessFunction(){
		return new ContainerSelectionFitnessFunction();
	}

	public static void main(String[] args)
			throws Exception{
		
		ContainerSelectionMain app = new ContainerSelectionMain();
		// Create JGAP Configuration object and add GA config parameters
		
	    // Here we need to initialize the Genotype .. The initial population
	    
	}

}
*/

package containerselection;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.jgap.Chromosome;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.FixedBinaryGene;
 

public class ContainerSelectionMain {
	
	double maxWeight = 1000;
	Payload payload;
	Genotype population;
	
	public ContainerSelectionMain()	
			throws FileNotFoundException,IOException{
		 payload= new Payload(Constants.CSV_FILE,64); 
		 printPayload();
	}
	
	//print Details of Payload of containers
	public void  printPayload(){
		for (int i=0;i<payload.getShipContainer().size();i++){
			System.out.print(payload.getShipContainer().get(i).getContainerID());
			System.out.println("\t"+payload.getShipContainer().get(i).getWeight());
			}
	}
	
	
	public void configureJGAP() 
			throws FileNotFoundException,IOException,InvalidConfigurationException,ClassNotFoundException{
		// Create JGAP Configuration object and add GA config parameters
				org.jgap.Configuration conf = new org.jgap.impl.DefaultConfiguration();
				// Breeder settings
				MyBreeder a_breeder = new MyBreeder();
				
			    conf.setPreservFittestIndividual(true);
			    conf.setKeepPopulationSizeConstant(true);
			    FitnessFunction myFunc =
			            new ContainerSelectionFitnessFunction(payload,maxWeight);
			    conf.setFitnessFunction(myFunc);
			    Gene sampleGene = new FixedBinaryGene(conf,1);
			    IChromosome sampleChromosome = new Chromosome(conf, sampleGene, 64);
			    conf.setSampleChromosome(sampleChromosome);
			    conf.setBreeder(a_breeder);
	
			    // Initial Population size
			    conf.setPopulationSize(10);
			    
			    // Here we need to initialize the Genotype .. The initial population
			    population = Genotype.randomInitialGenotype(conf);
			    			    
			    // Just printing the Population gene wise
			    int[] bits;
			    for (int pop=0; pop < population.getPopulation().size();pop++){
			    	IChromosome chromosome = population.getPopulation().getChromosome(pop);
				    for(int i=0;i<sampleChromosome.size();i++){
				    	bits = (int[])chromosome.getGene(i).getAllele();
				    	//System.out.println("Writing Genes: "+i+" "+bits[0]);
				    }
			    } 
			    
			    //evolution ... Dummy now
			    long startTime = System.currentTimeMillis();
			    population.evolve();
			    long endTime = System.currentTimeMillis();
			    System.out.println("Total evolution time: " + ( endTime - startTime));
			    // Display the best solution we found.
			    // -----------------------------------
			    IChromosome bestSolutionSoFar = population.getFittestChromosome();
			    double v1 = bestSolutionSoFar.getFitnessValue();
			    System.out.println("The best solution has a fitness value of " +
			                       bestSolutionSoFar.getFitnessValue());
			    writeSolutionToFile(bestSolutionSoFar);

	}
	
	public void writeSolutionToFile(IChromosome bestSolutionSoFar)
		throws IOException{
		 System.out.println("Writing Solution to File...");
		 int[] bits;
		 BufferedWriter bw = new BufferedWriter(new FileWriter(Constants.OUPUT_FILE));
		 for (int i=0;i<bestSolutionSoFar.size();i++){
			 bits = (int[])bestSolutionSoFar.getGene(i).getAllele();
			 if (bits[0] == 1){
			/*	 System.out.print(" "+payload.getShipContainer().get(i).getContainerID());
				 System.out.println(" "+payload.getShipContainer().get(i).getWeight());*/
				 bw.write(payload.getShipContainer().get(i).getContainerID()
						  + ","+payload.getShipContainer().get(i).getWeight()+"\n");
			 }
		 }
		 bw.close();
	}
}