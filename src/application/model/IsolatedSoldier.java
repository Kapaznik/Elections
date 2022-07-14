package application.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class IsolatedSoldier extends Soldier implements Serializable {
	protected boolean isCaryingWeapon;
	protected int daysOfCandidateSickness;
	@SuppressWarnings("unchecked")
	protected IsolatedSoldier(String name, String id, int year, @SuppressWarnings("rawtypes") Ballotbox ballotbox, boolean isIsolated, boolean isProtected,  int daysOfCandidateSickness, boolean isCaryingWeapon)  {
		super(name, id, year, ballotbox, isIsolated, isProtected, isCaryingWeapon);
		this.daysOfCandidateSickness = daysOfCandidateSickness;
	}
	
	@Override
	// showing soldier as a citizen
	public String toString() {
		return super.toString();
	}
}
