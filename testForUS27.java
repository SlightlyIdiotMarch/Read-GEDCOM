import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class testForUS27 {

	@Test
	public void testHaveError() {
		List<Individual> indi_list = new ArrayList<>();
		Individual indi1 = new Individual();
		indi1.id = "I1";
		indi1.birthday = "8 FEB 1985";
		indi1.death = "12 APR 2016";
		indi_list.add(indi1);
		Individual indi2 = new Individual();
		indi2.id = "I2";
		indi2.birthday = "23 DEC 1993";
		indi_list.add(indi2);
		CheckValidate cv = new CheckValidate();
		for (Individual indi : indi_list) {
			System.out.println("The age of " + indi.id + " is " + cv.IncludeIndividualAges(indi));
		}
	}
}
