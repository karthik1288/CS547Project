package cs542Prj;


import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JOptionPane;

@SuppressWarnings("unused")
public class Routing_Methods 
{	
	//Function which gets the Next_Node hop from the Source_Node router to Destination_Nodeination
	static void find_print_Routing_Table(int Source_Node) 
	{
	    int i;
	    for (i = 0; i < Variables.Router_Count; i++) 
	    {
	        int temp = Variables.Prev_Node[Source_Node][i];
	        if (temp == -1) 
	        {
	        	Variables.Next_Node[Source_Node][i] = -1;
	        } 
	        else 
	        {
	            while (temp != Source_Node) 
	            {
	            	Variables.Next_Node[Source_Node][i] = temp;
	                temp = Variables.Prev_Node[Source_Node][temp];
	            }
	        }
	    }
	}

	//print the routing table of #Source_Node router, indicating the Next_Node hop
	static void print_Routing_Table(int Source_Node) 
	{
		String sstr= "The routing table for router " + (Source_Node+1) + " is: \n ************************** \n Router No    \tNext Hop in Route \n";
		String str1="",str2="",str3="";
	    for (int i = 0; i < Variables.Router_Count; i++) 
	    {
	        str1 = Integer.toString(i + 1) + "                           ";
	        int thisDis = Variables.Distance_BW_Nodes[Source_Node][i];
	        int thisWeight = Variables.Weight_Of_Edges[Source_Node][i];
	        if (thisDis == thisWeight) 
	        {
	            str2 = "              " + "-"+"\n";
	        } 
	        else 
	        {
	            str2 = "              " + Integer.toString(Variables.Next_Node[Source_Node][i] + 1) + "\n";
	        }
	        str3 = str3+str1+str2;
	    }
	    //System.out.println();
	    JOptionPane.showMessageDialog(null, sstr+str3);	
	    }

	//find the path of a Source_Node router to Destination_Nodeination router
	static void findPath(int Source_Node, int Destination_Node) 
	{
	    int[] queue = new int[32784];
	    //Find the path BW SOurce and Destination
	    int i = 0;
	    queue[i++] = Destination_Node;
	    int temp = Variables.Prev_Node[Source_Node][Destination_Node];
	    while (temp != Source_Node) 
	    {
	        queue[i++] = temp;
	        temp = Variables.Prev_Node[Source_Node][temp];
	    }
	    //print the path
	    int j = 0;
	    String strr1= "";
	    for (j = i - 1; j > 0; j--) 
	    {
	        strr1= strr1+(queue[j] + 1) + "-";
	    }
	    JOptionPane.showMessageDialog(null, "The shortest path from " + (Source_Node + 1) + "to " + (Destination_Node + 1) + " is: " +(Source_Node + 1) + "-" + strr1 + (Destination_Node + 1) + ", the total cost is " + Variables.Distance_BW_Nodes[Source_Node][Destination_Node] + ".\n");
	}

	// Check for proper router number whether it exceeds the number of routers calculated based on matrix
	static int Check_for_Proper_RN(String line) 
	{
	    int rn = 0;
	    try 
	    {
	        	// Check for more than router count condition
	            if (Integer.parseInt(line) > Variables.Router_Count || Integer.parseInt(line) <= 0) 
	            {
	            	JOptionPane.showMessageDialog(null, "Invalid Router Number \n Enter a valid router number(1 to " + Variables.Router_Count + " ) ");
	                rn = 999;
	            } 
	            else 
	            {
	            rn = Integer.parseInt(line);
	            }
	    } 
	    catch (NumberFormatException|ArrayIndexOutOfBoundsException e) 
	    {
	    	JOptionPane.showMessageDialog(null, "Invalid Router Number \n Entered input is not a number!");
	    }
	    
	    return rn;
	}
	
	//Initialize the Weight_Of_Edges and Distance_BW_Nodes according to the Loaded_Matrix
	static void init_Distance_Matrix() 
	{
	    int j, k;
	    // Loop for entire matrix reassigning the node weight as large number for all doesn't have 
	    for (j = 0; j < Variables.Router_Count; j++) 
	    {
	        for (k = 0; k < Variables.Router_Count; k++) 
	        {
	            if (Variables.Loaded_Matrix[j][k] == -1) 
	            { 
	            	Variables.Weight_Of_Edges[j][k] = Variables.Random_Large_Number;
	            } 
	            else 
	            {
	            	Variables.Weight_Of_Edges[j][k] = Variables.Loaded_Matrix[j][k];
	            }
	        }
	    }
	    
	    // Based on network topology re-making based on the weights to distance  
	    for (j = 0; j < Variables.Router_Count; j++) 
	    {
	        for (k = 0; k < Variables.Router_Count; k++) 
	        {
	        	Variables.Distance_BW_Nodes[j][k] = Variables.Weight_Of_Edges[j][k];  	 
	        }
	    }

	}
}
