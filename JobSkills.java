import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/**
 * program name: JobSkills.java
 * author Xin Li
 * email: xinli2@email.arizona.edu
 */

public class JobSkills {
	/**
	 * This program takes two or three arguments from the command line.
	 * The first command-line argument an input file name.
	 * The second command-line argument will be a command to run on the data
	 * from the input file, the commands consist of CATCOUNT and LOCATIONS.
	 * If an unknown command is provided, the program will print "Invalid Command".
	 * 
	 * CATCOUNT - This command reads an input file and for each job "Category"
	 * 			  prints out the category and the number of jobs in that category.
	 * LOCATIONS - For a given category list the locations that have a position 
	 *             in that category open. Each location will be followed
	 *             by a number indicating how many positions in that category
	 *             are open in a particular location.
	 */
	
	public static void main(String[] args) throws IOException {
		String filename = args[0];
		String type = args[1];
		
		if (type.equals("CATCOUNT")) {
			System.out.println("Number of positions for each category");
			System.out.println("-------------------------------------");
			HashMap<String,Integer> map = catCoutFromCsv(filename);
			sortPrintResult(map);
		} else if (type.equals("LOCATIONS")) {
			String option = args[2];
			System.out.printf("LOCATIONS for category: %s\n", option);
			System.out.println("-------------------------------------");
			HashMap<String,Integer> map = locationsFromCsv(filename, option);
			sortPrintResult(map);
		} else {
			System.out.println("Invalid Command");
		}
	}
	
	/**
	 * This method will sort the keys in place and print the result.
	 */
	public static void sortPrintResult(HashMap<String,Integer> map) {
		ArrayList<String> sortedKeys = new ArrayList<String>(map.keySet());
		Collections.sort(sortedKeys);
		for (String key: sortedKeys) {
			System.out.printf("%s, %d\n", key, map.get(key));
		}
	}
	
	public static HashMap<String,Integer> catCoutFromCsv(String filePath) throws IOException {
		HashMap<String,Integer> map = new HashMap<String, Integer>();
        InputStream inputStream =new FileInputStream(filePath);
        Scanner scan = new Scanner(inputStream,"UTF-8");
        
        while(scan.hasNext()) {
        	String line=scan.nextLine();
        	String category = line.split(",")[2];
        	if (!category.equals("Category")) {
	        	if (map.containsKey(category)) {
	        		map.put(category, map.get(category)+1);
	        	} else {
	        		map.put(category, 1);
	        	}
        	}
        }
        
    	scan.close();
    	inputStream.close();
		return map;
    }
	
	public static HashMap<String,Integer> locationsFromCsv(String filePath, String option) throws IOException {
		HashMap<String,Integer> map = new HashMap<String, Integer>();
        InputStream inputStream =new FileInputStream(filePath);
        Scanner scan = new Scanner(inputStream,"UTF-8");
        
        while(scan.hasNext()) {
        	String line=scan.nextLine();
        	String category = line.split(",")[2];
        	String location = line.split(",")[3];
        	if (!category.equals("Category") && category.equals(option)) {
	        	if (map.containsKey(location)) {
	        		map.put(location, map.get(location)+1);
	        	} else {
	        		map.put(location, 1);
	        	}
        	}
        }
        
    	scan.close();
    	inputStream.close();
		return map;
    }
}
