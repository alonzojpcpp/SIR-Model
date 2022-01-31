package SIRModel;

// Hector Arvizu, Jacob Alonzo, James Contreras, Manan Patel, Sebastian Cursaro

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class sirmodel {
	public static String simulate(String patientsList, String tempPatientsList, int ROWS, int COLUMNS, double INFECTED_RATE, double RECOVERY_RATE) {
		Random random = new Random();
		// Itterates through every patient, figuring out what status they are
		// If a patient is S, the program scans nearby neighbors and sees if they are infected or not
		// If a patient is I, the program runs a random number generator to see if the infected patient gets recovered
		// If a patient is R, nothing happens
		for(int j = 0; j < patientsList.length(); j++) {
			
			// This variable counts how many neighbors around an S patient are infected
			
			int tempInfectedNeighbor = 0;
			if(patientsList.charAt(j) == 'S') {
				// Top Left
				if(j == 0) {
					if(patientsList.charAt(j + 1) == 'I') {
						tempInfectedNeighbor += 1;
					}
					if(patientsList.charAt(j + COLUMNS) == 'I') {
						tempInfectedNeighbor += 1;
					}
				}
				// Top Right
				else if (j == COLUMNS - 1) {
					if(patientsList.charAt(j - 1) == 'I') {
						tempInfectedNeighbor += 1;
					}
					if(patientsList.charAt(j + COLUMNS) == 'I') {
						tempInfectedNeighbor += 1;
					}
				}
				// Bottom Left
				else if (j == ((COLUMNS * ROWS) - (COLUMNS))) {
					if(patientsList.charAt(j + 1) == 'I') {
						tempInfectedNeighbor += 1;
					}
					if(patientsList.charAt(j - COLUMNS) == 'I') {
						tempInfectedNeighbor += 1;
					}
				}
				// Bottom Right
				else if (j == (COLUMNS * ROWS) - 1) {
					if(patientsList.charAt(j - 1) == 'I') {
						tempInfectedNeighbor += 1;
					}
					if(patientsList.charAt(j - COLUMNS) == 'I') {
						tempInfectedNeighbor += 1;
					}
				}
				
				// Top Side
				else if(j != 0 && j < COLUMNS ) {
					if(patientsList.charAt(j - 1) == 'I') {
						tempInfectedNeighbor += 1;
					}
					if(patientsList.charAt(j + 1) == 'I') {
						tempInfectedNeighbor += 1;
					}
					if(patientsList.charAt(j + COLUMNS) == 'I') {
						tempInfectedNeighbor += 1;
						
					}
				}
				
				// Left Side
				else if(j != 0 && j != ((COLUMNS * ROWS) - (COLUMNS)) && (j % COLUMNS) == 0) {
					if(patientsList.charAt(j + 1) == 'I') {
						tempInfectedNeighbor += 1;
					}
					if(patientsList.charAt(j - COLUMNS) == 'I') {
						tempInfectedNeighbor += 1;
					}
					if(patientsList.charAt(j + COLUMNS) == 'I') {
						tempInfectedNeighbor += 1;
					}
				}
				// Right Side
				else if(j != 0 && j != COLUMNS - 1 && j != ((COLUMNS * ROWS) - 1) && (j + 1) % COLUMNS == 0 ) {
					if(patientsList.charAt(j - 1) == 'I') {
						tempInfectedNeighbor += 1;
					}
					if(patientsList.charAt(j - COLUMNS) == 'I') {
						tempInfectedNeighbor += 1;
					}
					if(patientsList.charAt(j + COLUMNS) == 'I') {
						tempInfectedNeighbor += 1;
					}
				}
				// Bottom Side
				else if(j != 0 && j < (COLUMNS * ROWS) - 1 && j > (COLUMNS * ROWS) - COLUMNS ) {
					if(patientsList.charAt(j - 1) == 'I') {
						tempInfectedNeighbor += 1;
					}
					if(patientsList.charAt(j + 1) == 'I') {
						tempInfectedNeighbor += 1;
					}
					if(patientsList.charAt(j - COLUMNS) == 'I') {
						tempInfectedNeighbor += 1;
					}
				}
				// Middle area
				else {
					if(patientsList.charAt(j - 1) == 'I') {
						tempInfectedNeighbor += 1;
					}
					if(patientsList.charAt(j + COLUMNS) == 'I') {
						tempInfectedNeighbor += 1;
					}
					if(patientsList.charAt(j - COLUMNS) == 'I') {
						tempInfectedNeighbor += 1;
					}
					if(patientsList.charAt(j + 1) == 'I') {
						tempInfectedNeighbor += 1;
					}
				}
				
				// Calculates total infected rate
				double combinedInfectedRate = INFECTED_RATE * tempInfectedNeighbor;
				
				// Creates a random number, if the number is less than the combined infected rate the patient becomes infected
				if(random.nextDouble() < combinedInfectedRate) {
					tempPatientsList = tempPatientsList + "I";
				}
				else {
					tempPatientsList = tempPatientsList + patientsList.charAt(j);
				}
			}
			
			// If patient is infected, creates a random number, if the number is less than recovery rate the patient becomes recovered
			if(patientsList.charAt(j) == 'I') {
				if(random.nextDouble() < RECOVERY_RATE) {
					tempPatientsList = tempPatientsList + "R";
				}
				else {
					tempPatientsList = tempPatientsList + patientsList.charAt(j);
				}
			}

			if(patientsList.charAt(j) == 'R') {
				tempPatientsList = tempPatientsList + "R";
			}
		}
		return tempPatientsList;
	}

	public static String createList(String patientsList, int ROWS, int COLUMNS) {
		// Creates a string where all patients are S
		for (int i = 0; i < ((ROWS * COLUMNS)); i++) {
			patientsList = patientsList + "S";
		}
		return patientsList;
	}
	
	public static String placeInfected(String patientsList, String tempPatientsList, int INFECTED_ROW, int INFECTED_COLUMN, int COLUMNS) {
		// Places infected in string of all S's
		final int INFECTED_PLACEMENT = (COLUMNS * (INFECTED_ROW - 1)) + INFECTED_COLUMN - 1;
		for (int i=0;i < patientsList.length(); i++) {
			if(i == (INFECTED_PLACEMENT)) {
				tempPatientsList = tempPatientsList + "I";
			}
			else {
				tempPatientsList = tempPatientsList + patientsList.charAt(i);
			}
		}
		return tempPatientsList;
	}

	public static void fileCreator(String patientsList, String tempPatientsList, int sCount, int iCount, int rCount, int timeStepCount, int ROWS, int COLUMNS) throws IOException {
		
		// Goes through the patients and counts logs every patient's status
					for (int n = 0; n < patientsList.length(); n++) {
						if(patientsList.charAt(n) == 'S') {
							sCount++;
						}
						else if(patientsList.charAt(n) == 'R') {
							rCount++;
						}
						else if (patientsList.charAt(n) == 'I') {
							iCount++;
						}
					}
		// Adds spaces to create the "matrix"
		for (int m = 0; m < patientsList.length(); m++) {
			tempPatientsList = tempPatientsList + patientsList.charAt(m);
				
			if((m + 1) % COLUMNS == 0) {
				tempPatientsList = tempPatientsList +"\n";
			}
		}
		
		// Prints out stored values into a text file
		String newFile = timeStepCount + ".txt";
		FileWriter fw = new FileWriter(newFile);
		fw.write("Time Step # " + timeStepCount + "\n");
		fw.write(tempPatientsList + "\n");
		fw.write("Susceptible Patients: " + (int) sCount + "\n");
		fw.write("Infected Patients: " + (int) iCount + "\n");
		fw.write("Recovered Patients: " + (int) rCount + "\n");
		fw.write("Infected/Total: " + (int) iCount + "/" + (ROWS * COLUMNS) + "\n");
		fw.close();
	}

	public static char simulateAgain(char anotherSimulation) {
		Scanner scanner = new Scanner(System.in);
		// Keeps asking the user if they want to repeat the simulation again and only continues if the user inputs the correct character
		while(true) {
			System.out.print("Run another simulation? (Y/N): ");
			anotherSimulation = scanner.next().charAt(0);
			
			if(anotherSimulation == 'Y' || anotherSimulation == 'N') {
				break;
			}
		}
		return anotherSimulation;
	}

	public static void deleteFiles(int timeStepCount) throws IOException {
		// Gets path of file
		Path path = Paths.get("1.txt");
		// Iterates through every file to delete them all
		for (int i = 1; i <= timeStepCount; i++) {
			String filePath = i + ".txt";
			path = Paths.get(filePath);
			Files.delete(path);
		}
	}
	
	public static void main(String[] args) throws IOException {
		char anotherSimulation;
		do {
			// Sets up scanner and initializes variables
			Scanner scanner = new Scanner(System.in);
			String patientsList = "";
			String tempPatientsList = "";
			int sCount = 0;
			int iCount = 0;
			int rCount = 0;
			int timeStepCount = 0;
			anotherSimulation ='0';
			
			// Gets the rows and columns from the user
			System.out.print("Rows: ");
			final int ROWS = scanner.nextInt();
			System.out.print("Columns: ");
			final int COLUMNS = scanner.nextInt();
			
			// Creates a string containing how many patients the user wants from earlier
			patientsList = createList(patientsList, ROWS, COLUMNS);

			// Gets the infected patient's rows and columns
			System.out.print("Infected Patient Position Row: ");
			final int INFECTED_ROW = scanner.nextInt();
			System.out.print("Infected Patient Position Column: ");
			final int INFECTED_COLUMN = scanner.nextInt();
			
			// Updates the string and placed the infected patient where the user specified
			tempPatientsList = placeInfected(patientsList, tempPatientsList, INFECTED_ROW, INFECTED_COLUMN, COLUMNS);
			
			patientsList = tempPatientsList;
			tempPatientsList = "";
			
			// Gets the recovery rate, infected rate, and amount of time steps from the user
			System.out.print("Recovery Rate (0 - 1): ");
			final double RECOVERY_RATE = scanner.nextDouble();
			System.out.print("Infected Rate (0 - 1): ");
			final double INFECTED_RATE = scanner.nextDouble();
			System.out.print("Time Steps: ");
			final int TIME_STEPS = scanner.nextInt();
			
			// Simulates based on how many time steps the user inputted
			for (int i = 0; i < TIME_STEPS; i++) {
				// Simulates the disease spread
				tempPatientsList = simulate(patientsList, tempPatientsList, ROWS, COLUMNS, INFECTED_RATE, RECOVERY_RATE);
				patientsList = tempPatientsList;
				tempPatientsList = "";

				// File writes onto file
				timeStepCount++;
				fileCreator(patientsList, tempPatientsList, sCount, iCount, rCount, timeStepCount, ROWS, COLUMNS);
				sCount = 0;
				rCount = 0;
				iCount = 0;
				tempPatientsList = "";
			}

			// Asks the user if they want to simulate again
			anotherSimulation = simulateAgain(anotherSimulation);
			deleteFiles(timeStepCount);
		} while(anotherSimulation != 'N');
	}
}