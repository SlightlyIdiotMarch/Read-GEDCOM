

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class testForUS06 {

	@Test
	public void test() {
		String address = "src/testfile/test_US06.ged";
		List<Individual> indi_list = new ArrayList<>();
		List<Family> family_list = new ArrayList<>();
		GEDCOMParser.parse(address, indi_list, family_list);
		CheckValidate cv = new CheckValidate();
		cv.DivorceBeforeDeath(family_list, indi_list);
	}

}
