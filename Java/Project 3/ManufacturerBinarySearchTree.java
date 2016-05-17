/**
 * CS 360 Project 3 Due 02/22/15
 *
 * ManufacturerBinarySearchTree.java
 * Kyle Galloway
 *
 * ManufacturerBinarySearchTree class
 * Contains functions and methods to access and modify
 * the manufacturerBinarySearchTree properties.
 *
 **/

public class ManufacturerBinarySearchTree
{

	Manufacturer[] manufacturerArray;
	private Node root;
	private int  numberOfManufacturers;

	// Constructor for the ManufacturerBinarySearchTree
	public ManufacturerBinarySearchTree()
	{
		manufacturerArray = new Manufacturer[ 1 ];
		root = null;
		numberOfManufacturers = 0;
	} // End Constructor

	// Returns the number of Manufacturers
	public int getNumberOfManufacturers() { return numberOfManufacturers; }

	// Returns the root of the Binary Search Tree
	public Node getRoot() { return root; }

	// Adds a manufacturer to the ManufacturerBinarySearchTree
	public void addManufacturer( Manufacturer manufacturer )
	{
		Node trailer = null;
		Node pointer = root;
		while ( pointer != null )
		{
			trailer = pointer;
			if ( manufacturer.getCode() < pointer.manufacturer.getCode() )
			{
				pointer = pointer.left;
			}
			else
			{
				pointer = pointer.right;
			}
		}
		if ( trailer == null )
		{
			root = new Node( manufacturer );
			addManufacturerToArray( manufacturer );
			numberOfManufacturers++;
		}
		else if ( manufacturer.getCode() < trailer.manufacturer.getCode() )
		{
			trailer.left = new Node( manufacturer );
			addManufacturerToArray( manufacturer );
			numberOfManufacturers++;
		}
		else
		{
			trailer.right = new Node( manufacturer );
			addManufacturerToArray( manufacturer );
			numberOfManufacturers++;
		}
	} // Ends method addManufacturer

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
	} // End method addManufacturerToArray

	// Searches for a manufacturer by code.
	private Node searchForManufacturer( Node n, long codeToFind )
	{
		if ( n == null || codeToFind == n.manufacturer.getCode() )
		{
			return n;
		}
		if ( codeToFind < n.manufacturer.getCode() )
		{
			return searchForManufacturer( n.left, codeToFind );
		}
		else
		{
			return searchForManufacturer( n.right, codeToFind );
		}
	} // End function searchForManufacturer

	// Adds a new Product to the manufacturer that it belongs.
	public void addProduct( String productName, long manufacturerCode, long productCode )
	{
		Manufacturer manufacturer = searchForManufacturer( getRoot(), manufacturerCode ).manufacturer;
		if ( manufacturer != null )
		{
			manufacturer.addProduct( productName, manufacturerCode, productCode );
		}
		else
		{
			System.err.printf( "Manufacturer matching manufacturer code %06d does not exist.%n", manufacturerCode );
		}
	} // End function addProduct

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
	} // End method printReport

	private class Node
	{
		public Manufacturer manufacturer;
		public Node         left;
		public Node         right;

		// Constructor for Node Class
		public Node( Manufacturer manufacturer )
		{
			this.manufacturer = manufacturer;
			this.left = null;
			this.right = null;
		}
	} // End of Node Class
}