/**
 * CS 360 Project 5 Due 04/02/15
 * <p/>
 * Boggle.java
 * Kyle Galloway
 * <p/>
 * Fulfills all requirements for Project 5 in CS 360.
 * Compiled with Java 7.
 */

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

class Boggle {
	private Scanner           input;
	private String[][]        inputBoard;
	private ArrayList<String> dictionary;
	private Graph             board;

	// File read operations for the board file.
	public void readBoard() {
		try {
			input = new Scanner( Paths.get( "board.txt" ) );

			String[] line = input.nextLine().split( " " );

			int rows = Integer.parseInt( line[ 0 ] );
			int columns = Integer.parseInt( line[ 1 ] );
			inputBoard = new String[ rows ][ columns ];

			for ( int i = 0; i < rows; i++ ) {
				line = input.nextLine().split( " " );
				for ( int j = 0; j < columns; j++ ) {
					inputBoard[ i ][ j ] = line[ j ];
				}
			}
		}
		catch ( IOException ioException ) {
			System.err.println( "Error opening file. Terminating." );
			System.exit( 1 );
		}
		catch ( NoSuchElementException elementException ) {
			System.err.println( "File improperly formed. Terminating." );
			System.exit( 1 );
		}
		catch ( IllegalStateException stateException ) {
			System.err.println( "Error reading from file. Terminating." );
			System.exit( 1 );
		}

		if ( input != null ) { input.close(); }
	} // End method readBoard

	// File read operations for the dictionary file.
	public void readDictionary() {
		try {
			input = new Scanner( Paths.get( "dict.txt" ) );
			dictionary = new ArrayList<String>( 10 );
			while ( input.hasNext() ) {
				String line = input.nextLine();
				if ( line.length() > 1 ) { dictionary.add( line.trim().toUpperCase() ); }
				else { input.nextLine(); }
			}
		}
		catch ( IOException ioException ) {
			System.err.println( "Error opening file. Terminating." );
			System.exit( 1 );
		}
		catch ( NoSuchElementException elementException ) {
			System.err.println( "File improperly formed. Terminating." );
			System.exit( 1 );
		}
		catch ( IllegalStateException stateException ) {
			System.err.println( "Error reading from file. Terminating." );
			System.exit( 1 );
		}

		if ( input != null ) { input.close(); }
	} // End method readDictionary

	// The main logic function of the boggle solver
	public void run() {
		// Built-in enhanced mergesort O(n lg n)
		Collections.sort( dictionary );
		// Creates a new Graph based on the input board (2D array)
		board = new Graph( inputBoard );

		ArrayList<Vertex> vertices = board.getVertices();

		// Runs a depth-first search on each vertex of the Graph
		for ( Vertex vertex : vertices ) {
			board.DFS( dictionary, vertex );
		}
	} // End method run
} // End class Boggle
