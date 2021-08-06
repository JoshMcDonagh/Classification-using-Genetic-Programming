package main.java.datasetAccess;

import java.io.FileNotFoundException;

/**
 * Implements accessibility of the Climate Model Simulation Crash Dataset.
 * @author Joshua McDonagh
 *
 */
public class ClimateModelSimCrashDataset extends Dataset
{
	private final String[] names = {
			"vconst_corr",
			"vconst_2",
			"vconst_3",
			"vconst_4",
			"vconst_5",
			"vconst_7",
			"ah_corr",
			"ah_bolus",
			"slm_corr",
			"efficiency_factor",
			"tidal_mix_max",
			"vertical_decay_scale",
			"convect_corr",
			"bckgrnd_vdc1",
			"bckgrnd_vdc_ban",
			"bckgrnd_vdc_eq",
			"bckgrnd_vdc_psim",
			"Prandtl",
			"outcome"
	};
	
	/**
	 * Constructor class which gets the external dataset.
	 * @throws FileNotFoundException
	 */
	ClimateModelSimCrashDataset() throws FileNotFoundException
	{
		setName("Climate Model Sim Crash Dataset");
		generateDataset("pop_failures.dat");
		generateDatasetExceptIDs(2);
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
