package project03;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GEDCOMParser {
	
	
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
	
	//US02 Birth before marriage
	public static boolean birthBeforeMarr(List<Individual> individuals, List<Family> families) throws ParseException {
		boolean result = true;
		for (Individual individual: individuals) {
			Date birthday = processDate(individual.birthday);
			ArrayList<Family> getFamalies = getFamiliesByIndiId(individual, families);
			if (getFamalies.size() > 0) {
				for (Family family: getFamalies) {
					Date marriagedate = processDate(family.marriage_date);
					if (marriagedate.before(birthday)) {
						System.out.println("ERROR: INDIVIDUAL: US02: " + individual.id + ": Birthday " + birthday + " is before the marriage date " + marriagedate);
						result = false;
					}	
				}
			}
		}
		return result;
	}
	
	//US05 Marriage before Death
	public static boolean marraigeBeforeDeath(List<Individual> individuals, List<Family> families) throws ParseException {
		boolean result = true;
		for (Individual individual: individuals) {
			if (individual.death != null) {
				Date deathdate = processDate(individual.death);
				ArrayList<Family> getFamilies = getFamiliesByIndiId(individual, families);
				if (getFamilies.size() > 0) {
					for (Family family: getFamilies) {
						Date marriagedate = processDate(family.marriage_date );
						if (deathdate.before(marriagedate)) {
							System.out.println("ERROR: INDIVIDUAL: US05: " + individual.id + ": Deathdate " + deathdate + " is before the marriage date " + marriagedate);
							result = false;
						}	
					}
				}
			}
		}
		return result;
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
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
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
