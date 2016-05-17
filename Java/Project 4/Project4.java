/**
 * CS 360 Project 4 Due 03/11/15
 *
 * Project4.java
 * Kyle Galloway
 *
 * Fulfills all requirements for Project4 in CS 360.
 * Compiled with Java 7.
 */

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Project4 {
	private static Scanner input;
	private static Activity[] activities;
	private static int numberOfActivities = 0;

	public static void main( String[] args ) {
		readInput();
		quickSort( activities, 0, numberOfActivities - 1 ); // Run a quickSort on the entire activities array.
		printOptimal();
	} // End main

	public static void readInput() {
		try {
			input = new Scanner( System.in ); // Read input from stdin.

			while ( input.hasNext() ) { // Reads the first line of the file.
				String[] firstLine  = input.nextLine().split( ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)" );
				int largestTime     = Integer.parseInt( firstLine[ 0 ] );
				int totalActivities = Integer.parseInt( firstLine[ 1 ] );

				activities = new Activity[ totalActivities ];

				while ( input.hasNext() ) { // Reads the rest of the file.
					String[] line = input.nextLine().split( ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)" );

					if ( line.length > 1 ) {
						String name   = line[ 0 ].trim().replaceAll( "^\"|\"$", "" );
						int startTime = Integer.parseInt( line[ 1 ] );
						int endTime   = Integer.parseInt( line[ 2 ] );

						Activity newActivity = new Activity( name, startTime, endTime );
						activities[ numberOfActivities++ ] = newActivity;
					} // End if
					else { input.nextLine(); }
				} // End inner while
			} // End outer while
		} // End try
		catch ( NoSuchElementException elementException ) {
			System.err.println( "File improperly formed. Terminating." );
			System.exit( 1 );
		} // End catch

		if ( input != null ) { input.close(); }
	} // End readInput method

	// QuickSort method to sort the activitiesArray
	private static void quickSort( Activity[] activityArray, int startPoint, int endPoint ) {
		if ( startPoint < endPoint ) {
			int midPoint = partition( activityArray, startPoint, endPoint );
			quickSort( activityArray, startPoint, midPoint - 1 );
			quickSort( activityArray, midPoint + 1, endPoint );
		}
	} // End quickSort method

	// Partition function for quickSort method
	private static int partition( Activity[] activityArray, int startPoint, int endPoint ) {
		Activity pivot = activityArray[ endPoint ];
		int index = startPoint - 1;
		for ( int j = startPoint; j < endPoint; j++ )
		{
			if ( activityArray[ j ].getEndTime() < pivot.getEndTime() )
			{
				index += 1;
				Activity exchange = activityArray[ index ];
				activityArray[ index ] = activityArray[ j ];
				activityArray[ j ] = exchange;
			}
		}
		Activity exchange = activityArray[ index + 1 ];
		activityArray[ index + 1 ] = activityArray[ endPoint ];
		activityArray[ endPoint ] = exchange;
		return index + 1;
	} // End partition function

	// Prints the optimal schedule of activities.
	public static void printOptimal() {
		System.out.println( "Schedule" );
		int i = 0;
		System.out.printf( "%s from %s to %s%n", activities[ i ].getName(), activities[ i ].getStartTime(), activities[ i ].getEndTime() );
		for ( int j = 1; j < numberOfActivities; j++ ) {
			if ( activities[ j ].getStartTime() >= activities[ i ].getEndTime() ) {
				System.out.printf( "%s from %s to %s%n", activities[ j ].getName(), activities[ j ].getStartTime(), activities[ j ].getEndTime() );
				i = j;
			}
		} // End for
	} // End printOptimal method
} // End Project4 class


// Activity object class
class Activity {
	private String name;
	private int    startTime, endTime;

	public Activity( String name, int startTime, int endTime ) {
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
	} // End Constructor

	// Get/Set for name.
	public String getName() { return this.name; }
	public void setName( String name ) { this.name = name; }

	// Get/Set for startTime.
	public int getStartTime() { return this.startTime; }
	public void setStartTime( int startTime ) { this.startTime = startTime; }

	// Get/Set for endTime.
	public int getEndTime() { return this.endTime; }
	public void setEndTime( int endTime ) { this.endTime = endTime; }
} // End Activity class