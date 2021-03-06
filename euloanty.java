/******************************************************************************

The MIT License (MIT)

Copyright (c) 2017 cqwrteur

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

******************************************************************************/

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;


public class euloanty
{
	private static String convertgc(java.util.GregorianCalendar g)
	{
		return (1+g.get(Calendar.MONTH))+"/"+g.get(Calendar.DAY_OF_MONTH)+"/"+g.get(Calendar.YEAR);
	}
	
	private static String getlastname(String s)
	{
		String[] strs = s.split("/");
		return strs[1];
	}
	
	//US07	Less then 150 years old
	//Death should be less than 150 years after birth for dead people, and current date should be less than 150 years after birth for all living people
	public static void less_than_150_years_old(List<Individual> individuals) throws java.text.ParseException
	{
		for (Individual individual: individuals)
		{
			if(individual.birthday!=null)
			{
				java.util.GregorianCalendar birthdate = UtilityZS.ConvertDateStringToGregorianCalendar(individual.birthday);
				if(individual.death!=null)
				{
					java.util.GregorianCalendar deathdate = UtilityZS.ConvertDateStringToGregorianCalendar(individual.death);
					if (birthdate.get(Calendar.YEAR)+150<deathdate.get(Calendar.YEAR))
						System.out.println("ERROR: INDIVIDUAL: US07: " + individual.id + " Death date (" + individual.death + ") is higher than 150 years after birth ("+individual.birthday+")");					
				}
				else
				{
					java.util.GregorianCalendar today = new java.util.GregorianCalendar();
					if (birthdate.get(Calendar.YEAR)+150<today.get(Calendar.YEAR))
						System.out.println("ERROR: INDIVIDUAL: US07: " + individual.id + " Current date (" + convertgc(today)+") is higher than 150 years after birth ("+individual.birthday+")");
				}
			}
		}
	}

	//US08	Birth before marriage of parents
	//Children should be born after marriage of parents (and not more than 9 months after their divorce)
	public static void birth_before_marriage_of_parents(List<Family> families, List<Individual> individuals) throws java.text.ParseException
	{
		for (Family family: families)
		{
			if(family.child_ids!=null)
				for (String child : family.child_ids)
				{
					for (Individual individual : individuals)
					{
						if (individual.id.equals(child))
						{
							java.util.GregorianCalendar birthdate = UtilityZS.ConvertDateStringToGregorianCalendar(individual.birthday);
							if(family.marriage_date!=null)
							{
								java.util.GregorianCalendar marriagedate = UtilityZS.ConvertDateStringToGregorianCalendar(family.marriage_date);
								if (birthdate.before(marriagedate))
									System.out.println("ERROR: FAMILY: US08: " + family.id + " Child (" + child + ") is born ("+individual.birthday+") before marriage of parents("+family.marriage_date+")");
							}
							if (family.divorce_date!=null)
							{
								java.util.GregorianCalendar divorcedate = UtilityZS.ConvertDateStringToGregorianCalendar(family.divorce_date);
								if (divorcedate.getTimeInMillis()+3600*1000*24*30*8<birthdate.getTimeInMillis())
									System.out.println("ERROR: FAMILY: US08: " + family.id + " Child (" + child + ") is born ("+individual.birthday+") after 8 month of divorce of parents("+family.divorce_date+")");
							}

						}
					}
				}
		}
	}

	//US12	Parents not too old
	//Mother should be less than 60 years older than her children and father should be less than 80 years older than his children
	public static void parents_not_too_old(List<Family> families, List<Individual> individuals) throws java.text.ParseException
	{
		for (Family family: families)
		{
			Individual father=null;
			if(family.husband_id!=null)
				for (Individual individual : individuals)
						if (individual.id.equals(family.husband_id))
							father = individual;
			Individual mother=null;
			if(family.wife_id!=null)
				for (Individual individual : individuals)
						if (individual.id.equals(family.wife_id))
							mother = individual;
			if(family.child_ids!=null)
				for (String child : family.child_ids)
				{
					for (Individual individual : individuals)
					{
						if (individual.id.equals(child)&&individual.birthday!=null)
						{

							java.util.GregorianCalendar birthdate = UtilityZS.ConvertDateStringToGregorianCalendar(individual.birthday);
							if (father!=null&&father.birthday!=null)
							{
								java.util.GregorianCalendar fb = UtilityZS.ConvertDateStringToGregorianCalendar(father.birthday);
								if (fb.get(Calendar.YEAR)+80<birthdate.get(Calendar.YEAR))
									System.out.println("ERROR: FAMILY: US12: " + family.id + " Father (" + father.birthday + ") is over 80 years higher than child ("+individual.birthday+")");
							}
							if (mother!=null&&mother.birthday!=null)
							{
								java.util.GregorianCalendar mb = UtilityZS.ConvertDateStringToGregorianCalendar(mother.birthday);
								if (mb.get(Calendar.YEAR)+60<birthdate.get(Calendar.YEAR))
									System.out.println("ERROR: FAMILY: US12: " + family.id + " Mother (" + mother.birthday + ") is over 60 years higher than child ("+individual.birthday+")");
							}
						}
					}
				}
		}
	}

