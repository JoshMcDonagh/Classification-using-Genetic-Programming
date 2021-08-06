package main.java.functionsAndTerminals;

import io.jenetics.prog.op.Op;
import io.jenetics.util.ISeq;

/**
 * Singleton class sets the function set for the GP system.
 * @author Joshua McDonagh
 *
 */
public class FunctionSet 
{
	private static FunctionSet instance = null;
	
	/**
	 * Static method returns (and initialises if run first time) an instance of FunctionSet.
	 * @return Instance of FunctionSet
	 */
	public static ISeq<Op<Double>> get()
	{
		if (instance == null)
			instance = new FunctionSet();
		
		return instance.functions;
	}
	
	private final ISeq<Op<Double>> functions;
	
	/**
	 * Constructor method which constructs the function set.
	 */
	private FunctionSet()
	{
		functions = ISeq.of(
				additionOp(),
				subtractionOp(),
				multiplicationOp(),
				divisionOp(),
				exponentiationOp(),
				nthRootOp(),
				greaterThanOp(),
				lessThanOp(),
				equalsOp()
				);
	}
	
	/**
	 * Implements the addition operator.
	 * @return Addition operator
	 */
	private Op<Double> additionOp()
	{
		return Op.of("+", 2, v -> v[0] + v[1]);
	}
	
	/**
	 * Implements the subtraction operator.
	 * @return Subtraction operator
	 */
	private Op<Double> subtractionOp()
	{
		return Op.of("-", 2, v -> v[0] - v[1]);
	}
	
	/**
	 * Implements the multiplication operator.
	 * @return Multiplication operator
	 */
	private Op<Double> multiplicationOp()
	{
		return Op.of("×", 2, v -> v[0] * v[1]);
	}
	
	/**
	 * Implements the protected division operator.
	 * @return Protected division operator
	 */
	private Op<Double> divisionOp()
	{
		return Op.of("÷", 2, v -> {
			if (v[1] == 0)
				return (double) 0;
			return v[0] / v[1];
		});
	}
	
	/**
	 * Implements exponentiation operator.
	 * @return Exponentiation operator
	 */
	private Op<Double> exponentiationOp()
	{
		return Op.of("exp", 2, v -> Math.pow(v[0], v[1]));
	}
	
	/**
	 * Implements nth root operator.
	 * @return nth root operator
	 */
	private Op<Double> nthRootOp()
	{
		return Op.of("rt", 2, v -> Math.pow(v[1], 1/v[0]));
	}
	
	/**
	 * Implements the greater-than operator.
	 * @return Greater-than operator
	 */
	private Op<Double> greaterThanOp()
	{
		return Op.of(">", 4, v -> {
			if (v[0] > v[1])
				return v[2];
			return v[3];
		});
	}
	
	/**
	 * Implements the less-than operator.
	 * @return Less-than operator
	 */
	private Op<Double> lessThanOp()
	{
		return Op.of("<", 4, v -> {
			if (v[0] < v[1])
				return v[2];
			return v[3];
		});
	}
	
	/**
	 * Implements the equals-to operator.
	 * @return Equals-to operator
	 */
	private Op<Double> equalsOp()
	{
		return Op.of("==", 4, v -> {
			if (v[0] == v[1])
				return v[2];
			return v[3];
		});
	}
}
