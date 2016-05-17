/**
 * CS 360 Project 3 Due 02/22/15
 *
 * Project3.java
 * Kyle Galloway
 *
 * Fulfills all requirements for Project3 in CS 360.
 * Compiled with Java 7.
 *
 **/

import java.io.IOException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Project3
{
	private static Scanner                      input;
	private static ManufacturerBinarySearchTree manufacturerBinarySearchTree;
	private static ManufacturerHashTable        manufacturerHashTable;


	// main method
	public static void main( String[] args )
	{
		if ( args.length == 1 && args[ 0 ].equals( "-b" ) )
		{
			manufacturerBinarySearchTree = new ManufacturerBinarySearchTree();
			readMCodesFile( 0 );
			readSalesFile( 0 );
			printReport( 0 );
		}
		else if ( args.length == 0 )
		{
			manufacturerHashTable = new ManufacturerHashTable();
			readMCodesFile( 1 );
			readSalesFile( 1 );
			printReport( 1 );
		}
		else
		{
			System.err.printf( "%n%s%n%n%s%n%s%n%s%n", "Please supply a valid set of arguments.",
			                   "Either supply no arguments (hashTable) or ",
			                   "supply 1 argument that matches '-b'.",
			                   "Terminating." );
			System.exit( 1 );
		}
	} // End main method


	// File read operations for the manufacturing codes file.
	private static void readMCodesFile( int typeSwitch )
	{
		try
		{
			input = new Scanner( Paths.get( "mcodes.csv" ) );

			while ( input.hasNext() )
			{
				// Split on the comma, if that comma has an even number of quotes ahead of it.
				// This fixes any comma split problems that occur from strings with commas inside them.
				String[] line = input.nextLine().split( ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)" );
				if ( line.length > 1 )
				{
					long manufacturerCode = Long.parseLong( line[ 0 ] );

					// Replaces all beginning and ending quotes with an empty string.
					String manufacturerName = line[ 1 ].trim().replaceAll( "^\"|\"$", "" );

					Manufacturer newManufacturer = new Manufacturer( manufacturerCode, manufacturerName );
					if ( typeSwitch == 0 )
					{
						manufacturerBinarySearchTree.addManufacturer( newManufacturer );
					}
					else
					{
						manufacturerHashTable.addManufacturer( newManufacturer );
					}
				}
				else
				{
					input.nextLine();
				}
			}
		}
		catch ( IOException ioException )
		{
			System.err.println( "Error opening file. Terminating." );
			System.exit( 1 );
		}
		catch ( NoSuchElementException elementException )
		{
			System.err.println( "File improperly formed. Terminating." );
			System.exit( 1 );
		}
		catch ( IllegalStateException stateException )
		{
			System.err.println( "Error reading from file. Terminating." );
			System.exit( 1 );
		}

		if ( input != null )
		{ input.close(); }
	} // End method readMCodesFile

	// File read operations for the sales file.
	private static void readSalesFile( int typeSwitch )
	{
		try
		{
			input = new Scanner( Paths.get( "sales.csv" ) );

			while ( input.hasNext() )
			{
				// Split on the comma, if that comma has an even number of quotes ahead of it.
				// This fixes any comma split problems that occur from strings with commas inside them.
				String[] line = input.nextLine().split( ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)" );
				if ( line.length > 1 )
				{
					long productCode = Long.parseLong( line[ 0 ] );
					long manufacturerCode = Long.parseLong( line[ 0 ].substring( 0, 6 ) );

					// Replaces all beginning and ending quotes with an empty string.
					String productName = line[ 2 ].trim().replaceAll( "^\"|\"$", "" );

					if ( typeSwitch == 0 )
					{
						manufacturerBinarySearchTree.addProduct( productName, manufacturerCode, productCode );
					}
					else
					{
						manufacturerHashTable.addProduct( productName, manufacturerCode, productCode );
					}
				}
				else
				{
					input.nextLine();
				}

			}
		}
		catch ( IOException ioException )
		{
			System.err.println( "Error opening file. Terminating." );
			System.exit( 1 );
		}
		catch ( NoSuchElementException elementException )
		{
			System.err.println( "File improperly formed. Terminating." );
			System.exit( 1 );
		}
		catch ( IllegalStateException stateException )
		{
			System.err.println( "Error reading from file. Terminating." );
			System.exit( 1 );
		}

		if ( input != null )
		{ input.close(); }
	} // End method readSalesFile

	// Prints the final values out in the correct format.
	private static void printReport( int typeSwitch )
	{
		if ( typeSwitch == 0 )
		{
			manufacturerBinarySearchTree.printReport();
		}
		else
		{
			manufacturerHashTable.printReport();
		}
	} // End method printReport
} // End class Project3
