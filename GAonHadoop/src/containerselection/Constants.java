package containerselection;

public final class Constants {
	
	public static final String JOB1_INPUTFILE = "SeqInput";
	public static final String JOB1_OUTPUTFILE = "output";
	
	public static final String JOB2_INPUTFILE = "SeqInput";
	public static final String JOB2_OUTPUTFILE = "output";
	
	public static final String SEQ_WRITE_FILE = "SeqInput/empty.txt";
	public static final String SEQ_READ_FILE = "output/part-r-00000";
	
	public static final String CSV_FILE = "input/containersel.csv";
	public static final String OUPUT_FILE = "output/selectedcontainers.csv";
	
	public static final int CHROMOSOME_SIZE = 64;
	public static final int POPULATION_SIZE = 50;
	public static final int NUMBER_OF_EVOLUTIONS = 20;
	
	
	/**
	 * For Amazon purposes
	 * 
	*/
/*	public static final String JOB1_INPUTFILE = "s3n://iss.hadoop/seqInput";
	public static final String JOB1_OUTPUTFILE = "s3n://iss.hadoop/output/job1_output";
	
	public static final String JOB2_INPUTFILE = "";
	public static final String JOB2_OUTPUTFILE = "";
	
	public static final String SEQ_WRITE_FILE = "s3n://iss.hadoop/seqInput/empty.txt";
	public static final String SEQ_READ_FILE = "s3n://iss.hadoop/output/job1_output/part-r-00000";
	
	public static final String CSV_FILE = "s3n://iss.hadoop/input/containersel.csv";*/
	
	public static final int NUMBER_OF_SUBPOPULATIONS = 3;
	
}
