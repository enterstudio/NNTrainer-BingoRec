package com.example.bingorec;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Vector;


public class NNTrainer {

	/**
	 * @param args
	 */
	
	private static String workingDir = "C:/parsed";
	
	private static String logFile = workingDir + "/results.txt";
	
	private static Vector<Instance> trainingSet = new Vector<Instance>();
	private static Vector<Instance> validationSet = new Vector<Instance>();
	
	public static void pickTrainData(int iteration,Vector<Instance> trainData)
	{
		int total = trainData.size();
		int tenth = total / 10;
		int exclude_start = iteration * tenth;
		int exclude_end = exclude_start + tenth;
		
		trainingSet = new Vector<Instance>();
		validationSet = new Vector<Instance>();
		
		for(int i=0;i<trainData.size();i++)
		{
			Instance inst = trainData.get(i);
			if(i >= exclude_start && i < exclude_end)
			{
				validationSet.add(inst);
			}
			else // if(i < exclude_start || i >= exclude_end)
			{
				
				trainingSet.add(inst);
			}
		}
		
		
		
	}
	
	public static void MultiNetworkTrainAndValidate(int iteration)
	{
		MultiNetwork multinet = new MultiNetwork(workingDir);
		
		multinet.trainNetworks(trainingSet);
		
		
		String result = "";
        result += "Multi network iteration: " + iteration + "\n";
        result += "Training data size: " + trainingSet.size() + "\n";
        result += "Validation set size: " + validationSet.size() + "\n";
        
        double [] trainAccuracy = multinet.evaluateTrainAccuracy();
        
        result += "Training accuracy: \n";
        for(int i=0;i<trainAccuracy.length;i++)
        {
        	result += "number " + i + " accuracy " + trainAccuracy[i] + " % \n";
        }
        
        int [] trainTotal = multinet.evaluateTotalTrainAccuracy();
        
        double dTotalTrain = (double)(trainTotal[0]) / (double)(trainTotal[1]) * 100.0;
        
        result += "Training total accuracy: " + dTotalTrain + "\n";
        
        double [] finalAccuracy = multinet.evaluateAccuracy(validationSet);
        String strFinal = "";
        for(int i=0;i<finalAccuracy.length;i++)
        {
        	strFinal += "number " + i + " accuracy " + finalAccuracy[i] + " % \n";
        }
        result += "Final accuracy: " + strFinal + "\n";
        int [] totalAcc = multinet.evaluateTotalAccuracy(validationSet);
        result += "Multinet correct " + totalAcc[0] + " out of " + totalAcc[1] + "\n";
        double dTotalAcc = (double)(totalAcc[0]) / (double)(totalAcc[1]) * 100.0;
        result += "Total accuracy: " + dTotalAcc + "\n";
        result += "\n";
        logResult(result);
	}
	
	public static void SingleNetworkTrainAndValidate(int iteration)
	{
		Network net = new Network(324, 60, 10);
        
        double accuracy = 0;
        double targetAccuracy = 99.0;
        int iterations = 1;
        while (accuracy < targetAccuracy)
        {
            net.train(trainingSet);
            
            accuracy = net.evaluateAccuracy(trainingSet);
            System.out.println("accuracy: " + accuracy);
            iterations++;
            
            if (iterations > 2500)
            {
            	System.out.println("Out of iterations");
            	break;
            }
            
            if(iterations % 30 == 0)
            {
            	System.out.println("Saving network!");
            	Network.saveNetwork(workingDir,net);
            }
            
        }
        
        String result = "";
        result += "Single network iteration: " + iteration + "\n";
        result += "Training data size: " + trainingSet.size() + "\n";
        result += "Validation set size: " + validationSet.size() + "\n";
        result += "Accuracy on training set: " + net.evaluateAccuracy(trainingSet) + "\n";
        double finalAccuracy = net.evaluateAccuracy(validationSet);
        result += "Final accuracy: " + finalAccuracy + "\n";
        result += "\n";
        logResult(result);
	}
	
	public static void main(String[] args) 
	{
		
		Vector<Instance> trainData;
		
		trainData = loadTrainData(workingDir);
		
		System.out.println(trainData.size());
		
		
		for(int i=0;i<10;i++)
		{
			pickTrainData(i,trainData);
			SingleNetworkTrainAndValidate(i);
			MultiNetworkTrainAndValidate(i);
		}
        
      

	}
	
	
	public static void saveTrainData(String dir,Vector<Instance> data)
    {
    	try
        {
           FileOutputStream fileOut =
           new FileOutputStream(dir+"/traindata.ser");
           ObjectOutputStream out = new ObjectOutputStream(fileOut);
           out.writeObject(data);
           out.close();
           fileOut.close();
           
        }catch(IOException i)
        {
            throw new RuntimeException(i);
        }
    }
    
    public static Vector<Instance> loadTrainData(String dir)
    {
    	Vector<Instance> n = null;
        try
        {
           
           FileInputStream fileIn = new FileInputStream(dir+"/traindata.ser");
           ObjectInputStream in = new ObjectInputStream(fileIn);
           n = (Vector<Instance>) in.readObject();
           in.close();
           fileIn.close();
           return n;
        }
        catch(Exception e)
        {
          throw new RuntimeException(e);
        }
        
    }
    
    public static void logResult(String str)
    {
    	
    	try {
    	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));
    	    out.println(str);
    	    out.close();
    	} catch (IOException e) 
    	{
    	 System.out.println("Could not write to file: " + e.getMessage());
    	 System.exit(-1);
    	}
    }

}
