package main.java;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import main.java.crossValidation.CrossValidation;
import main.java.crossValidation.FitnessInfo;
import main.java.datasetAccess.Dataset;
import main.java.datasetAccess.DatasetController;
import main.java.datasetAccess.DatasetSelectorPrompt;
import main.java.paramTuning.ParamTuningSelectorPrompt;
import main.java.paramTuning.ParamTuningSettings;
import main.java.paramTuning.ParamTuningTextFileWriter;
import main.java.parameterSettings.ParameterSettings;
import main.java.runDataTextFileWriter.RunDataTextFileWriter;
import main.java.utilities.Utilities;

/**
 * Main class.
 * @author Joshua McDonagh
 *
 */
public class Main 
{
	/**
	 * Main function which runs when the application is loaded.
	 * @param args	Command line arguments are not used
	 */
	public static void main(String[] args) 
	{
		System.out.println("Program running...");
		
		try
		{
			if (ParamTuningSelectorPrompt.run() == 1)
				runGP();
			else
				tuneParams();
		}
		
		catch (InterruptedException | FileNotFoundException e)
		{
			System.out.println("EXCEPTION OCCURED");
		    e.printStackTrace();
		}
		
		System.out.println("Program ended");
	}
	
	/**
	 * Runs a GP instance using the dataset selected by the user via an input prompt.
	 * @return FitnessInfo instance containing the fitness information from the GP run
	 * @throws InterruptedException
	 * @throws FileNotFoundException
	 */
	private static FitnessInfo runGP() throws InterruptedException, FileNotFoundException
	{
		return runGP(DatasetSelectorPrompt.run());
	}
	
	/**
	 * Runs a GP instance using the given dataset.
	 * @param dataset	The dataset utilised in the GP instance
	 * @return FitnessInfo instance containing the fitness information from the GP run
	 * @throws InterruptedException
	 */
	private static FitnessInfo runGP(Dataset dataset) throws InterruptedException
	{
		RunDataTextFileWriter textFileWriter = RunDataTextFileWriter.get();
		
		textFileWriter.write("Dataset: " + dataset.getName());
		ParameterSettings.get().writeParameterSettings();
		
		
		CrossValidation crossValidator = new CrossValidation(dataset);
		FitnessInfo fitnessInfo = crossValidator.run(ParameterSettings.get());
		
		textFileWriter.close();
		return fitnessInfo;
	}
	
	/**
	 * Runs the parameter tuning process.
	 * @throws InterruptedException
	 */
	private static void tuneParams() throws InterruptedException
	{
		try
		{
			ParamTuningTextFileWriter textFileWriter = ParamTuningTextFileWriter.get();
			ParamTuningTextFileWriter.get().write("Iteration,Dataset,MaxGeneration,PopulationSize,CrossoverProbability,MutationProbability,MaxTreeDepth,Accuracy,Precision,Recall,F-Measure");
			
			ParameterSettings parameterSettings = ParameterSettings.get();
			ParamTuningSettings tuningSettings = ParamTuningSettings.get();
			
			ArrayList<Double> crossoverProbs = new ArrayList<Double>();
			ArrayList<Double> mutationProbs = new ArrayList<Double>();
			ArrayList<Integer> maxTreeDepths = new ArrayList<Integer>();
			
			int runCount = 0;
			
			// Generates the parameter settings for each tuning run instance
			for (double j = tuningSettings.minCrossoverProb() / tuningSettings.crossoverStep(); Utilities.fixDouble(j * tuningSettings.crossoverStep()) <= tuningSettings.maxCrossoverProb(); j += 1)
			{
				double crossoverProb = Utilities.fixDouble(j * tuningSettings.crossoverStep());
				
				for (double k = tuningSettings.minMutationProb() / tuningSettings.mutationStep(); Utilities.fixDouble(k * tuningSettings.mutationStep()) <= tuningSettings.maxMutationProb(); k += 1)
				{
					double mutationProb = Utilities.fixDouble(k * tuningSettings.mutationStep());
					
					for (int l = tuningSettings.minMaxTreeDepths() / tuningSettings.maxTreeDepthStep(); l * tuningSettings.maxTreeDepthStep() <= tuningSettings.maxMaxTreeDepths(); l++)
					{
						int maxTreeDepth = l * tuningSettings.maxTreeDepthStep();
						
						crossoverProbs.add(crossoverProb);
						mutationProbs.add(mutationProb);
						maxTreeDepths.add(maxTreeDepth);
						
						runCount++;
					}
				}
			}
			
			// Runs the parameter tuning process for each dataset
			for (int i = 0; i < 2; i++)
			{
				Dataset dataset;
				
				// Checks which datasets are in use for this tuning process
				if (i == 0)
				{
					if (tuningSettings.runBreastCancerDataset())
						dataset = DatasetController.getBreastCancerDataset();
					else
						continue;
				}
				else
				{
					if (tuningSettings.runClimateModelSimCrashDataset())
						dataset = DatasetController.getClimateModelSimCrashDataset();
					else
						continue;
				}
				
				System.out.println("\nDataset: " + dataset.getName());
				System.out.println("Run count: " + runCount);
				
				// Applies the GP system (via cross-validation) for each tuning instance
				for (int j = 0; j < runCount; j++)
				{
					parameterSettings.setMaxGeneration(tuningSettings.maxGeneration());
					parameterSettings.setPopulationSize(tuningSettings.populationSize());
					parameterSettings.setCrossoverProbability(crossoverProbs.get(j));
					parameterSettings.setMutationProbability(mutationProbs.get(j));
					parameterSettings.setMaxTreeDepth(maxTreeDepths.get(j));
					
					System.out.println("Iteration: " + (j + 1));
					System.out.println("Date and time: " + Utilities.getDateAndTime("dd/MM/yyyy HH:mm:ss"));
					System.out.println("Maximum Generation: " + parameterSettings.maxGeneration());
					System.out.println("Population Size: " + parameterSettings.populationSize());
					System.out.println("Crossover Probability: " + parameterSettings.crossoverProbability());
					System.out.println("Mutation Probability: " + parameterSettings.mutationProbability());
					System.out.println("Maximum Tree Depth: " + parameterSettings.maxTreeDepth());
					
					FitnessInfo fitnessInfo = runGP(dataset);
					
					ParamTuningTextFileWriter.get().write(
							(j + 1) + "," +
							dataset.getName() + "," + 
							parameterSettings.maxGeneration() + "," + 
							parameterSettings.populationSize() + "," + 
							parameterSettings.crossoverProbability() + "," +
							parameterSettings.mutationProbability() + "," +
							parameterSettings.maxTreeDepth() + "," +
							fitnessInfo.getAccuracyAverage() + "," +
							fitnessInfo.getPrecisionAverage() + "," +
							fitnessInfo.getRecallAverage() + "," +
							fitnessInfo.getFMeasureAverage()
					);	
				}
			}
			
			textFileWriter.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("EXCEPTION OCCURED");
		    e.printStackTrace();
		}
	}
}
