package cs542Prj;

public class Variables {
	
	// Global variables
	static int Random_Large_Number = 32784; // To compare with any other edge weight
	static int Router_Count = 0; // To hold router count
	
	// For initial load 
	static int[][] Loaded_Matrix;  // To hold the the matrix loaded either manually or from file 
		
	// For building routing table
	static int[][] Prev_Node;  // To hold previous nodes in a path        
	static int[][] Next_Node;  // To hold the next nodes in the path
	
	// For Applying Dijkstra's Algorithm
	static int[][] Weight_Of_Edges; // To hold all the edge weights
	static int[][] Distance_BW_Nodes;  // To calculate distance between both nodes
	
	static boolean fileflag = false;
}
