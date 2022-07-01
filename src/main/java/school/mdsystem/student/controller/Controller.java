package school.mdsystem.student.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import school.mdsystem.student.model.Bill;
import school.mdsystem.student.model.Student;

import java.time.LocalDate;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
public class Controller {
    protected static Student user;

    protected static LocalDate loginDate = null;//保存登录时的年月日，方便查找已加入购物车的物品

    static ObservableList<Bill> waitBillList = FXCollections.observableArrayList();

}
