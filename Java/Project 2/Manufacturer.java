/*******************************************************************************
* CS 360 Project 2 Due 02/08/15                                                *
*                                                                              *
* Manufacturer.java                                                            *
* Kyle Galloway                                                                *
*                                                                              *
* Manufacturer class                                                           *
* Contains functions and methods to access and modify the                      *
* manufacturer's properties.                                                   *
*                                                                              *
*******************************************************************************/


public class Manufacturer
{
 	private long[] manufacturerCodeArray;
	private String manufacturerName;
	private Product[] productsArray;
	private int numberOfProducts;

	public Manufacturer(long[] manufacturerCodeArray, String manufacturerName)
	{
		this.manufacturerCodeArray = manufacturerCodeArray;
	 	this.manufacturerName = manufacturerName;
	 	productsArray = new Product[10];
	 	numberOfProducts = 0;
	} // End constructor

	// Returns the array of manufacturing codes attached to the manufacturer.
	public long[] getCodeArray()
	{
		return manufacturerCodeArray;
	} // End function getCodeArray

	// Returns the name of the manufacturer.
	public String getName()
	{
		return manufacturerName;
	} // End function getName

	// Returns the number of products attached to the manufacturer.
	public int getNumberOfProducts()
	{
		return numberOfProducts;
	} // End function getNumberOfProducts

	// Returns the array of products attached to the manufacturer.
	public Product[] getProductsArray()
	{
		if (numberOfProducts > 0)
		{
			return productsArray;
		}
		else
		{
			System.err.printf("There are no products for %s", manufacturerName);
			System.exit(1);
		}
		return null;
	} // End function getProductsArray

	// Adds a new code to the manufacturer.
	public void addCode(long[] newManufacturerCodeArray)
	{
		int totalLength = newManufacturerCodeArray.length + manufacturerCodeArray.length;
		long[] newCodeArray = new long[totalLength];

		for (int i = 0; i < manufacturerCodeArray.length; i++)
		{
			newCodeArray[i] = manufacturerCodeArray[i];
		}

		for (int j = 0; j < newManufacturerCodeArray.length; j++)
		{
			newCodeArray[j + manufacturerCodeArray.length] = newManufacturerCodeArray[j];
		}

		manufacturerCodeArray = newCodeArray;
	} // End method addCode

	// Adds a new product to the manufacturer.
	public void addProduct(String productName, long manufacturerCode, long productCode)
	{
		if (productsArray.length == numberOfProducts)
		{
		 	Product[] newProductsArray = new Product[2 * numberOfProducts];

			for (int i = 0; i < numberOfProducts; i++)
				{
					newProductsArray[i] = productsArray[i];
				}

			productsArray = newProductsArray;
		}

		boolean sameProduct = false;
	    for (int i = 0; i < numberOfProducts; i++)
		{
			if (productsArray[i].getName().equalsIgnoreCase(productName))
			{
				sameProduct = true;
				productsArray[i].addQuantity();
			}
		}

		if (!sameProduct)
		{
			Product product = new Product(productName, manufacturerCode, productCode);
			if (numberOfProducts > 0)
			{
				int insertPosition = binarySearchForProductCode(productCode);
				for (int j = numberOfProducts - 1; j >= insertPosition; j--)
				{
					productsArray[j + 1] = productsArray[j];
				}
				productsArray[insertPosition] = product;
				numberOfProducts++;
			}
			else
			{
				productsArray[0] = product;
				numberOfProducts++;
			}
		}
	} // End method addProduct

	// Finds the correct position to insert the given code.
	// This keeps the code array sorted.
	private int binarySearchForProductCode(long productCode) 
	{
		int low = 0;
		int high = numberOfProducts - 1;
		int currentIndex = (low + high) / 2;

		while (low <= high)
		{
			currentIndex = (low + high) / 2;
			long existingCode = productsArray[currentIndex].getProductCode();

			if (existingCode < productCode)
			{
				low = currentIndex + 1;
			}
			else if (existingCode > productCode)
			{
				high = currentIndex - 1;
			}
		}
		
		if (productsArray[currentIndex].getProductCode() < productCode)
		{
			return currentIndex + 1;
		}
		else
		{
			return currentIndex;
		}
	} // End function binarySearchForProductCode
} // End class Manufacturer