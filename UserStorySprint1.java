import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserStorySprint1 {
	
	//US02 Birth before marriage
	public static boolean birthBeforeMarr(List<Individual> individuals, List<Family> families) throws ParseException {
		boolean result = true;
		for (Individual individual: individuals) {
			Date birthday = UtilityZS.processDate(individual.birthday);
			ArrayList<Family> getFamalies = UtilityZS.getFamiliesByIndiId(individual, families);
			if (getFamalies.size() > 0) {
				for (Family family: getFamalies) {
					Date marriagedate = UtilityZS.processDate(family.marriage_date);
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
				Date deathdate = UtilityZS.processDate(individual.death);
				ArrayList<Family> getFamilies = UtilityZS.getFamiliesByIndiId(individual, families);
				if (getFamilies.size() > 0) {
					for (Family family: getFamilies) {
						Date marriagedate = UtilityZS.processDate(family.marriage_date );
						if (deathdate.before(marriagedate)) {
							System.out.println("ERROR: INDIVIDUAL: US05: " + individual.id + " Deathdate " + deathdate + " is before the marriage date " + marriagedate);
							result = false;
						}	
					}
				}
			}
		}
		return result;
	}
	
	//US10 Marriage after 14
	public static boolean marrageAfterFourteen(List<Individual> individuals, List<Family> families) throws ParseException {
		boolean result = true;
		for (Individual individual: individuals) {
			if (individual.fSpouse != null) {
				ArrayList<Family> families2 = UtilityZS.getFamiliesByIndiId(individual, families);
				if (families2.size() > 0) {
					for (Family family: families2) {
						Date marry = UtilityZS.processDate(family.marriage_date);
						int age = UtilityZS.getAge(UtilityZS.processDate(individual.birthday)) - UtilityZS.getAge(marry);
						if (age < 14) {
							System.out.println("ERROR: INDIVIDUAL: US10: " + individual.id + " married before 14 in famaily number: " + family.id);
							result = false;
						}
					}
				}
			}
		}
		return result;
	}
	
	//US14 Multiple births <= 5
	public static boolean multipleBirth(List<Family> families) {
		boolean result = true;
		for (Family family: families) {
			if (family.child_ids != null && family.child_ids.size() > 5) {
				System.out.println("ERROR: FAMILY: US14: " + family.id + " family has multiple births more than 5");
				result = false;
			}
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}