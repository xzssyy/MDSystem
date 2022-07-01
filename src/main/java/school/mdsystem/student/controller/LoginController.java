package school.mdsystem.student.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import school.mdsystem.App;
import school.mdsystem.student.SysException;
import school.mdsystem.student.model.Open_time;
import school.mdsystem.student.model.Student;
import school.mdsystem.student.validator.Opened;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
public class LoginController extends Controller{
    @FXML
    private TextField field_id;

    @FXML
    private TextField field_password;

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

    public static void testEnv() throws SysException, SQLException {
        user = new Student("xzssyy","py20021023");
        //设置当前日期
        loginDate = LocalDate.of(2022, 6, 16);
        LocalDate latestBuyDate = LocalDate.of(2022,6,20);
        Open_time.setBuyDate(latestBuyDate);
        Opened.ini(loginDate);
    }
}
