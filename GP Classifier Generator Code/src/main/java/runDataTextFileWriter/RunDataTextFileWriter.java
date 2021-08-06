package main.java.runDataTextFileWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import main.java.datasetAccess.DatasetController;
import main.java.utilities.Utilities;

/**
 * Singleton class which deals with the writing of each GP run to corresponding individual text files.
 * @author Joshua McDonagh
 *
 */
public class RunDataTextFileWriter 
{
	private static RunDataTextFileWriter instance = null;
	
	/**
	 * Static method which returns (and initialises if run first time) an instance of RunDataTextFileWriter.
	 * @return Instance of RunDataTestFileWriter
	 */
	public static RunDataTextFileWriter get()
	{
		if (instance == null)
			instance = new RunDataTextFileWriter();
		
		return instance;
	}
	
	private boolean isBroken = false;
	
	private BufferedWriter writer;
	
	/**
	 * Constructor method which opens the writer.
	 */
	private RunDataTextFileWriter()
	{
		try 
		{
			String dateAndTime = Utilities.getDateAndTime();
			String datasetName = DatasetController.getCurrentDataset().getName();
			File runDataFile = new File("src/main/runData/" + datasetName + "_" + dateAndTime + ".txt");
			runDataFile.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(runDataFile);
			writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
			writer.write(dateAndTime);
			writer.newLine();
		} 
		catch (IOException e) 
		{
			isBroken = true;
			e.printStackTrace();
		}
	}
	
	/**
	 * Method which closes the writer.
	 */
	public void close()
	{
		if (isBroken)
			return;
		
		try 
		{
			writer.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		instance = null;
	}
	
	/**
	 * Method which allows the ability to write a new line to the opened text file.
	 * @param text	String text to add as a line in the opened text file
	 */
	public void write(String text)
	{
		if (isBroken)
			return;
		
		try 
		{
			writer.write(text);
			writer.newLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
