package application.view;


import java.io.IOException;

import java.util.Vector;

import javax.swing.JOptionPane;

import application.listeners.ViewListeners;
import application.model.Ballotbox;
import application.model.Citizen;
import application.model.Model;
import application.model.Party;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class View {

	private Vector<ViewListeners> allListeners = new Vector<ViewListeners>();
	private Vector<Citizen> allVoters = new Vector<Citizen>();
	private int counterCitizens;

	private Stage stage;

	public View(Stage primaryStage) {
		stage = primaryStage;
		stage.setTitle("תפריט ראשי");
		stage.setScene(createMainManuScene());
		stage.show();
	}

	public void registerListener(ViewListeners listener) {
		allListeners.add(listener);
	}

	private Scene createMainManuScene() {
		stage.setTitle("מסך פתיחה");

		Scene currentScene;
		HBox options = new HBox();
		Button newElections = new Button("סבב בחירות חדש");
		RadioButton load = new RadioButton("טען קבצים");
		Button exit = new Button("צא מהתוכנה");

		exit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		HBox loadFile = new HBox();
		options.getChildren().addAll(newElections, exit);
		options.setAlignment(Pos.CENTER);
		loadFile.getChildren().addAll(load);
		loadFile.setAlignment(Pos.BOTTOM_LEFT);
		newElections.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (load.isSelected()) {
					try {
						loadFile();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else
					try {
						resetElections();
						addHardCoded();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				stage.setScene(creatOptionsManuScene());

			}
		});

		VBox options1 = new VBox(options, loadFile);
		options1.setSpacing(1);
		loadFile.setPadding(new Insets(53));
		options1.setAlignment(Pos.CENTER);
		newElections.setPrefHeight(150);
		newElections.setPrefWidth(150);
		newElections.setWrapText(true);
		newElections.setTextAlignment(TextAlignment.CENTER);
		exit.setPrefHeight(150);
		exit.setPrefWidth(150);
		newElections.setScaleX(1.2);
		newElections.setScaleY(1.2);
		exit.setScaleX(1.2);
		exit.setScaleY(1.2);
		options.setSpacing(50);
		currentScene = new Scene(options1, 500, 500);
		return currentScene;
	}

	private Scene createShowAllResultsScene() {

		Scene currentScene;
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(20, 20, 20, 20));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		Label finalResults = new Label("תוצאות הבחירות: ");
		TextArea printFinalResults = new TextArea(showElectionResultToUI());
		HBox resulsts = new HBox();
		HBox printResults = new HBox();
		resulsts.getChildren().addAll(finalResults);
		printResults.getChildren().addAll(printFinalResults);
		VBox resultsManu = new VBox();
		Button back = new Button("חזור לתפריט");
		resultsManu.getChildren().addAll(resulsts, printResults, back);

		resulsts.setAlignment(Pos.CENTER);
		resulsts.setSpacing(20);
		printResults.setAlignment(Pos.CENTER);
		printResults.setSpacing(20);
		resultsManu.setAlignment(Pos.CENTER);
		resultsManu.setSpacing(20);

		// Create a scene and place it in the stage
		stage.setTitle("תוצאות הבחירות");
		// stage.setAlwaysOnTop(true);

		currentScene = new Scene(resultsManu, 400, 400);
		back.setOnAction(e -> stage.setScene(creatOptionsManuScene()));

		return currentScene;
	}

	private Scene createStartVoteprogressScene() {

		Scene currentScene;
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(20, 20, 20, 20));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		Button vote = new Button("הצבע!");
		ChoiceBox<String> partyChoices = new ChoiceBox<String>();
		partyChoices.getItems().setAll(getPartiesNames());
		RadioButton wantToVote = new RadioButton("כן");
		RadioButton dontWantToVote = new RadioButton("לא");
		final ToggleGroup groupIsolated = new ToggleGroup();

		wantToVote.setToggleGroup(groupIsolated);
		wantToVote.setSelected(true);
		dontWantToVote.setToggleGroup(groupIsolated);
		Label wellcomeMsg = new Label("שלום  " + allVoters.get(counterCitizens).getName());
		Label askToVote = new Label("האם ברצונך להצביע?");
		Label partyToVoteFor = new Label("לאיזה מפלגה");
		VBox askVote = new VBox();
		HBox radioVote = new HBox();
		HBox partyToVote = new HBox();
		HBox partiesCombo = new HBox();
		askVote.getChildren().addAll(wellcomeMsg, askToVote);
		radioVote.getChildren().addAll(wantToVote, dontWantToVote);
		partyToVote.getChildren().addAll(partyToVoteFor);
		partiesCombo.getChildren().add(partyChoices);
		VBox voteManu = new VBox();
		partyChoices.setValue(getPartiesNames().get(0));
		voteManu.getChildren().addAll(askVote, radioVote, partyToVote, partiesCombo, vote);

		askVote.setAlignment(Pos.CENTER);
		askVote.setSpacing(20);
		partyToVote.setAlignment(Pos.CENTER);
		partyToVote.setSpacing(20);
		radioVote.setAlignment(Pos.CENTER);
		radioVote.setSpacing(20);
		partiesCombo.setAlignment(Pos.CENTER);
		partiesCombo.setSpacing(20);
		voteManu.setAlignment(Pos.CENTER);
		voteManu.setSpacing(20);

		// Create a scene and place it in the stage
		stage.setTitle("הולכים להצביע!");
		stage.show();
		stage.setAlwaysOnTop(true);

		currentScene = new Scene(voteManu, 400, 400);

		// back.setOnAction(e -> stage.setScene(creatOptionsManuScene()));

		vote.setOnAction(e -> {
			if (wantToVote.isSelected() == true) {
				addVote(allVoters.get(counterCitizens).getBallotbox(),
						getPartiesNames().indexOf(partyChoices.getValue()));
			}

			counterCitizens++;

			if (counterCitizens == allVoters.size()) {
				stage.setScene(creatOptionsManuScene());
				return;

			}
			stage.setScene(createStartVoteprogressScene());

		});
		return currentScene;

	}

	private Scene createShowAllPartiesScene() {
		stage.setTitle("רשימת המפלגות");

		Scene currentScene;
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(20, 20, 20, 20));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		Label partiesList = new Label("רשימת המפלגות: ");
		TextArea printPartiesList = new TextArea(showAllPartiesToUI());
		HBox parties = new HBox();
		HBox printParties = new HBox();
		parties.getChildren().addAll(partiesList);
		printParties.getChildren().addAll(printPartiesList);
		VBox partiesPrintManu = new VBox();
		Button back = new Button("חזור לתפריט");
		partiesPrintManu.getChildren().addAll(parties, printParties, back);

		parties.setAlignment(Pos.CENTER);
		parties.setSpacing(20);
		printParties.setAlignment(Pos.CENTER);
		printParties.setSpacing(20);
		partiesPrintManu.setAlignment(Pos.CENTER);
		partiesPrintManu.setSpacing(20);

		// Create a scene and place it in the stage
		stage.setTitle("רשימת המפלגות");
		stage.show();
		stage.setAlwaysOnTop(true);

		currentScene = new Scene(partiesPrintManu, 400, 400);
		back.setOnAction(e -> stage.setScene(creatOptionsManuScene()));

		return currentScene;
	}

	private Scene createShowAllCitizensScene() {
		stage.setTitle("רשימת האזרחים");

		Scene currentScene;
		GridPane pane = new GridPane();
		// pane.setGridLinesVisible(true);
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(20, 20, 20, 20));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		Label CitizensList = new Label("רשימת האזרחים: ");
		TextArea printCitizens = new TextArea(ShowAllCitizensToUI());
		printCitizens.setPrefSize(800, 400);
		HBox Citizens = new HBox();
		HBox printCitizensList = new HBox();
		Citizens.getChildren().addAll(CitizensList);
		printCitizensList.getChildren().addAll(printCitizens);
		VBox citizensPrintManu = new VBox();
		Button back = new Button("חזור לתפריט");
		citizensPrintManu.getChildren().addAll(Citizens, printCitizensList, back);

		Citizens.setAlignment(Pos.CENTER);
		Citizens.setSpacing(20);
		printCitizensList.setAlignment(Pos.CENTER);
		printCitizensList.setSpacing(20);
		citizensPrintManu.setAlignment(Pos.CENTER);
		citizensPrintManu.setSpacing(20);

		// Create a scene and place it in the stage
		stage.setTitle("רשימת האזרחים");
		stage.show();
		stage.setAlwaysOnTop(true);

		currentScene = new Scene(citizensPrintManu, 900, 400);
		back.setOnAction(e -> stage.setScene(creatOptionsManuScene()));

		return currentScene;
	}

	private Scene createShowAllBallotBox() {
		stage.setTitle("רשימת הקלפיות");

		Scene currentScene;
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(20, 20, 20, 20));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		Label ballotBoxesList = new Label("רשימת הקלפיות: ");
		TextArea printBallotBoxes = new TextArea(showAllBallotboxesToUI());
		HBox ballotBoxes = new HBox();
		HBox printBallot = new HBox();
		ballotBoxes.getChildren().addAll(ballotBoxesList);
		printBallot.getChildren().addAll(printBallotBoxes);
		VBox ballotboxMenu = new VBox();
		Button back = new Button("חזור לתפריט");
		ballotboxMenu.getChildren().addAll(ballotBoxes, printBallot, back);

		ballotBoxes.setAlignment(Pos.CENTER);
		ballotBoxes.setSpacing(20);
		printBallot.setAlignment(Pos.CENTER);
		printBallot.setSpacing(20);
		ballotboxMenu.setAlignment(Pos.CENTER);
		ballotboxMenu.setSpacing(20);

		// Create a scene and place it in the stage
		stage.setTitle("רשימת קלפיות");
		stage.show();
		stage.setAlwaysOnTop(true);

		currentScene = new Scene(ballotboxMenu, 400, 400);
		back.setOnAction(e -> stage.setScene(creatOptionsManuScene()));

		return currentScene;
	}

	private Scene createAddCandidateScene() {
		stage.setTitle("הוספת מועמד");

		Scene currentScene;
		RadioButton isolated = new RadioButton("חולה");
		RadioButton health = new RadioButton("בריא");
		RadioButton isProtected = new RadioButton("ממוגן");
		RadioButton noProtected = new RadioButton("לא ממוגן");
		Button btAddCandidate = new Button("הוסף מועמד");
		Button back = new Button("חזור לתפריט");
		final ToggleGroup groupProtected = new ToggleGroup();
		final ToggleGroup groupIsolated = new ToggleGroup();

		health.setToggleGroup(groupIsolated);
		health.setSelected(true);
		isolated.setToggleGroup(groupIsolated);
		noProtected.setDisable(true);
		isProtected.setToggleGroup(groupProtected);
		isProtected.setSelected(true);
		noProtected.setToggleGroup(groupProtected);

		health.setSelected(true);
		isProtected.setSelected(true);
		Label candidateNameLable = new Label("שם מלא: ");
		TextField candidateNameFill = new TextField();
		Label canddiateIDLable = new Label("תעודת זהות:");
		TextField canddiateIDFill = new TextField();
		Label canddiateBirthYearLable = new Label("שנת לידה:");
		TextField canddiateBirthYear = new TextField();
		Label canddiateCoronaStatusLable = new Label("סטטוס קורונה:");
		Label canddiateProtectionStatusLable = new Label("סטטוס מיגון:");
		Label partyBelongTo = new Label("מפלגה:");

		ChoiceBox<String> partyBelong = new ChoiceBox<String>();
		partyBelong.getItems().setAll(getPartiesNames());
		partyBelong.setValue(getPartiesNames().get(0));
		// partyBelong.setValue();
		Label candidateRankInPartyLable = new Label("דירוג במפלגה:");
		TextField candidateRankInParty = new TextField();
		Label canddiateDaysOfSicknessLable = new Label("ימי מחלה");
		TextField canddiateDaysOfSicknessFill = new TextField("0");
		canddiateDaysOfSicknessFill.setVisible(false);
		canddiateDaysOfSicknessLable.setVisible(false);
		HBox candidateName = new HBox();
		HBox idCandidate = new HBox();
		HBox birthYearCandidate = new HBox();
		HBox candidateCoronaStatusCheckBoxes = new HBox();
		HBox candidateProtectionStatusCheckBoxes = new HBox();
		HBox candidateParty = new HBox();
		HBox candidateRank = new HBox();
		HBox candidateDaysOfSickness = new HBox();

		candidateName.getChildren().addAll(candidateNameFill, candidateNameLable);
		idCandidate.getChildren().addAll(canddiateIDFill, canddiateIDLable);
		birthYearCandidate.getChildren().addAll(canddiateBirthYear, canddiateBirthYearLable);
		candidateCoronaStatusCheckBoxes.getChildren().addAll(isolated, health);
		candidateProtectionStatusCheckBoxes.getChildren().addAll(noProtected, isProtected);
		candidateDaysOfSickness.getChildren().addAll(canddiateDaysOfSicknessFill, canddiateDaysOfSicknessLable);
		candidateParty.getChildren().addAll(partyBelong, partyBelongTo);
		candidateRank.getChildren().addAll(candidateRankInParty, candidateRankInPartyLable);
		VBox candidate = new VBox();
		candidate.getChildren().addAll(candidateName, idCandidate, birthYearCandidate, canddiateCoronaStatusLable,
				candidateCoronaStatusCheckBoxes, canddiateProtectionStatusLable, candidateProtectionStatusCheckBoxes,
				candidateDaysOfSickness, candidateParty, candidateRank, btAddCandidate, back);
		candidate.setSpacing(20);
		candidate.setAlignment(Pos.CENTER);
		candidateName.setSpacing(20);
		candidateName.setAlignment(Pos.CENTER);
		idCandidate.setSpacing(20);
		idCandidate.setAlignment(Pos.CENTER);
		birthYearCandidate.setSpacing(20);
		birthYearCandidate.setAlignment(Pos.CENTER);
		candidateCoronaStatusCheckBoxes.setSpacing(20);
		candidateCoronaStatusCheckBoxes.setAlignment(Pos.CENTER);
		candidateProtectionStatusCheckBoxes.setSpacing(20);
		candidateProtectionStatusCheckBoxes.setAlignment(Pos.CENTER);
		candidateParty.setSpacing(20);
		candidateParty.setAlignment(Pos.CENTER);
		candidateRank.setSpacing(20);
		candidateRank.setAlignment(Pos.CENTER);
		candidateDaysOfSickness.setSpacing(20);
		candidateCoronaStatusCheckBoxes.setSpacing(50);
		candidateProtectionStatusCheckBoxes.setSpacing(50);
		candidateDaysOfSickness.setAlignment(Pos.CENTER);

		GridPane.setHalignment(btAddCandidate, HPos.RIGHT);
		isolated.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (isolated.isSelected() == true) {
					canddiateDaysOfSicknessFill.setVisible(true);
					canddiateDaysOfSicknessLable.setVisible(true);
					noProtected.setDisable(false);
				}
			}
		});
		health.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (health.isSelected() == true) {
					canddiateDaysOfSicknessFill.setVisible(false);
					canddiateDaysOfSicknessLable.setVisible(false);

					noProtected.setDisable(true);
				}
			}
		});
		btAddCandidate.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (candidateNameFill.getText().isEmpty() || canddiateIDFill.getText().isEmpty()
						|| canddiateBirthYear.getText().isEmpty() || candidateRankInParty.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "כל השדות חייבים להיות מלאים!");
				} else {
					try {
						if (addCitizen(candidateNameFill.getText(), canddiateIDFill.getText(),
								Integer.parseInt(canddiateBirthYear.getText()), isolated.isSelected(),
								isProtected.isSelected(), false,
								Integer.parseInt(canddiateDaysOfSicknessFill.getText())) == true)
							JOptionPane.showMessageDialog(null, "Candidate added");
						else {
							JOptionPane.showMessageDialog(null, "Citizen with the same ID already exist");

						}

					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});

		// Create a scene and place it in the stage
		currentScene = new Scene(candidate, 600, 600);
		back.setOnAction(e -> stage.setScene(creatOptionsManuScene()));
		return currentScene;
	}

	private Scene createAddPartyScene() {
		stage.setTitle("הוספת מפלגה");

		Scene currentScene;
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(20, 20, 20, 20));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		Button btAddParty = new Button("הוסף מפלגה");
		Label partyNameLable = new Label("שם המפלגה: ");
		TextField nameFillParty = new TextField();
		Label partyWingBelongTo = new Label("זרם המפלגה: ");
		ChoiceBox<Party.eSection> partyWing = new ChoiceBox<>();
		partyWing.getItems().setAll(Party.eSection.values());
		partyWing.setValue(Party.eSection.מרכז);
		Button back = new Button("חזור לתפריט");

		Label dataOfFoundLable = new Label("תאריך היווסדות המפלגה:");
		dataOfFoundLable.setFont(new Font("Arial", 16));
		dataOfFoundLable.setStyle("-fx-font-weight: bold;-fx-underline: true");
		Label yearOfFoundLable = new Label("שנת הקמה המפלגה: ");
		TextField yearOfFound = new TextField();
		Label monthOfFoundLable = new Label("חודש הקמת המפלגה: ");
		TextField monthOfFound = new TextField();

		HBox partyName = new HBox();
		HBox partyWingType = new HBox();
		HBox partyBirthMsg = new HBox();
		HBox partyBirthDate = new HBox();
		HBox partyBirthInt = new HBox();

		partyName.getChildren().addAll(nameFillParty, partyNameLable);
		partyWingType.getChildren().addAll(partyWing, partyWingBelongTo);
		partyBirthMsg.getChildren().addAll(dataOfFoundLable);
		partyBirthDate.getChildren().addAll(yearOfFoundLable, monthOfFoundLable);
		partyBirthInt.getChildren().addAll(yearOfFound, monthOfFound);

		VBox addPartyVBox = new VBox();
		addPartyVBox.getChildren().addAll(partyName, partyWingType, partyBirthMsg, partyBirthDate, partyBirthInt,
				btAddParty, back);

		partyName.setAlignment(Pos.CENTER);
		partyName.setSpacing(20);
		partyWingType.setAlignment(Pos.CENTER);
		partyWingType.setSpacing(20);
		partyBirthMsg.setAlignment(Pos.CENTER);
		partyBirthMsg.setSpacing(20);
		partyBirthDate.setAlignment(Pos.CENTER);
		partyBirthDate.setSpacing(20);
		partyBirthInt.setAlignment(Pos.CENTER);
		partyBirthInt.setSpacing(20);
		addPartyVBox.setAlignment(Pos.CENTER);
		addPartyVBox.setSpacing(20);

		GridPane.setHalignment(btAddParty, HPos.RIGHT);
		currentScene = new Scene(addPartyVBox, 400, 400);
		btAddParty.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (nameFillParty.getText().isEmpty() || yearOfFound.getText().isEmpty()
						|| monthOfFound.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "כל השדות חייבים להיות מלאים!");
				} else {
					try {
						if (addParty(nameFillParty.getText(), partyWing.getValue(),
								Integer.parseInt(yearOfFound.getText()),
								Integer.parseInt(monthOfFound.getText())) == true)
							JOptionPane.showMessageDialog(null, "המפלגה נוספה!");
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		back.setOnAction(e -> stage.setScene(creatOptionsManuScene()));
		return currentScene;
	}

	private Scene createAddBallotboxScene() {
		stage.setTitle("הוספת קלפי");

		Scene currentScene;
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(20, 20, 20, 20));
		pane.setHgap(5.5);
		pane.setVgap(5.5);
		Button btAddBallotbox = new Button("הוסף קלפי");
		Label BallotboxAddressLable = new Label("כתובת הקלפי: ");
		TextField nameFillAdress = new TextField();
		Label BallotBoxTypeLable = new Label("סוג הקלפי: ");
		ChoiceBox<Ballotbox.eType> BallotType = new ChoiceBox<>();
		BallotType.getItems().setAll(Ballotbox.eType.values());
		BallotType.setValue(Ballotbox.eType.רגילה);
		Button back = new Button("חזור לתפריט");

		HBox BallotboxAddress = new HBox();
		HBox BallotBoxType = new HBox();
		VBox addBallotBoxVBox = new VBox();
		BallotboxAddress.getChildren().addAll(nameFillAdress, BallotboxAddressLable);
		BallotBoxType.getChildren().addAll(BallotType, BallotBoxTypeLable);
		addBallotBoxVBox.getChildren().addAll(BallotboxAddress, BallotBoxType, btAddBallotbox, back);

		BallotboxAddress.setAlignment(Pos.CENTER);
		BallotboxAddress.setSpacing(20);
		BallotBoxType.setAlignment(Pos.CENTER);
		BallotBoxType.setSpacing(20);
		addBallotBoxVBox.setAlignment(Pos.CENTER);
		addBallotBoxVBox.setSpacing(20);
		GridPane.setHalignment(btAddBallotbox, HPos.RIGHT);

		btAddBallotbox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (nameFillAdress.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "כל השדות חייבים להיות מלאים!");
				} else {
					StackPane secondaryLayout = new StackPane();
					if (addBallotBox(BallotType.getValue(), nameFillAdress.getText()) == true) {
						Label added = new Label("הקלפי נוספה בהצלחה!");
						secondaryLayout.getChildren().add(added);
					} else {
						Label cantAdd = new Label("המערכת לא הצליחה להוסיף את הקלפי!" + "\n" + "נסה שנית!");
						secondaryLayout.getChildren().add(cantAdd);
					}

					Scene secondScene = new Scene(secondaryLayout, 230, 100);

					// New window (Stage)
					Stage newWindow = new Stage();
					newWindow.setTitle("הודעה!");
					newWindow.setScene(secondScene);

					// Specifies the modality for new window.
					newWindow.initModality(Modality.WINDOW_MODAL);

					// Specifies the owner Window (parent) for new window
					newWindow.initOwner(stage);

					// Set position of second window, related to primary window.
					newWindow.setX(stage.getX() + 200);
					newWindow.setY(stage.getY() + 100);

					newWindow.show();
				}
			}
		});

		currentScene = new Scene(addBallotBoxVBox, 500, 500);
		back.setOnAction(e -> stage.setScene(creatOptionsManuScene()));

		return currentScene;
	}

	private Scene creatAddCitizenScene() {
		stage.setTitle("הוספת אזרח");

		Scene currentScene;
		RadioButton isolated = new RadioButton("חולה");
		RadioButton health = new RadioButton("בריא");
		RadioButton isProtected = new RadioButton("ממוגן");
		RadioButton noProtected = new RadioButton("לא ממוגן");
		CheckBox isCarryingWeapon = new CheckBox("נושא נשק");
		Button btAddCitizen = new Button("הוסף אזרח");
		Button back = new Button("חזור לתפריט");
		final ToggleGroup groupProtected = new ToggleGroup();
		final ToggleGroup groupIsolated = new ToggleGroup();

		health.setToggleGroup(groupIsolated);
		health.setSelected(true);
		isolated.setToggleGroup(groupIsolated);

		isProtected.setToggleGroup(groupProtected);
		isProtected.setSelected(true);
		noProtected.setToggleGroup(groupProtected);

		health.setSelected(true);
		noProtected.setDisable(true);
		isProtected.setSelected(true);
		Label citizenNameLable = new Label("שם מלא: ");
		TextField nameFill = new TextField();
		Label citizenIDLable = new Label("תעודת זהות:");
		TextField citizenIDFill = new TextField();
		Label citizenBirthYearLable = new Label("שנת לידה:");
		TextField citizenBirthYear = new TextField();
		Label citizenCoronaStatusLable = new Label("סטטוס קורונה:");
		Label citizenProtectionStatusLable = new Label("סטטוס מיגון:");
		Label citizenDaysOfSicknessLable = new Label("ימי מחלה");
		TextField citizenDaysOfSicknessFill = new TextField("0");

		HBox citizenName = new HBox();
		HBox idCitizen = new HBox();
		HBox CitibirthYearCitizen = new HBox();
		HBox citizenCoronaStatusCheckBoxes = new HBox();
		HBox citizenProtectionStatusCheckBoxes = new HBox();
		HBox citizenDaysOfSickness = new HBox();
		citizenName.getChildren().addAll(nameFill, citizenNameLable);
		idCitizen.getChildren().addAll(citizenIDFill, citizenIDLable);
		CitibirthYearCitizen.getChildren().addAll(citizenBirthYear, citizenBirthYearLable);
		citizenCoronaStatusCheckBoxes.getChildren().addAll(isolated, health);
		citizenProtectionStatusCheckBoxes.getChildren().addAll(noProtected, isProtected);
		citizenDaysOfSickness.getChildren().addAll(citizenDaysOfSicknessFill, citizenDaysOfSicknessLable);

		VBox citizen = new VBox();
		citizen.getChildren().addAll(citizenName, idCitizen, CitibirthYearCitizen, citizenCoronaStatusLable,
				citizenCoronaStatusCheckBoxes, citizenProtectionStatusLable, citizenProtectionStatusCheckBoxes,
				citizenDaysOfSickness, isCarryingWeapon, btAddCitizen, back);
		citizen.setSpacing(20);
		citizen.setAlignment(Pos.CENTER);
		citizenName.setSpacing(20);
		citizenName.setAlignment(Pos.CENTER);
		idCitizen.setSpacing(20);
		idCitizen.setAlignment(Pos.CENTER);
		CitibirthYearCitizen.setSpacing(20);
		CitibirthYearCitizen.setAlignment(Pos.CENTER);
		citizenCoronaStatusCheckBoxes.setSpacing(20);
		citizenCoronaStatusCheckBoxes.setAlignment(Pos.CENTER);
		citizenProtectionStatusCheckBoxes.setSpacing(20);
		citizenProtectionStatusCheckBoxes.setAlignment(Pos.CENTER);
		citizenDaysOfSickness.setSpacing(20);
		citizenCoronaStatusCheckBoxes.setSpacing(50);
		citizenProtectionStatusCheckBoxes.setSpacing(50);

		citizenDaysOfSickness.setAlignment(Pos.CENTER);
		citizenDaysOfSickness.setVisible(false);
		btAddCitizen.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (nameFill.getText().isEmpty() || citizenIDFill.getText().isEmpty()
						|| citizenBirthYear.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "כל השדות חייבים להיות מלאים!");
				} else {
					boolean isCarryingWeaponBoolean = false;
					;
					if (isSoldier(Integer.parseInt(citizenBirthYear.getText())) == true
							&& isCarryingWeapon.isSelected() == true)
						isCarryingWeaponBoolean = true;
					try {
						if (addCitizen(nameFill.getText(), citizenIDFill.getText(),
								Integer.parseInt(citizenBirthYear.getText()), isolated.isSelected(),
								isProtected.isSelected(), isCarryingWeaponBoolean,
								Integer.parseInt(citizenDaysOfSicknessFill.getText())) == true)
							JOptionPane.showMessageDialog(null, "Citizen added");
						else
							JOptionPane.showMessageDialog(null, "Citizen with the same ID already exist");

					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

		});
		isolated.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (isolated.isSelected() == true) {
					citizenDaysOfSickness.setVisible(true);
					noProtected.setDisable(false);
				}
			}
		});
		health.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (health.isSelected() == true) {
					citizenDaysOfSickness.setVisible(false);
					noProtected.setDisable(true);
				}
			}
		});

		// Create a scene and place it in the stage
		currentScene = new Scene(citizen, 500, 500);
		back.setOnAction(e -> stage.setScene(creatOptionsManuScene()));

		return currentScene;
	}

	private Scene creatOptionsManuScene() {
		stage.setTitle("תפריט ראשי");
		Scene currentScene;
		Label label = new Label(" בחירות   " + getDate());
		label.setScaleX(3);
		label.setScaleY(3);
		label.setTextFill(Color.DARKBLUE);
		VBox options = new VBox();
		Button addBallotBox = new Button("הוסף קלפי");
		addBallotBox.setOnAction(e -> stage.setScene(createAddBallotboxScene()));
		Button addCitizen = new Button("הוסף אזרח");
		addCitizen.setOnAction(e -> stage.setScene(creatAddCitizenScene()));
		Button addParty = new Button("הוסף מפלגה");
		addParty.setOnAction(e -> stage.setScene(createAddPartyScene()));
		Button addCandidate = new Button("הוסף מועמד");
		addCandidate.setOnAction(e -> stage.setScene(createAddCandidateScene()));
		HBox AddButtons = new HBox();
		Button showBallotBoxes = new Button("הצג קלפיות");
		showBallotBoxes.setOnAction(e -> stage.setScene(createShowAllBallotBox()));
		Button showCitizens = new Button("הצג אזרחים");
		showCitizens.setOnAction(e -> stage.setScene(createShowAllCitizensScene()));
		Button showParties = new Button("הצג מפלגות");
		showParties.setOnAction(e -> stage.setScene(createShowAllPartiesScene()));
		HBox showButtons = new HBox();
		Button elect = new Button("לך לבחירות");
		elect.setOnAction(e -> {
			counterCitizens = 0;
			allVoters = getAllVoters();
			stage.setScene(createStartVoteprogressScene());
		});
		Button showResults = new Button("הצג בחירות");
		showResults.setOnAction(e -> {
			if (checkIfWereElections() == false)
				JOptionPane.showMessageDialog(null, "לא התבצעו בחירות");
			else
				stage.setScene(createShowAllResultsScene());

		});
		Button endCurrentRound = new Button("סיים סבב בחירות נוכחי");
		endCurrentRound.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (allVoters.isEmpty() == false) {
					allVoters.clear();
					try {
						saveFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				stage.setScene(createMainManuScene());
			}

		});

		addBallotBox.setScaleX(1.2);
		addBallotBox.setScaleY(1.2);
		addCitizen.setScaleX(1.2);
		addCitizen.setScaleY(1.2);
		addParty.setScaleX(1.2);
		addParty.setScaleY(1.2);
		addCandidate.setScaleX(1.2);
		addCandidate.setScaleY(1.2);

		showBallotBoxes.setPrefWidth(110);
		showCitizens.setPrefWidth(110);
		showParties.setPrefWidth(110);

		elect.setPrefWidth(420);
		elect.setPrefHeight(40);
		showResults.setPrefWidth(420);
		elect.setPrefHeight(40);

		showBallotBoxes.setScaleX(1.2);
		showBallotBoxes.setScaleY(1.2);
		showCitizens.setScaleX(1.2);
		showCitizens.setScaleY(1.2);
		showParties.setScaleX(1.2);
		showParties.setScaleY(1.2);

		AddButtons.getChildren().addAll(addBallotBox, addCitizen, addParty, addCandidate);
		showButtons.getChildren().addAll(showBallotBoxes, showCitizens, showParties);

		AddButtons.setAlignment(Pos.CENTER);
		showButtons.setAlignment(Pos.CENTER);
		elect.setAlignment(Pos.CENTER);
		showResults.setAlignment(Pos.CENTER);
		endCurrentRound.setAlignment(Pos.CENTER);

		AddButtons.setSpacing(30);
		showButtons.setSpacing(30);

		options.getChildren().addAll(label, AddButtons, showButtons, elect, showResults, endCurrentRound);
		options.setAlignment(Pos.CENTER);
		options.setSpacing(20);
		currentScene = new Scene(options, 500, 500);
		return currentScene;
	}

	public String ShowAllCitizensToUI() {
		for (ViewListeners listener : allListeners) {
			return listener.ShowAllCitizensToUI();

		}
		return "ERROR";

	}

	public String showAllBallotboxesToUI() {
		for (ViewListeners listener : allListeners) {
			return listener.showAllBallotboxesToUI();

		}
		return "ERROR";

	}

	public String showAllPartiesToUI() {
		for (ViewListeners listener : allListeners) {
			return listener.showAllPartiesToUI();

		}
		return "ERROR";
	}

	public Vector<String> getPartiesNames() {
		for (ViewListeners listener : allListeners) {
			return listener.getPartiesNames();
		}
		return null;
	}

	public String showElectionResultToUI() {

		return getElectionResult();

	}

	public boolean checkIfIDisValidToUI(String ID) {
		for (ViewListeners listener : allListeners) {
			return listener.checkIfIDisValidToUI(ID);
		}
		return false;
	}

	public void joptionMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg);

	}

	public boolean addCitizen(String name, String id, int year, boolean isIsolated, boolean isProtected,
			boolean isCarryingWeapon, int daysOfSickness) throws Exception {
		for (ViewListeners listener : allListeners) {
			return listener.addCitizen(name, id, year, isIsolated, isProtected, isCarryingWeapon, daysOfSickness);
		}
		return isCarryingWeapon;
	}

	public boolean addCandidate(String name, String id, int year, boolean isIsolated, boolean isProtected,
			boolean isCarryingWeapon, int daysOfSickness, int partyIndex, int rank) throws Exception {
		for (ViewListeners listener : allListeners) {
			return listener.addCandidate(name, id, year, isIsolated, isProtected, false, daysOfSickness, partyIndex,
					rank);
		}
		return false;
	}

	public boolean addBallotBox(Ballotbox.eType type, String adress) {
		for (ViewListeners listener : allListeners) {
			return listener.addBallotBox(type, adress);
		}
		return false;
	}

	public boolean isSoldierToUi(int birthYear) {
		for (ViewListeners listener : allListeners) {
			return listener.isSoldier(birthYear);
		}
		return false;
	}

	public boolean addParty(String name, Party.eSection choice, int birthYear, int birthMonth) throws Exception {
		for (ViewListeners listener : allListeners) {
			return listener.addParty(name, choice, birthYear, birthMonth);
		}
		return false;
	}

	public Vector<Citizen> getAllVoters() {
		for (ViewListeners listener : allListeners) {
			return listener.getAllVoters();
		}
		return null;
	}

	public boolean addVote(@SuppressWarnings("rawtypes") Ballotbox ballotBox, int index) {
		for (ViewListeners listener : allListeners) {
			return listener.addAVote(ballotBox, index);
		}
		return false;
	}

	public String getElectionResult() {
		for (ViewListeners listener : allListeners) {
			return listener.getElectionResult();
		}
		return null;
	}

	public Model loadFile() throws ClassNotFoundException, IOException, Exception {
		for (ViewListeners listener : allListeners) {
			return listener.loadFile();
		}
		return null;

	}

	public void saveFile() throws IOException {
		for (ViewListeners listener : allListeners) {
			listener.saveFile();
		}

	}

	public void addHardCoded() throws Exception {
		for (ViewListeners listener : allListeners) {
			listener.addHardCoded();
		}
	}

	public boolean isSoldier(int birthYear) {
		for (ViewListeners listener : allListeners) {
			listener.isSoldier(birthYear);
		}
		return false;
	}

	public boolean checkIfWereElections() {
		for (ViewListeners listener : allListeners) {
			return listener.checkIfWereElections();
		}
		return false;
	}

	public String getDate() {
		for (ViewListeners listener : allListeners) {
			return listener.getDate();
		}
		return null;
	}

	public void resetElections() throws Exception {
		for (ViewListeners listener : allListeners) {
			listener.resetElections();
			{
			}
		}
	}
}