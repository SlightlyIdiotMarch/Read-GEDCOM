import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class testForUS23 {

	@Test
	public void testHaveError() {
		List<Individual> indi_list = new ArrayList<>();
		Individual indi1 = new Individual();
		indi1.id = "I1";
		indi1.name = "Xie /Chen/";
		indi1.birthday = "5 FEB 1992";
		indi_list.add(indi1);
		Individual indi2 = new Individual();
		indi2.id = "I2";
		indi2.name = "Xie /Chen/";
		indi2.birthday = "5 FEB 1992";
		indi_list.add(indi2);
		CheckValidate cv = new CheckValidate();
		cv.UniqueNameAndBirth(indi_list);
	}
	
	@Test
	public void testOnlySameName() {
		List<Individual> indi_list = new ArrayList<>();
		Individual indi1 = new Individual();
		indi1.id = "I1";
		indi1.name = "Xie /Chen/";
		indi1.birthday = "5 FEB 1992";
		indi_list.add(indi1);
		Individual indi2 = new Individual();
		indi2.id = "I2";
		indi2.name = "Xie /Chen/";
		indi2.birthday = "12 JAN 2011";
		indi_list.add(indi2);
		CheckValidate cv = new CheckValidate();
		cv.UniqueNameAndBirth(indi_list);
	}
	
	@Test
	public void testOnlySameBirth() {
		List<Individual> indi_list = new ArrayList<>();
		Individual indi1 = new Individual();
		indi1.id = "I1";
		indi1.name = "Xie /Chen/";
		indi1.birthday = "5 FEB 1992";
		indi_list.add(indi1);
		Individual indi2 = new Individual();
		indi2.id = "I2";
		indi2.name = "Xia /Che/";
		indi2.birthday = "5 FEB 1992";
		indi_list.add(indi2);
		CheckValidate cv = new CheckValidate();
		cv.UniqueNameAndBirth(indi_list);
	}
	
	@Test
	public void testNotSame() {
		List<Individual> indi_list = new ArrayList<>();
		Individual indi1 = new Individual();
		indi1.id = "I1";
		indi1.name = "Xie /Chen/";
		indi1.birthday = "5 FEB 1992";
		indi_list.add(indi1);
		Individual indi2 = new Individual();
		indi2.id = "I2";
		indi2.name = "Xia /Che/";
		indi2.birthday = "12 JAN 2011";
		indi_list.add(indi2);
		CheckValidate cv = new CheckValidate();
		cv.UniqueNameAndBirth(indi_list);
	}
}
