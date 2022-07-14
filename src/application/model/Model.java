package application.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Vector;

import application.listeners.ViewListeners;
import application.model.Ballotbox.eType;

@SuppressWarnings("serial")
public class Model implements Serializable {
	LocalDate nowDate = LocalDate.now();
	public final int CURRENT_YEAR;
	public final int CURRENT_MONTH;
	private Vector<Party> parties = new Vector<Party>();
	private Set<Citizen> citizens = new Set<Citizen>();
	private Vector<Ballotbox<Citizen>> regularBallotboxes = new Vector<Ballotbox<Citizen>>();
	private Vector<Ballotbox<Isolated>> coronaBallotboxes = new Vector<Ballotbox<Isolated>>();
	private Vector<Ballotbox<Soldier>> militaryBallotbox = new Vector<Ballotbox<Soldier>>();
	private Vector<Ballotbox<IsolatedSoldier>> coronaAndMilitaryBallotboxes = new Vector<Ballotbox<IsolatedSoldier>>();
	private Vector<Integer> finalVotes = new Vector<Integer>();
	private Vector<ViewListeners> listeners;
	private Vector<Citizen> voters = new Vector<Citizen>();

	public Model() throws Exception {
		CURRENT_YEAR = nowDate.getYear();
		CURRENT_MONTH = nowDate.getMonthValue();
		listeners = new Vector<ViewListeners>();
	}
	
	public void resetElections() {
		parties.clear();
		citizens.clear();
		regularBallotboxes.clear();
		coronaBallotboxes.clear();
		militaryBallotbox.clear();
		coronaAndMilitaryBallotboxes.clear();
		listeners.clear();
		voters.clear();
	}

	public void registerListener(ViewListeners listener) {
		listeners.add(listener);
	}

	public Vector<Party> getParties() {

		return parties;
	}

	public Vector<String> getPartiesNames() {
		Vector<String> partiesNames = new Vector<String>();
		for (int counter = 0; counter < parties.size(); counter++)
			partiesNames.add(parties.get(counter).getName());

		return partiesNames;
	}

	public int getNumberOfParties() {
		return parties.size();
	}

	public Vector<Ballotbox<Citizen>> getRegularBallotBoxes() {
		return regularBallotboxes;
	}

	public Vector<Ballotbox<Isolated>> getCoronaBallotboxes() {
		return coronaBallotboxes;
	}

	public Vector<Ballotbox<Soldier>> getMilitaryBallotbox() {
		return militaryBallotbox;
	}

	public Vector<Ballotbox<IsolatedSoldier>> getCoronaAndMilitaryBallotboxes() {
		return coronaAndMilitaryBallotboxes;
	}

