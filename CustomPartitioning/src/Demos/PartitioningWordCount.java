package Demos;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PartitioningWordCount {
	public static class Map extends
			Mapper<LongWritable, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

		public void map(LongWritable key, Text value, Context context)// OutputCollector<Text,IntWritable> output, Reporter reporter)
				throws IOException, InterruptedException {
			String line = value.toString();
			StringTokenizer tokenizer = new StringTokenizer(line);
			while (tokenizer.hasMoreTokens()) {
				word.set(tokenizer.nextToken());
				// /output.collect(word,one);
				context.write(word, one);
			}
		}
	}

	public static class Reduce extends
			Reducer<Text, IntWritable, Text, IntWritable> {

		public void reduce(Text key, Iterator<IntWritable> values,
				Context context)
		// OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException, InterruptedException {
			int sum = 0;
			while (values.hasNext()) {
				sum += values.next().get();
			}
			// output.collect(key, new IntWritable(sum));
			context.write(key, new IntWritable(sum));
		}
	}

	public static void main(String[] args) throws Exception {

		Configuration conf = new Configuration();

		Job job = new Job(conf, "wordcount");

		FileInputFormat.setInputPaths(job, new Path("counterinput"));
		// Erase previous run output (if any)
		FileSystem.get(conf).delete(new Path("counteroutput"), true);
		FileOutputFormat.setOutputPath(job, new Path("counteroutput"));

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setMapperClass(Map.class);
		// job.setCombinerClass(Reduce.class);
		// job.setReducerClass(Reduce.class);

		// Splitting the number of Reducer tasks
		job.setNumReduceTasks(2);

		// Overriding the default Partition with the Partitioner that I wrote
		job.setPartitionerClass(MyPartitioner.class);

		System.out.println("Number of Reducer's : " + job.getNumReduceTasks());
		job.waitForCompletion(true);
	}

}