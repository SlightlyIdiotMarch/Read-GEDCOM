
public class Check3 {
	public void checkReplace(String content){
	 String[] k=new String[17]; 
	 k[0]="INDI";
	 k[1]="NAME";
	 k[2]="SEX";
	 k[3]="BIRT";
	 k[4]="DEAT";
	 k[5]="FAMC";
	 k[6]="FAMS";
	 k[7]="FAM";
	 k[8]="MARR";
	 k[9]="HUSB";
	 k[10]="WIFE";
	 k[11]="CHIL";
	 k[12]="DIV";
	 k[13]="DATE";
	 k[14]="HEAD";
	 k[15]="TRLR";
	 k[16]="NOTE";
	 String content_1=content.trim();	
	 if(content_1.length()>0){
		 System.out.println("--> "+content);
		 
		 String[] strs = content_1.split(" ");
		 if(strs.length>=2){
			 if(strs[strs.length-1].equals("INDI")){
				 content_1 = content_1.replace(k[0], "");
				 content_1 = content_1.replaceFirst(" ", "|INDI|Y|");
				 
				 System.out.println("<-- "+content_1);
			 }else if(strs[strs.length-1].equals("FAM")){
				 content_1 = content_1.replace(k[7], "");
				 content_1 = content_1.replaceFirst(" ", "|FAM|Y|");
				 System.out.println("<-- "+content_1);
			 }else{
				 for(int i=0;i<k.length;i++){
			 		 
					 if(strs[1].equals(k[i])){
						 content_1 = content_1.replaceFirst(" ", "|");
						 if(content_1.contains(" "))
							 content_1 = content_1.replaceFirst(" ", "|Y|");
						 else
							 content_1 += "|Y|";
						 System.out.println("<-- "+content_1);
						 return;
					 }
			 	}
				 content_1 = content_1.replaceFirst(" ", "|");
				 if(content_1.contains(" "))
					 content_1 = content_1.replaceFirst(" ", "|N|");
				 else
					 content_1 += "|N|";
				 System.out.println("<-- "+content_1);
			 }
		 }
		
	 }
	 
	 	 
	}
}