	//US16	Male last names
	//All male members of a family should have the same last name
	public static void male_last_names(List<Family> families, List<Individual> individuals) throws java.text.ParseException
	{
		for (Family family: families)
		{
			Individual father=null;
			if(family.husband_id!=null)
				for (Individual individual : individuals)
						if (individual.id.equals(family.husband_id))
							father = individual;
			
			if(father==null||!father.sex.equals("M")||father.name==null)
				continue;
			String father_lastname = getlastname(father.name);
			if(family.child_ids!=null)
				for (String child : family.child_ids)
				{
					for (Individual individual : individuals)
					{
						if (individual.id.equals(child))
						{
							if(individual.sex.equals("M"))
							{
								if(individual.name!=null)
								{
									String s = getlastname(individual.name);
									if(!father_lastname.equals(s))
										System.out.println("ERROR: FAMILY: US16: " + family.id + " Male member of a family does not have same last name (" + individual.name + "), father is "+father.name);
								}
							}
						}
					}
				}
		}
	}
	private static Individual get_individual(List<Individual> individuals,String s) throws java.text.ParseException
	{
		for(Individual individual : individuals)
			if(individual.id.equals(s))
				return individual;
		return null;
	}

	//US20	Aunts and uncles
	//Aunts and uncles should not marry their nieces or nephews
	public static void aunts_and_uncles(List<Family> families, List<Individual> individuals) throws java.text.ParseException
	{
		for (Family family: families)
		{
			if(family.child_ids==null)
				continue;
			for (String child1 : family.child_ids)
			{
				if(child1==null)
					continue;
				for (String child2 : family.child_ids)
					if(child1!=child2)
					{
						for (Family cofc1 : families)
							if(cofc1!=null)
							{
								if(cofc1.husband_id.equals(child2)||cofc1.wife_id.equals(child2))
								{

									if(cofc1.child_ids==null)
										continue;
									for(String np : cofc1.child_ids)
									{
										if(np!=null)
										{
											for(Family fm2 : families)
												if((fm2.husband_id.equals(child1)&&fm2.wife_id.equals(np))||(fm2.wife_id.equals(child1)&&fm2.husband_id.equals(np)))
												{
													System.out.println("ERROR: FAMILY: US20: " + family.id + " Aunts or uncles ("+child1+") marry their nieces or nephews("+np+")");
												}
										}
									}
								}
							}
					}
			}
		}
	}

	//US24	Unique families by spouses
	//No more than one family with the same name and birth date should appear in a family
	public static void unique_families_by_spouses(List<Family> families, List<Individual> individuals) throws java.text.ParseException
	{
		for (int i=0;i!=families.size();++i)
		{
			Family f1=families.get(i);
			Individual h=get_individual(individuals, f1.husband_id);
			Individual w=get_individual(individuals, f1.wife_id);
			for(int j=i+1;j<families.size();++j)
			{
					Family f2=families.get(j);
					if(!f1.marriage_date.equals(f2.marriage_date))
						continue;
					Individual h1=get_individual(individuals, f2.husband_id);
					Individual w1=get_individual(individuals, f2.wife_id);
					if(h.name.equals(h1.name)&&w.name.equals(w1.name))
					{
						System.out.println("ERROR: FAMILY: US24: ("+f1.id+") ("+f2.id+") has the same name and marriage date");
					}
			}
		}
	}
		
	//US28 Order siblings by age
	//List siblings in families by decreasing age, i.e. oldest siblings first
	public static void order_siblings_by_age(List<Family> families, List<Individual> individuals) throws java.text.ParseException
	{
		for (Family family: families)
		{
			if(family.child_ids==null)
				continue;
			if(family.child_ids.size()<2)
				continue;
			java.util.ArrayList<Individual> ar=new java.util.ArrayList<Individual>();
			for (String child : family.child_ids)
			{
				Individual iv=get_individual(individuals, child);
				if(iv!=null)
					ar.add(iv);
			}
			java.util.Collections.sort(ar,new java.util.Comparator<Individual>()
			{
				@Override
				public int compare(Individual a,Individual b)
				{
					try
					{
						java.util.GregorianCalendar afb = UtilityZS.ConvertDateStringToGregorianCalendar(a.birthday);
						java.util.GregorianCalendar bfb = UtilityZS.ConvertDateStringToGregorianCalendar(b.birthday);
						return afb.compareTo(bfb);
					}
					catch(java.text.ParseException e)
					{
						return -2;
					}
				}
			});
			System.out.println("US28: FAMILY "+family.id+": Order siblings by age:");
			for(Individual idv:ar)
				System.out.print(idv.id+"\tname:"+idv.name+"\t"+idv.birthday+"\n");
			System.out.println();
		}
	}
		
	//US32 List multiple births
	//List all multiple births in a GEDCOM file
	public static void list_multiple_births(List<Family> families, List<Individual> individuals) throws java.text.ParseException
	{
		for (Family family: families)
		{
			if(family.child_ids==null)
				continue;
			if(family.child_ids.size()<2)
				continue;
			java.util.ArrayList<String> child_ids = family.child_ids;
			for (int i=0;i!=child_ids.size();++i)
			{
				Individual c1=get_individual(individuals, child_ids.get(i));
				if(c1==null)
					continue;
				for (int j=i+1;j<child_ids.size();++j)
				{
					Individual c2=get_individual(individuals, child_ids.get(j));
					if(c2==null)
						continue;
					if(c1.birthday.equals(c2.birthday))						
						System.out.println("US32: FAMILY "+family.id+": have multiple births ("+c1.id+":"+c1.birthday+") & ("+c2.id+":"+c2.birthday+")");
				}
			}
		}
	}
}