	public Set<Citizen> getCitizens() {
		return citizens;
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	public boolean addBallotBox(eType type, String adress) throws Exception {
		@SuppressWarnings("rawtypes")
		Ballotbox ballotbox;
		if (adress == null)
			throw new Exception("Adress field must be filled");
		if (type == eType.רגילה) {
			if (regularBallotboxes.isEmpty() == true) {
				ballotbox = new Ballotbox<Citizen>(0, Ballotbox.eType.רגילה, adress);
				regularBallotboxes.add(ballotbox);
			} else {
				ballotbox = new Ballotbox<Citizen>(
						regularBallotboxes.get(regularBallotboxes.size() - 1).getSerialNumber() + 4,
						Ballotbox.eType.רגילה, adress);
				regularBallotboxes.add(ballotbox);
			}
			return true;
		}
		if (type == type.קורונה) {
			if (coronaBallotboxes.isEmpty() == true) {
				ballotbox = new Ballotbox<Isolated>(1, Ballotbox.eType.קורונה, adress);
				coronaBallotboxes.add(ballotbox);
			} else {
				ballotbox = new Ballotbox<Isolated>(
						coronaBallotboxes.get(coronaBallotboxes.size() - 1).getSerialNumber() + 4,
						Ballotbox.eType.קורונה, adress);
				coronaBallotboxes.add(ballotbox);
			}
			return true;
		}
		if (type == eType.צבאית) {
			if (militaryBallotbox.isEmpty() == true) {
				ballotbox = new Ballotbox<Soldier>(2, Ballotbox.eType.צבאית, adress);
				militaryBallotbox.add(ballotbox);
			} else {
				ballotbox = new Ballotbox<Soldier>(
						militaryBallotbox.get(militaryBallotbox.size() - 1).getSerialNumber() + 4,
						Ballotbox.eType.צבאית, adress);
				militaryBallotbox.add(ballotbox);
			}
			return true;
		}

		if (type == eType.משולבת) {
			if (coronaAndMilitaryBallotboxes.isEmpty() == true) {
				ballotbox = new Ballotbox<IsolatedSoldier>(3, Ballotbox.eType.משולבת, adress);
				coronaAndMilitaryBallotboxes.add(ballotbox);
			} else {
				ballotbox = new Ballotbox<IsolatedSoldier>(
						(coronaAndMilitaryBallotboxes.get(coronaAndMilitaryBallotboxes.size() - 1).getSerialNumber())
								+ 4,
						Ballotbox.eType.משולבת, adress);
				coronaAndMilitaryBallotboxes.add(ballotbox);
			}
			return true;
		}

		return false;
	}



	public boolean addCitizen(String name, String id, int year, boolean isIsolated, boolean isProtected,
			boolean isCarryingWeapon, int daysOfSickness) throws Exception {
		if (checkIfIDIsValid(id) == false)
			throw new Exception("תעודת זהות חייבת להכיל 9 ספרות!");
		if (checkIfCitizenYearValid(year) == false)
			throw new Exception("שנת הלידה חייבת להיות 0-" + CURRENT_YEAR);
		if (checkIfDaysOfSicknessValid(daysOfSickness) == false)
			throw new Exception("ימי המחלה לא יכולים להיות מספר שלילי");

		if (isIsolated == true) {
			if (isCarryingWeapon == true) {
				if (citizens.add(new IsolatedSoldier(name, id, year, null, isIsolated, isProtected, daysOfSickness,
						isCarryingWeapon)) == true)
					return true;
			}
			if (citizens.add(new Isolated(name, id, year, null, isIsolated, isProtected, daysOfSickness)) == true)
				return true;
		}
		if (isCarryingWeapon == true) {
			if (citizens.add(new Soldier(name, id, year, null, isIsolated, isProtected, isCarryingWeapon)) == true)
				return true;
		}
		if (citizens.add(new Citizen(name, id, year, null, isIsolated, isProtected)) == true)
			return true;

		return false;
	}

	public boolean addCandidate(String name, String id, int year, boolean isIsolated, boolean isProtected,
			boolean isCarryingWeapon, int daysOfSickness, int partyIndex, int rank) {
		if (isIsolated == false) {
			if (citizens.add(new Candidate(name, id, year, null, isIsolated, isProtected, parties.get(partyIndex - 1),
					rank)) == true) {
				parties.get(partyIndex - 1).addCandidate((Candidate) (new Candidate(name, id, year, null, isIsolated,
						isProtected, parties.get(partyIndex - 1), rank)));
				return true;
			}
		}
		if (citizens.add(new IsolatedCandidate(name, id, year, null, isIsolated, isProtected, daysOfSickness,
				parties.get(partyIndex - 1), rank)) == true) {
			parties.get(partyIndex - 1).addCandidate((Candidate) (new Candidate(name, id, year, null, isIsolated,
					isProtected, parties.get(partyIndex - 1), rank)));
			return true;
		}
		return false;
	}

	public boolean addParty(String name, Party.eSection choice, int birthYear, int birthMonth) throws Exception {

		if (checkIfPartyNameExist(name) == true)
			throw new Exception("השם של המפלגה כבר קיים");
		if (checkIfSPartyFoundationMonthValid(birthMonth) == false)
			throw new Exception("חודש הקמה חייב להיות 1-12");
		if (checkIfSPartyFoundationYearValid(birthYear) == false)
			throw new Exception("השנה חייבת להיות 1948-" + CURRENT_YEAR);
		if (checkIfSPartyFoundationMonthYearValid(birthYear, birthMonth) == false)
			throw new Exception("תאריך הקמה לא יכול להיות מאוחר יותר מהתאריך של היום");

		Party p = new Party(name, choice, birthYear, birthMonth);
		parties.add(p);
		return true;
	}

//	private Party[] extendParties(Party[] oldArray) {
//		Party[] newArr = new Party[parties.length * 2];
//		for (int partiesIndex = 0; partiesIndex < parties.length; partiesIndex++) {
//			newArr[partiesIndex] = parties[partiesIndex];
//		}
//		return newArr;
//	}

//	private Ballotbox[] extendBallotboxes(Ballotbox[] array) {
//		Ballotbox[] newArr = new Ballotbox[array.length * 2];
//		for (int i = 0; i < array.length; i++)
//			newArr[i] = array[i];
//		return newArr;
//
//	}

	public String showAllBallotboxes() {
		String showTheBallotBoxes = "";
		if (regularBallotboxes.size() == 0 & coronaBallotboxes.size() == 0 && militaryBallotbox.size() == 0
				&& coronaAndMilitaryBallotboxes.size() == 0) {
			showTheBallotBoxes = showTheBallotBoxes + "There were no ballot boxes created.\n";
		}
		showTheBallotBoxes = showTheBallotBoxes + "קלפיות רגילות:\n";
		for (int i = 0; i < regularBallotboxes.size(); i++)
			showTheBallotBoxes = showTheBallotBoxes + regularBallotboxes.get(i).toString() + "\n";
		showTheBallotBoxes = showTheBallotBoxes + "\nקלפיות קורונה:\n";
		for (int i = 0; i < coronaBallotboxes.size(); i++)
			showTheBallotBoxes = showTheBallotBoxes + coronaBallotboxes.get(i).toString() + "\n";
		showTheBallotBoxes = showTheBallotBoxes + "\nקלפיות צבאיות:\n";
		for (int i = 0; i < militaryBallotbox.size(); i++)
			showTheBallotBoxes = showTheBallotBoxes + militaryBallotbox.get(i).toString() + "\n";
		showTheBallotBoxes = showTheBallotBoxes + "\nקלפיות קורונה - צבאיות:\n";
		for (int i = 0; i < coronaAndMilitaryBallotboxes.size(); i++)
			showTheBallotBoxes = showTheBallotBoxes + coronaAndMilitaryBallotboxes.get(i).toString() + "\n";

		return showTheBallotBoxes;
	}

	public boolean AreThereCitizens() {
		if (citizens.size() == 0)
			return false;
		return true;
	}

	public String showAllCitizens() {
		String citizenListString = "";
		for (int i = 0; i < citizens.size(); i++) {
			citizenListString = citizenListString.concat(((Citizen) (citizens.get(i))).toString() + "\n");
		}
		return citizenListString;
	}

	public boolean AreThereParties() {
		if (parties.size() == 0)
			return false;
		return true;
	}

	public String showPartiesForUser() {
		String partyString = "";
		if (parties.size() == 0) {
			partyString = partyString + "No parties were created.\n";
		}
		for (int partyIndex = 0; partyIndex < parties.size(); partyIndex++) {
			partyString = partyString + (partyIndex + 1) + " - " + parties.get(partyIndex).getName() + "\n";
		}
		return partyString;
	}

	// Citizen gets a ballot box only in case 8. The method:
	// 1) setting the citizen's ballot box if he can vote
	// 2) adding him to the voter list in the certain ballot box
	public void setBallotBox() {
		voters.clear();
		int ballotBoxIndex;
		for (int i = 0; i < citizens.size(); i++) {
			Citizen currentCitizen = citizens.get(i);
			if ((CURRENT_YEAR - currentCitizen.getYear()) >= 18 && currentCitizen.getIsProtected() == true) {
				if (currentCitizen.getIsIsloated() == true) {
					if ((currentCitizen instanceof IsolatedSoldier) == true) {
						ballotBoxIndex = (int) ((coronaAndMilitaryBallotboxes.size()) * Math.random());
						currentCitizen.setBallotBox(coronaAndMilitaryBallotboxes.get(ballotBoxIndex));
						coronaAndMilitaryBallotboxes.get(ballotBoxIndex).addVoter((IsolatedSoldier) currentCitizen);
						voters.add(currentCitizen);
					} else if ((currentCitizen instanceof Isolated) == true
							|| (currentCitizen instanceof IsolatedCandidate) == true) {
						ballotBoxIndex = (int) ((coronaBallotboxes.size()) * Math.random());
						currentCitizen.setBallotBox(coronaBallotboxes.get(ballotBoxIndex));
						if (currentCitizen instanceof Isolated) {
							coronaBallotboxes.get(ballotBoxIndex).addVoter((Isolated) currentCitizen);
							voters.add(currentCitizen);
						} else {
							coronaBallotboxes.get(ballotBoxIndex).addVoter((IsolatedCandidate) currentCitizen);
							voters.add(currentCitizen);
						}
					}
				} else {
					if ((currentCitizen instanceof Soldier) == true) {
						ballotBoxIndex = (int) ((militaryBallotbox.size()) * Math.random());
						currentCitizen.setBallotBox(militaryBallotbox.get(ballotBoxIndex));
						militaryBallotbox.get(ballotBoxIndex).addVoter((Soldier) currentCitizen);
						voters.add(currentCitizen);
					} else if ((currentCitizen instanceof Candidate) == true
							|| (currentCitizen instanceof Citizen) == true) {
						ballotBoxIndex = (int) ((regularBallotboxes.size()) * Math.random());
						currentCitizen.setBallotBox(regularBallotboxes.get(ballotBoxIndex));
						if (currentCitizen instanceof Candidate) {
							regularBallotboxes.get(ballotBoxIndex).addVoter((Candidate) currentCitizen);
							voters.add(currentCitizen);
						} else {
							regularBallotboxes.get(ballotBoxIndex).addVoter((Citizen) currentCitizen);
							voters.add(currentCitizen);
						}

					}

				}
			}
		}
		for (int i = 0; i < regularBallotboxes.size(); i++)
			regularBallotboxes.get(i).initializeVotesArray(parties.size());
		for (int i = 0; i < coronaBallotboxes.size(); i++)
			coronaBallotboxes.get(i).initializeVotesArray(parties.size());
		for (int i = 0; i < militaryBallotbox.size(); i++)
			militaryBallotbox.get(i).initializeVotesArray(parties.size());
		for (int i = 0; i < coronaAndMilitaryBallotboxes.size(); i++)
			coronaAndMilitaryBallotboxes.get(i).initializeVotesArray(parties.size());

	}

	// getting as an input a ballotbox and a selected party's index, and adding the
	// vote to the party in the citizen's ballotbox
	public boolean addAVote(@SuppressWarnings("rawtypes") Ballotbox ballotbox, int partyIndex) {

		if (ballotbox.getSerialNumber() % 4 == 0)
			for (int counter = 0; counter < regularBallotboxes.size(); counter++)
				if (regularBallotboxes.get(counter).getSerialNumber() == ballotbox.getSerialNumber()) {
					regularBallotboxes.get(counter).elect(partyIndex);
					return true;
				}
		if (ballotbox.getSerialNumber() % 4 == 1)
			for (int counter = 0; counter < coronaBallotboxes.size(); counter++)
				if (coronaBallotboxes.get(counter).getSerialNumber() == ballotbox.getSerialNumber()) {
					coronaBallotboxes.get(counter).elect(partyIndex);
					return true;
				}
		if (ballotbox.getSerialNumber() % 4 == 2)
			for (int counter = 0; counter < militaryBallotbox.size(); counter++)
				if (militaryBallotbox.get(counter).getSerialNumber() == ballotbox.getSerialNumber()) {
					militaryBallotbox.get(counter).elect(partyIndex);
					return true;
				}
		if (ballotbox.getSerialNumber() % 4 == 3)
			for (int counter = 0; counter < coronaAndMilitaryBallotboxes.size(); counter++)
				if (coronaAndMilitaryBallotboxes.get(counter).getSerialNumber() == ballotbox.getSerialNumber()) {
					coronaAndMilitaryBallotboxes.get(counter).elect(partyIndex);
					return true;
				}

		return false;
	}

	public Model loadFile() throws ClassNotFoundException, IOException, Exception {
		Model electionModel;
		ObjectInputStream inFile;
		try {
			inFile = new ObjectInputStream(new FileInputStream("Election.dat"));
			electionModel = (Model) inFile.readObject();
			inFile.close();
		} catch (IOException e) {
			addHardCoded();
			throw new Exception("לא קיים קובץ לטעינה!");
			
		}
		return electionModel;
	}

	public void saveFile(Model electionModel) {
		try {
			ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("Election.dat"));
			outFile.writeObject(electionModel);
			outFile.close();
		} catch (IOException e) {
		}
	}

