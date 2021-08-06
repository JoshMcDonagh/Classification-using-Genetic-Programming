package main.java.classificationProblem;

import io.jenetics.prog.regression.Sample;

/**
 * Handles sample that can vary in terms of its values
 * @author Joshua McDonagh
 *
 * @param <T>	Type of the dataset feature values
 */
public interface VariableSample<T> extends Sample<T>
{
	/**
	 * Static method which generates a sample of double values.
	 * @param values	Double values to add to sample
	 * @return DoubleSample instance generated with given values
	 */
	static Sample<Double> ofDouble(final double[] values)
	{
		return new DoubleSample(values);
	}
}
