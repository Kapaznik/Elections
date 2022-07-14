package application.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class IsolatedCandidate extends Isolated implements Serializable{
	protected Party partyBelong;
	protected int rankInList;
	protected int daysOfCandidateSickness;

	protected IsolatedCandidate(String name, String id, int year, Ballotbox<Isolated> ballotbox, boolean isIsolated, boolean isProtected,  int daysOfCandidateSickness,	Party partyBelong, int rankInList)  {
		super(name, id, year, ballotbox, isIsolated, isProtected, daysOfCandidateSickness);
		this.partyBelong= partyBelong;
		this.rankInList = rankInList;
	}

	
	public Party getParty() {
		return partyBelong;
	}
	
	public int getRankInList() {
		return rankInList;
	}
	
	public void setPosition(int rankInList) {
		this.rankInList = rankInList;
	}
	// comparing two candidates' rank in order to sort them.
	public int compareTo(Candidate other) {
		if (other!=null) {
		if (rankInList < other.rankInList)
			return -1;
		else if (rankInList > other.rankInList)
			return 1;
		else
			return 0;
		}
		return 0;
	}
	@Override
	// showing candidate as a citizen
	public String toString() {
		return super.toString();
	}
	//showing candidate as a candidate
	public String showCandidate() {
			return "name: " + super.getName() + " Position: " + rankInList;
		}
	
}
