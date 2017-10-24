package classForUserStory;

public class US06Info {
	private String divorce = null;
	private String deathOfHusb = null;
	private String deathOfWife = null;
	private String Husband_id = null;
	private String Wife_id = null;
	
	public void setDivorce(String divorceDate) {
		divorce = divorceDate;
	}
	
	public String getDivorce() {
		return divorce;
	}
	
	public void setDeathOfHusb(String deathDate) {
		deathOfHusb = deathDate;
	}
	
	public String getDeathOfHusb() {
		return deathOfHusb;
	}
	
	public void setDeathOfWife(String deathDate) {
		deathOfWife = deathDate;
	}
	
	public String getDeathOfWife() {
		return deathOfWife;
	}
	
	public void setHusbId(String id) {
		Husband_id = id;
	}
	
	public String getHusbId() {
		return Husband_id;
	}
	
	public void setWifeId(String id) {
		Wife_id = id;
	}
	
	public String getWifeId() {
		return Wife_id;
	}
}
