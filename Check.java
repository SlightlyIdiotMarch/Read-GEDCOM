import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
				System.out.println("ERROR: FAMILY: "+"US04:"+fam.id+":"+"divorceDate"+divorce+" is after "+"US04:"+fam.id+":"+"marriageDate"+marry);
				
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
		//Date date=new Date();
		
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
	
					 if(((Integer.parseInt(fatherTime[1])+(Integer.parseInt(fatherTime[2])*12))-(Integer.parseInt(childTime[1])+(Integer.parseInt(childTime[2])*12)))<9){
						 System.out.println("ERROR: INDIVIDUAL: US09"+" Children birthday:"+birthDateOfChild+" is after father's deathdate"+ deathDateOfFather);
					 }
					 }
					 if(deathDateOfMother!=null){
					 motherTime=deathDateOfMother.split(" ");
					 if(((Integer.parseInt(motherTime[1])+(Integer.parseInt(motherTime[2])*12))-(Integer.parseInt(childTime[1])+(Integer.parseInt(childTime[2])*12)))<9){
						 System.out.println("ERROR: INDIVIDUAL: US09"+" Children birthday:"+birthDateOfChild+" is after mother's deathdate"+ deathDateOfMother);
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
							System.out.println("ERROR: INDIVIDUAL: US13: "+"ID:"+firstChildID+" and ID: "+secondChildID+" is sibling spacing");
						}else if((Integer.parseInt(secondDate[0])-Integer.parseInt(firstDate[0]))>1||(Integer.parseInt(firstDate[0])-Integer.parseInt(secondDate[0])>1)){
							System.out.println("ERROR: INDIVIDUAL: US13: "+"ID:"+firstChildID+" and ID: "+secondChildID+" is sibling spacing");
						}
						
					}
				}
				}
				
			}
		}
			
		
	
	
	
	
	
	}
		
		
		
		
	}
	
	
	//US17 Parents should not marry any of their descendants
	/*public static void noMarriageToDescendant(List<Family> family_list){
		
		for(Family fami:family_list){
			if(fami.child_ids!=null){
				for(int i=0;i<fami.child_ids.size();i++){
					if(fami.child_ids.get(i).equals(fami.husband_id)){
						System.out.println("ERROR: FAMILY: US17: Child "+ fami.child_ids.get(i)+" should not marry with mother "+fami.wife_id);
					}
					if(fami.child_ids.get(i).equals(fami.wife_id)){
						System.out.println("ERROR: Child: US17: "+ fami.child_ids.get(i)+" should not marry with father "+fami.husband_id);
					}
				}
			}
		}		
	}*/
	
	
	//US21 Husband in family should be male and wife in family should be female
	public static void correctGenderForRole(List<Family> family_list,List<Individual> individual_list){
		//List<String> duplicateID=new ArrayList<String>();
		HashSet<String> duplicateIDtest=new HashSet<String>();
		for(Family fami:family_list){
			for(Individual indi:individual_list){
				if(fami.husband_id.equals(indi.id)){
					if(indi.sex.equals("F")){
						if(duplicateIDtest.isEmpty()){
							System.out.println("ERROR: INDIVIDUAL: US21: individual(husband)"+indi.id+" should be male");
							duplicateIDtest.add(indi.id);
						}else{
							if(!duplicateIDtest.contains(indi.id)){
							System.out.println("ERROR: INDIVIDUAL: US21: individual(husband)"+indi.id+" should be male");
							duplicateIDtest.add(indi.id);
							}	
						}
					}
				}
				
				if(fami.wife_id.equals(indi.id)){
					if(indi.sex.equals("M")){
						if(duplicateIDtest.isEmpty()){
							System.out.println("ERROR: INDIVIDUAL: US21: individual(wife)"+indi.id+" should be female");
							duplicateIDtest.add(indi.id);
						}else{
							if(!duplicateIDtest.contains(indi.id)){
								System.out.println("ERROR: INDIVIDUAL: US21: individual(wife)"+indi.id+" should be female");
								duplicateIDtest.add(indi.id);
							}
						}	
					}
				}
			
			}
		
		}
	}
	
	/*public static void  noMarriageToDescendant(List<Family> family_list,List<Individual> individual_list){
		HashSet<String> duplicateID=new HashSet<String>();
		for(Family family:family_list){
			for(Individual individual:individual_list){
				if(family.husband_id.equals(individual.id)){
					for(Family searchOriginalFamily:family_list){
					    if(individual.fChild!=null){
					    	if(individual.fChild.equals(searchOriginalFamily.id)&&individual.id.equals(searchOriginalFamily.husband_id)){
					    		System.out.println("ERROR: FAMILY: US17: Child "+ individual.id+" should not marry with mother "+searchOriginalFamily.wife_id);
					    	}
					    }else{
					    	if(family.child_ids!=null){
					    		for(int i=0; i<family.child_ids.size();i++){
					    			if(family.husband_id.equals(family.child_ids.get(i))){
					    				if(!duplicateID.contains(family.husband_id)){
					    				System.out.println("ERROR: FAMILY: US17: Child "+ family.child_ids.get(i)+" should not marry with mother "+family.wife_id);
					    				duplicateID.add(family.husband_id);
					    				}
					    			}
					    		}
					    	}
					    }
					}
				}
			}
		}
		
		
	}*/
	
	//US17 Parents should not marry any of their descendants
	public static void  noMarriageToDescendant(List<Family> family_list){
		
		HashSet<String> husbandDuplicateID= new HashSet<String>();
		for(Family family:family_list){
			HashSet<String> wife=new HashSet<String>();
			for(Family findAllWife:family_list){
				if(family.husband_id.equals(findAllWife.husband_id)){
					if(!wife.contains(findAllWife.wife_id)){
						wife.add(findAllWife.wife_id);
					}
				}
			}
			
			for(Family findErrorChildren:family_list){
				HashSet<String> childrenDuplicateID=new HashSet<String>();
				if(wife.contains(findErrorChildren.wife_id)){
					if(findErrorChildren.child_ids!=null){
						for(int i=0;findErrorChildren.child_ids.size()>i;i++){
							if(family.husband_id.equals(findErrorChildren.child_ids.get(i))){
								if(!husbandDuplicateID.contains(family.husband_id)){
									husbandDuplicateID.add(family.husband_id);
									if(husbandDuplicateID.contains(family.husband_id)&&!childrenDuplicateID.contains(findErrorChildren.child_ids.get(i))){
										System.out.println("ERROR: FAMILY: US17: Child "+ family.husband_id+" should not marry with mother "+findErrorChildren.wife_id);
										childrenDuplicateID.add(findErrorChildren.child_ids.get(i));
									}
								}
							}
						}
					}
				}
			}
		}
		
		
		
		HashSet<String> wifeDuplicateID=new HashSet<String>();
		for(Family family:family_list){
			HashSet<String> husband=new HashSet<String>();
			for(Family findAllHusband:family_list){
				if(family.wife_id.equals(findAllHusband.wife_id)){
					if(!husband.contains(findAllHusband.husband_id)){
						husband.add(findAllHusband.husband_id);
					}
				}
			}
			
			for(Family findErrorChildren:family_list){
				HashSet<String> childrenDuplicateID=new HashSet<String>();
				if(husband.contains(findErrorChildren.husband_id)){
					if(findErrorChildren.child_ids!=null){
						for(int i=0;findErrorChildren.child_ids.size()>i;i++){
							if(family.wife_id.equals(findErrorChildren.child_ids.get(i))){
								if(!wifeDuplicateID.contains(family.wife_id)){
									wifeDuplicateID.add(family.wife_id);
									if(wifeDuplicateID.contains(family.wife_id)&&!childrenDuplicateID.contains(findErrorChildren.child_ids.get(i))){
										System.out.println("ERROR: FAMILY: US17: Child "+ family.wife_id+" should not marry with father "+findErrorChildren.husband_id);
										childrenDuplicateID.add(findErrorChildren.child_ids.get(i));
									}
								}
							}
						}
					}
				}
			}
		}
		
			
		
		
		
	}
	
