import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GEDCOMParser {
	public static void parse(String file, List<Individual> indis, List<Family> families) {
		String[] k = new String[17];
		k[0] = "INDI";
		k[1] = "NAME";
		k[2] = "SEX";
		k[3] = "BIRT";
		k[4] = "DEAT";
		k[5] = "FAMC";
		k[6] = "FAMS";
		k[7] = "FAM";
		k[8] = "MARR";
		k[9] = "HUSB";
		k[10] = "WIFE";
		k[11] = "CHIL";
		k[12] = "DIV";
		k[13] = "DATE";
		k[14] = "HEAD";
		k[15] = "TRLR";
		k[16] = "NOTE";

		String[] indiPs = { "NAME", "SEX", "BIRT", "DEAT", "FAMC", "FAMS" };
		String[] familyPs = { "MARR", "HUSB", "WIFE", "CHIL", "DIV" };

		File f = new File(file);

		Individual indi = null;
		Family family = null;
		try (FileInputStream iStream = new FileInputStream(f);
				InputStreamReader rStream = new InputStreamReader(iStream);
				BufferedReader bReader = new BufferedReader(rStream);) {
			String content = null;
			while ((content = bReader.readLine()) != null) {
				String content_1 = content.trim();
				if (content_1.length() > 0) {

					String[] strs = content_1.split(" ");
					if (strs.length >= 2) {
						if (strs[strs.length - 1].equals("INDI")) {
							indi = new Individual();
							indi.id = strs[1];
							indis.add(indi);
						} else if (strs[strs.length - 1].equals("FAM")) {
							family = new Family();
							family.id = strs[1];
							families.add(family);
						} else {
							switch (strs[1]) {
							case "NAME":
								if(indi!=null){
									indi.name = getValue(content,"NAME");
								}
								break;
							case "SEX":
								if(indi!=null){
									indi.sex = getValue(content,"SEX");
								}
								break;
							case "BIRT":
								if(indi!=null){
									indi.birthday = getDate(content,"BIRT",bReader);
								}
								break;
							case "DEAT":
								if(indi!=null){
									indi.death = getDate(content,"DEAT",bReader);
								}
								break;
							case "FAMC":
								if(indi!=null){
									indi.fChild = getValue(content,"FAMC");
								}
								break;
							case "FAMS":
								if(indi!=null){
									indi.fSpouse = getValue(content,"FAMS");
								}
								break;
							case "MARR":
								if(family!=null){
									family.marriage_date = getDate(content,"MARR",bReader);
								}
								break;
							case "HUSB":
								if(indi!=null){
									family.husband_id = getValue(content,"HUSB");
								}
								break;
							case "WIFE":
								if(indi!=null){
									family.wife_id = getValue(content,"WIFE");
								}
								break;
							case "CHIL":
								if(indi!=null){
									if(family.child_ids==null)
										family.child_ids = new ArrayList<>();
									String c_id = getValue(content,"CHIL");
									family.child_ids.add(c_id);
								}
								break;
							case "DIV":
								if(family!=null){
									family.divorce_date = getDate(content,"DIV",bReader);
								}
								break;
							default:
								break;
							}
							
						}
					}

				}
			}
			bReader.close();
		} catch (FileNotFoundException e) {

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static String getValue(String content,String key){
		if((content.indexOf(key) + key.length() + 1)<=content.length())
			return content.substring(content.indexOf(key) + key.length() + 1,
					content.length());
		else
			return null;
	}
	public static String getDate(String content,String key,BufferedReader bReader) throws IOException{
		String date = getValue(content,key);
		if(date==null||date.trim().length()==0){
			String cc = bReader.readLine();
			if(cc != null){
				date = getValue(cc,"DATE");
			}
		}
		return date;
	}
}
