package main.java.classificationProblem;

import main.java.datasetAccess.Dataset;
import main.java.parameterSettings.ParameterSettings;

/**
 * Class responsible for generating classification problem instances.
 * @author Joshua McDonagh
 *
 */
public class GenerateProblem 
{
	/**
	 * Generates a classification problem instance using given arguments.
	 * @param parameters	Parameter settings of the GP system
	 * @param fitness		Fitness function
	 * @param dataset		Dataset to be operated on
	 * @return Classification instance
	 */
	public static Classification<Double> classification(ParameterSettings parameters, FitnessFunction<Double> fitness, Dataset dataset)
	{
		return Classification.of(
				Classification.codecOf(parameters.functionSet(), parameters.terminalSet(), parameters.maxTreeDepth()),
				Fitness.of(fitness),
				dataset
		);
	}
}
