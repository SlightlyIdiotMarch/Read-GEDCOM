
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class GEDCOMParser {
	public static void parse(String file, List<Individual> indis, List<Family> families) throws IOException {
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

		File f = new File(file);
		try (
			FileInputStream iStream = new FileInputStream(f);
				InputStreamReader rStream = new InputStreamReader(iStream);
				BufferedReader bReader = new BufferedReader(rStream);) {
			Individual indi = null;
			Family family = null;

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
									if(indi.fSpouse==null)
										indi.fSpouse = new ArrayList<>();
									String spouse = getValue(content,"FAMS");
									indi.fSpouse.add(spouse);

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

	//convert string to date
		public static Date processDate(String date) throws ParseException {
			String result = "";
			for (String format: date.split(" ")) {
				if (format.length() == 1) {
					format = "0" + format;
				}
				result += format + "-";
			}
			result = result.substring(0, result.length() - 1);
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
			Date resultdate = dateFormat.parse(result);
			return resultdate;
		}
		
		
		// compute age
		public static int getAge(Date dateOfBirth) {
		    Calendar today = Calendar.getInstance();
		    Calendar birthDate = Calendar.getInstance();
		    birthDate.setTime(dateOfBirth);
		    if (birthDate.after(today)) {
		        throw new IllegalArgumentException("You don't exist yet");
		    }
		    int todayYear = today.get(Calendar.YEAR);
		    int birthDateYear = birthDate.get(Calendar.YEAR);
		    int todayDayOfYear = today.get(Calendar.DAY_OF_YEAR);
		    int birthDateDayOfYear = birthDate.get(Calendar.DAY_OF_YEAR);
		    int todayMonth = today.get(Calendar.MONTH);
		    int birthDateMonth = birthDate.get(Calendar.MONTH);
		    int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);
		    int birthDateDayOfMonth = birthDate.get(Calendar.DAY_OF_MONTH);
		    int age = todayYear - birthDateYear;

		    // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
		    if ((birthDateDayOfYear - todayDayOfYear > 3) || (birthDateMonth > todayMonth)){
		        age--;
		    
		    // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
		    } else if ((birthDateMonth == todayMonth) && (birthDateDayOfMonth > todayDayOfMonth)){
		        age--;
		    }
		    return age;
		}
		
		public static ArrayList<Family> getFamiliesByIndiId(Individual individual, List<Family> families) {
			ArrayList<Family> result = new ArrayList<Family>();
			if (individual.fSpouse != null) {
				for (String fams: individual.fSpouse) {
					for (Family family: families) {
						if (fams.equals(family.id)) {
							result.add(family);
							break;
						}
					}
				}
			}
			return result;
		}
}


