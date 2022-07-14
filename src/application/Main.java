package application;
	
import application.controller.Controller;
import application.model.Model;
import application.view.View;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
		public void start(Stage primaryStage) throws Exception {
		View ElectionView = new View(primaryStage);
		Model ElectionModel = new Model();
		@SuppressWarnings("unused")
		Controller controller = new Controller(ElectionModel,ElectionView);
		}
	
	public static void main(String[] args) {
		launch(args);
	}
}
