package school.mdsystem.delivery;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.Callback;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
public class mrController {
    @FXML
    private ChoiceBox<String> choSbnt;

    @FXML
    private TableView<String> deltbl;

    @FXML
    private TextArea detailtxt;

    @FXML
    private Label mydelivery_win;

    @FXML
    private TableColumn<String, String> oc1;

    @FXML
    private TableColumn<String, String> oc2;

    @FXML
    private Label pickbills_win;

    @FXML
    private Label ranking_win;

    @FXML
    private Button sbilbnt;

    @FXML
    private TextField sbiltxt;

    @FXML
    private TableView<String> udeltbl;

    @FXML
    private Button upbnt;

    @FXML
    private Button renewbnt;

    @FXML
    private TextField uptxt;

    String d;
    String exp = "del001";

    ObservableList<String> choboxitem = FXCollections.observableArrayList("未配送","配送中","已送达");

    @FXML
    private void initialize()
    {
        choSbnt.setItems(choboxitem);
        tabshow2();
        tabshow3();
    }

    @FXML
    void renewtbl(ActionEvent event) {
        try
        {
            tabshow2();
            tabshow3();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void jump_mydelivery(MouseEvent event)
    {
        try
        {
            test_mydelivery tpb=new test_mydelivery();
            tpb.showscene();
            Stage aStage=(Stage)renewbnt.getScene().getWindow();
            aStage.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    void jump_picbills(MouseEvent event)
    {
        try
        {
            test_pickbills tpb=new test_pickbills();
            tpb.showscene();
            Stage aStage=(Stage)renewbnt.getScene().getWindow();
            aStage.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void jump_ranking(MouseEvent event) {
        try
        {
            test_ranking tpb=new test_ranking();
            tpb.showscene();
            Stage aStage=(Stage)renewbnt.getScene().getWindow();
            aStage.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    void showcho(MouseEvent event) {

    }

    @FXML
    void showdetail(MouseEvent event) {
        try
        {
            d=sbiltxt.getText();
            del_func aFunction2=new del_func();

            String str = aFunction2.findbills2(d);
            detailtxt.setText(str);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void upstate(MouseEvent event) {/*根据多选框和订单号更新配送条目状态*/
        try
        {
            d=uptxt.getText();
            String sta = choSbnt.getValue();
            String stacvt="0";
            del_func aFunction2=new del_func();

            if(sta=="配送中") stacvt="0";
            else if(sta=="已送达") stacvt="1";

            int c=aFunction2.up_delsta(stacvt,d);
            if(c==1){
                //d=choBtxt2.getText();
                aFunction2.up_delsta(stacvt,d);
                JOptionPane.showMessageDialog(null, "修改成功");
            }
            else {
                JOptionPane.showMessageDialog(null, "修改非法", "Errorʾ", JOptionPane.ERROR_MESSAGE);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    @FXML/*展示该配送员未完成订单号*/
    void tabshow2() {//MouseEvent event
        try
        {
            del_func cfFunction2=new del_func();
            ObservableList<String> c = cfFunction2.fidudelb(exp);

            oc1.setCellValueFactory(new Callback<CellDataFeatures<String, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(CellDataFeatures<String, String> p) {
                    return new SimpleStringProperty(p.getValue());
                }
            });

            udeltbl.setItems(c);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML/*展示该配送员已完成订单号*/
    void tabshow3() {//MouseEvent event
        try
        {
            del_func cfFunction2=new del_func();
            ObservableList<String> c = cfFunction2.fiddelb(exp);

            oc2.setCellValueFactory(new Callback<CellDataFeatures<String, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(CellDataFeatures<String, String> p) {
                    return new SimpleStringProperty(p.getValue());
                }
            });

            deltbl.setItems(c);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
