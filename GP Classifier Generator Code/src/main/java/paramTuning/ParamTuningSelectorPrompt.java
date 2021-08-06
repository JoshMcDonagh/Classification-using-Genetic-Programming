package main.java.paramTuning;

import java.util.Scanner;

/**
 * Class handles the prompt and input from the user in terms of whether to run the standard GP system or run parameter tuning.
 * @author Joshua McDonagh
 *
 */
public class ParamTuningSelectorPrompt 
{
	/**
	 * Static method outputs options as to what the user can choose to do, provides a prompt for input, and returns the user input (if it is valid).
	 * @return User input as an integer
	 */
	public static int run()
	{
		int code;
		
		System.out.println("1: Run standard GP system");
		System.out.println("2: Run GP parameter tuning");
		
		while (true)
		{
			System.out.print("To choose a running state, enter a code from the list above: ");
			
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			code = scanner.nextInt();
			
			if (code != 1 && code != 2)
				continue;
			
			break;
		}
		
		System.out.println("Input accepted");
		
		return code;
	}
}
