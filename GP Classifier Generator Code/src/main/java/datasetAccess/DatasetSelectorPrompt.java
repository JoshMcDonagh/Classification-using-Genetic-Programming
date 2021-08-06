package main.java.datasetAccess;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class handles the prompt and input from the user in terms of selecting the dataset to train the GP system on.
 * @author Joshua McDonagh
 *
 */
public class DatasetSelectorPrompt 
{
	/**
	 * Static method outputs options as to what the user can choose to do, provides a prompt for input, and returns the user input (if it is valid).
	 * @return Dataset chosen by the user
	 * @throws FileNotFoundException
	 */
	public static Dataset run() throws FileNotFoundException
	{
		Dataset dataset;
		
		System.out.println("1: Breast Cancer Dataset");
		System.out.println("2: Climate Model Sim Crash Dataset");
		
		while (true)
		{
			System.out.print("To choose a dataset to use, enter a code from the list above: ");
			
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			int code = scanner.nextInt();
			
			if (code == 1)
				dataset = DatasetController.getBreastCancerDataset();
			
			else if (code == 2)
				dataset = DatasetController.getClimateModelSimCrashDataset();
			
			else
				continue;
			
			break;
		}
		
		System.out.println("Input accepted");
		
		return dataset;
	}
}
