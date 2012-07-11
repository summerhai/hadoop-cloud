package containerselection;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.jgap.Population;

public class MapReducer{
	
	private String inputPath;
	private String outputPath;
	private Population population;
	
	
	public Population getPopulation() {
		return population;
	}


	public void setPopulation(Population population) {
		this.population = population;
	}


	public String getInputPath() {
		return inputPath;
	}


	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}


	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public static void main(String[] args)
			throws Exception{
		MapReducer mapreducer = new MapReducer();
		// calls the JGAP set up part
		
		ContainerSelectionMain csm = new ContainerSelectionMain();
		csm.configureJGAP();
	}
	
	public static void runJob() throws Exception{
		Configuration config = new Configuration();
		Job job = new Job(config, "fitnesscalculation");
		// Change as per Amazon S3 path
		FileInputFormat.setInputPaths(job, new Path("SeqInput"));
		// Erase previous run output (if any)
		FileSystem.get(config).delete(new Path("output"), true);
		// Change as per Amazon S3 path
		FileOutputFormat.setOutputPath(job, new Path("output"));
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(BytesWritable.class);
		job.setMapperClass(Map.class);
		System.out.println("Job 1: Fitness Calculation Begins");
		job.waitForCompletion(true);
	}
	
	public void runJob2() throws Exception{
		Configuration config = new Configuration();
		Job job2 = new Job(config, "GeneticOps");
		// Change as per Amazon S3 path
		FileInputFormat.setInputPaths(job2, new Path("SeqInput"));
		// Erase previous run output (if any)
		FileSystem.get(config).delete(new Path("output"), true);
		// Change as per Amazon S3 path
		FileOutputFormat.setOutputPath(job, new Path("output"));
		job2.setInputFormatClass(SequenceFileInputFormat.class);
		job2.setOutputFormatClass(SequenceFileOutputFormat.class);
		
		job2.setOutputKeyClass(IntWritable.class);
		job2.setOutputValueClass(BytesWritable.class);
		job2.setMapperClass(Map.class);
		System.out.println("Job 1: Fitness Calculation Begins");
		job2.waitForCompletion(true);
	}
}
