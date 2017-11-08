import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class testNoMarriageToDescendantUS17 {

	@Test
	public void test() {
		List<Family> family_list=new ArrayList<Family>();
		Family family=new Family();
		family.child_ids=new ArrayList<String>();
		family.husband_id="I1";
		family.wife_id="I2";
		//String childID1="I1";
		//String childID2="I4";
		family.child_ids.add("I1");
		family.child_ids.add("I4");
		family_list.add(family);
		Check.noMarriageToDescendant(family_list);
		
	}
	
	@Test
	public void test1() {
		List<Family> family_list=new ArrayList<Family>();
		Family family=new Family();
		family.child_ids=new ArrayList<String>();
		family.husband_id="I1";
		family.wife_id="I2";
		//String childID1="I1";
		//String childID2="I4";
		family.child_ids.add("I3");
		family.child_ids.add("I4");
		family_list.add(family);
		Check.noMarriageToDescendant(family_list);
		
	}

}
