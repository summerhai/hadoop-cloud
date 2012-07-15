package counter;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {

    public void reduce(Text key, Iterable<IntWritable> values, Context context) 
      throws IOException, InterruptedException {
        int sum = 0;
        System.out.println("Key Reducer: " + key.toString());
        for (IntWritable val : values) {
        	
            sum += val.get();
        }
        context.write(key, new IntWritable(sum));
    }
}