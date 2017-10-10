import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public class Project03 {
	//This is the main function
	public static void main(String args[]) {
		try
		{
			if(args.length==0)
				throw new RuntimeException("Usage : <Filename>");
			String path = args[0];
			HashMap<String,HashMap<String,String>> individuals = new HashMap<String,HashMap<String,String>>();
			HashMap<String,HashMap<String,String>> families = new HashMap<String,HashMap<String,String>>();
			readGed(path, individuals, families);
			System.out.println("Individuals");
			printIndividuals(individuals);
			System.out.println("");
			System.out.println("Families");
			printFamilies(families,individuals);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	//Read GEMCOM file and save information into memory
	private static void readGed(String filePath, HashMap<String,HashMap<String,String>> individuals, HashMap<String,HashMap<String,String>> families) throws java.io.IOException
	{
		File file = new File(filePath);
		if (!(file.isFile() && file.exists()))
			throw new java.io.FileNotFoundException(filePath+" not exists");
		HashSet<String> set0 = new HashSet<String>(){
			{
				add("INDI");
				add("FAM");
			}
		};
		HashSet<String> set1 = new HashSet<String>(){
			{
				add("NAME");
				add("SEX");
				add("FAMC");
				add("FAMS");
				add("HUSB");
				add("WIFE");
				add("CHIL");
			}
		};
		HashSet<String> set1_spe = new HashSet<String>(){
			{
				add("BIRT");
				add("DEAT");
				add("MARR");
				add("DIV");
			}
		};
		HashSet<String> set2 = new HashSet<String>(){
			{
				add("DATE");
			}
		};
		// mapType indicates which map is used to store the following information
		HashMap<String,HashMap<String,String>> mapType = new HashMap<String,HashMap<String,String>>();
		// mapEle is one element of the top level map.
		HashMap<String,String> mapEle = new HashMap<String,String>();
		//Those two variables are used to save the ID and key that are needed for hashmap
		String ID = new String();
		String key = new String();
		try(FileInputStream fis = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(reader))
		{
			
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] array = line.split(" ");
				String value = new String();
				for (int i = 2; i < array.length; i++) {
					value += array[i] + " ";
				}
				value = value.trim();
				switch (array[0]) {
				case "0" : 
					if (array.length > 2 && set0.contains(array[2])){
						if (array[2].equals("INDI")) {
							mapType = individuals;
						} else {
							mapType = families;
						}
						ID = array[1];
						if (mapType.containsKey(ID)) {
							mapEle = mapType.get(ID);
						} else {
							mapEle = new HashMap();
							mapType.put(ID, mapEle);
						}
					}
					break;
				case "1" :
					if (set1.contains(array[1])) {
						for (String tags:set1) {
							if (array[1].equals(tags)) {
								key = array[1];
								break;
							}
						}
						mapEle.put(key, value);
					} else if (set1_spe.contains(array[1])){
						for (String tags:set1_spe) {
							if (array[1].equals(tags)) {
								key = array[1];
								break;
							}
						}
					}
					break;
				case "2" :
					if (set2.contains(array[1])) {
						mapEle.put(key, value);
					}
					break;
				default: 
					break;
				}
			}
		}
	}
	
	// This function is used to print individuals information
	private static void printIndividuals(HashMap<String,Map<String,String>> individuals){
		TreeSet<Integer> sortSet = new TreeSet<Integer>();
		ArrayList<String> keys = new ArrayList<String>();
		for (String key : individuals.keySet()) {
			key = key.replaceAll("@", "");
			key = key.replaceAll("I", "");
			sortSet.add(Integer.parseInt(key));
		}
		
		for (int num : sortSet) {
			keys.add("@I" + num + "@");
		}
		System.out.println("ID 	Name		");
		//for(Map.Entry<String, Map<String,String>> entry:individuals.entrySet()) {
		for (String key : keys) {
			System.out.println(key.replaceAll("@", "") + "	" + individuals.get(key).get("NAME"));
		}
	}
	
	//This funtion is used to print families information
	private static void printFamilies(Map<String,Map<String,String>> families,Map<String,Map<String,String>> individuals){
		TreeSet<Integer> sortSet = new TreeSet<Integer>();
		ArrayList<String> keys = new ArrayList<String>();
		for (String key : families.keySet()) {
			key = key.replaceAll("@", "");
			key = key.replaceAll("F", "");
			sortSet.add(Integer.parseInt(key));
		}
		
		for (int num : sortSet) {
			keys.add("@F" + num + "@");
		}
		System.out.println("ID	Husban Name	Wife Name");
		//for(Map.Entry<String, Map<String,String>> entry:individuals.entrySet()) {
		for (String key : keys) 
			System.out.println(key.replaceAll("@", "") + "	"
								+ individuals.get(families.get(key).get("HUSB")).get("NAME") + "	"
								+ individuals.get(families.get(key).get("WIFE")).get("NAME"));
		}
	}
	
}

