import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class unit_test12 {
	public static void main(String[] arge) {
		try
		{
			String address = "unit_test12.ged";
			List<Individual> indi_list = new ArrayList<>();
			List<Family> family_list = new ArrayList<>();
			GEDCOMParser.parse(address, indi_list, family_list);
			euloanty.parents_not_too_old(family_list,indi_list);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
