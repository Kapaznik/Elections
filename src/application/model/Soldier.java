package application.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Soldier extends Citizen implements Serializable {
	protected boolean isCaryingWeapon;

	protected Soldier(String name, String id, int year, Ballotbox<Soldier> ballotbox, boolean isIsolated, boolean isProtected,
			boolean isCaryingWeapon) {
		super(name, id, year, ballotbox, isIsolated, isProtected);
		this.isCaryingWeapon = isCaryingWeapon;

	}

	// comparing two candidates' rank in order to sort them.

	@Override
	// showing soldier as a citizen
	public String toString() {
		return super.toString();
	}

	// showing candidate as a candidate

//	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Citizen))
			return false;
		if (!(super.equals(obj)))
			return false;
		Citizen other = (Citizen) obj;
		return other.getId().equals(getId()) ;
	}
}
