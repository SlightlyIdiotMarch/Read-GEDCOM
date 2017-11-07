import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class testbeforeCurrent {

	@Test
	public void test() {
		Check c=new Check();
		List<Family> fam_List=new ArrayList();
		List<Individual> indi_list=new ArrayList();
		Family fam=new Family();
		Individual indi=new Individual();
		fam.divorce_date="2 MAY 2004";
		fam.marriage_date="2 MAY 2005";
		indi.birthday="2 MAY 2021";
		indi.death="2 MAY 2020";
		fam_List.add(fam);
		indi_list.add(indi);
		c.beforeCurrent(indi_list, fam_List);
	}	

}
