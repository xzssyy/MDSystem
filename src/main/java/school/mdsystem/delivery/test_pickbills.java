package school.mdsystem.delivery;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.fxml.*;
import school.mdsystem.App;


public class test_pickbills extends Application{

    Stage primaryStage=new Stage();
    public void start(Stage primaryStage) {
        try {
            FXMLLoader f1 = new FXMLLoader();
            f1.setLocation(App.class.getResource("pickbills_view1.fxml"));
            AnchorPane root = (AnchorPane)f1.load();
            Scene scene = new Scene(root,823,602);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("配送员界面——接单页面");
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