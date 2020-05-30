import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.*;
import java.util.*;


public class Parser{
	/* this main methos takes the result of the fork() program and parse it into a tree starting from the parent
	 node and then the children of it and then the cheldren ofits cheldren and so on.*/

	public static void main(String[] args) throws FileNotFoundException{
		BufferedReader reader;

		//an arraylist to hold parents nodes
		ArrayList<BinaryNode> parents = new ArrayList<>(); 

		try {
			System.out.println("enter the file you want to parse");
			int i =0;
			String s = null;
			while(i < args.length){
				s = args[i];
				System.out.println(s);
				i++;
			}
			reader = new BufferedReader(new FileReader("./" + s));
			String line = reader.readLine();

			/*this while loop bascilly does the whole work:
			 * 1- reads new line each iteration
			 * 2- create a parent and a child node after it gets their values
			 * 3- checks if the arraylist is empty and do the following:
			 		* if its empty, add the new node to the list.
			 		* if it not empty, check if the parent is equal to any parent pid# in the list, if yes
			 				it sets the child node to that node, otherwise it add a new parent node to the list
			 				*/
			while (line != null) {

				//System.out.println(line.substring((line.lastIndexOf("child=") + 6), line.lastIndexOf("child=")+10));
				//System.out.println(line.substring((line.lastIndexOf("parent=") + 7), line.lastIndexOf("parent=")+11));
				int child = Integer.parseInt(line.substring((line.lastIndexOf("child=") + 6), (line.lastIndexOf("child=")+10)));
				int parent = Integer.parseInt(line.substring((line.lastIndexOf("parent=") + 7), line.lastIndexOf("parent=")+11));
				//System.out.println(child);
				//System.out.println(parent);
				BinaryNode p = new BinaryNode(parent);
				BinaryNode ch = new BinaryNode(child);
				int index =alIndex(parents, parent);
				//p.setChild(ch);
				if (parents.isEmpty()){
					
					p.setChild(ch);
					parents.add(p);

				}else if (index != -1){
					parents.get(index).setChild(ch);
					
				}else{
					p.setChild(ch);
					parents.add(p);
				}
				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// for loop to display parents nodes .
		for(int i =0; i < parents.size(); i++){
			System.out.println(parents.get(i).get());
			
		}
		System.out.println();// a seperation
		graph(parents);
		

	}//end of main method.

	/* a method that checks if a node has the same pid# as the new parent value of the new read line
	 * if yes, it returns the index value of that node, otherwise it returns -1*/
	public static int alIndex(ArrayList<BinaryNode> bn, int parent){
		int inx = -1;
		for (int i =0; i <bn.size(); i++ ){
			if (bn.get(i).get() == parent){
				 inx =i;
			
			}		
		}
		return inx;
	}

	public static void graph(ArrayList<BinaryNode> bn){
		GraphViz gv  = new GraphViz();
		gv.addln(gv.start_graph());
		for (int i = 0; i < bn.size(); i++){
			for (int j = 0; j < bn.get(i).getNumOfChildren(); j++){
				 gv.addln(bn.get(i).get() + "->" + bn.get(i).getNthChild(j).get());
	
			}

		}
        gv.addln(gv.end_graph());
        System.out.println(gv.getDotSource());
        try{
         PrintStream o = new PrintStream(new File("output.dot")); 
         PrintStream console = System.out; 
         System.setOut(o); 
         System.out.println(gv.getDotSource()); 
  
      
    	} catch(IOException e){
    		e.printStackTrace();

    	}

       // String type = "png";
       // File out = new File("out." + "dot");   // out.png in this example
 	    //gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
	}


}// end of parser class
