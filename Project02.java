import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Project02 {
	public static void main(String argv[]) {
		String path = "D:\\Project01.ged";
		readGemFile(path);
	}
	
	private static void readGemFile(String filePath) {
		try{
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {
				Set<String> set0 = new HashSet<String>(){
					{
						add("HEAD");
						add("TRLR");
						add("NOTE");
					}
				};
				Set<String> set0_spe = new HashSet<String>(){
					{
						add("INDI");
						add("FAM");
					}
				};
				Set<String> set1 = new HashSet<String>(){
					{
						add("NAME");
						add("SEX");
						add("BIRT");
						add("DEAT");
						add("FAMC");
						add("FAMS");
						add("MARR");
						add("HUSB");
						add("WIFE");
						add("CHIL");
						add("DIV");
						
					}
				};
				Set<String> set2 = new HashSet<String>(){
					{
						add("DATE");
					}
				};
				
				InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
				BufferedReader br = new BufferedReader(reader);
				String line = null;
				while ((line = br.readLine()) != null) {
					System.out.println("--> " + line);
					String[] array = line.split(" ");
					String str = new String();
					for (int i = 2; i < array.length; i++) {
						str += array[i] + " ";
					}
					str = str.trim();
					switch (array[0]) {
					case "0" : 
						if (set0.contains(array[1])) {
							System.out.println("<-- " + array[0] + "|" + array[1] + "|Y|" + str);
						} else if (set0_spe.contains(array[2])) {
							System.out.println("<-- " + array[0] + "|" + array[2] + "|Y|" + array[1]);
						} else {
							System.out.println("<-- " + array[0] + "|" + array[1] + "|N|" + str);
						}
						break;
					case "1" :
						if (set1.contains(array[1])) {
							System.out.println("<-- " + array[0] + "|" + array[1] + "|Y|" + str);
						} else {
							System.out.println("<-- " + array[0] + "|" + array[1] + "|N|" + str);
						}
						break;
					case "2" :
						if (set2.contains(array[1])) {
							System.out.println("<-- " + array[0] + "|" + array[1] + "|Y|" + str);
						} else {
							System.out.println("<-- " + array[0] + "|" + array[1] + "|N|" + str);
						}
					}
				}
				reader.close();
			}	
		} catch (Exception e) {
			System.out.println("error");
			e.printStackTrace();
		}
	}
}
