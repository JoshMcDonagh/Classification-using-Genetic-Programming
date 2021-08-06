package main.java.functionsAndTerminals;

import java.util.ArrayList;

import io.jenetics.prog.op.EphemeralConst;
import io.jenetics.prog.op.Op;
import io.jenetics.prog.op.Var;
import io.jenetics.util.ISeq;

/**
 * Class sets the terminal set for the GP system.
 * @author Joshua McDonagh
 *
 */
public class TerminalSet 
{
	private static ArrayList<Op<Double>> featureVars = new ArrayList<Op<Double>>();
	private static boolean isVarListChanged = true;
	
	/**
	 * Static method adds feature variable to the terminal set.
	 * @param name	Feature name as String
	 * @param index	Feature index as integer
	 */
	public static void addFeatureVariable(String name, int index)
	{
		featureVars.add(Var.of(name, index));
		isVarListChanged = true;
	}
	
	private static TerminalSet instance;
	
	/**
	 * Static method returns the sequence of terminal operators in the terminal set.
	 * @return Sequence of terminal operators
	 */
	public static ISeq<Op<Double>> get()
	{
		if (instance == null || isVarListChanged)
		{
			//featureVars = new ArrayList<Op<Double>>();
			instance = new TerminalSet(featureVars);
			isVarListChanged = false;
		}
		
		return instance.terminals;
	}
	
	private final ISeq<Op<Double>> terminals;
	
	/**
	 * Constructor method which constructs the terminal set.
	 * @param featureVars	ArrayList of feature variable operators
	 */
	private TerminalSet(ArrayList<Op<Double>> featureVars)
	{
		ArrayList<Op<Double>>terminalList = featureVars;
		terminalList.add(constTerminal());
		terminals = ISeq.of(terminalList);
	}
	
	/**
	 * Returns a constant terminal operator.
	 * @return Ephemeral constant operator
	 */
	private EphemeralConst<Double> constTerminal()
	{
		EphemeralConst<Double> val = EphemeralConst.of("Const", Math::random);
		return val;
	}
}
