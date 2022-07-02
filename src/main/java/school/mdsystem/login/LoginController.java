package school.mdsystem.login;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import school.mdsystem.App;
import school.mdsystem.student.SysException;
import school.mdsystem.student.controller.Controller;
import school.mdsystem.student.model.Open_time;
import school.mdsystem.student.model.Student;
import school.mdsystem.student.validator.Opened;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:登录页面控制器
 */
public class LoginController extends Controller {
    @FXML
    private TextField field_id;

    @FXML
    private TextField field_password;

    @FXML
    private Button register;

    @FXML
    public void login(){
        String id = field_id.getText().trim();
        String password = field_password.getText().trim();

        try {
            //是否delivery登录
            if(id.equals("del001")) {
                App.loginToDelivery();
                return;
            }

            if(id.equals("adm001")) {
                App.loginToAdmin();
                return;
            }

            if(id.equals("log001")) {
                App.loginToLogistic();
                return;
            }



            Student student = new Student(id, password);
            //设置用户以及登陆时间
            user = student;
            loginDate = LocalDate.now();

            //初始化时间列表和验证器
            Open_time.ini();
            Opened.ini(loginDate);
            App.loginToBase();
        } catch (SysException e) {
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //动态直接加载
    @FXML
    void addStudent(MouseEvent event) {
        Stage stage= new Stage();
        VBox background = new VBox();
        Scene scene = new Scene(background, 300, 280);


        Label l1 = new Label("用户名:"), l2 = new Label("密码:"), l3 = new Label("名字:"),
                l4= new Label("住址:"),l5 = new Label("电话:");

        l1.setPadding(new Insets(0,10,0,0));
        l2.setPadding(new Insets(0,21,0,0));
        l3.setPadding(new Insets(0,21,0,0));
        l4.setPadding(new Insets(0,21,0,0));
        l5.setPadding(new Insets(0,21,0,0));

        TextField textId = new TextField() , textPs = new TextField(), textName = new TextField(),
            textAddress = new TextField(), textPhone = new TextField();

        textId.setFocusTraversable(false);
        textId.setPromptText("字母开头，长度6-10");
        textPs.setFocusTraversable(false);
        textPs.setPromptText("长度6-20");

        Button register = new Button("注册");
        register.setOnMousePressed(mouseEvent -> {
            String id =  textId.getText();
            String password = textPs.getText();
            String name = textName.getText();
            String address = textAddress.getText();
            String phone = textPhone.getText();

            if(id.equals("") || password.equals("")){
                try {
                    throw new SysException("请不要空着!");
                } catch (SysException e) {
                    return;
                }
            }

            if(!checkName(id)){
                try {
                    throw new SysException("用户名格式错误");
                } catch (SysException e) {
                    textId.setText("");
                    textId.setFocusTraversable(false);
                    return;
                }
            }

            if(!checkPwd(password)){
                try {
                    throw new SysException("密码名格式错误");
                } catch (SysException e) {
                    textPs.setText("");
                    textPs.setFocusTraversable(false);
                    return;
                }
            }

            if(!checkPhone(phone)){
                try {
                    throw new SysException("别瞎输入电话！！");
                } catch (SysException e) {
                    textPhone.setText("");
                    textPhone.setFocusTraversable(false);
                    return;
                }
            }

            try {
                Student.register(id, password,name,address,phone);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "创建成功");
                alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
                    @Override
                    public void handle(DialogEvent dialogEvent) {
                        stage.close();
                    }
                });
                alert.show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (SysException e) {
                textId.setText("");
                textId.setFocusTraversable(true);
            }


        });



        HBox hBox1 = new HBox(l1,textId), hBox2 = new HBox(l2,textPs), hBox3 = new HBox(register),
        hBox4 = new HBox(l3, textName), hBox5 = new HBox(l4, textAddress), hBox6 = new HBox(l5, textPhone);
        hBox1.setPadding(new Insets(20, 0, 0, 20));
        hBox2.setPadding(new Insets(20, 0, 0, 20));
        hBox4.setPadding(new Insets(20, 0, 0, 20));
        hBox5.setPadding(new Insets(20, 0, 0, 20));
        hBox6.setPadding(new Insets(20, 0, 0, 20));

        hBox3.setAlignment(Pos.CENTER_RIGHT);
        hBox3.setPadding(new Insets(30, 70, 0, 0));


        background.getChildren().addAll(hBox1, hBox2, hBox4, hBox5, hBox6, hBox3);

        stage.setScene(scene);
        stage.setTitle("注册");
        stage.show();

    }

    public static boolean checkName(String name) {
        String regExp = "^[^0-9][\\w_]{5,9}$";
        if(name.matches(regExp)) {
            return true;
        }else {
            return false;
        }
    }

    public static boolean checkPwd(String pwd) {
        String regExp = "^[\\w_]{6,20}$";
        if(pwd.matches(regExp)) {
            return true;
        }
        return false;
    }

    public static boolean checkPhone(String pwd) {
        String regExp = "^[0-9]+$";
        if(pwd.matches(regExp)) {
            return true;
        }
        return false;
    }

    public static void testEnv() throws SysException, SQLException {
        user = new Student("xzssyy","py20021023");
        //设置当前日期
        loginDate = LocalDate.of(2022, 6, 16);
        LocalDate latestBuyDate = LocalDate.of(2022,6,20);
        Open_time.setBuyDate(latestBuyDate);
        Opened.ini(loginDate);
    }
}
