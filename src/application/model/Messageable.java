package application.model;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Messageable {

	void addBallotBox();

	void addCitizen();

	void addParty();

	void addCandidate();

	void showCitizens();

	void showParties();

	void elect();

	void showElectionResults();

	boolean exitCurrentRound() throws Exception, FileNotFoundException, IOException, ClassNotFoundException;

	void wrongCurrentRoundInput();

	void createNewElectionRound() throws FileNotFoundException, IOException, ClassNotFoundException;

	int showOptionsInCurrentRound();

	void wrongInputInGeneralOptions();

	void exitProgram();

}