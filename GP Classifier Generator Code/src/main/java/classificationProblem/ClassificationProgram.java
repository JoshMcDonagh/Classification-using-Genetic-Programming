package main.java.classificationProblem;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import java.lang.reflect.Array;

import io.jenetics.ext.util.Tree;
import io.jenetics.prog.op.Op;
import io.jenetics.prog.op.Program;
import io.jenetics.prog.op.Var;
import main.java.datasetAccess.DatasetController;

/**
 * Class implements an additional layer of function on top of the Program class for classification problems.
 * @author Joshua McDonagh
 *
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class ClassificationProgram extends Program
{
	/**
	 * Constructor method for the class.
	 * @param name	Name of the program
	 * @param tree	The tree representation of the program
	 */
	@SuppressWarnings("unchecked")
	public ClassificationProgram(String name, Tree tree) 
	{
		super(name, tree);
	}
	
	/**
	 * Static method which evaluates a given tree representation with certain values
	 * @param <T>		Type of the dataset feature values
	 * @param tree		Tree representation object
	 * @param variables	Values to evaluate the tree program on
	 * @return Predicted label of the given tree evaluated using the given values
	 */
	@SafeVarargs
	public static <T> double doubleEval(
			final Tree<? extends Op<T>, ?> tree,
			final T... variables
	) {
		requireNonNull(tree);
		requireNonNull(variables);
		
		final Op<T> op = tree.value();
		T treeEvaluation = op.isTerminal()
				? evalOp(op, variables)
				: evalOp(op, evalChildren(tree, variables));
		
		// If the value produced by the syntax tree is positive, return the positive class representation
		if ((double)treeEvaluation >= 0)
			return DatasetController.getCurrentDataset().getPositiveClassRep();
		// Else return the negative class representation
		else
			return DatasetController.getCurrentDataset().getNegativeClassRep();
	}
	
	/**
	 * Static method which evaluates a given operation using given values.
	 * @param <T>		Type of the dataset feature values
	 * @param op		Operation to be evaluated
	 * @param variables	Values to evaluate the operation on
	 * @return Evaluated value
	 */
	@SafeVarargs
	private static <T> T evalOp(final Op<T> op, final T... variables) {
		if (op instanceof Var && ((Var)op).index() >= variables.length) {
			throw new IllegalArgumentException(format(
				"No value for variable '%s' given.", op
			));
		}

		return op.apply(variables);
	}
	
	/**
	 * Static method which evaluates the children of a given tree node.
	 * @param <T>		Type of the dataset feature values
	 * @param node		Node to evaluate the children of
	 * @param variables	Values to evaluate the child nodes on
	 * @return Evaluated value
	 */
	@SafeVarargs
	private static <T> T[] evalChildren(
		final Tree<? extends Op<T>, ?> node,
		final T... variables
	) {
		final T[] result = newArray(variables.getClass(), node.childCount());
		for (int i = 0; i < node.childCount(); ++i) {
			result[i] = eval(node.childAt(i), variables);
		}
		return result;
	}
	
	/**
	 * Static method which generates an array of a given type 
	 * @param <T>		Type of the dataset feature values
	 * @param arrayType Class type of new array
	 * @param size		Size of new array
	 * @return Newly created array
	 */
	@SuppressWarnings("unchecked")
	private static <T> T[] newArray(final Class<?> arrayType, final int size) {
		return (T[])Array.newInstance(arrayType.getComponentType(), size);
	}
}
