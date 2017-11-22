import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestListAllMarried {

	@Test
	public void test() {
		List<Individual> individuals = new ArrayList<>();
		List<Family> families = new ArrayList<>();
		try {
			System.out.println("NoLivingMarriedPeople:\n");
			GEDCOMParser.parse("src/testfile/US30NoLivingMarried.ged", individuals, families);
			UserStorySprint1.listAllMarriedPeople(families, individuals);
			System.out.println("Success:");
			individuals = new ArrayList<>();
			families = new ArrayList<>();
			GEDCOMParser.parse("src/Project01.ged", individuals, families);
			UserStorySprint1.listAllMarriedPeople(families, individuals);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
