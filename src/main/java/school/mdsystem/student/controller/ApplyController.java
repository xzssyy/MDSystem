package school.mdsystem.student.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import school.mdsystem.student.model.Apply;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:学生申请页面控制器
 */
public class ApplyController extends Controller implements Initializable{
    @FXML
    private Button submit;
    @FXML
    private TableView<Apply> table;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField typeArea;

    /**
     * @Description: 申请提交
     */
    @FXML
    public void onSubmit(MouseEvent mouseEvent) throws SQLException {
        String iType = typeArea.getText();
        String iApply = textArea.getText();
        Apply apply = new Apply(user.getId(), iType, iApply, "暂无回复");
        Apply.submit(apply);
        textArea.clear();
        ObservableList<Apply> fxlist = FXCollections.observableArrayList();
        fxlist.add(apply);
        table.getItems().addAll(fxlist);
    }


    /**
     * @Description: 获取申请信息
     */
    private  List<Apply> getData() throws SQLException, IOException {
        return Apply.getAllApplies(user.getId());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("[info] 加载申请记录");

        try {
            List<Apply> applyList = getData();
            ObservableList<Apply> list = FXCollections.observableArrayList();

            //添加列
            TableColumn type = new TableColumn("申请类型");
            type.prefWidthProperty().bind(table.widthProperty().divide(10));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));

            TableColumn info = new TableColumn("详细信息");
            info.prefWidthProperty().bind(table.widthProperty().divide(20 / 9));
            info.setCellValueFactory(new PropertyValueFactory<>("info"));

            TableColumn reply = new TableColumn("回复");
            reply.prefWidthProperty().bind(table.widthProperty().divide(20 / 9));
            reply.setCellValueFactory(new PropertyValueFactory<>("reply"));

            table.getColumns().addAll(type, info, reply);

            for (Apply apply : applyList) {
                list.add(apply);
            }

            table.setItems(list);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
