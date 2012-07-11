package containerselection;


import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.jgap.IChromosome;
import org.jgap.Population;

public class CreateSeqFile {
	


	public static void createSequnceFile(List<IChromosome> listOfChromosomes
								  )
										  throws Exception{
		//BufferedWriter bw = new BufferedWriter(new FileWriter("sequencefilelog"));
		Configuration config = new Configuration();
		FileSystem fileSystem = FileSystem.get(config);
		Path outputPath = new Path(Constants.SEQ_WRITE_FILE);
		System.out.println("Call in Create Sequnce File Writer");
		//System.out.println("listOfChromosomes.size() "+listOfChromosomes.size());
		SequenceFile.Writer swriter = 
				 SequenceFile.createWriter(fileSystem, config, outputPath,
			     IntWritable.class, BytesWritable.class,
			    SequenceFile.CompressionType.BLOCK);
		for (int i=0;i<listOfChromosomes.size();i++){
			IntWritable key = new IntWritable(i);
			IChromosome chrom = listOfChromosomes.get(i);
			BytesWritable value = new BytesWritable(ObjectConverter.toBytes(chrom));
			swriter.append(key,value);
			System.out.println("Chromosome# "+i+" written");
			//bw.write("Chromosome# "+i+" written");
		}
		swriter.close();
	}
	
	public static List<IChromosome> readSequnceFile() 
	 				throws Exception{
		Configuration config = new Configuration();
		FileSystem fileSystem = FileSystem.get(config);
		Path inputPath = new Path(Constants.SEQ_READ_FILE);
		SequenceFile.Reader reader = new 
			SequenceFile.Reader(fileSystem,inputPath,config);
		IntWritable key = new IntWritable();
		BytesWritable value = new BytesWritable();
		List<IChromosome> listOfChromosomes = new ArrayList<IChromosome>();
		IChromosome chrom;
		while(reader.next(key,value)){
			chrom = ObjectConverter.toObject(value.getBytes());
			listOfChromosomes.add(chrom);
		}
		reader.close();
		return listOfChromosomes;
	}
	
	public static void createPopSequnceFile(List <Population> subPops)
					throws Exception{
		Configuration config = new Configuration();
		FileSystem fileSystem = FileSystem.get(config);
		Path outputPath = new Path(Constants.SEQ_WRITE_FILE);
		System.out.println("Call in Create Sequnce File Writer");
		SequenceFile.Writer swriter = 
				 SequenceFile.createWriter(fileSystem, config, outputPath,
			     IntWritable.class, BytesWritable.class,
			    SequenceFile.CompressionType.BLOCK);
		for (int i=0;i<subPops.size();i++){
			IntWritable key = new IntWritable(i);
			Population pop = subPops.get(i);
			BytesWritable value = new BytesWritable(ObjectConverter.toPopBytes(pop));
			swriter.append(key, value);
		}
	}

	public static List <Population> readPopSequnceFile() 
				throws Exception{
		Configuration config = new Configuration();
		FileSystem fileSystem = FileSystem.get(config);
		Path inputPath = new Path(Constants.SEQ_READ_FILE);
		SequenceFile.Reader reader = new 
			SequenceFile.Reader(fileSystem,inputPath,config);
		IntWritable key = new IntWritable();
		BytesWritable value = new BytesWritable();
		List<Population> subPops = new ArrayList<Population>();;
		Population pop;
		while(reader.next(key,value)){
			pop = ObjectConverter.toPopObject(value.getBytes());
			subPops.add(pop);
		}
		reader.close();
		return subPops;
	}
}
