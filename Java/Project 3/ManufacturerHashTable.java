/**
 * CS 360 Project 3 Due 02/22/15
 *
 * ManufacturerHashTable.java
 * Kyle Galloway
 *
 * ManufacturerHashTable class
 * Contains functions and methods to access and modify
 * the manufacturerHash properties.
 */

public class ManufacturerHashTable
{
	HashTableEntry[] table;
	Manufacturer[]   manufacturerArray;
	private int tableSize             = 128;
	private int numberOfManufacturers = 0;
	private int randA;
	private int randB;
	private int tablePrime;

	// Constructor for the Manufacturer Hash Table
	ManufacturerHashTable()
	{
		manufacturerArray = new Manufacturer[ 1 ];
		tablePrime = getNextPrime( tableSize );
		randA = ( int ) Math.ceil( Math.random() * tablePrime );
		randB = ( int ) Math.floor( Math.random() * tablePrime );
		table = new HashTableEntry[ tableSize ];
		for ( int i = 0; i < tableSize; i++ ) { table[ i ] = null; }
	} // Ends Constructor

	// Returns the next prime after the given number.
	private int getNextPrime( int n )
	{
		int i = n;
		while ( i < 2 * n )
		{
			if ( !isPrime( i ) ) { i++; }
			return i;
		}
		return i;
	} // Ends function getNextPrime

	// Returns true if the number is prime.
	private boolean isPrime( int n )
	{
		for ( int i = 2; i <= n; i++ )
		{
			if ( n % i == 0 ) { return false; }
		}
		return true;
	} // Ends function isPrime

	// Adds a manufacturer to the Manufacturer Hash Table.
	public void addManufacturer( Manufacturer manufacturer )
	{
		if ( numberOfManufacturers / tableSize > 0.5 )
		{
			HashTableResize();
		}

		int hash = ( int ) ( ( ( randA * ( ( int ) manufacturer.getCode() ) + randB ) % tablePrime ) % tableSize );
		int i = 0;
		while ( table[ hash ] != null && table[ hash ].manufacturer.getCode() != manufacturer.getCode() )
		{
			hash = ( int ) ( ( ( ( randA * ( ( int ) manufacturer.getCode() ) + randB ) + Math.pow( i, 2 ) ) % tablePrime ) % tableSize );
			i++;
		}
		table[ hash ] = new HashTableEntry( manufacturer );
		addManufacturerToArray( manufacturer );
		numberOfManufacturers++;
	} // Ends method addManufacturer

	// Resizes and Rehashes the Hash Table
	private void HashTableResize()
	{
		int newTablePrime = getNextPrime( tableSize );
		int newRandA = ( int ) Math.ceil( Math.random() * newTablePrime );
		int newRandB = ( int ) Math.floor( Math.random() * newTablePrime );
		int newTableSize = tableSize * 2;
		HashTableEntry[] newTable = new HashTableEntry[ newTableSize ];

		for ( int i = 0; i < newTableSize; i++ ) { newTable[ i ] = null; }

		for ( HashTableEntry each : table )
		{
			int hash = ( int ) ( ( ( newRandA * ( ( int ) each.manufacturer.getCode() ) + newRandB ) % newTablePrime ) % tableSize );
			int i = 0;
			while ( table[ hash ] != null && table[ hash ].manufacturer.getCode() != each.manufacturer.getCode() && i < tableSize )
			{
				hash = ( int ) ( ( ( ( newRandA * ( ( int ) each.manufacturer.getCode() ) + newRandB ) + Math.pow( i, 2 ) ) % newTablePrime ) % tableSize );
				i++;
			}
			table[ hash ] = new HashTableEntry( each.manufacturer );
		}

		tableSize = newTableSize;
		table = newTable;
		randA = newRandA;
		randB = newRandB;
		tablePrime = newTablePrime;
	} // Ends method HashTableResize

	// Adds a manufacturer to the manufacturer reference array.
	public void addManufacturerToArray( Manufacturer manufacturer )
	{
		if ( manufacturerArray.length <= numberOfManufacturers )
		{
			Manufacturer[] newManufacturerArray = new Manufacturer[ 2 * numberOfManufacturers ];

			for ( int i = 0; i < numberOfManufacturers; i++ )
			{
				newManufacturerArray[ i ] = manufacturerArray[ i ];
			}

			manufacturerArray = newManufacturerArray;
		}

		manufacturerArray[ numberOfManufacturers ] = manufacturer;
	} // Ends method addManufacturerToArray

