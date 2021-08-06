package main.java.datasetAccess;

import java.io.FileNotFoundException;

/**
 * Class controls the access and use of datasets.
 * @author Joshua McDonagh
 *
 */
public class DatasetController 
{
	private static BreastCancerDataset breastCancerDataset = null;
	
	/**
	 * Static method which returns the Breast Cancer Diagnosis dataset and sets this dataset to the current dataset.
	 * @return Breast Cancer Diagnosis dataset
	 * @throws FileNotFoundException
	 */
	public static BreastCancerDataset getBreastCancerDataset() throws FileNotFoundException
	{
		return getBreastCancerDataset(true);
	}
	
	/**
	 * Static method which returns the Breast Cancer Diagnosis dataset and sets this dataset to the current dataset based on a given input.
	 * @param setAsCurrent	Boolean which determines if the dataset is to be set as currently used (true) or not (false)
	 * @return Breast Cancer Diagnosis dataset
	 * @throws FileNotFoundException
	 */
	public static BreastCancerDataset getBreastCancerDataset(boolean setAsCurrent) throws FileNotFoundException
	{
		breastCancerDataset = new BreastCancerDataset();
		
		if (setAsCurrent)
			currentDataset = breastCancerDataset;
		
		return breastCancerDataset;
	}
	
	private static ClimateModelSimCrashDataset climateModelSimCrashDataset = null;
	
	/**
	 * Static method which returns the Climate Model Simulation Crash dataset and sets this dataset to the current dataset.
	 * @return Climate Model Simulation Crash dataset
	 * @throws FileNotFoundException
	 */
	public static ClimateModelSimCrashDataset getClimateModelSimCrashDataset() throws FileNotFoundException
	{
		return getClimateModelSimCrashDataset(true);
	}
	
	/**
	 * Static method which returns the Climate Model Simulation dataset and sets this dataset to the current dataset based on a given input.
	 * @param setAsCurrent	Boolean which determines if the dataset is to be set as currently used (true) or not (false)
	 * @return Climate Model Simulation Crash dataset
	 * @throws FileNotFoundException
	 */
	public static ClimateModelSimCrashDataset getClimateModelSimCrashDataset(boolean setAsCurrent) throws FileNotFoundException
	{
		climateModelSimCrashDataset = new ClimateModelSimCrashDataset();
		
		if (setAsCurrent)
			currentDataset = climateModelSimCrashDataset;
		
		return climateModelSimCrashDataset;
	}
	
	private static Dataset currentDataset = null;
	
	/**
	 * Static method which returns the dataset that is set as currently being used.
	 * @return Dataset that is currentl being used
	 */
	public static Dataset getCurrentDataset()
	{
		return currentDataset;
	}
}
