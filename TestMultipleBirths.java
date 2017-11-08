import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestMultipleBirths {

	@Test
	public void test() {
		String address = "c:\\users\\shizekang\\desktop\\cs-555\\Project01.ged";
		List<Individual> indi_list = new ArrayList<>();
		List<Family> family_list = new ArrayList<>();
		try {
			GEDCOMParser.parse(address, indi_list, family_list);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		assertEquals(false, UserStorySprint1.multipleBirth(family_list));
	}

}
