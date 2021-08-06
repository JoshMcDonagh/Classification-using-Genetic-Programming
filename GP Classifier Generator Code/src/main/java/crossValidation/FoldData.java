package main.java.crossValidation;

import java.util.ArrayList;

import main.java.datasetAccess.CustomDataset;
import main.java.datasetAccess.Dataset;
import main.java.utilities.Utilities;

/**
 * Controls the data stored for each fold of the dataset.
 * @author Joshua McDonagh
 *
 */
public class FoldData 
{
	private ArrayList<ArrayList<double[]>> foldData = new ArrayList<ArrayList<double[]>>();
	private String[] names;
	
	/**
	 * Constructor class.
	 * @param k			Total number of folds
	 * @param dataset	Dataset to be split into folds
	 */
	public FoldData(int k, Dataset dataset)
	{
		names = dataset.getNames();
		
		for (int i = 0; i < k; i++)
			foldData.add(new ArrayList<double[]>());
		
		ArrayList<double[]> positiveCases = new ArrayList<double[]>();
		ArrayList<double[]> negativeCases = new ArrayList<double[]>();
		
		for (int i = 0; i < dataset.getAllExceptIDs().length; i++)
		{
			double[] instance = dataset.getAllExceptIDs()[i];
			
			if (instance[instance.length-1] == dataset.getPositiveClassRep())
				positiveCases.add(instance);
			else
				negativeCases.add(instance);
		}
		
		//Collections.shuffle(positiveCases, Utilities.getRandom());
		//Collections.shuffle(negativeCases, Utilities.getRandom());
		
		// Add positive cases to folds
		for (int i = 0; i < positiveCases.size(); i++)
		{
			int iFold = i % k;
			foldData.get(iFold).add(positiveCases.get(i));
		}
		
		// Add negative cases to folds
		for (int i = 0; i < negativeCases.size(); i++)
		{
			int iFold = i % k;
			foldData.get(iFold).add(negativeCases.get(i));
		}
	}
	
	/**
	 * Returns the training folds as a single dataset given a certain fold is set as the testing set.
	 * @param testingIndex	Fold index set-aside for testing
	 * @return Training dataset
	 */
	public Dataset getTrainingSet(int testingIndex)
	{
		ArrayList<double[]> newDataset = new ArrayList<double[]>();
		
		for (int i = 0; i < foldData.size(); i++)
		{
			if (i == testingIndex)
				continue;
			
			for (int j = 0; j < foldData.get(i).size(); j++)
				newDataset.add(foldData.get(i).get(j));
		}
		
		double[][] dataset = Utilities.convertDoubleArrayListToArray(newDataset);
		return new CustomDataset(names, dataset, 0);
	}
	
	/**
	 * Returns the testing fold as a dataset.
	 * @param testingIndex	Fold index set-aside for testing
	 * @return Testing dataset
	 */
	public Dataset getTestingSet(int testingIndex)
	{
		double[][] dataset = Utilities.convertDoubleArrayListToArray(foldData.get(testingIndex));
		return new CustomDataset(names, dataset, 0);
	}
}
