package main.java.classificationProblem;

import static java.lang.String.format;

import main.java.datasetAccess.DatasetController;

/**
 * Interface implements fitness functions.
 * @author Joshua McDonagh
 *
 * @param <T>	Type of the dataset feature values
 */
public interface FitnessFunction<T> 
{
	/**
	 * Abstract class that applies a fitness function.
	 * @param predicted	Predicted values for each problem instance
	 * @param expected	Actual values for each problem instance
	 * @return Fitness value
	 */
	double apply(final T[] predicted, final T[] expected);
	
	/**
	 * Calculates precision.
	 * @param predicted	Predicted values for each problem instance
	 * @param expected	Actual values for each problem instance
	 * @return Precision fitness value
	 */
	static double precision(final Double[] predicted, final Double[] expected)
	{
		if (expected.length != predicted.length) 
		{
			throw new IllegalArgumentException(format(
				"Expected result and calculated results have different " +
					"length: %d != %d",
				expected.length, predicted.length
			));
		}
		
		double positiveClassRep = DatasetController.getCurrentDataset().getPositiveClassRep();
		double negativeClassRep = DatasetController.getCurrentDataset().getNegativeClassRep();
		
		double truePositives = 0;
		double falsePositives = 0;
		
		for (int i = 0; i < predicted.length; i++)
		{
			if (predicted[i] == positiveClassRep && expected[i] == positiveClassRep)
				truePositives++;
			
			else if (predicted[i] == positiveClassRep && expected[i] == negativeClassRep)
				falsePositives++;
		}
		
		if ((truePositives + falsePositives) == 0)
			return 0;
		
		return truePositives / (truePositives + falsePositives);
	}
	
	/**
	 * Calculates accuracy.
	 * @param predicted	Predicted values for each problem instance
	 * @param expected	Actual values for each problem instance
	 * @return Accuracy fitness value
	 */
	static double accuracy(final Double[] predicted, final Double[] expected)
	{
		if (expected.length != predicted.length) 
		{
			throw new IllegalArgumentException(format(
				"Expected result and calculated results have different " +
					"length: %d != %d",
				expected.length, predicted.length
			));
		}
		
		double correctCount = 0;
		
		for (int i = 0; i < predicted.length; i++)
		{
			if ((double)predicted[i] == (double)expected[i])
				correctCount++;
		}
		
		if (predicted.length == 0)
			return 0;
		
		
		double calc = correctCount / predicted.length;
		
		return calc;
	}
	
	/**
	 * Calculates recall.
	 * @param predicted	Predicted values for each problem instance
	 * @param expected	Actual values for each problem instance
	 * @return Recall fitness value
	 */
	static double recall(final Double[] predicted, final Double[] expected)
	{
		if (expected.length != predicted.length) 
		{
			throw new IllegalArgumentException(format(
				"Expected result and calculated results have different " +
					"length: %d != %d",
				expected.length, predicted.length
			));
		}
		
		double positiveClassRep = DatasetController.getCurrentDataset().getPositiveClassRep();
		double negativeClassRep = DatasetController.getCurrentDataset().getNegativeClassRep();
		
		double truePositives = 0;
		double falseNegatives = 0;
		
		for (int i = 0; i < predicted.length; i++)
		{
			if (predicted[i] == positiveClassRep && expected[i] == positiveClassRep)
				truePositives++;
			
			else if (predicted[i] == negativeClassRep && expected[i] == positiveClassRep)
				falseNegatives++;
		}
		
		if ((truePositives + falseNegatives) == 0)
			return 0;
		
		return truePositives / (truePositives + falseNegatives);
	}
	
	/**
	 * Calculates fMeasure.
	 * @param predicted	Predicted values for each problem instance
	 * @param expected	Actual values for each problem instance
	 * @return fMeasure fitness value
	 */
	static double fMeasure(final Double[] predicted, final Double[] expected)
	{
		if (expected.length != predicted.length) 
		{
			throw new IllegalArgumentException(format(
				"Expected result and calculated results have different " +
					"length: %d != %d",
				expected.length, predicted.length
			));
		}
		
		double B = 1;
		double BSquared = B * B;
		double precision = precision(predicted, expected);
		double recall = recall(predicted, expected);
		
		if (((BSquared * precision) + recall) == 0)
			return 0;
		
		return (1 + BSquared) * ((precision * recall) / ((BSquared * precision) + recall));
	}
}
