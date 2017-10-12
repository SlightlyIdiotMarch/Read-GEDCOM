


public class Utils {
	public static String getFillString(String input,int length){
		return getFillString(input, length, " ");
	}
	public static String getFillString(String input,int length,String fill_char){
		if(input==null)
			input = "";
		if(input.length()>=length)
			return input;
		if(fill_char==null||fill_char.length()==0){
			fill_char = " ";
		}
		
		StringBuffer sb = new StringBuffer(input.trim());
		boolean isAddAtRight = true;
		while(sb.length()<length){
			if(isAddAtRight){
				isAddAtRight = false;
				sb.append(fill_char);
			}else{
				isAddAtRight = true;
				sb.insert(0, fill_char);
			}
		}
		return sb.toString();
	}
}
