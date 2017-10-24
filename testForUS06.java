

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class testForUS06 {

	@Test
	public void test() throws ParseException {
		List<Individual> indi_list = new ArrayList<>();
		List<Family> family_list = new ArrayList<>();
		Individual husband = new Individual();
		husband.id = "I1";
		husband.death = "1 APR 1930";
		indi_list.add(husband);
		Individual wife = new Individual();
		wife.id = "I2";
		wife.death = "1 APR 1990";
		indi_list.add(wife);
		Family family = new Family();
		family.id = "F1";
		family.husband_id = "I1";
		family.wife_id = "I2";
		family.divorce_date = "1 APR 1940";
		family_list.add(family);
		CheckValidate cv = new CheckValidate();
		cv.DivorceBeforeDeath(family_list, indi_list);
	}

}
