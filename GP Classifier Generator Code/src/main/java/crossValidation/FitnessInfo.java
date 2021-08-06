package main.java.crossValidation;

import java.util.ArrayList;

/**
 * Class stores the fitness information for each GP run of the cross-validation process.
 * @author Joshua McDonagh
 *
 */
public class FitnessInfo 
{
	private ArrayList<Double> accuracyVals = new ArrayList<Double>();
	private ArrayList<Double> precisionVals = new ArrayList<Double>();
	private ArrayList<Double> recallVals = new ArrayList<Double>();
	private ArrayList<Double> fMeasureVals = new ArrayList<Double>();
	
	/**
	 * Method stores the accuracy fitness for a GP run.
	 * @param accuracy	Accuracy fitness as a double
	 */
	public void enterAccuracyVal(double accuracy)
	{
		accuracyVals.add(accuracy);
	}
	
	/**
	 * Method stores the precision fitness for a GP run.
	 * @param precision	Precision fitness as a double
	 */
	public void enterPrecisionVal(double precision)
	{
		precisionVals.add(precision);
	}
	
	/**
	 * Method stores the recall fitness for a GP run.
	 * @param recall	Recall fitness as a double
	 */
	public void enterRecallVal(double recall)
	{
		recallVals.add(recall);
	}
	
	/**
	 * Method stores the f-measure fitness for a GP run.
	 * @param fMeasure	f-Measure fitness as a double
	 */
	public void enterFMeasureVal(double fMeasure)
	{
		fMeasureVals.add(fMeasure);
	}
	
	/**
	 * Method returns the average accuracy fitness.
	 * @return Average accuracy as a double
	 */
	public double getAccuracyAverage()
	{
		return getAverage(accuracyVals);
	}
	
	/**
	 * Method returns the average precision fitness.
	 * @return Average precision as a double
	 */
	public double getPrecisionAverage()
	{
		return getAverage(precisionVals);
	}
	
	/**
	 * Method returns the average recall fitness.
	 * @return Average recall as a double
	 */
	public double getRecallAverage()
	{
		return getAverage(recallVals);
	}
	
	/**
	 * Method returns the average f-measure fitness.
	 * @return Average f-measure as a double
	 */
	public double getFMeasureAverage()
	{
		return getAverage(fMeasureVals);
	}
	
	/**
	 * Method returns the average of a given list of double values.
	 * @param values	ArrayList of double values to average
	 * @return Result of the averaging as a double value
	 */
	private double getAverage(ArrayList<Double> values)
	{
		double sum = 0;
		
		for (int i = 0; i < values.size(); i++)
			sum += values.get(i);
		
		return sum / values.size();
	}
}
