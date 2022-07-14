package application.listeners;

import java.io.IOException;
import java.util.Vector;

import application.model.Ballotbox;
import application.model.Citizen;
import application.model.Model;
import application.model.Party;
import application.model.Ballotbox.eType;

public interface ViewListeners {

	void addBallotBoxToUI(eType type, String adress);

	String ShowAllCitizensToUI();

	String showAllBallotboxesToUI();

	String showAllPartiesToUI();

	String showElectionResultToUI();

	boolean addBallotBox(eType eType, String string); // WORKING

	boolean addParty(String name, Party.eSection choice, int birthYear, int birthMonth) throws Exception;

	boolean addCitizen(String name, String id, int year, boolean isIsolated, boolean isProtected,
			boolean isCarryingWeapon, int daysOfSickness) throws Exception;

	boolean addCandidate(String name, String id, int year, boolean isIsolated, boolean isProtected,
			boolean isCarryingWeapon, int daysOfSickness, int partyIndex, int rank) throws Exception;

	Vector<String> getPartiesNames();

	Vector<Citizen> getAllVoters();

	boolean checkIfIDisValidToUI(String ID);

	boolean isSoldier(int birthYear);

	void joptionMessage(String msg);

	public boolean addAVote(@SuppressWarnings("rawtypes") Ballotbox ballotbox, int partyIndex);

	public String getElectionResult();

	Model loadFile() throws ClassNotFoundException, IOException, Exception;

	void saveFile() throws IOException;

	void addHardCoded() throws Exception;

	public boolean checkIfWereElections();

	public String getDate();
	
	public void resetElections() throws Exception;
	
}
