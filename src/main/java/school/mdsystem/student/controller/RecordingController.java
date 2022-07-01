package school.mdsystem.student.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import school.mdsystem.App;
import school.mdsystem.student.model.Bill;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
public class RecordingController extends Controller implements Initializable {
    @FXML
    private TableView<Bill> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("[info] 加载购买记录");

        try {
            List<Bill> billList =  Bill.getAllBill(user.getId());
            //ObservableList<Bill> list = FXCollections.observableArrayList();

            //手动添加列
            TableColumn time = new TableColumn("购买时间");
            time.prefWidthProperty().bind(table.widthProperty().divide(3));
            time.setCellValueFactory(new PropertyValueFactory<>("time"));


            TableColumn allPrice = new TableColumn("总价");
            allPrice.prefWidthProperty().bind(table.widthProperty().divide(3));
            allPrice.setCellValueFactory(new PropertyValueFactory<>("s_expense"));


            TableColumn actionCol = new TableColumn("详细信息");
            actionCol.prefWidthProperty().bind(table.widthProperty().divide(3));
            actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

            Callback<TableColumn<Bill, String>, TableCell<Bill, String>> cellFactory
                    =
                    new Callback<TableColumn<Bill, String>, TableCell<Bill, String>>() {
                        @Override
                        public TableCell call(final TableColumn<Bill, String> param) {
                            final TableCell<Bill, String> cell = new TableCell<Bill, String>() {

                                final Button btn = new Button("查看");

                                @Override
                                public void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty) {
                                        setGraphic(null);
                                        setText(null);
                                    } else {
                                        btn.setOnAction(event -> {

                                            //不在规定时间


                                            //在规定时间内

                                            //设置要展示的时间
                                            Bill bill = getTableView().getItems().get(getIndex());
                                            DetailController.setTargetDate(bill.getTime());
                                            Stage stageDetail = new Stage();
                                            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("detail.fxml"));

                                            Scene scene = null;
                                            try {
                                                scene = new Scene(fxmlLoader.load(), 600, 327);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }

                                            stageDetail.setScene(scene);
                                            stageDetail.setResizable(false);
                                            try {
                                                stageDetail.show();
                                            }catch (IllegalStateException e){

                                            }

                                        });
                                        setGraphic(btn);
                                        setText(null);
                                    }
                                }
                            };
                            return cell;
                        }
                    };

            actionCol.setCellFactory(cellFactory);

            table.getColumns().addAll( time, allPrice,actionCol);

            for(Bill bill : billList){
                waitBillList.add(bill);
            }


            table.setItems(waitBillList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
