package containerselection;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.jgap.IChromosome;
import org.jgap.Population;

public class CreateSeqFile {
	


	public static void createSequnceFile(List<IChromosome> listOfChromosomes
								  )
										  throws Exception{
		Configuration config = new Configuration();
		FileSystem fileSystem = FileSystem.get(config);
		Path outputPath = new Path(Constants.SEQ_WRITE_FILE);
		FileSystem.get(config).delete(new Path(Constants.SEQ_WRITE_FILE), true);
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
		}
		swriter.close();
	}
	
	public static List<IChromosome> readSequnceFile() 
	 				throws Exception{
		Configuration config = new Configuration();
		FileSystem fileSystem = FileSystem.get(config);
		List<IChromosome> listOfChromosomes = new ArrayList<IChromosome>();
		IChromosome chrom;
		Path dPath = new Path(Constants.SEQ_READ_FILE);
		FileStatus[] fs = fileSystem.globStatus(dPath);
		for(FileStatus f: fs){
			Path inputPath = f.getPath();
			SequenceFile.Reader reader = new 
				SequenceFile.Reader(fileSystem,inputPath,config);
			IntWritable key = new IntWritable();
			BytesWritable value = new BytesWritable();
			
			while(reader.next(key,value)){
				chrom = ObjectConverter.toObject(value.getBytes());
				listOfChromosomes.add(chrom);
			}
			reader.close();
		}
		return listOfChromosomes;
	}
	
	public static void createPopSequenceFile(List <Population> subPops)
					throws Exception{
		Configuration config = new Configuration();
		FileSystem fileSystem = FileSystem.get(config);
		Path outputPath = new Path(Constants.SEQ_WRITE_FILE);
		FileSystem.get(config).delete(new Path(Constants.SEQ_WRITE_FILE), true);
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
			System.out.println("Population# "+i+" written");
		}
		swriter.close();
	}

	public static List <Population> readPopSequnceFile() 
				throws Exception{
		String error="";
		
		Configuration config = new Configuration();
		FileSystem fileSystem = FileSystem.get(config);
		List<Population> subPops = new ArrayList<Population>();
		Population pop;
		Path dPath = new Path(Constants.SEQ_READ_FILE);
		FileStatus[] fs = fileSystem.globStatus(dPath);
		for(FileStatus f: fs){
			Path inputPath = f.getPath();
			SequenceFile.Reader reader;
			try {
				File file = new File(Constants.SEQ_READ_FILE);
				file.createNewFile();
				error = "Absolute path = " + file.getAbsolutePath();
				reader = new SequenceFile.Reader(fileSystem,
						inputPath, config);
			} catch (FileNotFoundException e) {
				throw new FileNotFoundException(error);
			}
			IntWritable key = new IntWritable();
			BytesWritable value = new BytesWritable();
			while (reader.next(key, value)) {
				pop = ObjectConverter.toPopObject(value.getBytes());
				subPops.add(pop);
			}
			reader.close();
		}
		return subPops;
	}
}
