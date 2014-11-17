
// Package was created as cs542 Project
package cs542Prj;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.lang.*;
import java.util.ArrayList;
import java.io.*;

@SuppressWarnings("unused")

// This class is for calculating shortest path with Link state routing (Dijkstra's Algorithm) to do 2 tasks
// 1) To find the Next_Node hop in shortest path
// 2) To find the shortest path between two specified hops

public class CS542Prj 
{

// Start of main method
public static void main(String args[]) 
{
	
	// Define Local Variables
	
    File fileName = null; // File name
    int i, j, k; // Counters
    int option = 0; // For holding menu option
    int routerNum; // Local variable to track router number
    
    // Display a menu to enter options
    System.out.println("Link State routing using Dijkstra's Algorithm\n");
    System.out.println("by Arun S,Shreyas and Karthik N\n");
    
    do 
    {
    	ArrayList<String> optionList = new ArrayList<String>();
    	optionList.add("1");
    	optionList.add("2");
    	optionList.add("3");
    	optionList.add("4");
    	optionList.add("5");
    	Object[] options = optionList.toArray();

        // Display Menu
    	option = JOptionPane.showOptionDialog(
                null,
                "Please select your Option: (1, 2, 3, 4 or 5)\n 1. Please Enter a file name to automatically load a file \n "
                + "2. Manually enter a distance matrix \n "
                + "3. Build routing table for a router \n"
                + "4. Find the shortest path between two routers \n"
                + "5. Exit \n",
                "CS542 Menu. Pick One option from below Menu!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                optionList.get(0));
    	System.out.println(option);

        //Will not execute any option unless file is loaded.
        if (Variables.fileflag == false && option+1 != 1 && option+1 != 2 && option+1 != 5) {
            System.out.println("File is not loaded. Press 1 to load a file: ");
            continue;
        }

        switch (option+1) {
            case 1:
                //Load a file. File name is specified by user from JFileChoser.
                try 
                {
                	Variables.fileflag = false;  //set the flag in case user chose a different file
                	Variables.Router_Count = 0;
                	
                	// Choose a file from FileSystem
                	JFileChooser fc=new JFileChooser();
                	fc.setDialogTitle("CS542 Project... Choose a matrix File");
                	fc.setApproveButtonText("Choose Textfile");
                	int returnVal=fc.showOpenDialog(new JFrame());
                	
                	if (returnVal == JFileChooser.APPROVE_OPTION) 
                	{
                	    fileName = fc.getSelectedFile();
                	}
                	
                    DataInputStream dis = new DataInputStream(new FileInputStream(fileName));
                    BufferedReader br = new BufferedReader(new InputStreamReader(dis));
                    String strLine;
                    Variables.fileflag = true;

                    // Input of the routing table
                    // 1. The number of rows is the number of routers
                    // 2. The -1 edge will be assigned with Random_Large_Number weight
                    
                    while ((strLine = br.readLine()) != null) 
                    {
                    	Variables.Router_Count++;
                    }
                    dis.close();
                    br.close();

                    Variables.Loaded_Matrix = new int[Variables.Router_Count][Variables.Router_Count];
                    Variables.Prev_Node = new int[Variables.Router_Count][Variables.Router_Count];
                    Variables.Next_Node = new int[Variables.Router_Count][Variables.Router_Count]; //two arrays which stores the Prev_Node and Next_Node_Node node of source
                    Variables.Weight_Of_Edges = new int[Variables.Router_Count][Variables.Router_Count];
                    Variables.Distance_BW_Nodes = new int[Variables.Router_Count][Variables.Router_Count];
                    
                    j = -1;

                    dis = new DataInputStream(new FileInputStream(fileName));
                    br = new BufferedReader(new InputStreamReader(dis));
                    
                    while ((strLine = br.readLine()) != null) 
                    {
                    	
                        j++;
                        String[] line = new String[Variables.Router_Count];
                        line = strLine.split(" ");
                        for (k = 0; k < Variables.Router_Count; k++) 
                        {
                        	Variables.Loaded_Matrix[j][k] = Integer.parseInt(line[k]);
                        }
                    }
                    
                    dis.close();
                    br.close();

                    // Print Routing table entered
                    System.out.println("Routing table loaded from file as below: ");
                    for (j = 0; j < Variables.Router_Count; j++) 
                    {
                        for (k = 0; k < Variables.Router_Count; k++) 
                        {
                            System.out.print(Variables.Loaded_Matrix[j][k] + " ");
                        }
                        System.out.println();
                    }

                    Routing_Methods.init_Distance_Matrix();

                    // Build routing table for all the nodes
                    for (j = 0; j < Variables.Router_Count; j++) 
                    {
                    	Dijkstra.perform_Dijkstra(j);
                    }
                    
                    // Finding next 
                    for (j = 0; j < Variables.Router_Count; j++) 
                    {
                    	Routing_Methods.find_print_Routing_Table(j);
                    }
                   
                } 
                catch (IOException | NumberFormatException| NullPointerException e) 
                {
                	// Avoid exceptions if any... If not then print
                    System.err.println(e.getMessage());
                    return;
                }

                break;
                
            case 2:
            	
                //Load a user input matrix from a text area.
                try 
                {
                	Variables.fileflag = false;  //set the flag in case user chose a different file
                	Variables.Router_Count = 0;
                	
                	JTextArea textArea = new JTextArea("Input your Matrix below:\n");
                	JScrollPane scrollPane = new JScrollPane(textArea);  
                	textArea.setLineWrap(true);  
                	textArea.setWrapStyleWord(true); 
                	scrollPane.setPreferredSize( new Dimension( 500, 500 ) );
                	JOptionPane.showMessageDialog(null, scrollPane, "dialog test with textarea",  
                	                                       JOptionPane.YES_NO_OPTION);
                	String[] lines = textArea.getText().split("\n");
                    Variables.fileflag = true;

                    // Input of the routing table
                    // 1. The number of rows is the number of routers
                    // 2. The -1 edge will be assigned with Random_Large_Number weight
                    
                    for (String line : textArea.getText().split("\\n")) 
                    {
                    	Variables.Router_Count++;
                    }
                    

                    Variables.Loaded_Matrix = new int[Variables.Router_Count][Variables.Router_Count];
                    Variables.Prev_Node = new int[Variables.Router_Count][Variables.Router_Count];
                    Variables.Next_Node = new int[Variables.Router_Count][Variables.Router_Count]; //two arrays which stores the Prev_Node and Next_Node_Node node of source
                    Variables.Weight_Of_Edges = new int[Variables.Router_Count][Variables.Router_Count];
                    Variables.Distance_BW_Nodes = new int[Variables.Router_Count][Variables.Router_Count];
                    
                    j = -1;

                    
                    for (String line : textArea.getText().split("\\n")) 
                    {
                    	
                        j++;
                        String[] lines1 = new String[Variables.Router_Count];
                        lines1 = line.split(" ");
                        for (k = 0; k < Variables.Router_Count; k++) 
                        {
                        	Variables.Loaded_Matrix[j][k] = Integer.parseInt(lines1[k]);
                        }
                    }
                    

                    // Print 
                    System.out.println("Routing table loaded from file as below: ");
                    for (j = 0; j < Variables.Router_Count; j++) 
                    {
                        for (k = 0; k < Variables.Router_Count; k++) 
                        {
                            System.out.print(Variables.Loaded_Matrix[j][k] + " ");
                        }
                        System.out.println();
                    }

                    Routing_Methods.init_Distance_Matrix();

                    // Build routing table for all the nodes
                    for (j = 0; j < Variables.Router_Count; j++) 
                    {
                    	Dijkstra.perform_Dijkstra(j);
                    }
                    
                    // Finding next 
                    for (j = 0; j < Variables.Router_Count; j++) 
                    {
                    	Routing_Methods.find_print_Routing_Table(j);
                    }
                   
                } 
                catch ( NumberFormatException| NullPointerException e) 
                {
                	// Avoid exceptions if any... If not then print
                    System.err.println(e.getMessage());
                    return;
                }

                break;


            case 3:
                // Read the Router number from JOptionPane   
            	try
            	{
            	String router_No = JOptionPane.showInputDialog ( "Enter router Number: " ); 
            	
                routerNum = Routing_Methods.Check_for_Proper_RN(router_No); //check if the number is valid
                	if (routerNum == 999)
                	{
                		System.out.println("The routing table for routerCant be build\n");
                		break;
                	}
                	else
                	{
                		System.out.println("The routing table for router " + (routerNum) + " is:");

                		// Print the router table 
                		System.out.println("*****************");
                		System.out.println("Router No  \tNext Hop in Route");
             
                		// Print it hop by hop 
                		Routing_Methods.print_Routing_Table(routerNum);
                	}
            	}
            	catch (NumberFormatException e) 
            	{
                    System.out.println("Entered is a wrong input!!");
                }
                
                break;

            case 4:
                //Compute and print minimum path between 2 mentioned routers
                int source = 0,
                 dest = 0;
                try 
                {
                	String Src_Dest = JOptionPane.showInputDialog ( "Enter Source and Destination seperated by a Comma(,)" );
                    String[] temp = Src_Dest.split(",");

                    source = Integer.parseInt(temp[0]) - 1;
                    dest = Integer.parseInt(temp[1]) - 1;

                    Routing_Methods.findPath(source, dest);
                } 
                
                // Catch if not a number
                catch (NumberFormatException e) 
                {
                    System.out.println("Entered is a wrong input!!");
                }
                
                // Catch if number is in or out of array
                catch (ArrayIndexOutOfBoundsException e) 
                {
                    System.out.println("Need to enter Source and Destination seperated by a Comma!");
                }
                
                break;
            case 5:
                System.exit(0);
                break;
            default:
          	  System.out.println("Wrong choice enter again..!!");
                break;
        }
    } 
    while (option != 5);
}
}