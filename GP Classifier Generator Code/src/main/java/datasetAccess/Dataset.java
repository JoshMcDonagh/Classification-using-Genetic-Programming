package main.java.datasetAccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import main.java.functionsAndTerminals.TerminalSet;
import main.java.utilities.Utilities;

/**
 * Abstract class which implements fundamental mechanisms for datasets and provides abstract structure for subclasses.
 * @author Joshua McDonagh
 *
 */
public abstract class Dataset 
{
	public abstract String[] getNames();
	
	private String name;
	
	private double[][] dataset;
	private double[][] datasetExceptIDs;
	private double[][] inputSet;
	private double[] outputSet;
	
	private double positiveClass;
	private double negativeClass;
	
	/**
	 * Returns the name of the dataset.
	 * @return String name of the dataset
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns all of the dataset.
	 * @return 2D double array of all the dataset
	 */
	public double[][] getAll() 
	{
		return dataset;
	}
	
	/**
	 * Returns all of the dataset excluding the ID columns.
	 * @return 2D double array of the dataset excluding the ID columns
	 */
	public double[][] getAllExceptIDs()
	{
		return datasetExceptIDs;
	}
	
	/**
	 * Returns the dataset with only the input features.
	 * @return 2D double array of the dataset with only the input features
	 */
	public double[][] getInput()
	{
		return inputSet;
	}
	
	/**
	 * Returns the dataset with only the output feature.
	 * @return Double array of the dataset with only the output feature.
	 */
	public double[] getOutput()
	{
		return outputSet;
	}
	
	/**
	 * Returns the positive class representation/label.
	 * @return Positive class representation as a double
	 */
	public double getPositiveClassRep()
	{
		return positiveClass;
	}
	
	/**
	 * Returns the negative class representation/label.
	 * @return Negative class representation as a double
	 */
	public double getNegativeClassRep()
	{
		return negativeClass;
	}
	
	/**
	 * Sets the 2D double array the dataset stores.
	 * @param newDataset	2D double array of data instances
	 */
	void setDataset(double[][] newDataset)
	{
		dataset = newDataset;
	}
	
	/**
	 * Sets the name of the dataset.
	 * @param newName	String of the dataset name
	 */
	void setName(String newName)
	{
		name = newName;
	}
	
	/**
	 * Generates a dataset from an external file.
	 * @param fileName	String containing the file name of the external dataset file
	 * @throws FileNotFoundException
	 */
	void generateDataset(String fileName) throws FileNotFoundException
	{
		String fileDirectory = getClass().getResource("/main/datasets/" + fileName).getPath().replace("%20", " ");
		File datasetFile = new File(fileDirectory);
		Scanner reader = new Scanner(datasetFile);
		ArrayList<double[]> newDataset = new ArrayList<double[]>();
		
		// Reads each line of the external file
		while (reader.hasNextLine())
		{
			String[] strDataInstance = reader.nextLine().split(",");
			double[] dblDataInstance = new double[strDataInstance.length];
			boolean isInvalidInstance = false;
			
			// Parses each numerical value as a double, and rejects instances with incomplete data
			for (int i = 0; i <strDataInstance.length; i++)
			{
				try
				{
					dblDataInstance[i] = Double.parseDouble(strDataInstance[i]);
				}
				catch (NumberFormatException e)
				{
					isInvalidInstance = true;
					break;
				}
			}
			
			if (isInvalidInstance)
				continue;
			
			newDataset.add(dblDataInstance);
		}
		
		reader.close();
		
		dataset = Utilities.convertDoubleArrayListToArray(newDataset);
	}
	
	/**
	 * Generates the feature variable terminals in the terminal set.
	 */
	void generateTerminals()
	{
		for (int i = 0; i < getNames().length-1; i++)
			TerminalSet.addFeatureVariable(getNames()[i], i);
	}
	
	/**
	 * Generates the dataset 2D double array without the ID columns.
	 * @param startOffset	Integer offset to start setting dataset columns
	 */
	void generateDatasetExceptIDs(int startOffset)
	{
		int newRowLength = getAll()[0].length - startOffset;
		datasetExceptIDs = new double[getAll().length][newRowLength];
		
		for (int i = 0; i < getAll().length; i++)
			for (int j = 0; j < newRowLength; j++)
				datasetExceptIDs[i][j] = getAll()[i][j + startOffset];
	}
	
	/**
	 * Generates the dataset 2D double array with only the input feature columns.
	 */
	void generateInputSet()
	{
		int newRowLength = getAllExceptIDs()[0].length - 1;
		inputSet = new double[getAllExceptIDs().length][newRowLength];
		
		for (int i = 0; i < getAllExceptIDs().length; i++)
			for (int j = 0; j < newRowLength; j++)
				inputSet[i][j] = getAllExceptIDs()[i][j];
	}
	
	/**
	 * Generates the dataset 2D double array with only the output feature column.
	 */
	void generateOutputSet()
	{
		int classIndex = getAllExceptIDs()[0].length - 1;
		outputSet = new double[getAllExceptIDs().length];
		
		for (int i = 0; i < getAllExceptIDs().length; i++)
			outputSet[i] = getAllExceptIDs()[i][classIndex];
	}
	
	/**
	 * Finds and identifies all class representations of the dataset.
	 */
	void identifyClasses()
	{
		final double unidentified = -500;
		
		double[] classExamples = getOutput();
		double identifiedClassA = unidentified;
		double identifiedClassB = unidentified;
		
		// Finds different class representations of binary classification dataset
		for (int i = 0; i < classExamples.length; i++)
		{
			if (identifiedClassA == unidentified)
				identifiedClassA = classExamples[i];
			
			else if (classExamples[i] != identifiedClassA)
			{
				identifiedClassB = classExamples[i];
				break;
			}
		}
		
		int classACount = 0;
		int classBCount = 0;
		
		// Counts the number of dataset instances for each class label
		for (int i = 0; i < classExamples.length; i++)
		{
			if (classExamples[i] == identifiedClassA)
				classACount++;
			else
				classBCount++;
		}
		
		// Sets the positive class label to the class label that occurs in the minority cases
		if (classACount < classBCount)
		{
			positiveClass = identifiedClassA;
			negativeClass = identifiedClassB;
		}
		else
		{
			positiveClass = identifiedClassB;
			negativeClass = identifiedClassA;
		}
	}
}
