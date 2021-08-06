package main.java.datasetAccess;

import java.io.FileNotFoundException;

/**
 * Implements accessibility of the Breast Cancer Dataset.
 * @author Joshua McDonagh
 *
 */
public class BreastCancerDataset extends Dataset
{
	private final String[] names = {
			"ClumpThickness",
			"UniformityOfCellSize",
			"UniformityOfCellShape",
			"MarginalAdhesion",
			"SingleEpithelialCellSize",
			"BareNuclei",
			"BlandChromatin",
			"NormalNucleoli",
			"Mitoses",
			"Class"
	};
	
	/**
	 * Constructor class which gets the external dataset.
	 * @throws FileNotFoundException
	 */
	BreastCancerDataset() throws FileNotFoundException
	{
		setName("Breast Cancer Dataset");
		generateDataset("breast-cancer-wisconsin.data");
		generateDatasetExceptIDs(1);
		generateInputSet();
		generateOutputSet();
		identifyClasses();
		generateTerminals();
	}
	
	/**
	 * Returns an array of feature names for the dataset.
	 * @return String array of feature names
	 */
	@Override
	public String[] getNames()
	{
		return names;
	}
}
