package application.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Citizen implements Serializable {
	private String name;
	private String id;
	private int year;
	private boolean isIsolated;
	private boolean isProtected;
	@SuppressWarnings("rawtypes")
	protected Ballotbox ballotbox;
	

	public Citizen(String name, String id, int year, @SuppressWarnings("rawtypes") Ballotbox ballotbox, boolean isIsolated, boolean isProtected) {
		this.name = name;
		this.id = id;
		this.year = year;
		this.isIsolated = isIsolated;
		this.isProtected = isProtected;
		this.ballotbox = ballotbox;


	}
	
	public String getName() {
		return name;
	}

	public int getYearOfBirth() {
		return year;
	}

	public int getYear() {
		return year;
	}

	@SuppressWarnings("rawtypes")
	public Ballotbox getBallotbox() {
		return ballotbox;
	}

	public String getId() {
		return id;
	}

	public boolean getIsIsloated() {
		return isIsolated;
	}

	public boolean getIsProtected() {
		return isProtected;
	}
//	public boolean getIsCarryingWeapon() {
//		if (isCaryingWeapon == true)
//			return true;
//		return false;
//	}
//	public void carryinqWeapon() {
//		if (isCaryingWeapon== true)
//			System.out.println("soldier is carrying a weapon");
//			
//	}
	public void setBallotBox(@SuppressWarnings("rawtypes") Ballotbox ballotBox) {
		this.ballotbox = ballotBox;
	}

	@Override
	// showing citizens' details. ballot box is null if citizen can't vote or case 8 hasn't been chosen yet. showing serial number if has.
	public String toString() {
		String isolatedHebrew="";
		String protectedHebrew="";
		if (isIsolated == true){
			isolatedHebrew = "כן";
		}
		else {
			isolatedHebrew = "לא";
		}
		if (isProtected == true) {
			protectedHebrew = "כן";
		}
		else {
			protectedHebrew = "לא";
		}
		if (ballotbox != null)
			return "שם: " + name + "\t" + "תז: " + id + "\t" + "שנת לידה: " + year + "\t" + "מספר סידורי של הקלפי: "
					+ ballotbox.getSerialNumber() + "\t" + "האם בבידוד: " + isolatedHebrew + "\t" + "האם ממוגן: "
					+ protectedHebrew + "\t";
		else
			return "שם: " + name + "\t" + "תז: " + id + "\t" + "שנת לידה: " + year + "\t"
					+ "מספר סידורי של הקלפי: לא קיים " + "\t" + "האם בבידוד: " + isolatedHebrew + "\t" + "האם ממוגן: "
					+ protectedHebrew + "\t";

		
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Citizen))
			return false;
		Citizen other = (Citizen) obj;
		return other.getId().equals(getId());
	}
	
}
