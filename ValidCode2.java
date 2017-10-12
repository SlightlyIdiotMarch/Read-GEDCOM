import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ValidCode2 {
	public static void main(String[] arge){

		String address="c:\\Users\\shizekang\\desktop\\cs-555\\Project01.ged";

		List<Individual> indi_list = new ArrayList<>();
		List<Family> family_list = new ArrayList<>();
		GEDCOMParser.parse(address, indi_list, family_list);
    CheckValidate cv = new CheckValidate();
		//sortIndi(indi_list);
		System.out.println("Individuals");
		printIndiDiv();
		printIndi("ID","Name","Gender","Birthday","Age","Alive","Death","Child","Spouse");
		printIndiDiv();
		for(Individual indi:indi_list){
			String spouse1 = "";
			ArrayList<Family> fa = UtilityZS.getFamiliesByIndiId(indi, family_list);
			if (fa != null && fa.size() > 0) {
        spouse1 += "{";
				for (Family family: fa) {
          if (spouse1.length() > 1) {
						spouse1 += ",";
					}
					if (indi.sex.equals("M")) {
						spouse1 += "'" + family.wife_id + "'";
					} else {
						spouse1 += "'" + family.husband_id + "'";
					}
				}
        spouse1 += "}";
			}
			String age = "";
			try {
				if (indi.death == null) {
					age = String.valueOf(UtilityZS.getAge(UtilityZS.processDate(indi.birthday))); 
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String alive = "";
			String death = "";
			if (indi.death != null) {
				alive = "false";
				death = indi.death;
			} else {
				alive = "alive";
				death = "NA";
			}
			printIndi(indi.id,indi.name,indi.sex,indi.birthday,String.valueOf(age),alive,death,indi.fChild!=null?indi.fChild:"None", spouse1==""?"NA":spouse1);
		}
		printIndiDiv();
		System.out.println();
		System.out.println("Family");
		printFamDiv();
		printFam("ID","Married","Divorced","Husband ID","Husband Name","Wife ID","Wife Name","Children");
		printFamDiv();
		for(Family fam:family_list){
			
			String marriage_date = fam.marriage_date==null?"NA":fam.marriage_date;
			String divorced_date = fam.divorce_date==null?"NA":fam.divorce_date;
			StringBuffer children = new StringBuffer();
			if (fam.child_ids != null) {
				children.append("{");
				for(String id:fam.child_ids){
					if(children.length()>1){
						children.append(",");
					}
					children.append("'");
					children.append(id);
					children.append("'");
				}
				children.append("}");	
			}
			String husband_name = "NA";
			String wife_name = "NA";
			for(Individual indi:indi_list){
				if(indi.id.equals(fam.husband_id)){
					husband_name = indi.name;
				}
				if(indi.id.equals(fam.wife_id)){
					wife_name = indi.name;
				}
			}
			printFam(fam.id,marriage_date,divorced_date,fam.husband_id,husband_name,fam.wife_id,wife_name,(children.length()==0)?"NA":(children.toString()));
		}
		printFamDiv();
		try {
			UserStorySprint1.birthBeforeMarr(indi_list, family_list);
			UserStorySprint1.marraigeBeforeDeath(indi_list, family_list);
      cv.BirthBeforeDeath(indi_list);
      cv.DivorceBeforeDeath(family_list, indi_list);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Check.beforeCurrent(indi_list, family_list);
		Check.dateBeforeMarriage(family_list);
	}
/*	public static void sortIndi(List<Individual> indi_list){
		Collections.sort(indi_list,new Comparator<Individual>() {

			@Override
			public int compare(Individual o1, Individual o2) {
				int a = o1.id.compareTo(o2.id);
				if(a>0)
					return 1;
				if(a<0)
					return -1;
				return 0;
			}
		});
		
	}*/
	public static void printIndiDiv(){
		System.out.println(
				"+"+Utils.getFillString("",5, "-")
				+"+"+Utils.getFillString("",22, "-")
				+"+"+Utils.getFillString("",8, "-")
				+"+"+Utils.getFillString("",15, "-")
				+"+"+Utils.getFillString("",6, "-")
				+"+"+Utils.getFillString("",7, "-")
				+"+"+Utils.getFillString("",14, "-")
				+"+"+Utils.getFillString("",8, "-")
				+"+"+Utils.getFillString("",20, "-")
				+"+");
	}
	
	public static void printIndi(String id,String name,String gender,String birthday,String age,String alive,String death,String child, String spouse){
		System.out.println(
				"|"+Utils.getFillString(id,5)
				+"|"+Utils.getFillString(name,22)
				+"|"+Utils.getFillString(gender,8)
				+"|"+Utils.getFillString(birthday,15)
				+"|"+Utils.getFillString(age,6)
				+"|"+Utils.getFillString(alive,7)
				+"|"+Utils.getFillString(death,14)
				+"|"+Utils.getFillString(child,8)
				+"|"+Utils.getFillString(spouse,20)
				+"|");
	}
	
	public static void printFamDiv(){
		System.out.println(
				"+"+Utils.getFillString("",5, "-")
				+"+"+Utils.getFillString("",15, "-")
				+"+"+Utils.getFillString("",15, "-")
				+"+"+Utils.getFillString("",16, "-")
				+"+"+Utils.getFillString("",22, "-")
				+"+"+Utils.getFillString("",16, "-")
				+"+"+Utils.getFillString("",22, "-")
				+"+"+Utils.getFillString("",20, "-")
				+"+");
	}
	public static void printFam(String id,String married,String divorced,String husband_id,String husband_name,String wife_id,String wife_name,String children){
		System.out.println(
				"|"+Utils.getFillString(id,5, " ")
				+"|"+Utils.getFillString(married,15, " ")
				+"|"+Utils.getFillString(divorced,15, " ")
				+"|"+Utils.getFillString(husband_id,16, " ")
				+"|"+Utils.getFillString(husband_name,22, " ")
				+"|"+Utils.getFillString(wife_id,16, " ")
				+"|"+Utils.getFillString(wife_name,22, " ")
				+"|"+Utils.getFillString(children,20, " ")
				+"|");
	}
}
