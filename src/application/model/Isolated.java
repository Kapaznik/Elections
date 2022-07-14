package application.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Isolated extends Citizen implements Serializable {
	protected int daysOfCandidateSickness;
	protected Isolated(String name, String id, int year, Ballotbox<Isolated> ballotbox, boolean isIsolated, boolean isProtected,  int daysOfCandidateSickness)  {
		super(name, id, year, ballotbox, isIsolated, isProtected);
		this.daysOfCandidateSickness = daysOfCandidateSickness;
	}
	
	@Override
	// showing soldier as a citizen
	public String toString() {
		return super.toString();
	}
}
