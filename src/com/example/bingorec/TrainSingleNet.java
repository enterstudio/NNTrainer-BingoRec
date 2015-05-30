package com.example.bingorec;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

public class TrainSingleNet {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{

		String dir = "C:/parsed";
		
		Vector<Instance> data = loadTrainData(dir);
		
		Network net = new Network(324, 60, 10);
        
        double accuracy = 0;
        double targetAccuracy = 99.0;
        int iterations = 1;
        while (accuracy < targetAccuracy)
        {
            net.train(data);
            
            accuracy = net.evaluateAccuracy(data);
            System.out.println("accuracy: " + accuracy);
            iterations++;
            
            if (iterations > 1000)
            {
            	System.out.println("Out of iterations");
            	break;
            }
            
           
            
        }
        
        Network.saveNetwork(dir,net);

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

}
