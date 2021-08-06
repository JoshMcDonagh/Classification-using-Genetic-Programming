package main.java.classificationProblem;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.Arrays;

import io.jenetics.prog.regression.Sample;

/**
 * Class implements an additional layer of function on top of the Sample class for double specific implementations.
 * @author Joshua McDonagh
 *
 */
public class DoubleSample implements Sample<Double>, Serializable 
{
	private static final long serialVersionUID = 1L;

	private final double[] _sample;
	
	/**
	 * Constructor method for the class.
	 * @param sample	Sample values
	 */
	DoubleSample(final double... sample) 
	{
		if (sample.length < 2) 
			throw new IllegalArgumentException(format("Argument sample must contain at least two values: %s",sample.length));

		_sample = requireNonNull(sample);
	}
	
	/**
	 * Return the dimensionality of the sample point arguments.
	 * @return arity of the sample point
	 */
	@Override
	public int arity() 
	{
		return _sample.length - 1;
	}
	
	/**
	 * Returns the sample value at a given index.
	 * @param index	Index value of the sample value
	 * @return Sample value at the given index
	 */
	@Override
	public Double argAt(final int index) 
	{
		if (index < 0 || index >= arity()) 
			throw new ArrayIndexOutOfBoundsException(format("Argument index out or range [0, %s): %s", arity(), index));

		return _sample[index];
	}
	
	/**
	 * Returns the sample values.
	 * @return Sample values
	 */
	Double[] args() 
	{
		final Double[] result = new Double[arity()];
		for (int i = 0; i < result.length; ++i) 
			result[i] = _sample[i];
		return result;
	}
	
	/**
	 * Returns the result.
	 * @return The result
	 */
	@Override
	public Double result() 
	{
		return _sample[_sample.length - 1];
	}
	
	/**
	 * Returns the hash code of the sample.
	 * @return Hash code of the sample
	 */
	@Override
	public int hashCode() 
	{
		return Arrays.hashCode(_sample);
	}
	
	/**
	 * Returns true or false on whether the object is equal to the sample
	 * @param obj	Object to compare with the sample
	 * @return Boolean as to whether the object given is equal to the sample
	 */
	@Override
	public boolean equals(final Object obj) 
	{
		return obj == this ||
			obj instanceof DoubleSample &&
			Arrays.equals(_sample, ((DoubleSample)obj)._sample);
	}
	
	/**
	 * Converts the sample to a string.
	 * @return Sample as a string
	 */
	@Override
	public String toString() 
	{
		return format("%s -> %s", Arrays.toString(args()), result());
	}
}
