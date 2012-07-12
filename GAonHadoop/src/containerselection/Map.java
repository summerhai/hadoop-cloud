package containerselection;
import java.io.IOException;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.jgap.IChromosome;

	public class Map extends
	   Mapper<IntWritable, BytesWritable, IntWritable, BytesWritable> {
		
		MapReducer mapreduce;

	public void map(IntWritable key, BytesWritable value, Context context)// OutputCollector<Text,IntWritable> output, Reporter reporter)
	    throws IOException, InterruptedException {
			System.out.println("Mappper Begins");
			IChromosome chrom = ObjectConverter.toObject(value.getBytes());
			System.out.println("Object COnversion successful");
			chrom.getFitnessValue();
			BytesWritable chromBytes = new BytesWritable(ObjectConverter.toBytes(chrom));
			//mapreduce.getPopulation().addChromosome(chrom);
	        //DoubleWritable fitness = new DoubleWritable(chrom.getFitnessValue());
	        context.write(key,chromBytes);
	  }
	 }