//US25 No more than one child with the same name and birth date should appear in a family
		public static void uniqueFirstNamesInFamilies(List<Family> family_list, List<Individual> individual_list ){
		
 			for(Family findEveryFamily: family_list){
 				HashMap<String,String> childrenID = new HashMap<>();
 				HashMap<String,String> childrenFirstName = new HashMap<>();
 				HashMap<String,String> childrenBirthday = new HashMap<>();
 				HashMap<String,String> duplicate = new HashMap<>();
				if(findEveryFamily.child_ids != null){
					for(String findChildren:findEveryFamily.child_ids){
						childrenID.put(findChildren,findEveryFamily.id );
						//System.out.println(findChildren+" "+findEveryFamily.id);
						for(Individual findID:individual_list){
							if(childrenID.containsKey(findID.id)){
								String [] firstName = findID.name.split(" ");
								String birthday = findID.birthday;
								childrenBirthday.put(findID.id,birthday);
								childrenFirstName.put(findID.id,firstName[0]);
								//System.out.println(findID.id+" "+firstName[0]);
								//System.out.println(findID.id+" "+birthday);
							}
						}
						
						for(String check:childrenBirthday.keySet()){
							for(String check1:childrenBirthday.keySet()){
								if((check != check1)){
									if(childrenBirthday.get(check).equals(childrenBirthday.get(check1))){
										if(childrenFirstName.get(check).equals(childrenFirstName.get(check1))){
											if(childrenID.get(check).equals(childrenID.get(check1))){
												if(check1 == duplicate.get(check) || check == duplicate.get(check1)){
													continue;
												}else{
												duplicate.put(check, check1);
												duplicate.put(check1, check);
												System.out.println("ERROR: INDIVIDUAL: US25: "+check+ " and " +check1 +" has the same FirstName and birthday in family "+ childrenID.get(check));
												}
											}
										}
									}
								}
							}
						}
						
						
						
					}
				}
			}
			
			
		
			
		}	
		
//US29 List deceased marriage
		public static void listDeceased(List<Family> family_list, List<Individual> individual_list){
			for(Family family: family_list){
				for(Individual individual: individual_list){
					if(family.divorce_date == null){
						if(family.husband_id.equals(individual.id)){
							if(individual.death!=null){
								System.out.println("ERROR: INDIVIDUAL: US29: "+ "husband"+family.husband_id+" has died");
							}
						}
						if(family.wife_id.equals(individual.id)) {
							if(individual.death != null){
								System.out.println( "ERROR: INDIVIDUAL: US29: "+ "wife"+family.wife_id+" has died");
							}
						}
					}
				}
			}
			
			
			
					
		}
		
		
		
		
}


		
