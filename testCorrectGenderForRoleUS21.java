import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class testCorrectGenderForRoleUS21 {

	@Test
	public void test() {
		List<Family> family_list=new ArrayList<Family>();
		List<Individual> individual_list=new ArrayList<Individual>();
		Family family=new Family();
	    family.husband_id="I1";
		family.wife_id="I2";
		family_list.add(family);
		Individual individual= new Individual();
		individual.id="I1";
		individual.sex="F";
		individual_list.add(individual);
		Individual individual1= new Individual();
		individual1.id="I2";
		individual1.sex="M";
		individual_list.add(individual1);
		Check.correctGenderForRole(family_list, individual_list);
		
	}
	
	@Test
	public void test1() {
		List<Family> family_list=new ArrayList<Family>();
		List<Individual> individual_list=new ArrayList<Individual>();
		Family family=new Family();
	    family.husband_id="I1";
		family.wife_id="I2";
		family_list.add(family);
		Individual individual= new Individual();
		individual.id="I1";
		individual.sex="M";
		individual_list.add(individual);
		Individual individual1= new Individual();
		individual1.id="I2";
		individual1.sex="F";
		individual_list.add(individual1);
		Check.correctGenderForRole(family_list, individual_list);
		
	}

}
