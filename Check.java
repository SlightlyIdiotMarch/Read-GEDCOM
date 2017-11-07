import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Check {

	public static void beforeCurrent(List<Individual> indi_list,List<Family> family_list) {
		
		SimpleDateFormat format= new SimpleDateFormat("d MMM yyyy",Locale.ENGLISH);
		Date date=new Date();
		String s=format.format(date);

		//String pattern="d MMM yyyy";
		//SimpleDateFormat format= new SimpleDateFormat(pattern,Locale.ENGLISH);
		try{
		for(Individual indi:indi_list){
		if(indi.birthday!=null){
			String birth=indi.birthday;	
			if(format.parse(birth).after(format.parse(s))){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy MM dd");
				String birthday=sdf.format(format.parse(indi.birthday))	;
				System.out.println("ERROR: INDIVIDUAL: "+"US01: "+indi.id+":"+" Birthday "+birthday+" occurs in the future");
			}
		}
		
		if(indi.death!=null){
			String death=indi.death.toString();	
			if(format.parse(death).after(format.parse(s))){
				SimpleDateFormat format1= new SimpleDateFormat("yyyy MM dd",Locale.CHINA);
				String deathDate=format1.format(format.parse(indi.death));
				System.out.println("ERROR: INDIVIDUAL: "+"US01: "+indi.id+":"+" Deathdate"+deathDate+" occurs in the future");
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
				System.out.println("ERROR: FAMILY: "+"US04: "+fam.id+" divorceDate "+divorce+" is before "+"marriageDate "+marry);
				
			}
		}
		}
		
	}catch(Exception e){
		e.printStackTrace();
	}
	return;
	}	
	
	public static void birthBeforeDeathOfParents(List<Family> family_list, List<Individual> indis_List) throws ParseException{
		String deathDateOfFather=null;
		String deathDateOfMother=null;
		String birthDateOfChild = null;
		String husbandID;
		String wifeID;
		String childID=null;
		SimpleDateFormat format=new SimpleDateFormat("d MMM yyyy",Locale.ENGLISH);
		SimpleDateFormat format1=new SimpleDateFormat("d MM yyyy",Locale.ENGLISH);
		
		String[] childTime;
		String[] fatherTime;
		String[] motherTime;
		
		for(Family fam:family_list){
			if(fam.wife_id!=null&&fam.husband_id!=null&&fam.child_ids!=null){
				husbandID=fam.husband_id;
				wifeID=fam.wife_id;
				for(String c:fam.child_ids){	
					childID=c;
					for(Individual indi:indis_List){
						if(husbandID.equals(indi.id)){
							if(indi.death!=null)
							deathDateOfFather=format1.format(format.parse(indi.death));
						}else if(indi.id.equals(wifeID)){
							if(indi.death!=null)
							deathDateOfMother=format1.format(format.parse(indi.death));
							
						}else if(indi.id.equals(childID)){
							if(indi.birthday!=null)
							birthDateOfChild=format1.format(format.parse(indi.birthday));
						}
					}
					 childTime=birthDateOfChild.split(" ");
					 if(deathDateOfFather!=null){
					 fatherTime=deathDateOfFather.split(" ");
	
					 if(((Integer.parseInt(fatherTime[1])+(Integer.parseInt(fatherTime[2])*12))-(Integer.parseInt(childTime[1]+(Integer.parseInt(childTime[2])*12))))<9){
						 System.out.println("ERROR: INDIVIDUAL: US09: "+ c + " Children birthday: "+birthDateOfChild+" is after father's deathdate: "+ deathDateOfFather);
					 }
					 }
					 if(deathDateOfMother!=null){
					 motherTime=deathDateOfMother.split(" ");
					 if(((Integer.parseInt(motherTime[1])+(Integer.parseInt(motherTime[2])*12))-(Integer.parseInt(childTime[1]+(Integer.parseInt(childTime[2])*12))))<9){
						 System.out.println("ERROR: INDIVIDUAL: US09: "+ c + " Children birthday: "+birthDateOfChild+" is after mother's deathdate: "+ deathDateOfMother);
					 }	
					 }	
					
					}
				}
		}
		
		
			
		}
		
	public static void siblingsSpace(List<Family> family_list, List<Individual> indis_List) throws ParseException{
		String firstChildID;
		String secondChildID;
		String firstChildBirth=null;
		String secondChildBirth=null;
		String [] firstDate=null;
		String [] secondDate=null;
		SimpleDateFormat format=new SimpleDateFormat("d MMM yyyy",Locale.ENGLISH);
		SimpleDateFormat format1=new SimpleDateFormat("d MM yyyy",Locale.ENGLISH);
		
		for(Family fami:family_list){
			int i=0;
			if(fami.child_ids!=null&&fami.child_ids.size()>=2){
			for(i=0;i<fami.child_ids.size();i++){
				firstChildID=fami.child_ids.get(i);
			
				for(int j=i+1;j<fami.child_ids.size();j++){
					secondChildID=fami.child_ids.get(j);
					
					for(Individual indi:indis_List){
						
						if(indi.id.equals(firstChildID)){
							if(indi.birthday!=null)
							firstChildBirth=indi.birthday;
							firstChildBirth=format1.format(format.parse(firstChildBirth));
							firstDate=firstChildBirth.split(" ");
							
						}
						if(indi.id.equals(secondChildID)){
							if(indi.birthday!=null)
							secondChildBirth=indi.birthday;
							secondChildBirth=format1.format(format.parse(secondChildBirth));
							secondDate=secondChildBirth.split(" ");
						}
					}
					if(secondDate!=null&&firstDate!=null){
					if(Integer.parseInt(secondDate[2])==Integer.parseInt(firstDate[2])){
						if((Integer.parseInt(secondDate[1])-Integer.parseInt(firstDate[1]))<8||(Integer.parseInt(firstDate[1])-Integer.parseInt(secondDate[1])<8)){
							System.out.println("ERROR: INDIVIDUAL: US13: " + firstChildID+" and "+secondChildID+" is sibling spacing");
						}else if((Integer.parseInt(secondDate[0])-Integer.parseInt(firstDate[0]))>1||(Integer.parseInt(firstDate[0])-Integer.parseInt(secondDate[0])>1)){
							System.out.println("ERROR: INDIVIDUAL: US13: " + firstChildID+" and "+secondChildID+" is sibling spacing");
						}
						
					}
				}
				}
				
			}
		}
			
		
	
	
	
	
	
	}
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
}
