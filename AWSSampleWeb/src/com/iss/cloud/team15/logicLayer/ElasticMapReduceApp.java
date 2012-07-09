package com.iss.cloud.team15.logicLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduce;
import com.amazonaws.services.elasticmapreduce.AmazonElasticMapReduceClient;
import com.amazonaws.services.elasticmapreduce.model.AddJobFlowStepsRequest;
import com.amazonaws.services.elasticmapreduce.model.DescribeJobFlowsRequest;
import com.amazonaws.services.elasticmapreduce.model.DescribeJobFlowsResult;
import com.amazonaws.services.elasticmapreduce.model.HadoopJarStepConfig;
import com.amazonaws.services.elasticmapreduce.model.JobFlowDetail;
import com.amazonaws.services.elasticmapreduce.model.JobFlowExecutionState;
import com.amazonaws.services.elasticmapreduce.model.StepConfig;
import com.iss.cloud.team15.util.Configuration;

public class ElasticMapReduceApp {
	private static final String jobflowID = "j-U2S4P8HQC0CP";
	private static final String BUCKET_NAME = "iss.wordcount";
	private static final String S3N_HADOOP_JAR = "s3n://iss.wordcount/java/wordcount.jar";
	//private static final String S3N_LOG_URI = "s3n://" + BUCKET_NAME + "/logs";
	private static final UUID RANDOM_UUID = UUID.randomUUID();
	private static final String STEP_NAME = "wordcount-" + RANDOM_UUID.toString();
	private static final List<JobFlowExecutionState> DONE_STATES = Arrays
			.asList(new JobFlowExecutionState[] {
					JobFlowExecutionState.COMPLETED,
					JobFlowExecutionState.FAILED,
					JobFlowExecutionState.TERMINATED
			});
	
	private static final String[] JOB_ARGS =
	        new String[] { "s3n://" + BUCKET_NAME + "/input/Gitanjali.txt",
	                      "s3n://" + BUCKET_NAME + "/output/" + STEP_NAME};
	private static final List<String> ARGS_AS_LIST = Arrays.asList(JOB_ARGS);
	    
	static AmazonElasticMapReduce emr;

	/**
	 * The only information needed to create a client are security credentials
	 * consisting of the AWS Access Key ID and Secret Access Key. All other
	 * configuration, such as the service end points, are performed
	 * automatically. Client parameters, such as proxies, can be specified in an
	 * optional ClientConfiguration object when constructing a client.
	 * 
	 * @see com.amazonaws.auth.BasicAWSCredentials
	 * @see com.amazonaws.auth.PropertiesCredentials
	 * @see com.amazonaws.ClientConfiguration
	 */
	private static void init() throws Exception {		
		AWSCredentials credentials = new BasicAWSCredentials(getKey(), getSecret());		
		emr = new AmazonElasticMapReduceClient(credentials);

		emr.setEndpoint("elasticmapreduce.ap-southeast-1.amazonaws.com");
	}

	public static void run(String[] args) throws Exception {

		System.out.println("===========================================");
		System.out.println("Welcome to the Elastic Map Reduce!");
		System.out.println("===========================================");		
		
		init();
		try {
			AddJobFlowStepsRequest request = new AddJobFlowStepsRequest();
			String stepName = "Step" + System.currentTimeMillis();
			System.err.println(stepName);

			request.setJobFlowId(jobflowID);

			ArrayList<StepConfig> steps = new ArrayList<StepConfig>();
			StepConfig stepConfig = new StepConfig();
			stepConfig.setActionOnFailure("CANCEL_AND_WAIT");
			HadoopJarStepConfig jarsetup = new HadoopJarStepConfig();
			
			jarsetup.setArgs(ARGS_AS_LIST);
			jarsetup.setJar(S3N_HADOOP_JAR);

			stepConfig.setHadoopJarStep(jarsetup);

			stepConfig.setName(stepName);
			steps.add(stepConfig);

			request.setSteps(steps);
			System.out.println("Adding Steps to Job Flow");
			emr.addJobFlowSteps(request);
			String lastState = "";
			STATUS_LOOP: while (true) {
				DescribeJobFlowsRequest desc = new DescribeJobFlowsRequest(
						Arrays.asList(new String[] { jobflowID }));
				DescribeJobFlowsResult descResult = emr.describeJobFlows(desc);
				for (JobFlowDetail detail : descResult.getJobFlows()) {
					String state = detail.getExecutionStatusDetail().getState();
					if (isDone(state)) {
						System.out.println("Job " + state + ": "+ detail.toString());
						break STATUS_LOOP;
					} else if (!lastState.equals(state)) {
						
						System.out.println("Job " + state + " at "+ new Date().toString());
						System.out.println("Details: " + detail.toString());
						if(JobFlowExecutionState.fromValue(lastState) ==JobFlowExecutionState.RUNNING 
								&& JobFlowExecutionState.fromValue(state) ==JobFlowExecutionState.WAITING){
							break STATUS_LOOP;
						}else
							lastState = state;
					}
				}

				Thread.sleep(10000);
			}
		} catch (AmazonServiceException ase) {

			System.out.println("Caught Exception: " + ase.getMessage());
			System.out.println("Reponse Status Code: " + ase.getStatusCode());
			System.out.println("Error Code: " + ase.getErrorCode());
			System.out.println("Request ID: " + ase.getRequestId());
		} finally {

		}

	}
	
	 /**
     * @param state
     * @return
     */
    private static boolean isDone(String value)
    {
        JobFlowExecutionState state = JobFlowExecutionState.fromValue(value);
        return DONE_STATES.contains(state);
    }
    
    private static String getKey () {
		Configuration config = Configuration.getInstance();
		return config.getProperty("accessKey");
	}

    private static String getSecret () {
		Configuration config = Configuration.getInstance();
		return config.getProperty("secretKey");
	}
}