	// after entering case 8, when the number of parties is known, the finalVotes
	// array is initialized
	public void initializeFinalVotes() {
		for (int i = 0; i < parties.size(); i++)
			finalVotes.add(0);
	}

	public String getElectionResults() {
		for (int i=0; i<finalVotes.size();i++)
			finalVotes.set(i, 0);
		String results = "";
		if (regularBallotboxes.isEmpty() == false) {
			for (int i = 0; i < regularBallotboxes.size(); i++) {
				results = results + regularBallotboxes.get(i).toString() + ", " + regularBallotboxes.get(i).getType()
						+ ":\n";
				for (int j = 0; j < parties.size(); j++) {
					results = results + parties.get(j).getName() + ":"
							+ regularBallotboxes.get(i).getPartyVotes().get(j) + "\n";
					finalVotes.set(j, finalVotes.get(j) + regularBallotboxes.get(i).getPartyVotes().get(j));
				}

			}
		}
		if (coronaBallotboxes.isEmpty() == false) {
			for (int i = 0; i < coronaBallotboxes.size(); i++) {
				results = results + coronaBallotboxes.get(i).toString() + ", " + coronaBallotboxes.get(i).getType()
						+ ":\n";
				for (int j = 0; j < parties.size(); j++) {
					results = results + parties.get(j).getName() + ":" + coronaBallotboxes.get(i).getPartyVotes().get(j)
							+ "\n";
					finalVotes.set(j, finalVotes.get(j) + coronaBallotboxes.get(i).getPartyVotes().get(j));
				}

			}
		}
		if (militaryBallotbox.isEmpty() == false) {
			for (int i = 0; i < militaryBallotbox.size(); i++) {
				results = results + militaryBallotbox.get(i).toString() + ", " + militaryBallotbox.get(i).getType()
						+ ":\n";
				for (int j = 0; j < parties.size(); j++) {
					results = results + parties.get(j).getName() + ":" + militaryBallotbox.get(i).getPartyVotes().get(j)
							+ "\n";
					finalVotes.set(j, finalVotes.get(j) + militaryBallotbox.get(i).getPartyVotes().get(j));
				}
			}
		}
		if (coronaAndMilitaryBallotboxes.isEmpty() == false) {
			for (int i = 0; i < coronaAndMilitaryBallotboxes.size(); i++) {
				results = results + coronaAndMilitaryBallotboxes.get(i).toString() + ", "
						+ coronaAndMilitaryBallotboxes.get(i).getType() + ":\n";
				for (int j = 0; j < parties.size(); j++) {
					results = results + parties.get(j).getName() + ":"
							+ coronaAndMilitaryBallotboxes.get(i).getPartyVotes().get(j) + "\n";
					finalVotes.set(j, finalVotes.get(j) + coronaAndMilitaryBallotboxes.get(i).getPartyVotes().get(j));
				}
			}
		}
		results = results + "*************************\n";
		results = results + " THE FINAL RESULTS:\n";
		results = results + "*************************\n";
		for (int i = 0; i < parties.size(); i++) {
			results = results + parties.get(i).getName() + ": " + finalVotes.get(i) + "\n";
		}
		return results;
	}

