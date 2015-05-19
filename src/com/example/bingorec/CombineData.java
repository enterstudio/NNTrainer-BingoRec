package com.example.bingorec;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class CombineData {

	/**
	 * @param args
	 */
	
	static String dir = "C:/parsed";
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

		Vector<Instance> allData = new Vector<Instance>();
		for(int i=0;i<4;i++)
		{
			Vector<Instance> fileData = loadTrainData(dir,"traindata_ticket_"+i+".ser");
			allData.addAll(fileData);
		}
		
		saveTrainData(dir,allData);
		
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
