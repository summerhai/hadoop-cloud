package containerselection;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.jgap.Chromosome;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.FixedBinaryGene;
import org.jgap.impl.MutationOperator;
 

public class ContainerSelectionMain {
	
	//double maxWeight = 250000;
	Payload payload;
	Genotype population;
	
	public ContainerSelectionMain()	
			throws FileNotFoundException,IOException{
		 payload= new Payload(Constants.CSV_FILE,64); 
		 //printPayload();
	}
	
	//print Details of Payload of containers
	public void  printPayload(){
		for (int i=0;i<payload.getShipContainer().size();i++){
			System.out.print(payload.getShipContainer().get(i).getContainerID());
			System.out.println("\t"+payload.getShipContainer().get(i).getWeight());
			}
	}
	
	
	public void configureJGAP(String output, String weight) 
			throws FileNotFoundException,IOException,InvalidConfigurationException,ClassNotFoundException{
		// Create JGAP Configuration object and add GA config parameters
				org.jgap.Configuration conf = new org.jgap.impl.DefaultConfiguration();
				// Breeder settings
				MyBreeder a_breeder = new MyBreeder();
				
			    conf.setPreservFittestIndividual(true);
			    conf.setKeepPopulationSizeConstant(true);
			    FitnessFunction myFunc =
			            new ContainerSelectionFitnessFunction(payload, Double.valueOf(weight));
			    conf.setFitnessFunction(myFunc);
			    Gene sampleGene = new FixedBinaryGene(conf,1);
			    IChromosome sampleChromosome = new Chromosome(conf, sampleGene, payload.getRecordCount()); // Change to Dynamic
			    conf.setSampleChromosome(sampleChromosome);
			    conf.setBreeder(a_breeder);
			    conf.getGeneticOperators().clear();
			    conf.addGeneticOperator(new CrossoverOperator(conf,10));
			    conf.addGeneticOperator(new MutationOperator(conf,6));
			    // Initial Population size
			    conf.setPopulationSize(Constants.POPULATION_SIZE);
			    
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
			    for (int i=0;i<Constants.NUMBER_OF_EVOLUTIONS;i++){
			    	population.evolve();
				    IChromosome bestSolutionSoFar = population.getFittestChromosome();
				    double v1 = bestSolutionSoFar.getFitnessValue();
				    //System.out.println("Best Fitness as of Generation  " + (i+1) + " is "+
				    //                   bestSolutionSoFar.getFitnessValue());
				}
			    long endTime = System.currentTimeMillis();
			    //System.out.println("Total evolution time: " + ( endTime - startTime));
			    // Display the best solution we found.
			    // -----------------------------------
			    IChromosome bestSolutionSoFar = population.getFittestChromosome();
			    double v1 = bestSolutionSoFar.getFitnessValue();
			    //System.out.println("The best solution has a fitness value of " +
			    //                   bestSolutionSoFar.getFitnessValue());
			    writeSolutionToFile(bestSolutionSoFar, output);

	}
	
	public void writeSolutionToFile(IChromosome bestSolutionSoFar, String output)
		throws IOException{
		 //System.out.println("Writing Solution to File...");
		 int[] bits;
		 
		 //Amazon use
		Configuration conf = new Configuration();
		Path filePath = new Path(output);
		FileSystem fs = FileSystem.get(URI.create(output), conf);

		FSDataOutputStream fdos = fs.create(filePath, true);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fdos));
		
		 
		 //BufferedWriter bw = new BufferedWriter(new FileWriter(Constants.OUTPUT_FILE));
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