/**
 * CS 360 Project 3 Due 02/22/15
 *
 * Manufacturer.java
 * Kyle Galloway
 *
 * Product class
 * Contains functions and methods to access and modify the product's properties.
 *
 * Manufacturer class
 * Contains functions and methods to access and modify the
 * manufacturer's properties.
 */

class Product
{
	private int    quantity;
	private String name;
	private long   manufacturerCode;
	private long   productCode;

	// Constructor for Product Class
	public Product( String name, long manufacturerCode, long productCode )
	{
		this.name = name;
		this.manufacturerCode = manufacturerCode;
		this.productCode = productCode;
		this.quantity = 1;
	} // End constructor

	// Returns the name of the product.
	public String getName() { return name; }

	// Returns the manufacturer code of the product.
	public long getManufacturerCode() { return manufacturerCode; }

	// Returns the complete code of the product.
	public long getProductCode() { return productCode; }

	// Returns the quantity of the product.
	public int getQuantity() { return quantity; }

	// Adds one to the quantity of the product.
	public void addQuantity() { quantity++; }

	// Returns the string representation of the product.
	@Override
	public String toString() { return ( "QTY " + quantity + " - " + name ); }
} // End class Product


public class Manufacturer
{
	private long      manufacturerCode;
	private String    manufacturerName;
	private Product[] productsArray;
	private int       numberOfProducts;

	// Constructor for Manufacturer Class
	public Manufacturer( long manufacturerCode, String manufacturerName )
	{
		this.manufacturerCode = manufacturerCode;
		this.manufacturerName = manufacturerName;
		productsArray = new Product[ 10 ];
		numberOfProducts = 0;
	} // End constructor

	// Returns the array of manufacturing codes attached to the manufacturer.
	public long getCode() { return manufacturerCode; }

	// Returns the name of the manufacturer.
	public String getName() { return manufacturerName; }

	// Returns the number of products attached to the manufacturer.
	public int getNumberOfProducts() { return numberOfProducts; }

	// Returns the array of products attached to the manufacturer.
	public Product[] getProductsArray()
	{
		if ( numberOfProducts > 0 )
		{
			productArrayQuickSort( productsArray, 0, numberOfProducts - 1 );
			return productsArray;
		}
		else
		{
			System.err.printf( "There are no products for %s", manufacturerName );
			System.exit( 1 );
		}
		return null;
	} // End function getProductsArray

	// Adds a new product to the manufacturer.
	public void addProduct( String productName, long manufacturerCode, long productCode )
	{
		if ( productsArray.length == numberOfProducts )
		{
			Product[] newProductsArray = new Product[ 2 * numberOfProducts ];

			for ( int i = 0; i < numberOfProducts; i++ )
			{
				newProductsArray[ i ] = productsArray[ i ];
			}

			productsArray = newProductsArray;
		}

		boolean sameProduct = false;
		for ( int i = 0; i < numberOfProducts; i++ )
		{
			if ( productsArray[ i ].getName().equalsIgnoreCase( productName ) )
			{
				sameProduct = true;
				productsArray[ i ].addQuantity();
			}
		}

		if ( !sameProduct )
		{
			Product product = new Product( productName, manufacturerCode, productCode );
			productsArray[ numberOfProducts++ ] = product;
		}
	} // End method addProduct

	// Quicksort method to sort the productsArray
	private void productArrayQuickSort( Product[] productsArray, int startPoint, int endPoint )
	{
		if ( startPoint < endPoint )
		{
			int midPoint = productArrayPartition( productsArray, startPoint, endPoint );
			productArrayQuickSort( productsArray, startPoint, midPoint - 1 );
			productArrayQuickSort( productsArray, midPoint + 1, endPoint );
		}
	} // End method productArrayQuicksort

	// Partition function for quicksort method
	private int productArrayPartition( Product[] productsArray, int startPoint, int endPoint )
	{
		Product pivot = productsArray[ endPoint ];
		int index = startPoint - 1;
		for ( int j = startPoint; j < endPoint; j++ )
		{
			if ( productsArray[ j ].getProductCode() < pivot.getProductCode() )
			{
				index += 1;
				Product exchange = productsArray[ index ];
				productsArray[ index ] = productsArray[ j ];
				productsArray[ j ] = exchange;
			}
		}
		Product exchange = productsArray[ index + 1 ];
		productsArray[ index + 1 ] = productsArray[ endPoint ];
		productsArray[ endPoint ] = exchange;
		return index + 1;
	} // End method productArrayPartition
} // End class Manufacturer