package school.mdsystem.logistic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class logistic extends Application {
	//@Override
	Stage primaryStage = new Stage();
	public void start(Stage primaryStage) {
		try {
			FXMLLoader f = new FXMLLoader();
			f.setLocation(logistic.class.getResource("products_view.fxml"));
			Pane root = (Pane)f.load();
			Scene scene = new Scene(root,800,600);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	public void showscene() throws IOException {
		start(primaryStage);
	}
}
