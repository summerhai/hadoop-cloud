package counter;

import java.io.IOException;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class Map extends Mapper<IntWritable, BytesWritable, Text, IntWritable>{
	/*private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
        
    public void map(LongWritable key, Object value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        StringTokenizer tokenizer = new StringTokenizer(line);
        while (tokenizer.hasMoreTokens()) {
            word.set(tokenizer.nextToken());
            context.write(word, one);
        }
    }*/
	
	private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
	
	public void map(IntWritable key, BytesWritable value, Context context)throws IOException, InterruptedException{
		Employee employee = EmployeeConvertor.toEmployee(value.getBytes());
		System.out.println("Name: "+ employee.getName() );
		word.set(employee.getAddress());
		context.write(word,one);
	}
	
}
