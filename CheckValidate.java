import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import classForUserStory.US06Info;

public class CheckValidate {

	public Date stringToDate(String str) throws ParseException {
		String pattern = "d MMM yyyy";
		SimpleDateFormat format = new SimpleDateFormat(pattern,Locale.ENGLISH);
		return format.parse(str);
	}
	
	//	US03: Check if birth is before death
	public void BirthBeforeDeath(List<Individual> individuals) {
		try {
			for (Individual individual : individuals) {
				if (individual.death != null) {
					String death = individual.death;
					String birth = individual.birthday;
					if (stringToDate(birth).after(stringToDate(death))) {
						System.out.println("ERROR: INDIVIDUAL: US03: " + individual.id + " Died " + individual.death + " before born " + individual.birthday);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// US06: Check if divorce is before death
	public void DivorceBeforeDeath(List<Family> families, List<Individual> individuals) throws ParseException{
			for (Family family : families) {
				if (family.divorce_date != null) {
					US06Info info = new US06Info();
					info.setDivorce(family.divorce_date);
					for (Individual individual : individuals) {
						if (individual.id.equals(family.husband_id)) {
							info.setDeathOfHusb(individual.death);
							info.setHusbId(individual.id);
							break;
						}
					}
					for (Individual individual : individuals) {
						if (individual.id.equals(family.wife_id)) {
							info.setDeathOfWife(individual.death);
							info.setWifeId(individual.id);
							break;
						}
					}
					if (info.getDeathOfHusb() != null && stringToDate(info.getDivorce()).after(stringToDate(info.getDeathOfHusb()))) {
						System.out.println("ERROR: FAMILY: US06: " + family.id + " Divorced " + info.getDivorce() + " after husband's (" + info.getHusbId() + ") death on " + info.getDeathOfHusb());
					}
					if (info.getDeathOfWife() != null && stringToDate(info.getDivorce()).after(stringToDate(info.getDeathOfWife()))) {
						System.out.println("ERROR: FAMILY: US06: " + family.id + " Divorced " + info.getDivorce() + " after wife's (" + info.getWifeId() + ") death on " + info.getDeathOfWife());
					}
				}
			}
		
	}

	// US11: 
	public void NoBigamy(List<Family> families) {
		try {
			for (int i = 0; i < families.size(); i++) {
				Family family1 = families.get(i);
				for (int j = i + 1; j < families.size(); j++) {
					Family family2 = families.get(j);
					boolean bigamy = false;
					if (family2.wife_id.equals(family1.wife_id) || family2.husband_id.equals(family1.husband_id)) {
						if (family1.divorce_date == null && family2.divorce_date == null) {
							bigamy = true;
						} else if (family1.divorce_date == null) {
							if (stringToDate(family1.marriage_date).before(stringToDate(family2.divorce_date))) {
								bigamy = true;
							}
						} else if (family2.divorce_date == null) {
							if (stringToDate(family2.marriage_date).before(stringToDate(family1.divorce_date))) {
								bigamy = true;
							}
						} else {
							if (!((stringToDate(family2.marriage_date).before(stringToDate(family1.marriage_date)) && stringToDate(family2.divorce_date).before(stringToDate(family1.marriage_date))) 
									|| (stringToDate(family1.marriage_date).before(stringToDate(family2.marriage_date)) && stringToDate(family1.divorce_date).before(stringToDate(family2.marriage_date))))) {
								bigamy = true;
							}
						}
					}
					if (bigamy) {
						System.out.println("ERROR: FAMILY: US11: " + family1.id + " Marriage occured during another marriage in falimy " + family2.id);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// US15: Check if a family has fewer than 15 siblings
	public void FewerThanFifteenSiblings(List<Family> families) {
		try {
			for (Family family : families) {
				if (family.child_ids != null && family.child_ids.size() > 14) {
					System.out.println("ERROR: FAMILY: US15: " + family.id + " More than 15 siblings in this family");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// US19: First cousins should not marry
	public void FirstCousinsNotMarry(List<Family> families, List<Individual> individuals) {
		for (Family checkFamily : families) {
			String hudband_id = checkFamily.husband_id;
			String wife_id = checkFamily.wife_id;
			String husband_parent_fid = null;
			String wife_parent_fid = null;
			Family husband_parent_F = new Family();
			Family wife_parent_F = new Family();
			for (Individual individual : individuals) {
				if (individual.id.equals(hudband_id)) {
					husband_parent_fid  = individual.fChild;
				}
				if (individual.id.equals(wife_id)) {
					wife_parent_fid = individual.fChild;
				}
			}
			for (Family family : families) {
				if (family.id.equals(husband_parent_fid)) {
					husband_parent_F = family;
				}
				if (family.id.equals(wife_parent_fid)) {
					wife_parent_F = family;
				}
			}
			// If they are from the same family, they are siblings instead of first cousins
			if (husband_parent_F.equals(wife_parent_F)) {
				continue;
			}
			for (Family family : families) {
				if (husband_parent_F != null && wife_parent_F != null && family.child_ids != null) {
					if ((family.child_ids.contains(husband_parent_F.husband_id) || family.child_ids.contains(husband_parent_F.wife_id)) &&
							(family.child_ids.contains(wife_parent_F.husband_id) || family.child_ids.contains(wife_parent_F.wife_id))) {
						System.out.println("ERROR: FAMILY: US19: " + checkFamily.id + " the husband and wife are first cousins");
					}
				}
			}
		}
	}
	
	// US23: Unique name and birth date
	public void UniqueNameAndBirth(List<Individual> individuals) {
		for(int i = 0; i < individuals.size(); i++) {
			Individual indi1 = individuals.get(i);
			for(int j = i + 1; j < individuals.size(); j++) {
				Individual indi2 = individuals.get(j);
				if (indi1.name.equals(indi2.name) && indi1.birthday.equals(indi2.birthday)) {
					System.out.println("ERROR: INDIVIDUAL: US23: " + indi1.id + " has the same name and birthday as " + indi2.id);
				}
			}
		}
	}
	
	// US27: Include individual ages
	public String IncludeIndividualAges(Individual indi) {
		String age = "";
		try {
			if (UtilityZS.processDate(indi.birthday).before((new Date()))) {
				age = String.valueOf(UtilityZS.getAge(UtilityZS.processDate(indi.birthday)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return age;
	}
	
	//US31: List living single
	public void ListLivingSingle(List<Individual> individuals) {
		try {
			for (Individual indi : individuals) {
				if (indi.fSpouse == null || indi.fSpouse.size() == 0) {
					String age = "";
					if (indi.death == null) {
						if (UtilityZS.processDate(indi.birthday).before((new Date()))) {
							age = String.valueOf(UtilityZS.getAge(UtilityZS.processDate(indi.birthday)));
						}
					}
					if (Integer.valueOf(age) > 30) {
						System.out.println("LIST: INDIVIDUAL: US31: " + indi.id + " is a living single");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
