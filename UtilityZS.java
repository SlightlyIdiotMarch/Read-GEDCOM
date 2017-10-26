import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UtilityZS {
	public static ArrayList<Family> getFamiliesByIndiId(Individual individual, List<Family> families) {
		ArrayList<Family> result = new ArrayList<Family>();
		if (individual.fSpouse != null) {
			for (String fams: individual.fSpouse) {
				for (Family family: families) {
					if (fams.equals(family.id)) {
						result.add(family);
						break;
					}
				}
			}
		}
		return result;
	}
	
	//convert string to date
		public static Date processDate(String date) throws ParseException {
			String result = "";
			for (String format: date.split(" ")) {
				if (format.length() == 1) {
					format = "0" + format;
				}
				result += format + "-";
			}
			result = result.substring(0, result.length() - 1);
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
			Date resultdate = dateFormat.parse(result);
			return resultdate;
		}
		
		public static java.util.GregorianCalendar ConvertDateStringToGregorianCalendar(String date) throws ParseException {
			java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
			calendar.setTime(processDate(date));
			return calendar;
		}
		
		
		// compute age
		public static int getAge(Date dateOfBirth) {
		    Calendar today = Calendar.getInstance();
		    Calendar birthDate = Calendar.getInstance();
		    birthDate.setTime(dateOfBirth);
		    int todayYear = today.get(Calendar.YEAR);
		    int birthDateYear = birthDate.get(Calendar.YEAR);
		    int todayDayOfYear = today.get(Calendar.DAY_OF_YEAR);
		    int birthDateDayOfYear = birthDate.get(Calendar.DAY_OF_YEAR);
		    int todayMonth = today.get(Calendar.MONTH);
		    int birthDateMonth = birthDate.get(Calendar.MONTH);
		    int todayDayOfMonth = today.get(Calendar.DAY_OF_MONTH);
		    int birthDateDayOfMonth = birthDate.get(Calendar.DAY_OF_MONTH);
		    int age = todayYear - birthDateYear;

		    // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
		    if ((birthDateDayOfYear - todayDayOfYear > 3) || (birthDateMonth > todayMonth)){
		        age--;
		    
		    // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
		    } else if ((birthDateMonth == todayMonth) && (birthDateDayOfMonth > todayDayOfMonth)){
		        age--;
		    }
		    return age;
		}
		
}

