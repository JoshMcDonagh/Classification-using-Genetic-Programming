package main.java.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class implement some static methods which provide some general utility functionality.
 * @author Joshua McDonagh
 *
 */
public class Utilities 
{
	private static Random random = new Random();
	
	/**
	 * Static method which converts an ArrayList<double[]> to a double[][].
	 * @param arrayList	Input ArrayList object to be converted to a 2D double array
	 * @return	2D array of double type
	 */
	public static double[][] convertDoubleArrayListToArray(ArrayList<double[]> arrayList)
	{
		double[][] finalArray = new double[arrayList.size()][];
		
		for (int i = 0; i < arrayList.size(); i++)
			finalArray[i] = arrayList.get(i);
		
		return finalArray;
	}
	
	/**
	 * Static method which returns a pre-initialised Random object.
	 * @return Pre-initialised Random object
	 */
	public static Random getRandom()
	{
		return random;
	}
	
	/**
	 * Static method which returns a random integer within a given range.
	 * @param min	Minimum integer of range
	 * @param max	Maximum integer of range
	 * @return Random integer
	 */
	public static int randomIntInRange(int min, int max)
	{
		return getRandom().nextInt(max - min) + min;
	}
	
	/**
	 * Static method which returns a random double within a given range.
	 * @param min	Minimum double of range
	 * @param max	Maximum double of range
	 * @return Random double
	 */
	public static double randomDoubleInRange(double min, double max)
	{
		return min + (max - min) * getRandom().nextDouble();
	}
	
	/**
	 * Static method which returns a string of the current date and time in the format of: dd-MM-yyyy_HH-mm-ss.
	 * @return	String of current date and time
	 */
	public static String getDateAndTime()
	{
		return getDateAndTime("dd-MM-yyyy_HH-mm-ss");
	}
	
	/**
	 * Static method which returns a string of the current date and time in a given format.
	 * @param format	Format of which the current date and time will take
	 * @return String of current date and time
	 */
	public static String getDateAndTime(String format)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return formatter.format(LocalDateTime.now());
	}
	
	/**
	 * Static method which fixes double values by rounding them to 3 decimal places.
	 * @param value	Double value to round
	 * @return Fixed double value
	 */
	public static double fixDouble(double value)
	{
		return (double)Math.round(value * 1000d) / 1000d;
	}
}
