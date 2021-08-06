package main.java.parameterSettings;

import io.jenetics.prog.op.Op;
import io.jenetics.util.ISeq;
import main.java.classificationProblem.FitnessFunction;
import main.java.functionsAndTerminals.FunctionSet;
import main.java.functionsAndTerminals.TerminalSet;
import main.java.runDataTextFileWriter.RunDataTextFileWriter;

/**
 * Singleton class stores the parameter information for GP runs.
 * @author Joshua McDonagh
 *
 */
public class ParameterSettings 
{
	private static ParameterSettings instance = null;
	
	/**
	 * Static method returns (and initialises if run first time) an instance of ParameterSettings.
	 * @return Instance of ParameterSettings
	 */
	public static ParameterSettings get()
	{
		if (instance == null)
			instance = new ParameterSettings();
		
		return instance;
	}
	
	private int maxGeneration;
	private int populationSize;
	private double crossoverProbability;
	private double mutationProbability;
	private int maxTreeDepth;
	private String fitnessFunctionName;
	private FitnessFunction<Double> fitnessFunction;
	
	/**
	 * Constructor method which sets the parameter settings.
	 */
	private ParameterSettings()
	{
		// Default settings
		maxGeneration = 7000;
		populationSize = 1000;
		crossoverProbability = 0.9;
		mutationProbability = 0.15;
		maxTreeDepth = 3;
		fitnessFunctionName = "F-Measure";
		fitnessFunction = FitnessFunction::fMeasure;
	}
	
	/**
	 * Sets the maximum generation,
	 * @param newMaxGeneration	Maximum generation as an integer
	 */
	public void setMaxGeneration(int newMaxGeneration)
	{
		maxGeneration = newMaxGeneration;
	}
	
	/**
	 * Sets the minimum generation.
	 * @param newMaxGeneration	Minimum generation as an integer
	 */
	public void setPopulationSize(int newPopulationSize)
	{
		populationSize = newPopulationSize;
	}
	
	/**
	 * Sets the crossover probability.
	 * @param newCrossoverProbability	Crossover probability as a double
	 */
	public void setCrossoverProbability(double newCrossoverProbability)
	{
		crossoverProbability = newCrossoverProbability;
	}
	
	/**
	 * Sets the mutation probability.
	 * @param newMutationProbability	Mutation probability as a double
	 */
	public void setMutationProbability(double newMutationProbability)
	{
		mutationProbability = newMutationProbability;
	}
	
	/**
	 * Sets the maximum tree depth.
	 * @param newMaxTreeDepth	Maximum tree depth as an integer
	 */
	public void setMaxTreeDepth(int newMaxTreeDepth)
	{
		maxTreeDepth = newMaxTreeDepth;
	}
	
	/**
	 * Returns the sequence of terminal set operators.
	 * @return Sequence of terminal set operators
	 */
	public ISeq<Op<Double>> terminalSet()
	{
		return TerminalSet.get();
	}
	
	/**
	 * Returns the sequence of function set operators.
	 * @return Sequence of function set operators
	 */
	public ISeq<Op<Double>> functionSet()
	{
		return FunctionSet.get();
	}
	
	/**
	 * Returns the maximum generation parameter.
	 * @return Maximum generation as an integer
	 */
	public int maxGeneration()
	{
		return maxGeneration;
	}
	
	/**
	 * Returns the population size parameter.
	 * @return Population size as an integer
	 */
	public int populationSize()
	{
		return populationSize;
	}
	
	/**
	 * Returns the crossover probability parameter.
	 * @return Crossover probability as a double
	 */
	public double crossoverProbability()
	{
		return crossoverProbability;
	}
	
	/**
	 * Returns the mutation probability parameter.
	 * @return Mutation probability as a double
	 */
	public double mutationProbability()
	{
		return mutationProbability;
	}
	
	/**
	 * Returns the maximum tree depth parameter.
	 * @return Maximum tree depth as an integer
	 */
	public int maxTreeDepth()
	{
		return maxTreeDepth;
	}
	
	/**
	 * Returns the name of the fitness function.
	 * @return Fitness function name as a String
	 */
	public String fitnessFunctionName()
	{
		return fitnessFunctionName;
	}
	
	/**
	 * Returns the fitness function.
	 * @return Fitness function
	 */
	public FitnessFunction<Double> fitnessFunction()
	{
		return fitnessFunction;
	}
	
	/**
	 * Writes the parameter settings to the corresponding GP run external file.
	 */
	public void writeParameterSettings()
	{
		RunDataTextFileWriter writer = RunDataTextFileWriter.get();
		
		writer.write("Parameter Settings: ");
		writer.write("	Maximum Generation: " + maxGeneration());
		writer.write("	Population Size: " + populationSize());
		writer.write("	Crossover Probability: " + crossoverProbability());
		writer.write("	Mutation Probability: " + mutationProbability());
		writer.write("	Maximum Tree Depth: " + maxTreeDepth());
		writer.write("	Training Fitness Function: " + fitnessFunctionName());
	}
}