	// Returns the number of Manufacturers
	public int getNumberOfManufacturers() { return numberOfManufacturers; }

	// Returns the table size
	public int getTableSize() { return tableSize; }

	// Adds a new Product to the manufacturer that it belongs.
	public void addProduct( String productName, long manufacturerCode, long productCode )
	{
		int hashSpot = searchForManufacturer( manufacturerCode );
		if ( hashSpot >= 0 )
		{
			table[ hashSpot ].manufacturer.addProduct( productName, manufacturerCode, productCode );
		}
		else
		{
			System.err.printf( "Manufacturer matching manufacturer code %06d does not exist.%n", manufacturerCode );
		}
	} // End function addProduct

	// Searches for a manufacturer by code.
	private int searchForManufacturer( long codeToFind )
	{
		int hash = ( int ) ( ( ( randA * ( ( int ) codeToFind ) + randB ) % tablePrime ) % tableSize );
		int i = 0;
		while ( table[ hash ] != null && i < tableSize )
		{
			hash = ( int ) ( ( ( ( randA * ( ( int ) codeToFind ) + randB ) + Math.pow( i, 2 ) ) % tablePrime ) % tableSize );
			if ( table[ hash ].manufacturer.getCode() == codeToFind ) { return hash; }
			i++;
		}
		return -1;
	} // Ends function searchForManufacturer

	// Quicksort method to sort the manufacturerArray
	private void manufacturerArrayQuickSort( Manufacturer[] manufacturerArray, int startPoint, int endPoint )
	{
		if ( startPoint < endPoint )
		{
			int midPoint = manufacturerArrayPartition( manufacturerArray, startPoint, endPoint );
			manufacturerArrayQuickSort( manufacturerArray, startPoint, midPoint - 1 );
			manufacturerArrayQuickSort( manufacturerArray, midPoint + 1, endPoint );
		}
	} // End method manufacturerArrayQuickSort

	// Partition function for quicksort method
	private int manufacturerArrayPartition( Manufacturer[] manufacturerArray, int startPoint, int endPoint )
	{
		Manufacturer pivot = manufacturerArray[ endPoint ];
		int index = startPoint - 1;
		for ( int j = startPoint; j < endPoint; j++ )
		{
			if ( manufacturerArray[ j ].getName().compareToIgnoreCase( pivot.getName() ) < 0 )
			{
				index += 1;
				Manufacturer exchange = manufacturerArray[ index ];
				manufacturerArray[ index ] = manufacturerArray[ j ];
				manufacturerArray[ j ] = exchange;
			}
		}
		Manufacturer exchange = manufacturerArray[ index + 1 ];
		manufacturerArray[ index + 1 ] = manufacturerArray[ endPoint ];
		manufacturerArray[ endPoint ] = exchange;
		return index + 1;
	} // End function manufacturerArrayPartition

	// Prints the final values out in the correct format.
	public void printReport()
	{
		manufacturerArrayQuickSort( manufacturerArray, 0, numberOfManufacturers - 1 );
		String previousName = null;
		for ( Manufacturer each : manufacturerArray )
		{
			if ( each != null )
			{
				if ( !each.getName().equals( previousName ) )
				{
					System.out.println( each.getName() );
				}
				if ( each.getNumberOfProducts() > 0 )
				{
					Product[] productsArray = each.getProductsArray();
					for ( int j = 0; j < each.getNumberOfProducts(); j++ )
					{
						System.out.println( productsArray[ j ].toString() );
					}
				}
				else
				{
					System.err.printf( "%s does not have any products.%n", each.getCode() );
				}
				previousName = each.getName();
			}
		}
	} // Ends method printReport

	private class HashTableEntry
	{
		public Manufacturer manufacturer;

		// Constructor for HashtableEntry
		HashTableEntry( Manufacturer manufacturer ) { this.manufacturer = manufacturer; }
	} // End class HashTableEntry
} // End class ManufacturerHashTable