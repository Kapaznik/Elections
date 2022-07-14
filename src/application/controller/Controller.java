package application.controller;


import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;
import application.listeners.ViewListeners;
import application.model.Model;
import application.model.Party;
import application.model.Ballotbox;
import application.model.Ballotbox.eType;
import application.model.Citizen;
import application.view.View;

public class Controller implements ViewListeners{
	private Model electionModel;
	private View electionView;
	
	public Controller(Model m, View v) {
		electionModel = m;
		electionView = v;
		electionView.registerListener(this);
	}

	public String ShowAllCitizensToUI() {
		return electionModel.showAllCitizens();
	}
	
	public String showAllBallotboxesToUI() {
		return electionModel.showAllBallotboxes();
	}
	
	public String showAllPartiesToUI () {
		return electionModel.showPartiesForUser();
	}
	
	public String showElectionResultToUI() {
		return electionModel.getElectionResults();
	}
	
	public Vector<Party> getPartiesComboToUI () {
		return electionModel.getParties();
	}
	

		
	@Override

	public boolean checkIfIDisValidToUI(String ID) {
		return electionModel.checkIfIDIsValid(ID);
	}

	public boolean addCitizen (String name, String id, int year, boolean isIsolated, boolean isProtected,
			boolean isCarryingWeapon, int daysOfSickness) throws Exception{
		try {
			return electionModel.addCitizen(name, id, year, isIsolated, isProtected, isCarryingWeapon, daysOfSickness);
		}
		catch (Exception e) {
			electionView.joptionMessage(e.getMessage());
		}
		return false;
	}

	@Override
	public void joptionMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg);		
	}
	
	@Override
	public boolean addCandidate(String name, String id, int year, boolean isIsolated, boolean isProtected,
			boolean isCarryingWeapon, int daysOfSickness, int partyIndex, int rank) throws Exception {
		try {
			return electionModel.addCandidate(name, id, year, isIsolated, isProtected, isCarryingWeapon, daysOfSickness, partyIndex, rank);
		}
		catch (Exception e) {
			electionView.joptionMessage(e.getMessage());
		}
		return false;
	}
	
	public boolean isSoldier(int birthYear) {
		return electionModel.isSoldier(birthYear);
	}
	public void cannotAddBallotBox(String msg) {
		JOptionPane.showMessageDialog(null, msg);		
	}
	@Override
	public boolean addParty(String name, Party.eSection choice, int birthYear, int birthMonth) throws Exception {
		try {
			return electionModel.addParty(name, choice, birthYear, birthMonth);
		}
		catch (Exception e) {
			electionView.joptionMessage(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean addBallotBox(eType Type , String adress) {
		try {
			return electionModel.addBallotBox(Type, adress);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}



	@Override
	public void addBallotBoxToUI(eType type, String adress) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector<String> getPartiesNames() {
		return electionModel.getPartiesNames();
	}

	@Override
	public Vector<Citizen> getAllVoters() {
		return electionModel.getAllVoters();
	}
	@SuppressWarnings("rawtypes")
	public boolean addAVote(Ballotbox ballotbox, int partyIndex) {
		return electionModel.addAVote(ballotbox, partyIndex);

	}

	@Override
	public String getElectionResult() {
		return electionModel.getElectionResults();
	}
	

	@Override
	public Model loadFile() throws ClassNotFoundException, IOException, Exception {
		Model newFile;
		try {
			newFile = electionModel.loadFile();
			if (newFile != null) {
				electionModel = newFile;
				return electionModel;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			electionView.joptionMessage(e.getMessage());
		}
		return null;
		
	}

	@Override
	public void saveFile() throws IOException {
		electionModel.saveFile(electionModel);
		
	}
	public void addHardCoded() throws Exception {
		electionModel.addHardCoded();
	}
	public boolean checkIfWereElections() {
		return electionModel.checkIfWereElections();
	}
	public String getDate() {
		return electionModel.getDate();
	}
	
	public void resetElections() throws Exception {
		electionModel.resetElections();
	}
	

}
