package school.mdsystem.student.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import school.mdsystem.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
public class BaseController extends Controller implements Initializable {
    @FXML
    private HBox GoApply;

    @FXML
    private HBox GoRecording;

    @FXML
    private HBox GoStudent;

    @FXML
    private AnchorPane background;

    @FXML
    private Label userName;

    private Parent root1 = null, root2 = null, root3 = null;

    @FXML
    void goToApply(MouseEvent event) throws IOException {
        root1.setVisible(false);
        root2.setVisible(true);
        root3.setVisible(false);
    }

    @FXML
    void goToRecording(MouseEvent event) throws IOException {
        //System.out.println("goto recording");
        root1.setVisible(false);
        root2.setVisible(false);
        root3.setVisible(true);
    }

    @FXML
    void goToStudent(MouseEvent event) throws IOException {
        root1.setVisible(true);
        root2.setVisible(false);
        root3.setVisible(false);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userName.setText(user.getName());

        try {
            root1 = FXMLLoader.load(App.class.getResource("student.fxml"));
            root2 = FXMLLoader.load(App.class.getResource("apply.fxml"));
            root3 = FXMLLoader.load(App.class.getResource("recording.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        background.getChildren().addAll(root1,root2,root3);

        root1.setVisible(true);
        root2.setVisible(false);
        root3.setVisible(false);
    }
}
