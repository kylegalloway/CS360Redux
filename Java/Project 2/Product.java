/*******************************************************************************
* CS 360 Project 2 Due 02/08/15                                                *
*                                                                              *
* Product.java                                                                 *
* Kyle Galloway                                                                *
*                                                                              *
* Product class                                                                *
* Contains functions and methods to access and modify the product's properties.*
*                                                                              *
*******************************************************************************/

public class Product
{
 	private int quantity;
	private String name;
	private long manufacturerCode;
	private long productCode;

	public Product(String name, long manufacturerCode, long productCode)
	{
		this.name             = name;
		this.manufacturerCode = manufacturerCode;
		this.productCode      = productCode;
		this.quantity         = 1;
	} // End constructor

	// Returns the name of the product.
	public String getName()
	{
		return name;
	} // End function getName

	// Returns the manufacturer code of the product.
	public long getManufacturerCode()
	{
		return manufacturerCode;
	} // End function getManufacturerCode

	// Returns the complete code of the product.
	public long getProductCode()
	{
		return productCode;
	} // End function getProductCode

	// Returns the quantity of the product.
	public int getQuantity()
	{
		return quantity;
	} // End function getQuantity

	// Adds one to the quantity of the product.
	public void addQuantity()
	{
		quantity++;
	} // End method addQuantity

	// Returns the string representation of the product.
	public String toString()
	{
		return ("QTY " + quantity + " - " + name);
	} // End function toString
} // End class Product