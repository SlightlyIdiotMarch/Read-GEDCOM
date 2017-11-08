import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class testsiblingsSpace {

	@Test
	public void test() {
		Check c=new Check();
		List<Family> fam_List=new ArrayList();
		List<Individual> indi_list=new ArrayList();
		Family family=new Family();
		family.child_ids=new ArrayList();
		Individual individual=new Individual();
		family.child_ids.add("I1");
		family.child_ids.add("I2");
		fam_List.add(family);
		individual.id="I1";
		individual.birthday="5 JAN 1997";
		indi_list.add(individual);
		individual.id="I2";
		individual.birthday="8 JAN 1997";
		indi_list.add(individual);
		
	}

}
