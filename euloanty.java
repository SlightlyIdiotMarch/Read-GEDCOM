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

import classForUserStory.US06Info;

public class euloanty
{
	private static String convertgc(java.util.GregorianCalendar g)
	{
		return (1+g.get(Calendar.MONTH))+"/"+g.get(Calendar.DAY_OF_MONTH)+"/"+g.get(Calendar.YEAR);
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
	public static void birth_before_marriage_of_parents(List<Individual> individuals) throws java.text.ParseException
	{
		
	}

	//US12	Parents not too old
	//Mother should be less than 60 years older than her children and father should be less than 80 years older than his children
	public static void parents_not_too_old(List<Individual> individuals) throws java.text.ParseException
	{
		
	}

	//US16	Male last names
	//All male members of a family should have the same last name
	public static void male_last_names(List<Individual> individuals) throws java.text.ParseException
	{
		
	}

}
