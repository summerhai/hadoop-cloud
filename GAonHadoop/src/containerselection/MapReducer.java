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
	
	private static String outputPath;


	public static void main(String[] args)
			throws Exception{
		MapReducer mapreducer = new MapReducer();
		
		ContainerSelectionMain csm = new ContainerSelectionMain();
		csm.configureJGAP(args[0],args[1]);
	}
	
	public static void runJob() throws Exception{
		Configuration config = new Configuration();
		Job job = new Job(config, "fitnesscalculation");
		// Change as per Amazon S3 path
		FileInputFormat.setInputPaths(job, new Path(Constants.JOB1_INPUTFILE));
		// Erase previous run output (if any)
		FileSystem.get(config).delete(new Path(Constants.JOB1_OUTPUTFILE), true);
		// Change as per Amazon S3 path
		FileOutputFormat.setOutputPath(job, new Path(Constants.JOB1_OUTPUTFILE));
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(BytesWritable.class);
		job.setMapperClass(Map.class);
		//System.out.println("Job 1: Fitness Calculation Begins");
		job.waitForCompletion(true);
	}
	
	public static void runJob2() throws Exception{
		Configuration config = new Configuration();
		Job job2 = new Job(config, "GeneticOps");
		// Change as per Amazon S3 path
		FileInputFormat.setInputPaths(job2, new Path(Constants.JOB2_INPUTFILE));
		// Erase previous run output (if any)
		FileSystem.get(config).delete(new Path(Constants.JOB2_OUTPUTFILE), true);
		// Change as per Amazon S3 path
		FileOutputFormat.setOutputPath(job2, new Path(Constants.JOB2_OUTPUTFILE));
		job2.setInputFormatClass(SequenceFileInputFormat.class);
		job2.setOutputFormatClass(SequenceFileOutputFormat.class);
		
		job2.setOutputKeyClass(IntWritable.class);
		job2.setOutputValueClass(BytesWritable.class);
		job2.setMapperClass(Map2.class);
		//System.out.println("Job 2: Genetic Ops Begins");
		job2.waitForCompletion(true);
	}
}
