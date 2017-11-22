import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class testUniqueFirstNamesInFamiliesUS25 {

	@Test
	public void test() {
		List<Family> family_list = new ArrayList<>();
		Family family = new Family();
		family.child_ids=new ArrayList<String>();
		family.id = "F1";
		String kid1 = "I1";
		String kid2 = "I2";
		family.child_ids.add(kid1);
		family.child_ids.add(kid2);
		family_list.add(family);
		
		List<Individual> individual_list = new ArrayList<>();
		Individual individual1 = new Individual();
		individual1.id = "I1";
		individual1.birthday="5 JAN 1997";
		individual1.name = "Xue /Xia/";
		individual_list.add(individual1);
		Individual individual2 = new Individual();
		individual1.id = "I2";
		individual1.birthday="5 JAN 1997";
		individual1.name = "Xue /Xia/";
		individual_list.add(individual2);
		
		Check.uniqueFirstNamesInFamilies(family_list, individual_list);
	}
	
	@Test
	public void test1() {
		List<Family> family_list = new ArrayList<>();
		Family family = new Family();
		family.child_ids=new ArrayList<String>();
		family.id = "F1";
		String kid1 = "I1";
		String kid2 = "I2";
		family.child_ids.add(kid1);
		family.child_ids.add(kid2);
		family_list.add(family);
		
		List<Individual> individual_list = new ArrayList<>();
		Individual individual1 = new Individual();
		individual1.id = "I1";
		individual1.birthday="6 JAN 1997";
		individual1.name = "Xue /Xia/";
		individual_list.add(individual1);
		Individual individual2 = new Individual();
		individual1.id = "I2";
		individual1.birthday="5 JAN 1997";
		individual1.name = "Xue /Xia/";
		individual_list.add(individual2);
		
		Check.uniqueFirstNamesInFamilies(family_list, individual_list);
	}

}
