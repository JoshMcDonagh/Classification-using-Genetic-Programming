package main.java.paramTuning;

/**
 * Singleton class stores the parameter information for parameter tuning.
 * @author Joshua McDonagh
 *
 */
public class ParamTuningSettings 
{
	private static ParamTuningSettings instance = null;
	
	/**
	 * Static method returns (and initialises if run first time) an instance of ParamTuningSettings.
	 * @return Instance of ParamTuningSettings
	 */
	public static ParamTuningSettings get()
	{
		if (instance == null)
			instance = new ParamTuningSettings();
		
		return instance;
	}
	
	private boolean runBreastCancerDataset;
	private boolean runClimateModelSimCrashDataset;
	private int maxGeneration;
	private int populationSize;
	private double crossoverStep;
	private double mutationStep;
	private int maxTreeDepthStep;
	private double maxCrossoverProb;
	private double minCrossoverProb;
	private double maxMutationProb;
	private double minMutationProb;
	private int maxMaxTreeDepths;
	private int minMaxTreeDepths;
	
	/**
	 * Constructor method which sets the parameter information for tuning.
	 */
	private ParamTuningSettings()
	{
		runBreastCancerDataset = false;
		runClimateModelSimCrashDataset = true;
		
		maxGeneration = 7000;
		
		populationSize = 1000;
		crossoverStep = 0.05;
		mutationStep = 0.05;
		maxTreeDepthStep = 1;
		
		maxCrossoverProb = 1;
		minCrossoverProb = 0.7;
		maxMutationProb = 0.3;
		minMutationProb = 0;
		maxMaxTreeDepths = 5;
		minMaxTreeDepths = 1;
	}
	
	/**
	 * Returns the boolean value of whether the Breast Cancer Dataset is being run as part of the tuning process.
	 * @return Boolean value of if the Breast Cancer Dataset is being run as part of the tuning process
	 */
	public boolean runBreastCancerDataset()
	{
		return runBreastCancerDataset;
	}
	
	/**
	 * Returns the boolean value of whether the Climate Model Simulation Crash Dataset is being run as part of the tuning process.
	 * @return Boolean value of if the Climate Model Simulation Crash Dataset is being run as part of the tuning process
	 */
	public boolean runClimateModelSimCrashDataset()
	{
		return runClimateModelSimCrashDataset;
	}
	
	/**
	 * Returns the maximum generation for the GP systems to utilise.
	 * @return Maximum generation
	 */
	public int maxGeneration() 
	{
		return maxGeneration;
	}
	
	/**
	 * Returns the population size the GP systems will utilise.
	 * @return Population size
	 */
	public int populationSize()
	{
		return populationSize;
	}
	
	/**
	 * Returns the step between each crossover probability of each GP tuning run.
	 * @return Crossover probability step
	 */
	public double crossoverStep()
	{
		return crossoverStep;
	}
	
	/**
	 * Returns the step between each mutation probability of each GP tuning run.
	 * @return Mutation probability step
	 */
	public double mutationStep()
	{
		return mutationStep;
	}
	
	/**
	 * Returns the step between each maximum tree depth of each GP tuning run.
	 * @return Maximum tree depth step
	 */
	public int maxTreeDepthStep()
	{
		return maxTreeDepthStep;
	}
	
	/**
	 * Maximum crossover probability to utilise for tuning.
	 * @return Maximum tuning crossover probability
	 */
	public double maxCrossoverProb()
	{
		return maxCrossoverProb;
	}
	
	/**
	 * Minimum crossover probability to utilise for tuning.
	 * @return Minimum tuning crossover probability
	 */
	public double minCrossoverProb()
	{
		return minCrossoverProb;
	}
	
	/**
	 * Maximum mutation probability to utilise for tuning.
	 * @return Maximum tuning mutation probability
	 */
	public double maxMutationProb()
	{
		return maxMutationProb;
	}
	
	/**
	 * Minimum mutation probability to utilise for tuning.
	 * @return Minimum tuning mutation probability
	 */
	public double minMutationProb()
	{
		return minMutationProb;
	}
	
	/**
	 * Maximum max tree depth to utilise for tuning.
	 * @return Maximum max tree depth
	 */
	public int maxMaxTreeDepths()
	{
		return maxMaxTreeDepths;
	}
	
	/**
	 * Minimum max tree depth to utilise for tuning.
	 * @return Minimum max tree depth
	 */
	public int minMaxTreeDepths()
	{
		return minMaxTreeDepths;
	}
}
