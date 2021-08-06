package main.java.classificationProblem;

import static java.util.Objects.requireNonNull;

import io.jenetics.ext.util.Tree;
import io.jenetics.prog.op.Op;

/**
 * Interface sets single static method for fitness function implementation.
 * @author Joshua McDonagh
 *
 * @param <T>	Type of the dataset feature values
 */
public interface Fitness<T>
{
	/**
	 * Abstract method which applies a fitness function to a tree.
	 * @param program		Program tree to be utilised
	 * @param calculated	Predicted values for each problem instance
	 * @param expected		Actual values for each problem instance
	 * @return Fitness of the given tree
	 */
	double apply(
			final Tree<? extends Op<T>, ?> program,
			final T[] calculated,
			final T[] expected
			);
	
	/**
	 * Provides Fitness class object from FitnessFunction.
	 * @param <T>		Type of the dataset feature values
	 * @param fitness	Fitness function
	 * @return Fitness class object
	 */
	static <T> Fitness<T> of(final FitnessFunction<T> fitness)
	{
		requireNonNull(fitness);
		return (p, c, e) -> fitness.apply(c, e);
	}
}
