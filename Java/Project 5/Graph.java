/**
 * CS 360 Project 5 Due 04/02/15
 * <p/>
 * Graph.java
 * Kyle Galloway
 * <p/>
 * Fulfills all requirements for Project 5 in CS 360.
 * Compiled with Java 7.
 */

import java.util.ArrayList;
import java.util.Arrays;

class Vertex {
	private String            value;
	private ArrayList<Vertex> adjacencyList;
	private boolean           visited;

	// Constructor (handles Q case inside)
	Vertex( String value ) {
		if ( value.compareToIgnoreCase( "Q" ) == 0 ) {
			this.value = "QU";
		}
		else {
			this.value = value;
		}
		this.adjacencyList = new ArrayList<Vertex>();
		this.visited = false;
	}

	public String getValue() { return value; }

	public void setValue( String value ) { this.value = value; }

	// Adds a vertex pointer to the adjacency list
	public void addEdge( Vertex vertex ) {
		if ( !( adjacencyList.contains( vertex ) ) ) {
			this.adjacencyList.add( vertex );
		}
	}

	public ArrayList<Vertex> getAdjacencyList() { return adjacencyList; }

	public boolean isVisited() { return visited; }

	public void visit() { this.visited = true; }

	public void unVisit() { this.visited = false; }
} // End class Vertex

class Edge {
	private Vertex v1;
	private Vertex v2;

	public Edge( Vertex v1, Vertex v2 ) {
		this.v1 = v1;
		this.v2 = v2;
	}

	public Vertex getV1() { return v1; }

	public Vertex getV2() { return v2; }
} // End class Edge

class Graph {
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();

	// Constructer
	public Graph( String[][] inputBoard ) {

		// converts each place in 2D array to a vertex
		for ( int i = 0; i < inputBoard.length; i++ ) {
			for ( int j = 0; j < inputBoard[ 0 ].length; j++ ) {
				addVertex( inputBoard[ i ][ j ] );
			}
		}
		// Adds all edges for the board
		this.connect( vertices, inputBoard.length, inputBoard[ 0 ].length );
	}// end constructor

	// Adds a new vertex to the graph
	public void addVertex( String value ) { vertices.add( new Vertex( value ) ); }

	// Returns an ArrayList of vertices
	public ArrayList<Vertex> getVertices() { return vertices; }

	// Adds an edges for the given vertices
	public void addEdge( int position1, int position2 ) {
		Vertex v1 = vertices.get( position1 );
		Vertex v2 = vertices.get( position2 );
		v1.addEdge( v2 );
		v2.addEdge( v1 );
	}// End method addEdge