	public boolean checkIfPartyNameExist(String name) {
		for (int i = 0; i < parties.size(); i++) {
			if (name.equalsIgnoreCase(parties.get(i).getName()) == true)
				return true;
		}
		return false;
	}

	// in case that case 8 is running again after case 9, the actions in case 8 are
	// reseted in this method
	public void resetBallotbox() {
		for (int i = 0; i < regularBallotboxes.size(); i++) {
			regularBallotboxes.get(i).resetNumberOfActualVoters();
			regularBallotboxes.get(i).resetVoterList();
		}
		for (int i = 0; i < coronaBallotboxes.size(); i++) {
			coronaBallotboxes.get(i).resetNumberOfActualVoters();
			coronaBallotboxes.get(i).resetVoterList();
		}
		for (int i = 0; i < militaryBallotbox.size(); i++) {
			militaryBallotbox.get(i).resetNumberOfActualVoters();
			militaryBallotbox.get(i).resetVoterList();
		}
		for (int i = 0; i < coronaAndMilitaryBallotboxes.size(); i++) {
			coronaAndMilitaryBallotboxes.get(i).resetNumberOfActualVoters();
			coronaAndMilitaryBallotboxes.get(i).resetVoterList();
		}

	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Model))
			return false;
		Model other = (Model) obj;
		return other.CURRENT_YEAR == CURRENT_YEAR && other.CURRENT_MONTH == CURRENT_MONTH;
	}

	public boolean isAtLeastOneCitizen() {
		if (citizens.size() == 0) {
			return false;
		}
		return true;
	}

	public boolean isSoldier(int birthYear) {
		if (CURRENT_YEAR - birthYear >= 18 && CURRENT_YEAR - birthYear <= 21)
			return true;
		return false;
	}

	public boolean checkIfballotboxTypeValid(int ballotboxType) {
		if (ballotboxType < 1 || ballotboxType > 4)
			return false;
		return true;
	}

	public boolean checkIfCitizenYearValid(int citizenYear) {
		if (citizenYear < 0 || citizenYear > CURRENT_YEAR)
			return false;
		return true;
	}

	public boolean checkIfSectionNumberValid(int sectionNumber) {
		if (sectionNumber < 1 || sectionNumber > 3)
			return false;
		return true;
	}

	public boolean checkIfSPartyFoundationYearValid(int birthYear) {
		if (birthYear >= 1948 && birthYear <= CURRENT_YEAR)
			return true;
		return false;
	}

	public boolean checkIfSPartyFoundationMonthValid(int birthMonth) {
		if (birthMonth <= 12 && birthMonth >= 1)
			return true;
		return false;
	}

	public boolean checkIfSPartyFoundationMonthYearValid(int birthYear, int birthMonth) {
		if (birthYear == CURRENT_YEAR && CURRENT_MONTH <= birthMonth)
			return false;
		return true;
	}

	public boolean checkIfSCandidateInLegalValid(int candidateYear) {
		if (candidateYear < 0 || (CURRENT_YEAR - candidateYear) < 21)
			return false;
		return true;
	}

	public boolean checkIfYesOrNoAnswerValid(String ans) {
		if (ans.equalsIgnoreCase("yes") == false && ans.equalsIgnoreCase("no") == false)
			return false;
		return true;
	}

	public boolean checkifVotingValid(int vote) {
		while (vote < 1 || vote > parties.size())
			return false;
		return true;
	}

	public boolean checkIfPartyIndexIsValid(int partyIndex) {
		if (partyIndex < 1 || partyIndex > parties.size()) {
			return false;
		}
		return true;
	}

	public boolean checkIfRankIsExist(int rank, int partyIndex) {
		for (int i = 0; i < parties.get(partyIndex - 1).getCandidates().size(); i++) {
			if (rank == parties.get(partyIndex - 1).getCandidates().get(i).getRankInList()) {
				return false;
			}
		}
		return true;
	}

	public boolean checkIfThereIsARegularBallotBoxForRegularCitizen() {
		if (regularBallotboxes.size() == 0) {
			for (int i = 0; i < citizens.size(); i++) {
				if (citizens.get(i).getClass().getName() == "Candidate"
						|| citizens.get(i).getClass().getName() == "Citizen")
					return false;
			}
		}
		return true;
	}

	public boolean checkIfThereIsACoronaBallotBoxForCoronaCitizen() {
		if (regularBallotboxes.size() == 0) {
			for (int i = 0; i < citizens.size(); i++) {
				if (citizens.get(i).getClass().getName() == "IsolatedCandidate"
						|| citizens.get(i).getClass().getName() == "Isolated")
					return false;
			}
		}
		return true;
	}

	public boolean checkIfThereIsAMilitartyBallotBoxForMilitartyCitizen() {
		if (regularBallotboxes.size() == 0) {
			for (int i = 0; i < citizens.size(); i++) {
				if (citizens.get(i).getClass().getName() == "Soldier")
					return false;
			}
		}
		return true;
	}

	public boolean checkIfThereIsAMilitartyCoronaBallotBoxForMilitartyCoronaCitizen() {
		if (regularBallotboxes.size() == 0) {
			for (int i = 0; i < citizens.size(); i++) {
				if (citizens.get(i).getClass().getName() == "IsolatedSoldier")
					return false;
			}
		}
		return true;
	}

	public boolean checkIfBallotBoxessCreated() {
		if (regularBallotboxes.isEmpty() == true && coronaBallotboxes.isEmpty() == true
				&& militaryBallotbox.isEmpty() == true && coronaAndMilitaryBallotboxes.isEmpty() == true)
			return false;
		return true;
	}

	public boolean checkIfWereElections() {
		for (int i = 0; i < citizens.size(); i++) {
			if (citizens.get(i).getBallotbox() != null)
				return true;
		}
		return false;
	}

	public boolean checkIfDaysOfSicknessValid(int daysOfSickness) {
		if (daysOfSickness < 0)
			return false;
		return true;
	}

	public boolean checkIfIDIsValid(String ID) {
		if (ID.length() != 9)
			return false;
		return true;
	}

	public Vector<String> getPartyName() {
		Vector<String> res = new Vector<>();
		for (int i = 0; i < parties.size(); i++) {
			res.add(parties.get(i).getName());
		}
		return res;
	}

	public void addHardCoded() throws Exception {
		this.addParty("הליכוד", Party.eSection.ימין, 2011, 12);
		this.addParty("העבודה", Party.eSection.שמאל, 2017, 11);
		this.addParty("כחול לבן", Party.eSection.מרכז, 2011, 04);

		this.addBallotBox(eType.רגילה, "תל אביב");
		this.addBallotBox(eType.קורונה, "חיפה");
		this.addBallotBox(eType.צבאית, "עכו");
		this.addBallotBox(eType.רגילה, "רמת גן");
		this.addBallotBox(eType.רגילה, "יהוד");
		this.addBallotBox(eType.משולבת, "תל השומר");

		this.addCitizen("גוגו", "123456789", 1995, true, true, false, 12);
		this.addCitizen("לולו", "123456789", 1999, false, true, false, 0);
		this.addCitizen("מומו", "123512356", 2001, false, true, true, 0);
		this.addCitizen("פופו", "129876543", 2004, false, true, false, 0);
		this.addCitizen("רורו", "186435335", 1996, true, true, false, 20);
		this.addCitizen("טוטו", "129822243", 2001, true, true, true, 6);

		this.addCandidate("משה", "123456789", 1985, false, true, false, 0, 1, 8);
		this.addCandidate("צביקה", "123456783", 1985, false, true, false, 0, 1, 7);
		this.addCandidate("אלי", "123456759", 1985, false, true, false, 0, 1, 3);
		this.addCandidate("זרובבל", "123456359", 1985, false, true, false, 0, 1, 2);
		this.addCandidate("יורם", "135798655", 1988, true, true, false, 12, 2, 2);
		this.addCandidate("נטע", "196582953", 1997, true, false, false, 14, 3, 6);
	}

	public Vector<Citizen> getAllVoters() {
		setBallotBox();
		initializeFinalVotes();
		return voters;
	}
	public String getDate() {
		return CURRENT_MONTH+"/"+CURRENT_YEAR;
	}

}
