package counter;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class WordCount {
	
	public static void main(String[] args) throws Exception {
		ArrayList<Employee> employeeList = getEmployeeList();
		String output = "./counterinput/seq.txt";
		
		Configuration conf = new Configuration();	
		FilesPacker packer = new FilesPacker(conf);
		packer.packAsSequenceFile(employeeList, output);
		output = "./counterinput/seq2.txt";
		packer.packAsSequenceFile(employeeList, output);
		
		System.out.println("counter input path is "+new Path("counterinput").toUri().getRawPath());
		
		Job job = new Job(conf, "wordcount");
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);

		//job.setInputFormatClass(TextInputFormat.class);
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		//job.setNumReduceTasks(2);
		//job.setPartitionerClass(MyPartitioner.class);
		
		FileInputFormat.addInputPath(job, new Path("./counterinput"));
		// Erase previous run output (if any)
		/*FileSystem.get(conf).delete(new Path("counteroutput"), true);
		FileOutputFormat.setOutputPath(job, new Path("counteroutput"));*/
		
	
		job.setJarByClass(WordCount.class);	
		
		//FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.waitForCompletion(true);
	}
	
	public static ArrayList<Employee> getEmployeeList(){
		ArrayList<Employee> employeeList = new ArrayList<Employee>();
		Employee employee1 = new Employee("Sam","USA");
		employeeList.add(employee1);
		Employee employee2 = new Employee("Sam","UK");
		employeeList.add(employee2);
		Employee employee3 = new Employee("Sammy","USA");
		employeeList.add(employee3);
		Employee employee4 = new Employee("Sail","USA");
		employeeList.add(employee4);
		Employee employee5 = new Employee("Robert","UK");
		employeeList.add(employee5);
		Employee employee6 = new Employee("Fisher","Canada");
		employeeList.add(employee6);
		Employee employee7 = new Employee("Lincoln","USA");
		employeeList.add(employee7);
		
		return employeeList;
		
	}

}
