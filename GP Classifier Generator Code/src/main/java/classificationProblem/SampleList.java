package main.java.classificationProblem;

import static java.lang.String.format;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.List;
import java.util.stream.Stream;

import io.jenetics.ext.util.Tree;
import io.jenetics.prog.op.Op;
import io.jenetics.prog.regression.Sample;
import io.jenetics.prog.regression.Sampling;

/**
 * Accessible implementation of the SampleList class provided by the Jenetics GP extension module (and authored by Franz Wilhelmstötter):
 * https://github.com/jenetics/jenetics/blob/master/jenetics.prog/src/main/java/io/jenetics/prog/regression/SampleList.java
 * @author Joshua McDonagh
 *
 * @param <T>	Type of the dataset feature values
 */
class SampleList<T>
	extends AbstractList<Sample<T>>
	implements
		Sampling<T>,
		Serializable
{

	private static final long serialVersionUID = 1L;

	private final List<Sample<T>> _samples;

	private final Class<T> _type;
	private final T[][] _arguments;
	private final T[] _results;
	
	/**
	 * Class constructor.
	 * @param samples	List of samples
	 */
	@SuppressWarnings("unchecked")
	SampleList(final List<Sample<T>> samples) {
		if (samples.isEmpty()) {
			throw new IllegalArgumentException("Sample list must not be empty.");
		}

		_type = (Class<T>)samples.get(0).argAt(0).getClass();

		final int arity = samples.get(0).arity();
		if (arity == 0) {
			throw new IllegalArgumentException(
				"The arity of the sample point must not be zero."
			);
		}

		for (int i = 0; i < samples.size(); ++i) {
			final Sample<T> sample = samples.get(0);
			if (arity != sample.arity()) {
				throw new IllegalArgumentException(format(
					"Expected arity %d, but got %d for sample index %d.",
					arity, sample.arity(), i
				));
			}
		}

		_samples = samples;

		_arguments = samples.stream()
			.map(s -> args(_type, s))
			.toArray(size -> (T[][])Array.newInstance(_type, size, 0));

		_results = _samples.stream()
			.map(Sample::result)
			.toArray(size -> (T[])Array.newInstance(_type, size));
	}
	
	/**
	 * Returns sample arguments
	 * @param <T>		Type of the dataset feature values
	 * @param type		Class type
	 * @param sample	Individual sampple
	 * @return Arguments of sample
	 */
	private static <T> T[] args(final Class<T> type, final Sample<T> sample) {
		@SuppressWarnings("unchecked")
		final T[] args = (T[])Array
			.newInstance(sample.argAt(0).getClass(), sample.arity());
		for (int i = 0; i < args.length; ++i) {
			args[i] = sample.argAt(i);
		}

		return args;
	}

	/**
	 * Evaluates a given syntax tree program.
	 * @param program	Syntax tree program to be evaluated
	 * @return Result of the tree evaluation
	 */
	@Override
	public Result<T> eval(final Tree<? extends Op<T>, ?> program) {
		@SuppressWarnings("unchecked")
		final T[] calculated = (T[])Stream.of(_arguments)
			.map(args -> ClassificationProgram.doubleEval(program, args))
			.toArray(size -> (Double[])Array.newInstance(_type, size));
		
		return Result.of(calculated, _results);
	}
	
	/**
	 * Returns a sample at a given index.
	 * @param index	Index of the sample to retrieve
	 * @return Sample at the given index
	 */
	@Override
	public Sample<T> get(int index) {
		return _samples.get(index);
	}
	
	/**
	 * Returns the number of samples.
	 * @return Number of samples
	 */
	@Override
	public int size() {
		return _samples.size();
	}

}
