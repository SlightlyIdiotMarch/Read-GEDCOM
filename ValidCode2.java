import java.util.ArrayList;
import java.util.List;

public class ValidCode2 {
	public static void main(String[] arge){
		String address="hand code.txt";
		List<Individual> indi_list = new ArrayList<>();
		List<Family> family_list = new ArrayList<>();
		GEDCOMParser.parse(address, indi_list, family_list);
		System.out.println(indi_list.size());
		System.out.println(family_list.size());
		System.out.println("Individuals");
		System.out.println("+-----+--------------------+--------+----------------+-----+-------+------------+---------+---------+");
		System.out.println("| ID  |        Name        | Gender |    Birthday    | Age | Alive |   Death    |  Child  | Spouse  |");
		System.out.println("+-----+--------------------+--------+----------------+-----+-------+------------+---------+---------+");
		for(Individual indi:indi_list){
			System.out.println("| "+indi.id+" |  "+indi.name+"  | "+indi.sex+" |  "+indi.birthday+"  | Age | Alive |   Death    |  "+indi.fChild+"  | "+indi.fSpouse+"  |");
		}
		System.out.println("+-----+--------------------+--------+----------------+-----+-------+------------+---------+---------+");
	}
}
