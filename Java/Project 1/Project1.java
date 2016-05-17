/***************************************************************************************************
* CS 360 Project 1 Due 1/25/15
* 
* Project1.java
* @author Kyle Galloway
* 
* Fulfills all requirements for Project1 in CS 360.
* Compiled with Java 7.
* The methods openFile and closeFile, as well as the function readFile were adapted from
* "Java: How to Program" by Deitel & Deitel (pg. 655-656).
* 
* Additions count includes all additions and subtractions.
* 
* <<< Any sections that are commented out were changed due to intentionally not padding matrices.
* 	  These sections are to be put back into the file after submission so that the program will
* 	  work with any square matrices, not only power of two matrices. >>>
* 
***************************************************************************************************/

import java.io.IOException;
import java.lang.IllegalStateException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Project1 {

	private static int n;
	private static int multiplications = 0;
	private static int additions = 0;
	// Taken out due to change in project requirements.
	// private static int[][] readMatrixA;
	// private static int[][] readMatrixB;
	private static int[][] matrixA;
	private static int[][] matrixB;
	private static int[][] matrixC;
	private static Scanner input;

		
	// main method
	public static void main(String[] args)
	{
		if (args.length == 0)
		{
			openFile();
			readFile();
			closeFile();
			// Taken out due to change in project requirements.
			// matrixA = makeMatrix(readMatrixA);
			// matrixB = makeMatrix(readMatrixB);
			matrixC = strassen(matrixA, matrixB);
			printFormatted(matrixA, matrixB, matrixC);
		}
		else if (args.length == 2)
		{
			if (args[0].equals("-n"))
			{
				try
				{
					n = Integer.parseInt(args[1]);
					
					if (n > 0) 
					{
						if (isPowerOfTwo(n))
						{
							int[][] matrixA = makeMatrix();
							int[][] matrixB = makeMatrix();
							int[][] matrixC = strassen(matrixA, matrixB);
							printFormatted(matrixA, matrixB, matrixC);
						}
						else
						{
							System.err.printf("%s%n%n%s%n%s%n%s%n%s%n","Please supply a valid set of arguments.",
							"Either supply no arguments (read from datafile) or ",
							"supply 2 arguments of the form '-n <int>',",
							"where <int> is a power of two and the size of the matrix.",
							"Terminating.");
							System.exit(1);
						}
					}
					else
					{
						System.err.printf("%s%n%n%s%n%s%n%s%n%s%n","Please supply a valid set of arguments.",
						"Either supply no arguments (read from datafile) or ",
						"supply 2 arguments of the form '-n <int>',",
						"where <int> is a power of two and the size of the matrix.",
						"Terminating.");
						System.exit(1);
					}

				}
				catch (NumberFormatException numberException)
				{
					System.err.printf("%s%n%n%s%n%s%n%s%n%s%n","Please supply a valid set of arguments.",
					"Either supply no arguments (read from datafile) or ",
					"supply 2 arguments of the form '-n <int>',",
					"where <int> is a power of two and the size of the matrix.",
					"Terminating.");
					System.exit(1);
				}
			}
			else
			{
				System.err.printf("%s%n%n%s%n%s%n%s%n%s%n","Please supply a valid set of arguments.",
				"Either supply no arguments (read from datafile) or ",
				"supply 2 arguments of the form '-n <int>',",
				"where <int> is a power of two and the size of the matrix.",
				"Terminating.");
				System.exit(1);
			}
		}
		else
		{
			System.err.printf("%s%n%n%s%n%s%n%s%n%s%n","Please supply a valid set of arguments.",
			"Either supply no arguments (read from datafile) or ",
			"supply 2 arguments of the form '-n <int>',",
			"where <int> is a power of two and the size of the matrix.",
			"Terminating.");
			System.exit(1);
		}
	} // end main method


	// open file datafile
	private static void openFile()
	{
		try
		{
			input = new Scanner(Paths.get("datafile"));
		}
		catch (IOException ioException)
		{
			System.err.println("Error opening file. Terminating.");
			System.exit(1);
		}
	} // end method openFile


	// read record from file
	// Changed due to change in project requirements.
	// Originally read a file as readMatrixA & readMatrixB.
	private static void readFile()
	{		
		try
		{
			while (input.hasNext()) // while there is more to read
			{
				n = Integer.parseInt(input.nextLine().substring(2));
				matrixA = new int[n][n];
				matrixB = new int[n][n];
				while (input.hasNext())
				{
					for(int i = 0; i < n; i++)
					{
						String[] line = input.nextLine().split(",");

						for(int j = 0; j < n; j++)
						{
							matrixA[i][j] = Integer.parseInt(line[j]);
						}
					}
					for(int i = 0; i < n; i++)
					{
						String[] line = input.nextLine().split(",");

						for(int j = 0; j < n; j++)
						{
							matrixB[i][j] = Integer.parseInt(line[j]);
						}
					}
				}
			}
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
	} // end method readFile

	
	// close file and terminate application
	private static void closeFile()
	{
		if (input != null)
			input.close();
	} // end method closeFile


	// returns true if the given int is a power of two
	private static boolean isPowerOfTwo(int num)
	{
		int x = 1;
		while(num >= x)
		{
			if (num == x)
			{
				return true;
			}
			x *= 2;
		}
		return false;
	} // end function isPowerOfTwo


	// Taken out due to change in project requirements.
	/* // returns the next power of two
	private static int nextPowerOfTwo(int num)
	{
		int x = 1;
		while(num >= x)
		{
			if (num == x)
			{
				return num;
			}
			x *= 2;
		}
		return num;
	} // end function nextPowerOfTwo */


	// makes a matrix with random values from 0-9
	private static int[][] makeMatrix()
	{
		// Taken out due to change in project requirements.
		/* int nextPowerN = nextPowerOfTwo(n);
		int[][] matrix = new int[nextPowerN][nextPowerN];
		
		for (int i = 0; i < nextPowerN; i++)
            for (int j = 0; j < nextPowerN; j++)

		int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
            	matrix[i][j] = 0;
            }
        } */
        
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
            	matrix[i][j] = (int) (Math.random() * 10);
            }
        }
            	
		return matrix;
	} // end function makeMatrix


	// Taken out due to change in project requirements.
	/*// makes a matrix with the read-in values
	private static int[][] makeMatrix( int[][] matrix)
	{
		// int nextPowerN = nextPowerOfTwo(n);
		// int[][] newMatrix = new int[nextPowerN][nextPowerN];
		// 
		// for (int i = 0; i < nextPowerN; i++)
        //     for (int j = 0; j < nextPowerN; j++)

		int n = matrix.length;
		int[][] newMatrix = new int[n][n];
		
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
            	newMatrix[i][j] = 0;
            }
        }

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
            	newMatrix[i][j] = matrix[i][j];
            }
        }
            	
		return newMatrix;
	} // end function makeMatrix */


	// takes two matrices and multiplies them using strassen's method. Recursive.
	private static int[][] strassen(int[][] matrix1, int[][] matrix2)
	{
		int n = matrix1.length;
		int matrix3[][] = new int[n][n];

		if (n < 2)
		{
            for (int i = 0; i < n; i++)
            {
	            for (int j = 0; j < n; j++)
	            {
	                for (int k = 0; k < n; k++)
	                {
	                    matrix3[i][k] += matrix1[i][j] * matrix2[j][k];
	                    multiplications++;
	                }
	            }
	        }
	        return matrix3;
        }
        else
        {
			int newN = n / 2;

			int[][] a11 = new int[newN][newN];
			int[][] a12 = new int[newN][newN];
			int[][] a21 = new int[newN][newN];
			int[][] a22 = new int[newN][newN];

			int[][] b11 = new int[newN][newN];
			int[][] b12 = new int[newN][newN];
			int[][] b21 = new int[newN][newN];
			int[][] b22 = new int[newN][newN];

			int[][] c11 = new int[newN][newN];
			int[][] c12 = new int[newN][newN];
			int[][] c21 = new int[newN][newN];
			int[][] c22 = new int[newN][newN];

			// this divides the matrices into 4 sub-matrices, each.
			for (int i = 0; i < newN; i++)
			{
	            for (int j = 0; j < newN; j++)
	            {
	                a11[i][j] = matrix1[i][j];
	                a12[i][j] = matrix1[i][j + newN];
	                a21[i][j] = matrix1[i + newN][j];
	                a22[i][j] = matrix1[i + newN][j + newN];

	                b11[i][j] = matrix2[i][j];
	                b12[i][j] = matrix2[i][j + newN];
	                b21[i][j] = matrix2[i + newN][j];
	                b22[i][j] = matrix2[i + newN][j + newN];
	     		}
	     	}

	     	// s1 = b12 - b22
			int[][] s1 = subtract(b12,b22);

	     	// s2 = a11 + a12
			int[][] s2 = add(a11,a12);

	     	// s3 = a21 + a22
			int[][] s3 = add(a21,a22);

	     	// s4 = b21 - b11
			int[][] s4 = subtract(b21,b11);

	     	// s5 = a11 + a22
			int[][] s5 = add(a11,a22);

	     	// s6 = b11 + b22
			int[][] s6 = add(b11,b22);

	     	// s7 = a12 - a22
			int[][] s7 = subtract(a12,a22);

	     	// s8 = b21 + b22
			int[][] s8 = add(b21,b22);

	     	// s9 = a11 - a21
			int[][] s9 = subtract(a11,a21);

	     	// s10 = b11 + b12
			int[][] s10 = add(b11,b12);



	        // p1 = (a11) * (b12 - b22)
	        int[][] p1 = strassen(a11, s1);

	        // p2 = (a11 + a12) * (b22)
	        int[][] p2 = strassen(s2, b22);

	        // p3 = (a21 + a22) * (b11)
	        int[][] p3 = strassen(s3, b11);

	        // p4 = (a22) * (b21 - b11)
	        int[][] p4 = strassen(a22, s4);

	        // p5 = (a11 + a22) * (b11 + b22)
	        int[][] p5 = strassen(s5, s6);

	        // p6 = (a12 - a22) * (b21 + b22)
	        int[][] p6 = strassen(s7, s8);

	        // p7 = (a11 - a21) * (b11 + b12)
	        int[][] p7 = strassen(s9, s10);



	        // c11 = p5 + p4 - p2 + p6
	        c11 = add(subtract(add(p5, p4), p2), p6);

	        // c12 = p1 + p2
	        c12 = add(p1, p2);

	        // c21 = p3 + p4
	        c21 = add(p3, p4);

	        // c22 = p5 + p1 - p3 - p7
	        c22 = subtract(subtract(add(p5, p1), p3), p7);

	        for (int i = 0; i < newN; i++)
	        {
	        	for (int j = 0; j < newN; j++)
	        	{
					matrix3[i][j]               = c11[i][j];
					matrix3[i][j + newN]        = c12[i][j];
					matrix3[i + newN][j]        = c21[i][j];
					matrix3[i + newN][j + newN] = c22[i][j];
	        	}
	        }

			return matrix3;
		}
	} // end function strassen


	// adds the given two matrices
	private static int[][] add(int[][] A, int[][] B)
	{
        int n = A.length;
        int[][] C = new int[n][n];

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                C[i][j] = A[i][j] + B[i][j];
                additions++;
            }
        }
        return C;
    } // end function add


    // subtracts the given two matrices
    private static int[][] subtract(int[][] A, int[][] B)
    {
        int n = A.length;
        int[][] C = new int[n][n];

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                C[i][j] = A[i][j] - B[i][j];
                additions++;
            }
        }
        return C;
    } // end function subtract

		
	// prints out the formatted program output.
	private static void printFormatted(int[][] matrixA, int[][] matrixB, int[][] matrixC)
	{
		System.out.printf("%nN=%d%n%n",n);


		System.out.println("Input matrix A");
		printMatrix(matrixA);

		System.out.println("Input matrix B");
		printMatrix(matrixB);

		System.out.println("Output matrix C");
		System.out.println("");
		printMatrix(matrixC);

		System.out.printf("Number of multiplications: %d%n%n",multiplications);
		System.out.printf("Number of additions: %d",additions);

		System.exit(1);
	} // end method printFormatted

	
	// prints out the given matrix in standard form.
	private static void printMatrix(int[][] matrix)
	{
		for (int i = 0; i < n; i++)
		{
            for (int j = 0; j < n; j++)
            {
            	System.out.printf("%6d",matrix[i][j]);
            }
            System.out.println("");
		}
		System.out.println("");
	} // end method printMatrix
} // end class Project1