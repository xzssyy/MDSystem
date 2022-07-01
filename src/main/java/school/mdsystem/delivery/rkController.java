package school.mdsystem.delivery;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.SQLException;


public class rkController {

    @FXML
    private Label mydelivery_win;

    @FXML
    private Label pickbills_win;

    @FXML
    private TableView<delsort> ranbtbl;

    @FXML
    private Label ranking_win;

    @FXML
    private TableColumn<delsort, String> rn0;

    @FXML
    private TableColumn<delsort, String> rn1;

    @FXML
    private TableColumn<delsort, String> rn2;

    @FXML
	 void jump_mydelivery(MouseEvent event) 
	 {
		 try
		 {
			 test_mydelivery tpb=new test_mydelivery();
			 tpb.showscene();
			 Stage aStage=(Stage)pickbills_win.getScene().getWindow();
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
				 Stage aStage=(Stage)pickbills_win.getScene().getWindow();
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
				 Stage aStage=(Stage)pickbills_win.getScene().getWindow();
		    	 aStage.close();
			 }
			 catch(Exception e){
				   e.printStackTrace();
			 }
	  
	  }
	  
	  @FXML
	    private void initialize()
	    {
		  try {
	    	tabshow4();}
		  catch(Exception e){
				   e.printStackTrace();
			 }
	    }
	  
	  public void tabshow4() throws SQLException{
			//String name=ctxt1.getText();
			del_func cfFunction2=new del_func();
			ObservableList<delsort> c = cfFunction2.del_sort();
				
			rn2.setCellValueFactory(new Callback<CellDataFeatures<delsort, String>, ObservableValue<String>>() {
				     public ObservableValue<String> call(CellDataFeatures<delsort, String> p) {
				         // p.getValue() returns the Person instance for a particular TableView row
				         return p.getValue().get_deliveryman_id();
				     }
			});
			
			rn1.setCellValueFactory(new Callback<CellDataFeatures<delsort, String>, ObservableValue<String>>() {
			     public ObservableValue<String> call(CellDataFeatures<delsort, String> p) {
			         // p.getValue() returns the Person instance for a particular TableView row
			         return p.getValue().get_deltot();
			     }
			});
			
			rn0.setCellValueFactory(new Callback<CellDataFeatures<delsort, String>, ObservableValue<String>>() {
			     public ObservableValue<String> call(CellDataFeatures<delsort, String> p) {
			         // p.getValue() returns the Person instance for a particular TableView row
			         return p.getValue().get_rank();
			     }
			});
			
			ranbtbl.setItems(c);
			
		}

}
