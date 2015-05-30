package com.example.bingorec;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

public class TrainMultiNet {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String dir = "C:/parsed";
		
		Vector<Instance> dat = loadTrainData(dir);

		
		MultiNetwork multinet = new MultiNetwork(dir);
		
		multinet.trainNetworks(dat);
		
		multinet.saveNetworks();
		
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
