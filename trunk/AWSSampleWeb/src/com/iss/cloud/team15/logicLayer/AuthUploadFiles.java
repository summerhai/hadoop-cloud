package com.iss.cloud.team15.logicLayer;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.iss.cloud.team15.util.Configuration;

public class AuthUploadFiles {
	private String policy;
	private String signature;
	private String accessKey;
	
	public void setPolicy(String policy){
		this.policy = policy;
	}
	
	public String getPolicy(){
		return this.policy;
	}
	
	public void setSignature(String signature){
		this.signature = signature;
	}
	
	public String getSignature(){
		return this.signature;
	}
	
	public void setAccessKey(String accessKey){
		this.accessKey = accessKey;
	}
	
	public String getAccessKey(){
		this.accessKey = getKey();
		return this.accessKey;
	}
	
	public void authUpload(){
		//policy = (new BASE64Encoder()).encode(policy_document.getBytes("UTF-8")).replaceAll("\n","").replaceAll("\r","");
		//signature = (new BASE64Encoder()).encode(hmac.doFinal(policy.getBytes("UTF-8"))).replaceAll("\n", "");
		try {
			setPolicy((new BASE64Encoder()).encode(getPolicyDocument().getBytes("UTF-8")).replaceAll("\n","").replaceAll("\r",""));
			Mac hmac = Mac.getInstance("HmacSHA1");	
			hmac.init(new SecretKeySpec(getSecret().getBytes("UTF-8"), "HmacSHA1"));			
			setSignature((new BASE64Encoder()).encode(hmac.doFinal(policy.getBytes("UTF-8"))).replaceAll("\n", ""));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}
	
	private static String getKey () {
		Configuration config = Configuration.getInstance();
		return config.getProperty("accessKey");
	}
	
	private static String getSecret () {
		Configuration config = Configuration.getInstance();
		return config.getProperty("secretKey");
	}
	
	private static String getPolicyDocument(){
		File file = new File("policy_document.json");
		int ch;
		StringBuffer strContent = new StringBuffer("");
	    FileInputStream fin = null;
	    try {
	    	fin = new FileInputStream(file);
	    	while ((ch = fin.read()) != -1)
	    		strContent.append((char) ch);
	    	fin.close();
	    } catch (Exception e) {
	    	System.out.println(e);
	    }
	    
	    return strContent.toString();
	}
}
