package school.mdsystem.delivery;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import school.mdsystem.App;

import java.io.IOException;

public class test_ranking extends Application{
	
	Stage primaryStage=new Stage();
	public void start(Stage primaryStage) {
	   try {
		   FXMLLoader f1 = new FXMLLoader();
		   f1.setLocation(App.class.getResource("deliverysort.fxml"));
		   AnchorPane root = (AnchorPane)f1.load();
		   Scene scene = new Scene(root,823,602);
		   primaryStage.setScene(scene);
		   primaryStage.setResizable(false);
		   primaryStage.setTitle("配送员界面——排行榜");
		   primaryStage.show();
	   }catch(Exception e) {
		   e.printStackTrace();
	   }
	  }
	 
	public static void main(String[] args) {
		  Application.launch(args);
	  }
	
	public void showscene() throws IOException
	   {
		   start(primaryStage);
	   }
}
