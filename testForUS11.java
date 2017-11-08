import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class testForUS11 {

	@Test
	public void test() {
		List<Family> family_list = new ArrayList<>();
		Family family1 = new Family();
		family1.id = "F1";
		family1.husband_id = "I1";
		family1.wife_id = "I2";
		family1.marriage_date = "1 APR 1990";
		family1.divorce_date = "23 MAY 2000";
		family_list.add(family1);
		Family family2 = new Family();
		family2.id = "F2";
		family2.husband_id = "I3";
		family2.wife_id = "I2";
		family2.marriage_date = "1 APR 2000";
		family2.divorce_date = "1 JAN 2015";
		family_list.add(family2);
		CheckValidate cv = new CheckValidate();
		cv.NoBigamy(family_list);
	}

}
