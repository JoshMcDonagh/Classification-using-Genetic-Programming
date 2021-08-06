package main.java.datasetAccess;

/**
 * Class which handles datasets that are generated within the GP system and aren't loaded in from an external file.
 * @author Joshua McDonagh
 *
 */
public class CustomDataset extends Dataset
{
	private final String[] names;
	
	/**
	 * Constructor class that generates a new dataset.
	 * @param newNames		String array of feature names
	 * @param dataset		Dataset to utilise data from
	 * @param startOffset	Column offset of which to start carrying over dataset columns from
	 */
	public CustomDataset(String[] newNames, double[][] dataset, int startOffset)
	{
		names = newNames;
		setDataset(dataset);
		generateDatasetExceptIDs(startOffset);
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