	// Connects the graph (Adds all edges)
	public void connect( ArrayList<Vertex> vertices, int rows, int columns ) {
		for ( int i = 0; i < vertices.size(); i++ ) {
			Vertex vertex = vertices.get( i );
			// Top Left
			if ( i == 0 ) {
				vertex.addEdge( vertices.get( 1 ) );
				vertex.addEdge( vertices.get( columns ) );
				vertex.addEdge( vertices.get( columns + 1 ) );
			}
			// Bottom Right
			else if ( i == vertices.size() - 1 ) {
				vertex.addEdge( vertices.get( i - columns ) );
				vertex.addEdge( vertices.get( i - columns - 1 ) );
				vertex.addEdge( vertices.get( i - 1 ) );
			}
			// Bottom Left
			else if ( i == vertices.size() - columns ) {
				vertex.addEdge( vertices.get( i - columns ) );
				vertex.addEdge( vertices.get( i - columns + 1 ) );
				vertex.addEdge( vertices.get( i + 1 ) );
			}
			// Top Right
			else if ( i == columns - 1 ) {
				vertex.addEdge( vertices.get( i + columns ) );
				vertex.addEdge( vertices.get( i + columns - 1 ) );
				vertex.addEdge( vertices.get( i - 1 ) );
			}
			// Top non-corner
			else if ( i - rows < 0 ) {
				vertex.addEdge( vertices.get( i - 1 ) );
				vertex.addEdge( vertices.get( i + 1 ) );
				vertex.addEdge( vertices.get( i + columns - 1 ) );
				vertex.addEdge( vertices.get( i + columns ) );
				vertex.addEdge( vertices.get( i + columns + 1 ) );
			}
			// Bottom non-corner
			else if ( i + rows >= vertices.size() ) {
				vertex.addEdge( vertices.get( i - 1 ) );
				vertex.addEdge( vertices.get( i + 1 ) );
				vertex.addEdge( vertices.get( i - columns - 1 ) );
				vertex.addEdge( vertices.get( i - columns ) );
				vertex.addEdge( vertices.get( i - columns + 1 ) );
			}
			// Right non-corner
			else if ( i % columns == columns - 1 ) {
				vertex.addEdge( vertices.get( i - 1 ) );
				vertex.addEdge( vertices.get( i - columns - 1 ) );
				vertex.addEdge( vertices.get( i - columns ) );
				vertex.addEdge( vertices.get( i + columns ) );
				vertex.addEdge( vertices.get( i + columns - 1 ) );
			}
			// Left non-corner
			else if ( i % columns == 0 ) {
				vertex.addEdge( vertices.get( i + 1 ) );
				vertex.addEdge( vertices.get( i - columns + 1 ) );
				vertex.addEdge( vertices.get( i - columns ) );
				vertex.addEdge( vertices.get( i + columns ) );
				vertex.addEdge( vertices.get( i + columns + 1 ) );
			}
			// inner
			else {
				vertex.addEdge( vertices.get( i - 1 ) );
				vertex.addEdge( vertices.get( i + 1 ) );
				vertex.addEdge( vertices.get( i - columns - 1 ) );
				vertex.addEdge( vertices.get( i - columns ) );
				vertex.addEdge( vertices.get( i - columns + 1 ) );
				vertex.addEdge( vertices.get( i + columns + 1 ) );
				vertex.addEdge( vertices.get( i + columns ) );
				vertex.addEdge( vertices.get( i + columns - 1 ) );
			}
		}
	}// End method connect

	// Initiates an empty depth-first search
	public void DFS( ArrayList<String> dictionary, Vertex vertex ) {
		this.DFS( dictionary, vertex, "" );
	}

	// Initiates a depth-first search with the given string
	public void DFS( ArrayList<String> dictionary, Vertex vertex, String word ) {
		word += vertex.getValue();
		vertex.visit(); // Prevents using a vertex twice
		if ( wordCheck( dictionary, word ) ) {
			for ( Vertex adjVertex : vertex.getAdjacencyList() ) {
				if ( !( adjVertex.isVisited() ) ) {DFS( dictionary, adjVertex, word ); }
			}
		}
		vertex.unVisit(); // Enables use of the vertex in other DFS
	}// End method DFS

	// Prints a word if it is a word
	// Stops the search (false) if the word isn't a prefix
	// Returns a boolean to continue(true) or not(false)
	public boolean wordCheck( ArrayList<String> dictionary, String word ) {
		int existCheck = exists( dictionary, word );
		if ( existCheck == 1 ) {
			System.out.println( word );
			return true;
		}
		else if ( existCheck == 0 ) { return true; }
		return false;
	}// End function wordCheck

	// Does a binary search for the string (includes a prefix check)
	// Returns (1: word, 0: prefix, -1: neither)
	public int exists( ArrayList<String> dictionary, String word ) {

		// Conversion to static array required for binary search
		String[] wordArray = new String[ dictionary.size() ];
		dictionary.toArray( wordArray );
		int index = Arrays.binarySearch( wordArray, word );

		// returns 1 if matching word is found
		if ( index >= 0 ) { return 1; }
		else {
			int checkIndex = -( index + 1 );
			if ( checkIndex <= dictionary.size() - 1 ) {
				String precedingWord = dictionary.get( checkIndex );

				// returns 0 if string is a prefix; -1 otherwise
				return precedingWord.toUpperCase().startsWith( word.toUpperCase() ) ? 0 : -1;
			}
			return -1;
		}
	}// End function exists
}// End Graph class
