import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Check {
	public static void beforeCurrent(List<Individual> indi_list,List<Family> family_list) {
		String s="d MMM yyyy";
		SimpleDateFormat format= new SimpleDateFormat(s,Locale.ENGLISH);
		Date date=new Date();
		s=format.format(date);

		//String pattern="d MMM yyyy";
		//SimpleDateFormat format= new SimpleDateFormat(pattern,Locale.ENGLISH);
		try{
		for(Individual indi:indi_list){
		if(indi.birthday!=null){
			String birth=indi.birthday;	
			if(format.parse(birth).after(format.parse(s))){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy MM dd");
				String birthday=sdf.format(format.parse(indi.birthday))	;
				System.out.println("ERROR: INDIVIDUAL: "+"US01:"+indi.id+":"+"Birthday "+birthday+" occurs in the future");
			}
		}
		
		if(indi.death!=null){
			String death=indi.death.toString();	
			if(format.parse(death).after(format.parse(s))){
				SimpleDateFormat format1= new SimpleDateFormat("yyyy MM dd",Locale.CHINA);
				String deathDate=format1.format(format.parse(indi.death));
				System.out.println("ERROR: INDIVIDUAL: "+"US01:"+indi.id+":"+"Deathdate"+deathDate+" occurs in the future");
			}
			
		}
		}
		for(Family fam:family_list){
		if(fam.marriage_date!=null){
			String marryDate=fam.marriage_date.toString();	
			if(format.parse(marryDate).after(format.parse(s))){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy MM dd");
				String marriage=sdf.format(format.parse(fam.marriage_date));
				System.out.println("ERROR: INDIVIDUAL: "+"US01:"+fam.id+":"+"marriageDate "+marriage+" occurs in the future");
			}
		}
		
		if(fam.divorce_date!=null){
			String divorceDate=fam.divorce_date.toString();	
			if(format.parse(divorceDate).after(format.parse(s))){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy MM dd");
				String divorce=sdf.format(format.parse(fam.divorce_date));
				System.out.println("ERROR: INDIVIDUAL: "+"US01:"+fam.id+":"+"divorceDate "+divorce+" occurs in the future");
			}
		}
		}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return;
	}
	
	
	public static void dateBeforeMarriage(List<Family> family_list){
		String marriageDate;
		String divorceDate;
		SimpleDateFormat format=new SimpleDateFormat("d MMM yyyy",Locale.ENGLISH);
		try{
		for(Family fam:family_list){
		if((fam.marriage_date!=null)&&(fam.divorce_date!=null)){
			marriageDate=fam.marriage_date;
			divorceDate=fam.divorce_date;
			if((format.parse(marriageDate).after(format.parse(divorceDate)))){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy MM d");
				String marry=sdf.format(format.parse(fam.marriage_date))	;
				String divorce=sdf.format(format.parse(fam.divorce_date))	;
				System.out.println("ERROR: FAMILY: "+"US04:"+fam.id+":"+"divorceDate"+divorce+" occurs in the future");
				System.out.println("ERROR: FAMILY: "+"US04:"+fam.id+":"+"marriageDate"+marry+" occurs in the future");
			}
		}
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}
	return;
	}	
}
