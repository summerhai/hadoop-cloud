package counter;

import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;

public class FilesPacker {

	private Configuration conf;

	public FilesPacker(Configuration conf) {
		this.conf = conf;
	}

	/** Pack all files in a directory into a Hadoop SequenceFile */
	public void packAsSequenceFile(ArrayList<Employee> employees, String output)
			throws Exception {
		System.out.println("Packing Sequence File");
		
		Path outputPath = new Path(output);
		
		System.out.println("Output path is " + outputPath.toUri().getRawPath());
		
		FileSystem fileSystem = FileSystem.get(conf);
		SequenceFile.Writer swriter = SequenceFile.createWriter(fileSystem,
				conf, outputPath, IntWritable.class, BytesWritable.class,
				SequenceFile.CompressionType.BLOCK);
		Integer count = 0;
		try {
			for (Employee employee : employees) {
				IntWritable key = new IntWritable(count);
				BytesWritable value = new BytesWritable(EmployeeConvertor
						.toBytes(employee));
				swriter.append(key, value);
				count++;

			}
		} finally {
			if (swriter != null) {
				swriter.close();
			}
		}
	} // packAsSequenceFile

}