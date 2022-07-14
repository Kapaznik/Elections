package application.model;

import java.io.Serializable;
import java.util.Vector;

@SuppressWarnings("serial")
public class Ballotbox<T extends Citizen> implements Serializable{
	public enum eType {
		רגילה(""), קורונה(""), צבאית(""),  משולבת("צבאית קורונה");

		eType(String string) {
		}
	};

	protected int serialNumber;
	protected Vector<T> voterList = new Vector<T>();
	private eType type;
	protected String adress;
	protected int numberOfActualVoters;
	protected double votersPercent;
	protected int CURRENT_YEAR = 2021;
	protected Vector<Integer> partyVotes;
	protected static int counterRegular = 1;
	protected static int counterCorona = 2;
	protected static int counterMilitary = 3;
	protected static int counterCoronaAndMilitary = 4;

	public Ballotbox(int serialNumber, eType type, String adress) {
		this.serialNumber =serialNumber;
		this.type = type;
		this.adress = adress;
		if (voterList.isEmpty() == false)
			votersPercent = numberOfActualVoters / voterList.size() * 100;

	}

	public eType getType() {
		return type;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public String getAdress() {
		return adress;
	}

	public Vector<T> getVoterList() {
		return voterList;
	}

	public Vector<Integer> getPartyVotes() {
		return partyVotes;
	}

	public void resetNumberOfActualVoters() {
		this.numberOfActualVoters = 0;
	}

	public void resetVoterList() {
		voterList.clear();
	}

	public void addVoter(T currentCitizen) {
		voterList.add(currentCitizen);
	}


	// creating a party in each ballotbox array after knowing the number of parties
	public void initializeVotesArray(int numberOfParties) {
		partyVotes = new Vector<Integer>();
		for (int i = 0; i < numberOfParties; i++)
			partyVotes.add(0);
	}

	public void elect(int index) {
		partyVotes.set(index, partyVotes.get(index)+1);
		numberOfActualVoters++;

	}

	public String toString() {
		return "מספר סידורי של הקלפי: " + serialNumber + ", כתובת הקלפי: " + adress;
	}

	@SuppressWarnings("rawtypes")
	public boolean equals(Object obj) {
		if (!(obj instanceof Ballotbox))
			return false;
		Ballotbox other = (Ballotbox) obj;
		return other.getAdress().equals(getAdress())  && other.getSerialNumber() == getSerialNumber();

	}
}
