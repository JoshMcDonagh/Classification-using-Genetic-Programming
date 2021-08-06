package main.java.classificationProblem;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import io.jenetics.Genotype;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Problem;
import io.jenetics.ext.util.Tree;
import io.jenetics.prog.ProgramChromosome;
import io.jenetics.prog.ProgramGene;
import io.jenetics.prog.op.Op;
import io.jenetics.prog.regression.Sample;
import io.jenetics.prog.regression.Sampling;
import io.jenetics.prog.regression.Sampling.Result;
import io.jenetics.util.ISeq;
import main.java.datasetAccess.Dataset;

/**
 * Class which defines the classification problem for this GP system.
 * Based on the Regression class provided by the Jenetics GP extension module (and authored by Franz Wilhelmstötter):
 * https://github.com/jenetics/jenetics/blob/master/jenetics.prog/src/main/java/io/jenetics/prog/regression/Regression.java
 * @author Joshua McDonagh
 *
 * @param <T> Type of the dataset feature values
 */
public final class Classification<T> implements Problem<Tree<Op<T>, ?>, ProgramGene<T>, Double>
{
	private final Codec<Tree<Op<T>, ?>, ProgramGene<T>> _codec;
	private final Fitness<T> _fitness;
	private final Sampling<T> _sampling;
	
	/**
	 * The constructor for the Classification problem class.
	 * @param codec		Codec object assigned to this classification problem
	 * @param fitness	The fitness function utilised for this classification problem
	 * @param sampling	The Sampling object utilised for this classification problem
	 */
	private Classification(
			final Codec<Tree<Op<T>, ?>, ProgramGene<T>> codec,
			final Fitness<T> fitness,
			final Sampling<T> sampling
			)
	{
		_codec = requireNonNull(codec);
		_fitness = requireNonNull(fitness);
		_sampling = requireNonNull(sampling);
	}
	
	/**
	 * Returns the codec for the classification problem.
	 * @return Codec
	 */
	@Override
	public Codec<Tree<Op<T>, ?>, ProgramGene<T>> codec() 
	{
		return _codec;
	}
	
	/**
	 * Returns the fitness function for the classification problem.
	 * @return Fitness function
	 */
	@Override
	public Function<Tree<Op<T>, ?>, Double> fitness() 
	{
		return this::fitnessEval;
	}
	
	/**
	 * Evaluates the fitness of a given program (syntax tree) for this classification problem.
	 * @param program	The tree program to be evaluated
	 * @return The fitness of the given program (syntax tree)
	 */
	public double fitnessEval(final Tree<? extends Op<T>, ?> program)
	{
		final Result<T> result = _sampling.eval(program);
		return result != null
				? _fitness.apply(program,  result.calculated(), result.expected())
				: 0;
	}
	
	/**
	 * Static factory method which creates a new classification problem instance.
	 * @param <T>		Type of the dataset feature values
	 * @param codec		Codec of the classification problem instance to be created
	 * @param fitness	Fitness function of the classification problem instance to be created
	 * @param sampling	Sampling instance of the classification problem instance to be created
	 * @return Classification class instance
	 */
	public static <T> Classification<T> of(
			final Codec<Tree<Op<T>, ?>, ProgramGene<T>> codec,
			final Fitness<T> fitness,
			final Sampling<T> sampling)
	{
		return new Classification<>(codec, fitness, sampling);
	}
	
	/**
	 * Static factory method which creates a new classification problem instance.
	 * @param <T>		Type of the dataset feature values
	 * @param codec		Codec of the classification problem instance to be created
	 * @param fitness	Fitness function of the classification problem instance to be created
	 * @param samples	Samples of the classification problem instance to be created
	 * @return Classification class instance
	 */
	public static <T> Classification<T> of(
			final Codec<Tree<Op<T>, ?>, ProgramGene<T>> codec,
			final Fitness<T> fitness,
			final Iterable<? extends Sample<T>> samples
			)
	{
		if (!samples.iterator().hasNext())
			throw new IllegalArgumentException("Sample list must not be empty.");
		
		final List<Sample<T>> s = new ArrayList<>();
		samples.forEach(s::add);
		
		return new Classification<>(codec, fitness, new SampleList<>(s));
	}
	
	/**
	 * Static factory method which creates a new classification problem instance.
	 * @param <T>		Type of the dataset feature values
	 * @param codec		Codec of the classification problem instance to be created
	 * @param fitness	Fitness function of the classification problem instance to be created
	 * @param dataset	Dataset to be utilised for the classification instance to be created
	 * @return Classification class instance
	 */
	@SuppressWarnings("unchecked")
	public static <T> Classification<T> of(
			final Codec<Tree<Op<T>, ?>, ProgramGene<T>> codec,
			final Fitness<T> fitness,
			final Dataset dataset
			)
	{
		List<Sample<T>> samples = new ArrayList<Sample<T>>();
		double[][] data = dataset.getAllExceptIDs();
		
		for (int i = 0; i < data.length; i++)
			samples.add((Sample<T>) VariableSample.ofDouble(data[i]));
		
		return of(codec, fitness, samples);
	}
	
	/**
	 * Static factory method which generates a codec using given arguments.
	 * @param <T>			Type of the dataset feature values
	 * @param operations	The set of functions to be utilised
	 * @param terminals		The set of terminals to be utilised
	 * @param depth			The maximum depth of the program trees
	 * @param validator		The validator for the classification problem
	 * @return Codec class instance
	 */
	public static <T> Codec<Tree<Op<T>, ?>, ProgramGene<T>>
	codecOf(
			final ISeq<Op<T>> operations,
			final ISeq<Op<T>> terminals,
			final int depth,
			final Predicate<? super ProgramChromosome<T>> validator
			)
	{
		if (depth >= 30 || depth < 0)
			throw new IllegalArgumentException(format("Tree depth out of range [0, 30): %d", depth));
		
		return Codec.of(
				Genotype.of(
						ProgramChromosome.of(
								depth,
								validator,
								operations,
								terminals
								)
						),
						Genotype::gene
				);
	}
	
	/**
	 * Static factory method which generates a codec using given arguments.
	 * @param <T>			Type of the dataset feature values
	 * @param operations	The set of functions to be utilised
	 * @param terminals		The set of terminals to be utilised
	 * @param depth			The maximum depth of the program trees
	 * @return Codec class instance
	 */
	public static <T> Codec<Tree<Op<T>, ?>, ProgramGene<T>>
	codecOf(
			final ISeq<Op<T>> operations,
			final ISeq<Op<T>> terminals,
			final int depth
			)
	{
		return codecOf(
				operations,
				terminals,
				depth,
				ch -> ch.root().size() <= depth
				);
	}
}
	
	
