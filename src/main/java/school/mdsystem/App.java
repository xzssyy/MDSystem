package school.mdsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import school.mdsystem.administor.test_hdlusers;
import school.mdsystem.delivery.test_pickbills;
import school.mdsystem.logistic.logistic;
import school.mdsystem.student.SysException;
import school.mdsystem.login.LoginController;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:模块跳转
 */
public class App extends Application {

    public static Stage stage;



    @Override
    public void start(Stage stage) throws IOException {

        this.stage = stage;

        //testStart();              //学生端测试
        productionStart();        //登录
        //loginToDelivery();

        //loginToAdmin();
        //loginToLogistic();
    }

    /**
     * @Description: 学生端测试;使用用户xzssyy登录
     */
    public void testStart() {
        try {
            LoginController.testEnv();
        } catch (SysException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            loginToBase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void productionStart() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 327);
        stage.setTitle("Hello!");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @Description: 学生端基页面
     */
    public static void loginToBase() throws IOException {
        if(stage != null)
            stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("base.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1325, 750);
        stage.setTitle("Hello!");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setScene(scene);

        stage.show();
    }



    public static void loginToDelivery() throws IOException {
        stage.close();
        test_pickbills tpb = new test_pickbills();
        tpb.showscene();
    }

    public static void loginToAdmin() throws IOException {
        stage.close();
        test_hdlusers tpb = new test_hdlusers();
        tpb.showscene();
    }

    public static void loginToLogistic() throws IOException {
        stage.close();
        logistic tpb = new logistic();
        tpb.showscene();
    }

    public static void main(String[] args) {
        launch();
    }
}