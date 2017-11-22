import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestCorrespondEntry {

	@Test
	public void test() {
		List<Individual> individuals = new ArrayList<>();
		List<Family> families = new ArrayList<>();
		try {
			GEDCOMParser.parse("src/testfile/US26indifail.ged", individuals, families);
			assertEquals(UserStorySprint1.correspondEntry(families, individuals), false);
			individuals = new ArrayList<>();
			families = new ArrayList<>();
			GEDCOMParser.parse("src/testfile/US26familyfail.ged", individuals, families);
			assertEquals(UserStorySprint1.correspondEntry(families, individuals), false);
			individuals = new ArrayList<>();
			families = new ArrayList<>();
			GEDCOMParser.parse("src/testfile/US26pass.ged", individuals, families);
			assertEquals(UserStorySprint1.correspondEntry(families, individuals), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
