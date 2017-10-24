

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CheckValidate {

	
	// Set pattern to parse String into Date
	String pattern = "d MMM yyyy";
	SimpleDateFormat format = new SimpleDateFormat(pattern,Locale.ENGLISH);
	
	
	//	US03: Check if birth is before death
	public void BirthBeforeDeath(List<Individual> individuals) {
		try {
			for (Individual individual : individuals) {
				if (individual.death != null) {
					String death = individual.death;
					String birth = individual.birthday;
					if (format.parse(birth).after(format.parse(death))) {
						System.out.println("ERROR: INDIVIDUAL: US03: " + individual.id + " Died " + individual.death + " before born " + individual.birthday);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// US06: Check if divorce is before death
	public void DivorceBeforeDeath(List<Family> families, List<Individual> individuals) throws java.text.ParseException {
		for (Family family : families) {
			if (family.divorce_date != null) {
				String divorce = family.divorce_date;
				String deathOfHusb = null;
				String deathOfWife = null;
				Individual husband = null;
				Individual wife = null;
				for (Individual individual : individuals) {
					if (individual.id.equals(family.husband_id)) {
						husband = individual;
						deathOfHusb = individual.death;
						break;
					}
				}
				for (Individual individual : individuals) {
					if (individual.id.equals(family.wife_id)) {
						wife = individual;
						deathOfWife = individual.death;
						break;
					}
				}
				if (deathOfHusb != null && format.parse(divorce).after(format.parse(deathOfHusb))) {
					System.out.println("ERROR: FAMILY: US06: " + family.id + " Divorced " + family.divorce_date + " after husband's (" + husband.id + ") death on " + husband.death);
				}
				if (deathOfWife != null && format.parse(divorce).after(format.parse(deathOfWife))) {
					System.out.println("ERROR: FAMILY: US06: " + family.id + " Divorced " + family.divorce_date + " after wife's (" + wife.id + ") death on " + wife.death);
				}
			}
		}
	}
}
