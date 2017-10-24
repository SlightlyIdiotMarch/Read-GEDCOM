

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class testForUS03 {

	@Test
	public void test() {
		List<Individual> indi_list = new ArrayList<>();
		Individual individual = new Individual();
		individual.id = "I1";
		individual.death = "1 APR 1930";
		individual.birthday = "5 SEP 1940";
		indi_list.add(individual);
		CheckValidate cv = new CheckValidate();
		cv.BirthBeforeDeath(indi_list);
	}
}
