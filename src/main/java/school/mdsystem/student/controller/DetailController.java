package school.mdsystem.student.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import school.mdsystem.student.SysException;
import school.mdsystem.student.model.Order;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
public class DetailController extends Controller implements Initializable {

    @FXML
    private Label priceView;

    @FXML
    private TableView<Order> table;

    @FXML
    private BorderPane view;

    private static LocalDate targetDate;

    public static void setTargetDate(LocalDate date){
        targetDate = date;
    }

    private List<Order> getData(LocalDate date) throws SQLException {
        return Order.getAllOrder(date, user.getId());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("[info] 加载详情");

        double addAllPrice = 0;
        try {
            List<Order> orderList = getData(targetDate);
            ObservableList<Order> list = FXCollections.observableArrayList();

            //手动添加列
            TableColumn productName = new TableColumn("货物名");
            productName.setCellValueFactory(new PropertyValueFactory<>("p_name"));

            TableColumn time = new TableColumn("时间");
            time.setCellValueFactory(new PropertyValueFactory<>("time"));


            TableColumn amount = new TableColumn("数量");
            amount.setCellValueFactory(new PropertyValueFactory<>("amount"));


            TableColumn singlePrice = new TableColumn("单价");
            singlePrice.setCellValueFactory(new PropertyValueFactory<>("singlePrice"));


            TableColumn allPrice = new TableColumn("总价");
            allPrice.setCellValueFactory(new PropertyValueFactory<>("s_allPrice"));


            table.getColumns().addAll(productName, time, amount, singlePrice, allPrice);


            for(Order order : orderList){
                addAllPrice+=order.getAllPrice();

                if(order.getAmount() > 0)
                    list.add(order);
                else
                    order.delete();
            }


            table.setItems(list);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (SysException e) {
            throw new RuntimeException(e);
        }


        //设置总价
        priceView.setText(String.format("%.2f", addAllPrice)+"元");
    }
}
