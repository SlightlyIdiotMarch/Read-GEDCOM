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
							System.out.println("ERROR: INDIVIDUAL: US05: " + individual.id + ": Deathdate " + deathdate + " is before the marriage date " + marriagedate);
							result = false;
						}	
					}
				}
			}
		}
		return result;
	}
	
}