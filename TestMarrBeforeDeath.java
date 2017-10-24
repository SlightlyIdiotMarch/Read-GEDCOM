import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestMarrBeforeDeath {

	@Test
	public void test() throws IOException {
		String address = "c:\\users\\shizekang\\desktop\\cs-555\\Project01.ged";
		List<Individual> indi_list = new ArrayList<>();
		List<Family> family_list = new ArrayList<>();
		GEDCOMParser.parse(address, indi_list, family_list);
		try {
			assertEquals(true, UserStorySprint1.marraigeBeforeDeath(indi_list, family_list));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
