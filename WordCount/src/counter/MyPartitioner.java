package counter;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class MyPartitioner extends Partitioner<Text, IntWritable> {

	@Override
	public int getPartition(Text key, IntWritable value, int numValues) {

		// TODO Auto-generated method stub

		System.out.println("Number of Partitioners is : "+numValues);

		int length = key.getLength();

		if (length > 3) {

			return 0;

		} else {

			return 1;

		}

	}
}
