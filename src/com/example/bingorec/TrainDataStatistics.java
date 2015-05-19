package com.example.bingorec;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

public class TrainDataStatistics {

	/**
	 * @param args
	 */
	public static String dir = "C:/parsed";
	public static void main(String[] args)
	{
		Vector<Instance> trainData = loadTrainData(dir,"traindata.ser");
		
		int [] stats = new int[11];
		for(int i=0;i<stats.length;i++)
		{
			stats[i] = 0;
		}
		
		for(Instance inst : trainData)
		{
			double [] out = inst.getTarget();
			
			int target = Network.readANNOutput(out);
			if(target >= 0 && target <= 9)
			{
				stats[target]++;
			}
			else
			{
				stats[10]++;
			}
			
		}
		
		for(int i=0;i<stats.length;i++)
		{
			System.out.println("number " + i + " count: " + stats[i]);
		}
		

	}
	
	public static Vector<Instance> loadTrainData(String dir,String file)
    {
    	Vector<Instance> n = null;
        try
        {
           
           FileInputStream fileIn = new FileInputStream(dir+"/"+file);
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

}
