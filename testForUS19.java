import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class testForUS19 {

	@Test
	public void testHaveError() {
		List<Individual> indi_list = new ArrayList<>();
		List<Family> family_list = new ArrayList<>();
		//Create individuals
		Individual indi1 = new Individual();
		indi1.id = "I1";
		indi1.fChild = "F2";
		indi_list.add(indi1);
		Individual indi2 = new Individual();
		indi2.id = "I2";
		indi2.fChild = "F3";
		indi_list.add(indi2);
		Individual indi3 = new Individual();
		indi3.id = "I3";
		indi_list.add(indi3);
		Individual indi4 = new Individual();
		indi4.id = "I4";
		indi_list.add(indi4);
		Individual indi5 = new Individual();
		indi5.id = "I5";
		indi_list.add(indi5);
		Individual indi6 = new Individual();
		indi6.id = "I6";
		indi_list.add(indi6);
		Individual indi7 = new Individual();
		indi7.id = "I7";
		indi_list.add(indi7);
		Individual indi8 = new Individual();
		indi8.id = "I8";
		indi_list.add(indi8);
		// Create families
		Family family1 = new Family();
		family1.id = "F1";
		family1.husband_id = "I1";
		family1.wife_id = "I2";
		family_list.add(family1);
		Family family2 = new Family();
		family2.id = "F2";
		family2.husband_id = "I3";
		family2.wife_id = "I4";
		family_list.add(family2);
		Family family3 = new Family();
		family3.id = "F3";
		family3.husband_id = "I5";
		family3.wife_id = "I6";
		family_list.add(family3);
		Family family4 = new Family();
		family4.id = "F4";
		family4.husband_id = "I7";
		family4.wife_id = "I8";
		family4.child_ids = new ArrayList<String>();
		family4.child_ids.add("I4");
		family4.child_ids.add("I5");
		family_list.add(family4);
		// Check validate
		CheckValidate cv = new CheckValidate();
		cv.FirstCousinsNotMarry(family_list, indi_list);
	}
	
	@Test
	public void testNoError() {
		List<Individual> indi_list = new ArrayList<>();
		List<Family> family_list = new ArrayList<>();
		//Create individuals
		Individual indi1 = new Individual();
		indi1.id = "I1";
		indi1.fChild = "F2";
		indi_list.add(indi1);
		Individual indi2 = new Individual();
		indi2.id = "I2";
		indi2.fChild = "F3";
		indi_list.add(indi2);
		Individual indi3 = new Individual();
		indi3.id = "I3";
		indi_list.add(indi3);
		Individual indi4 = new Individual();
		indi4.id = "I4";
		indi_list.add(indi4);
		Individual indi5 = new Individual();
		indi5.id = "I5";
		indi_list.add(indi5);
		Individual indi6 = new Individual();
		indi6.id = "I6";
		indi_list.add(indi6);
		Individual indi7 = new Individual();
		indi7.id = "I7";
		indi_list.add(indi7);
		Individual indi8 = new Individual();
		indi8.id = "I8";
		indi_list.add(indi8);
		// Create families
		Family family1 = new Family();
		family1.id = "F1";
		family1.husband_id = "I1";
		family1.wife_id = "I2";
		family_list.add(family1);
		Family family2 = new Family();
		family2.id = "F2";
		family2.husband_id = "I3";
		family2.wife_id = "I4";
		family_list.add(family2);
		Family family3 = new Family();
		family3.id = "F3";
		family3.husband_id = "I5";
		family3.wife_id = "I6";
		family_list.add(family3);
		Family family4 = new Family();
		family4.id = "F4";
		family4.husband_id = "I7";
		family4.wife_id = "I8";
		family4.child_ids = new ArrayList<String>();
		family4.child_ids.add("I4");
		family_list.add(family4);
		// Check validate
		CheckValidate cv = new CheckValidate();
		cv.FirstCousinsNotMarry(family_list, indi_list);
	}

}
