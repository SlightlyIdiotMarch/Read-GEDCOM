import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	//US18 Siblings should not marry
	public static boolean siblingNotMarry(List<Family> families, List<Individual> individuals) {
		boolean b = true;
		for (Family family: families) {
			String husbandid = family.husband_id;
			Individual husband = UtilityZS.getIndividualById(individuals, husbandid);
			String wifeid = family.wife_id;
			Individual wife = UtilityZS.getIndividualById(individuals, wifeid);
			if (husband != null && wife != null) {
				if (husband.fChild != null && wife.fChild != null) {
					if (husband.fChild.equals(wife.fChild)) {
						b = false;
						System.out.println("ERROR: FAMILY: US18: No." + family.id + " family's husband(" + husbandid + ") and wife(" + wifeid + ") are sibling");
					}
				}
			}
		}
		return b;
	}
	
	//US22 Unique Id
	public static boolean uniqueId(List<Family> families, List<Individual> individuals) {
		boolean b = true;
		Set<String> individualset = new HashSet<String>();
		Set<String> repeatcount = new HashSet<String>();
		for (Individual individual: individuals) {
			if (individualset.contains(individual.id)) {
				if (!repeatcount.contains(individual.id)) {
					b = false;
					System.out.println("ERROR: INDIVIDUAL: US22: This Id is not unique: " + individual.id);
					repeatcount.add(individual.id);	
				}
			} else {
				individualset.add(individual.id);
			}
		}
		Set<String> familyset = new HashSet<String>();
		repeatcount = new HashSet<String>();
		for (Family family: families) {
			if (familyset.contains(family.id)) {
				if (!repeatcount.contains(family.id)) {
					b = false;
					System.out.println("ERROR: FAMILY: US22: This Id is not unique: " + family.id);
					repeatcount.add(family.id);
				}
			} else {
				familyset.add(family.id);
			}
		}
		return b;
	}
	
	//US26
	public static boolean correspondEntry(List<Family> families, List<Individual> individuals) {
		boolean b = true;
		Set<String> names = UtilityZS.getAllNamesFromFamReco(families);
		Set<String> individualId = UtilityZS.getAllNamesFromIndiRec(individuals);
		for (Individual individual: individuals) {
			if (!names.contains(individual.id)) {
				b = false;
				System.out.println("ERROR: INDIVIDUAL: US26: This individual " + individual.id + " has no information in family records");
			}
		}
		for (Family family: families) {
			if (family.wife_id != null) {
				if (!individualId.contains(family.wife_id)) {
					b = false;
					System.out.println("ERROR: FAMILY: US26: This family(" + family.id + ")'s wife(" + family.wife_id +") does not have information in the individual record");
					
				}
			}
			if (family.husband_id != null) {
				if (!individualId.contains(family.husband_id)) {
					b = false;
					System.out.println("ERROR: FAMILY: US26: This family(" + family.id + ")'s husband(" + family.husband_id +") does not have information in the individual record");
				}
			}
			if (family.child_ids != null) {
				for (String id: family.child_ids) {
					if (!individualId.contains(id)) {
						b = false;
						System.out.println("ERROR: FAMILY: US26: This family(" + family.id + ")'s child(" + id +") does not have information in the individual record");
					}
				}
			}
		}
		return b;
	}

	//US30
	public static void listAllMarriedPeople(List<Family> families, List<Individual> individuals) {
		for (Family family: families) {
			if (family.divorce_date == null) {
				Individual husband = UtilityZS.getIndividualById(individuals, family.husband_id);
				if (husband != null) {
					if (husband.death == null) {
						System.out.println("US30: The living married people: " + husband.name);
					}
				}
				Individual wife = UtilityZS.getIndividualById(individuals, family.wife_id);
				if (wife != null) {
					if (wife.death == null) {
						System.out.println("US30: The living married people: " + wife.name);
					}
				}
			}
			
		}
	}
	
}

