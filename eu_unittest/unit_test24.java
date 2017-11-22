import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class unit_test24 {
	public static void main(String[] arge) {
		try {
			String address = "unit_test24.ged";
			List<Individual> indi_list = new ArrayList<>();
			List<Family> family_list = new ArrayList<>();
			GEDCOMParser.parse(address, indi_list, family_list);			
			euloanty.unique_families_by_spouses(family_list,indi_list);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
