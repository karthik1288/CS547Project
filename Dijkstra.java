package cs542Prj;

public class Dijkstra {

	public static void perform_Dijkstra(int source) 
	{
	    //The number of source node is passed to this method
	    boolean[] visited = new boolean[Variables.Random_Large_Number];      //visited flag

	    //initialization of visited[] and Prev_Node[][]
	    for (int i = 0; i < Variables.Router_Count; i++) 
	    {
	        //Distance_BW_Nodes[source][i] = Weight_Of_Edges[source][i];
	        visited[i] = false;
	        if (Variables.Distance_BW_Nodes[source][i] > 1000) 
	        {
	        	Variables.Prev_Node[source][i] = -1;
	        } 
	        else 
	        {
	        	Variables.Prev_Node[source][i] = source;
	        }
	    }
	    
	     //the source node is the first one we visit, and the path length is 0
	    Variables.Distance_BW_Nodes[source][source] = 0;
	    visited[source] = true;

	    //we need n-1 iterations of the algorithm
	    for (int count = 1; count <= Variables.Router_Count - 1; count++) 
	    {
	        //find a node k which is least cost to source
	        int k = -1;
	        int dmin = Variables.Random_Large_Number;
	        for (int i = 0; i < Variables.Router_Count; i++) 
	        {
	            if (!visited[i] && Variables.Distance_BW_Nodes[source][i] < dmin) 
	            {
	                k = i;
	                dmin = Variables.Distance_BW_Nodes[source][i];
	            }
	        }

	        //the shortest path of node k is calculated, and dmin is the path length
	        Variables.Distance_BW_Nodes[source][k] = dmin;
	        visited[k] = true;

	        //adjust all other Distance_BW_Nodess with k as the intermediate node
	        for (int i = 0; i < Variables.Router_Count; i++) 
	        {
	            if (!visited[i] && Variables.Distance_BW_Nodes[k][i] < Variables.Random_Large_Number) 
	            {
	                dmin = Variables.Distance_BW_Nodes[source][k] + Variables.Weight_Of_Edges[k][i];
	            }
	            if (dmin < Variables.Distance_BW_Nodes[source][i] && Variables.Distance_BW_Nodes[k][i] < Variables.Random_Large_Number) 
	            {
	            	Variables.Distance_BW_Nodes[source][i] = dmin;
	            	Variables.Prev_Node[source][i] = k;
	            }
	        }

	    }
	}

}
