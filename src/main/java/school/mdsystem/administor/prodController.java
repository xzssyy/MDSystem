package school.mdsystem.administor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;

public class prodController {

    @FXML
    private Button addbut;

    @FXML
    private Button advbnt;

    @FXML
    private ChoiceBox<String> choBox;

    @FXML
    private Button delbnt;

    @FXML
    private TextArea detailtxt;

    @FXML
    private Button fidbnt;

    @FXML
    private Label hdldialo_win;

    @FXML
    private Label hdlprod_win;

    @FXML
    private Label hdluser_win;

    @FXML
    private TextField idtxt;

    @FXML
    private TextField infotxt;

    @FXML
    private TextField nametxt;

    @FXML
    private TextField numtxt;

    @FXML
    private TextField pricetxt;

    @FXML
    private Button updbnt;
    
    @FXML
    private Label hdltime_win;
    
    ObservableList<String> choboxitem = FXCollections.observableArrayList("日用百货","乳品烘培","休闲零食","抗议物资");
    
    @FXML
    private void initialize()
    {
    	choBox.setItems(choboxitem);
    }

    @FXML
    void addprod(MouseEvent event) {
    	try
    	{
    		String id,nam,info,cate,num,prc;
        	adm_func oper=new adm_func();
        	
        	cate = choBox.getValue();
        	id=idtxt.getText(); nam=nametxt.getText();
        	info=infotxt.getText();num=numtxt.getText();
        	prc=pricetxt.getText();
        	oper.add_prod(id,nam,info,cate,num,prc);
        	JOptionPane.showMessageDialog(null, "成功添加一条货物数据");
        	
    	}catch(Exception e){
			   e.printStackTrace();
		 }
    }

    @FXML
    void delprod(MouseEvent event) {
    	try
    	{
    		String id = idtxt.getText(); 
        	adm_func oper=new adm_func();
        	oper.delt_prod(id);
        	JOptionPane.showMessageDialog(null, "删除成功");

    	}catch(Exception e){
			   e.printStackTrace();
		 }
    }

    @FXML
    void fidprod(MouseEvent event) {
    	try
    	{
    		String id=idtxt.getText();
    		adm_func oper=new adm_func();
        	String str = "";
        	str = oper.fid_prod(id);
        	detailtxt.setText(str);
        	
    	}catch(Exception e){
			   e.printStackTrace();
		 }
    }


    @FXML
    void givadv(MouseEvent event) {

    }

    @FXML
    void jump_prod(MouseEvent event) {//页面跳转到货物管理
    	try
		 {
			 test_hdlprod tpb=new test_hdlprod();
			 tpb.showscene();
			 Stage aStage=(Stage)addbut.getScene().getWindow();
	    	 aStage.close();
		 }
		 catch(Exception e){
			   e.printStackTrace();
		 }
    }

    @FXML
    void jump_psal(MouseEvent event) {//页面跳转到申请管理
    	try
		 {
			 test_hdldiag tpb=new test_hdldiag();
			 tpb.showscene();
			 Stage aStage=(Stage)addbut.getScene().getWindow();
	    	 aStage.close();
		 }
		 catch(Exception e){
			   e.printStackTrace();
		 }
    }
    
    @FXML
    void jump_time(MouseEvent event) {
    	try
		 {
			 test_hdltime tpb=new test_hdltime();
			 tpb.showscene();
			 Stage aStage=(Stage)addbut.getScene().getWindow();
	    	 aStage.close();
		 }
		 catch(Exception e){
			   e.printStackTrace();
		 }
    }

    @FXML
    void jump_users(MouseEvent event) {//页面跳转到用户管理
    	try
		 {
			 test_hdlusers tpb=new test_hdlusers();
			 tpb.showscene();
			 Stage aStage=(Stage)addbut.getScene().getWindow();
	    	 aStage.close();
		 }
		 catch(Exception e){
			   e.printStackTrace();
		 }
    }


    @FXML
    void upprod(MouseEvent event) {
    	try
    	{
    		adm_func oper=new adm_func();
	    	String id=idtxt.getText();
	    	String num=numtxt.getText();
        	oper.up_prod_num(id,num);
        	JOptionPane.showMessageDialog(null, "更改库存成功");
        	
    	}catch(Exception e){
			   e.printStackTrace();
		 }
    }


}
