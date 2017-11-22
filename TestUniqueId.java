import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestUniqueId {

	@Test
	public void test() {
		List<Individual> individuals = new ArrayList<>();
		List<Family> families = new ArrayList<>();
		try {
			GEDCOMParser.parse("src/testfile/US22individualfail.ged", individuals, families);
			assertEquals(UserStorySprint1.uniqueId(families, individuals), false);
			individuals = new ArrayList<>();
			families = new ArrayList<>();
			GEDCOMParser.parse("src/testfile/US22familyfail.ged", individuals, families);
			assertEquals(UserStorySprint1.uniqueId(families, individuals), false);
			individuals = new ArrayList<>();
			families = new ArrayList<>();
			GEDCOMParser.parse("src/testfile/US22pass.ged", individuals, families);
			assertEquals(UserStorySprint1.uniqueId(families, individuals), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
