/*******************************************************************************
* CS 360 Project 2 Due 02/08/15                                                *
*                                                                              *
* Project2.java                                                                *
* Kyle Galloway                                                                *
*                                                                              *
* Fulfills all requirements for Project2 in CS 360.                            *
* Compiled with Java 7.                                                        *
*                                                                              *
*                                                                              *
* Run time averaged ~6 secs for test files.                                    *
*                                                                              *
*******************************************************************************/

import java.util.Scanner;
import java.io.IOException;
import java.lang.IllegalStateException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

public class Project2 
{
	private static Scanner input;
	private static DynamicManufacturerArray manufacturers = new DynamicManufacturerArray();


	// main method
	public static void main(String[] args)
	{
		// Run time averaged ~6 secs for test files.
		// final long startTime = System.currentTimeMillis();

		readMCodesFile();
		readSalesFile();
		printReport();

		// final long endTime = System.currentTimeMillis();

		// System.err.printf("Total execution time: %.2f s",((endTime - startTime) * .001));
	} // End main method


	// File read operations for the manufacturing codes file.
	private static void readMCodesFile()
	{
		try
		{
			input = new Scanner(Paths.get("mcodes.csv"));

			while (input.hasNext())
			{
				String[] line           = input.nextLine().split(",");
				if (line.length > 1)
				{
					long manufacturerCode   = Long.parseLong(line[0]);
					
					// Replaces all beginning and ending quotes with an empty string.
					String manufacturerName = line[1].trim().replaceAll("^\"|\"$", "");

					manufacturerName = manufacturerName;
					manufacturers.addManufacturer(manufacturerCode, manufacturerName);
				}
				else
				{
					input.nextLine();
				}
			}
		}
		catch (IOException ioException)
		{
			System.err.println("Error opening file. Terminating.");
			System.exit(1);
		}
		catch (NoSuchElementException elementException)
		{
			System.err.println("File improperly formed. Terminating.");
			System.exit(1);
		}
		catch (IllegalStateException stateException)
		{
			System.err.println("Error reading from file. Terminating.");
			System.exit(1);
		}

		if (input != null)
			input.close();
	} // End method readMCodesFile

	// File read operations for the sales file.
	private static void readSalesFile()
	{
		try
		{
			input = new Scanner(Paths.get("sales.csv"));

			while (input.hasNext())
			{
				// Split on the comma, if that comma has an even number of quotes ahead of it.
				// This fixes any comma split problems that occur from strings with commas inside them.
				String[] line = input.nextLine().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				if (line.length > 1)
				{
					long productCode      = Long.parseLong(line[0]);
					long manufacturerCode = Long.parseLong(line[0].substring(0,6));

					// Replaces all beginning and ending quotes with an empty string.
					String productName    = line[2].trim().replaceAll("^\"|\"$", "");
					
					productName = productName;
					manufacturers.addProduct(productName, manufacturerCode, productCode);
				}
				else
				{
					input.nextLine();
				}

			}
		}
		catch (IOException ioException)
		{
			System.err.println("Error opening file. Terminating.");
			System.exit(1);
		}
		catch (NoSuchElementException elementException)
		{
			System.err.println("File improperly formed. Terminating.");
			System.exit(1);
		}
		catch (IllegalStateException stateException)
		{
			System.err.println("Error reading from file. Terminating.");
			System.exit(1);
		}

		if (input != null)
			input.close();
	} // End method readSalesFile

	// Prints the final values out in the correct format.
	private static void printReport()
	{
		for (int i = 0; i < manufacturers.getNumberOfManufacturers(); i++)
		{
			String manufacturerName = manufacturers.getManufacturersArray()[i].getName();
			System.out.println(manufacturerName);
			if (manufacturers.manufacturerHasProducts(manufacturerName))
			{
				manufacturers.printManufacturerProducts(manufacturerName);
			}
			else
			{
				System.out.printf("%s does not have any products.%n",manufacturerName);
			}
			System.out.println();
		}
	} // End method printReport
} // End class Project2
