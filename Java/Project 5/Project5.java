/**
 * CS 360 Project 5 Due 04/02/15
 *
 * Project5.java
 * Kyle Galloway
 *
 * Fulfills all requirements for Project 5 in CS 360.
 * Compiled with Java 7.
 */

public class Project5 {
	// main method
	public static void main( String[] args ) {
		Boggle game = new Boggle();
		game.readBoard();
		game.readDictionary();
		game.run();
	} // End main method
} // End class Project5
