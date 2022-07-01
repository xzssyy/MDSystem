package school.mdsystem.logistic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class logistic_rewrite_oreo extends Application {
	Stage primaryStage =new Stage();
	//String pro;
	//@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader f = new FXMLLoader();
			f.setLocation(logistic_rewrite_oreo.class.getResource("rewrite_oreo.fxml"));
			Pane root = (Pane)f.load();
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//primaryStage.setTitle(pro);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception{
		launch(args);
	}
	public void showscene() {
		//pro = p;
		//primaryStage.setTitle(p);
		start(primaryStage);
	}
}

