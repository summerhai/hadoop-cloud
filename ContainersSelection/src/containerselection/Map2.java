package containerselection;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.jgap.GeneticOperator;
import org.jgap.Population;
import org.jgap.event.GeneticEvent;

public class Map2 extends 
	Mapper<IntWritable, BytesWritable, IntWritable, BytesWritable>{
	
	public void map(IntWritable key, BytesWritable value, Context context)// OutputCollector<Text,IntWritable> output, Reporter reporter)
		    throws IOException, InterruptedException {
				System.out.println("Mappper-2 Begins");
				Population a_pop = ObjectConverter.toPopObject(value.getBytes());
				System.out.println("Pop Object Conversion successful");
				org.jgap.Configuration a_config = a_pop.getConfiguration();

				List geneticOperators = a_config.getGeneticOperators();
			    Iterator operatorIterator = geneticOperators.iterator();
			    
			    while (operatorIterator.hasNext()) {
			      GeneticOperator operator = (GeneticOperator) operatorIterator.next();
			      /**@todo utilize jobs: integrate job into GeneticOperator*/
			      // Fire listener before genetic operator will be executed.
			      // -------------------------------------------------------
			      a_config.getEventManager().fireGeneticEvent(
			          new GeneticEvent(GeneticEvent.BEFORE_GENETIC_OPERATOR, new Object[] {
			                           this, operator}));

			      operator.operate(a_pop, a_pop.getChromosomes());
			      

			      // Fire listener after genetic operator has been executed.
			      // -------------------------------------------------------
			      a_config.getEventManager().fireGeneticEvent(
			          new GeneticEvent(GeneticEvent.AFTER_GENETIC_OPERATOR, new Object[] {
			                           this, operator}));
			    }


				BytesWritable popBytes = new BytesWritable(ObjectConverter.toPopBytes(a_pop));
				//mapreduce.getPopulation().addChromosome(chrom);
		        //DoubleWritable fitness = new DoubleWritable(chrom.getFitnessValue());
		        context.write(key,popBytes);
		  }
	
    

}
