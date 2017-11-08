import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class testForUS15 {

	@Test
	public void test() {
		List<Family> family_list = new ArrayList<>();
		Family family = new Family();
		family.child_ids = new ArrayList<String>();
		for (int i = 0; i < 15; i++) {
			family.child_ids.add("I" + i);
		}
		family_list.add(family);
		CheckValidate cv = new CheckValidate();
		cv.FewerThanFifteenSiblings(family_list);
	}

}
