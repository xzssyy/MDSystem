package school.mdsystem.delivery;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class pbController {

    @FXML
    private TextField choBtxt;

    @FXML
    private TextField choBtxt2;
    
    @FXML
    private TableColumn<deliveryinfo, String> cm01;

    @FXML
    private TableColumn<deliveryinfo, String> cm02;

    @FXML
    private TableColumn<deliveryinfo, String> cm03;

    @FXML
    private TableColumn<deliveryinfo, String> cm04;

    @FXML
    private Label mydelivery_win;

    @FXML
    private Button picbbut;

    @FXML
    private Label pickbills_win;

    @FXML
    private TableView<deliveryinfo> pictbl;

    @FXML
    private Label ranking_win;

    @FXML
    private Slider sl00;
    
    @FXML
    private Button renewbnt;
    
    String d;
    String exp="del001";
	
    
    @FXML
    private void initialize()
    {
    	try {tabshow1();} 
    	catch(Exception e){
			   e.printStackTrace();
		 }

    }
    
    @FXML
    void renewtbl(ActionEvent event) {//刷新表格
    	try {tabshow1();} 
    	catch(Exception e){
			   e.printStackTrace();
		 }
    }

    
	@FXML
    void up_delrec(ActionEvent event) throws IOException, SQLException{
		d=choBtxt.getText();
		del_func aFunction2=new del_func();
		int c=aFunction2.fid_delrec(d);
		if(c==1){
			//d=choBtxt2.getText();
			aFunction2.up_delrec(exp);
			aFunction2.up_ordsta(d);
			JOptionPane.showMessageDialog(null, "接单成功");
		}
		else {
			JOptionPane.showMessageDialog(null, "该订单号非法", "Errorʾ", JOptionPane.ERROR_MESSAGE);
		}
    }
	
	 @FXML
	 void jump_mydelivery(MouseEvent event) 
	 {
		 try
		 {
			 test_mydelivery tpb=new test_mydelivery();
			 tpb.showscene();
			 Stage aStage=(Stage)picbbut.getScene().getWindow();
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
			  	 tabshow1();
				 tpb.showscene();
				 Stage aStage=(Stage)picbbut.getScene().getWindow();
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
				 Stage aStage=(Stage)picbbut.getScene().getWindow();
		    	 aStage.close();
			 }
			 catch(Exception e){
				   e.printStackTrace();
			 }
	  
	  }
	
		
		public void tabshow1() throws SQLException{
			
			del_func cfFunction2=new del_func();
			ObservableList<deliveryinfo> c = cfFunction2.findbills();
				
			cm01.setCellValueFactory(new Callback<CellDataFeatures<deliveryinfo, String>, ObservableValue<String>>() {
				     public ObservableValue<String> call(CellDataFeatures<deliveryinfo, String> p) {
				         
				         return p.getValue().get_billid();
				     }
			});
			
			cm02.setCellValueFactory(new Callback<CellDataFeatures<deliveryinfo, String>, ObservableValue<String>>() {
			     public ObservableValue<String> call(CellDataFeatures<deliveryinfo, String> p) {
			        
			         return p.getValue().get_sname();
			     }
			});
			
			cm03.setCellValueFactory(new Callback<CellDataFeatures<deliveryinfo, String>, ObservableValue<String>>() {
			     public ObservableValue<String> call(CellDataFeatures<deliveryinfo, String> p) {
			         
			         return p.getValue().get_sphonenum();
			     }
			});
			
			cm04.setCellValueFactory(new Callback<CellDataFeatures<deliveryinfo, String>, ObservableValue<String>>() {
			     public ObservableValue<String> call(CellDataFeatures<deliveryinfo, String> p) {
			         
			         return p.getValue().get_saddress();
			     }
			});
				
			pictbl.setItems(c);
			
		}
	
	
	
	
	
	
	


}


