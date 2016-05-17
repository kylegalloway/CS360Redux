/*******************************************************************************
* CS 360 Project 2 Due 02/08/15                                                *
*                                                                              *
* DynamicManufacturerArray.java                                                *
* Kyle Galloway                                                                *
*                                                                              *
* Dynamic array class                                                          *
* Custom built from basic array type.                                          *
* Contains an array of Manufacturers with                                      *
* functions and  methods to access their properties.                           *
*                                                                              *
*******************************************************************************/

public class DynamicManufacturerArray
{
	private Manufacturer[] manufacturerArray;
 	private int numberOfManufacturers;

	public DynamicManufacturerArray()
	{
	 	manufacturerArray = new Manufacturer[100];
		numberOfManufacturers = 0;
	} // End constructor

	// Returns the number of manufacturers currently in the array.
	public int getNumberOfManufacturers()
	{
		return numberOfManufacturers;
	} // End function getNumberOfManufacturers

	// Returns the manufacturers array.
	public Manufacturer[] getManufacturersArray()
	{
		if (numberOfManufacturers > 0)
		{
			return manufacturerArray;
		}
		else
		{
			System.err.println("There are no manufacturers.");
			System.exit(1);
		}
		return null;
	} // End function getManufacturersArray

	// Inserts a new manufacturer into the manufacturers array.
	// Doubles the size if needed.
	public void addManufacturer(long manufacturerCode, String manufacturerName)
	{
		long[] newManufacturerCodeArray = {manufacturerCode};

		if (manufacturerArray.length == numberOfManufacturers)
		{
		 	Manufacturer[] newManufacturerArray = new Manufacturer[2 * numberOfManufacturers];

			for (int i = 0; i < numberOfManufacturers; i++)
				{
					newManufacturerArray[i] = manufacturerArray[i];
				}

			manufacturerArray = newManufacturerArray;
		}

		boolean sameName = false;
	    for (int i = 0; i < numberOfManufacturers; i++)
		{
			if (manufacturerArray[i].getName().equalsIgnoreCase(manufacturerName))
			{
				sameName = true;
				manufacturerArray[i].addCode(newManufacturerCodeArray);
			}
		}

		if (!sameName)
		{
			Manufacturer newManufacturer = new Manufacturer(newManufacturerCodeArray, manufacturerName);
			if (numberOfManufacturers > 0)
			{
				int insertPosition = binarySearchForManufacturerName(manufacturerName);
				for (int j = numberOfManufacturers - 1; j >= insertPosition; j--)
				{
					manufacturerArray[j + 1] = manufacturerArray[j];
				}
				manufacturerArray[insertPosition] = newManufacturer;
				numberOfManufacturers++;
			}
			else
			{
				manufacturerArray[0] = newManufacturer;
				numberOfManufacturers++;
			}
		}
	} // End method addManufacturer

	// Returns the index of where the given manufacturer name should be placed.
	// This keeps the manufacturers array sorted.
	private int binarySearchForManufacturerName(String manufacturerName) 
	{
		int low = 0;
		int high = numberOfManufacturers - 1;
		int currentIndex = (low + high) / 2;

		while (low <= high)
		{
			currentIndex = (low + high) / 2;
			int comparison = manufacturerArray[currentIndex].getName().compareToIgnoreCase(manufacturerName);

			if (comparison < 0)
			{
				low = currentIndex + 1;
			}
			else if (comparison > 0)
			{
				high = currentIndex - 1;
			}
		}
		
		if (manufacturerArray[currentIndex].getName().compareToIgnoreCase(manufacturerName) < 0)
		{
			return currentIndex + 1;
		}
		else
		{
			return currentIndex;
		}
	} // End function binarySearchForManufacturerName

	// Inserts a new product into the product array.
	// Doubles the size if needed.
	public void addProduct(String productName, long manufacturerCode, long productCode)
	{
		boolean manufacturerFound = false;
		for (int i = 0; i < numberOfManufacturers; i++)
		{
			long[] codeArray = manufacturerArray[i].getCodeArray();

			for (int j = 0; j < codeArray.length; j++)
			{
				if (codeArray[j] == manufacturerCode)
				{
					manufacturerFound = true;
					manufacturerArray[i].addProduct(productName, manufacturerCode, productCode);
				}
			}
		}

		if (!manufacturerFound)
		{
			System.err.printf("Manufacturer matching manufacturer code %06d does not exist.%n", manufacturerCode);
		}
	} // End function addProduct

	// Prints out a list of all Manufacturers.
	// For testing only.
	public void printAllManufacturers()
	{
		for (int i = 0; i < numberOfManufacturers; i++)
		{
			System.out.println(manufacturerArray[i].getName());
		}
	} // End method printAllManufacturers

	// Prints out a list of all codes for a given manufacturer.
	// For testing only.
	public void printManufacturerCodes(String manufacturerName)
	{
		for (int i = 0; i < numberOfManufacturers; i++)
		{
			if (manufacturerArray[i].getName().equalsIgnoreCase(manufacturerName))
			{
				long[] manufacturerCodeArray = manufacturerArray[i].getCodeArray();
				for (long manufacturerCode : manufacturerCodeArray)
				{
					System.out.println(String.format("%06d", manufacturerCode));
				}
			}
		}
	} // End method printManufacturerCodes

	// Prints out a list of all products for a given manufacturer.
	// For testing only.
	public void printManufacturerProducts(String manufacturerName)
	{
		for (int i = 0; i < numberOfManufacturers; i++)
		{
			if (manufacturerArray[i].getName().equalsIgnoreCase(manufacturerName))
			{
				Product[] productsArray = manufacturerArray[i].getProductsArray();
				for (int j = 0; j < manufacturerArray[i].getNumberOfProducts(); j++)
				{
					System.out.println(productsArray[j].toString());
				}
			}
		}
		if (!manufacturerHasProducts(manufacturerName))
		{
			System.err.printf("%s does not have any products",manufacturerName);
		}
	} // End method printManufacturerCodes

	// Returns a boolean that evaluates whether a manufacturer contains any products.
	// For testing only.
	public boolean manufacturerHasProducts(String manufacturerName)
	{
		for (int i = 0; i < numberOfManufacturers; i++)
		{
			if (manufacturerArray[i].getName().equalsIgnoreCase(manufacturerName))
			{
				if (manufacturerArray[i].getNumberOfProducts() > 0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		return false;
	} // End function manufacturerHasProducts
} // End class DynamicManufacturerArray