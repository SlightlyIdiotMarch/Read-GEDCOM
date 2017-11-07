import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class testbirthBeforeDeathOfParents {

	@Test
	public void test() throws ParseException {
		Check c=new Check();
		List<Family> fam_List=new ArrayList();
		List<Individual> indi_list=new ArrayList();
		Family family=new Family();
		family.child_ids=new ArrayList();
		Individual individual=new Individual();
		family.husband_id="I1";
		family.wife_id="I2";
		family.child_ids.add("I3");
		fam_List.add(family);
		individual.id="I1";
		individual.death="1 APR 1000";
		indi_list.add(individual);
		individual.id="I3";
		individual.birthday="1 APR 1900";
		indi_list.add(individual);
	    
		c.birthBeforeDeathOfParents(fam_List, indi_list);
	}

}
