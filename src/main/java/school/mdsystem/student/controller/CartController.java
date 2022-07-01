package school.mdsystem.student.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import school.mdsystem.student.SysException;
import school.mdsystem.student.listener.RefreshListener;
import school.mdsystem.student.model.Bill;
import school.mdsystem.student.model.Order;
import school.mdsystem.student.validator.Purchased;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
public class CartController extends Controller implements Initializable {
    @FXML
    private TableView<Order> table;

    @FXML
    private BorderPane view;

    @FXML
    private Label priceView;

    @FXML
    private Button submit;

    private RefreshListener listener;


    Double addAllPrice;

    @FXML
    void submitBill(MouseEvent event) throws SQLException {
        Bill bill = new Bill(user.getId(), loginDate, addAllPrice);
        Bill.submit(bill);
        Purchased.ini(true);
        listener.refreshCarriage();

        Stage stage = (Stage) view.getScene().getWindow();
        stage.close();

        //向recording加一条数据

        waitBillList.add(bill);
    }


    void changeAmount(TableColumn.CellEditEvent<Order, Integer> event) throws SQLException {

        //获取修改列
        TableColumn<Order, Integer> tc = event.getTableColumn();

        //获取监听行对象
        Order order = event.getRowValue();
        //获取新数量


        //更改模型
        try {
            Number new_amount = event.getNewValue();
            int diffNum = order.update(new_amount.intValue());

            //修改外部展示界面
            listener.refreshCarriage();


            //刷新总价
            addAllPrice = addAllPrice - diffNum * order.getSinglePrice();
            priceView.setText(String.format("%.2f", addAllPrice) + "元");


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INF");
            alert.setHeaderText("修改数量");
            alert.setContentText("成功!");
            alert.showAndWait();
        } catch (SysException e) {
        } catch (Exception e1) {
            System.out.println(123);
        } finally {
            //刷新购物车
            table.refresh();
        }

        //System.out.println(new_amount);
    }


    private List<Order> getData(LocalDate date) throws SQLException {
        return Order.getAllOrder(date, user.getId());
    }

    public void setListener(RefreshListener listener) {
        this.listener = listener;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("[info] 加载购物车");
        addAllPrice = 0.0;



        try {
            List<Order> orderList = getData(loginDate);
            ObservableList<Order> list = FXCollections.observableArrayList();

            //手动添加列
            TableColumn productName = new TableColumn("货物名");
            productName.setCellValueFactory(new PropertyValueFactory<>("p_name"));

            TableColumn time = new TableColumn("时间");
            time.setCellValueFactory(new PropertyValueFactory<>("time"));


            TableColumn amount = new TableColumn("数量");
            amount.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Order, Number>, ObservableValue<Number>>() {
                @Override
                public ObservableValue<Number> call(TableColumn.CellDataFeatures<Order, Number> orderNumberCellDataFeatures) {
                    return new SimpleIntegerProperty(orderNumberCellDataFeatures.getValue().getAmount());
                }
            });

            amount.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));

            amount.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
                @Override
                public void handle(TableColumn.CellEditEvent cellEditEvent) {
                    try {
                        changeAmount(cellEditEvent);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            TableColumn singlePrice = new TableColumn("单价");
            singlePrice.setCellValueFactory(new PropertyValueFactory<>("singlePrice"));


            TableColumn allPrice = new TableColumn("总价");
            allPrice.setCellValueFactory(new PropertyValueFactory<>("s_allPrice"));


            TableColumn actionCol = new TableColumn("Action");
            actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

            Callback<TableColumn<Order, String>, TableCell<Order, String>> cellFactory
                    =
                    new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {
                        @Override
                        public TableCell call(final TableColumn<Order, String> param) {
                            final TableCell<Order, String> cell = new TableCell<Order, String>() {

                                final Button btn = new Button("删除");

                                @Override
                                public void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty) {
                                        setGraphic(null);
                                        setText(null);
                                    } else {
                                        btn.setOnAction(event -> {
                                            //System.out.println(getIndex());
                                            Order order = getTableView().getItems().get(getIndex());
                                            //模型删除
                                            try {
                                                //System.out.println(order.toString());

                                                int diffNum = order.delete();
                                                addAllPrice = addAllPrice - diffNum * order.getSinglePrice();
                                                priceView.setText(String.format("%.2f", addAllPrice) + "元");
                                                listener.refreshCarriage();


                                            } catch (SQLException e) {
                                            } catch (SysException e) {
                                            }
                                            list.remove(getIndex());
                                            //System.out.println(getIndex());
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

            table.getColumns().addAll(productName, time, amount, singlePrice, actionCol);


            for (Order order : orderList) {
                addAllPrice += order.getAllPrice();
                System.out.println(order.toString());

                if (order.getAmount() > 0)
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
        priceView.setText(String.format("%.2f", addAllPrice) + "元");

    }
}
