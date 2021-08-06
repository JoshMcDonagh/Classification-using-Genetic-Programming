package main.java.paramTuning;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import main.java.utilities.Utilities;

/**
 * Singleton class which deals with the writing of each parameter tuning run to corresponding text files.
 * @author Joshua McDonagh
 *
 */
public class ParamTuningTextFileWriter 
{
	private static ParamTuningTextFileWriter instance = null;
	
	/**
	 * Static method which returns (and initialises if run first time) an instance of ParamTuningTextFileWriter.
	 * @return Instance of ParamTuningTextFileWriter
	 */
	public static ParamTuningTextFileWriter get()
	{
		if (instance == null)
			instance = new ParamTuningTextFileWriter();
		
		return instance;
	}
	
	private boolean isBroken = false;
	
	private BufferedWriter writer;
	
	/**
	 * Constructor method which opens the writer.
	 */
	private ParamTuningTextFileWriter()
	{
		try 
		{
			String dateAndTime = Utilities.getDateAndTime();
			File runDataFile = new File("src/main/runData/ParamTunedResults_" + dateAndTime + ".txt");
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
