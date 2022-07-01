package school.mdsystem.logistic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

class Data_proposal{
	int id;
	String pro_info;
	String reply_info;
	
	public Data_proposal(int id, String s_info, String r_info) {
		this.id = id;
		this.pro_info = s_info;
		this.reply_info = r_info;
	}
}

public class reply_control {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_all_reply; //筛选条件-全部已回复

    @FXML
    private Button btn_all_unreply; //筛选条件-全部未回复

    @FXML
    private Button btn_myreply; //筛选条件-我的回复

  //  @FXML
  //  private ListView<Data_proposal> proposal_list; //
    
    @FXML
    private TextArea proposal_list;

    @FXML
    private TextField reply_search; //针对信息进行搜索

    @FXML
    private Button reply_select; //搜索

    @FXML
    private Button reply_undo_select; //清空
    
    @FXML
    private Button btn_reply;
    
    @FXML
    private Label pickbill_order; //订单界面跳转

    @FXML
    private Label pickbill_product; //货物界面跳转

    @FXML
    private Label pickbill_reply; //回复界面跳转
    

    @FXML
    void switch_reply(MouseEvent event) {
    	btn_reply.setOnMouseClicked(e->{
    		try{
    			student_reply_coffee a = new student_reply_coffee();
    			a.showscene();
    		}catch(Exception io_e) {io_e.printStackTrace();}
    	});
    }
    
  //订单界面跳转
    @FXML
    void jump_order(MouseEvent event) {
    	try {
    		logistic_order_view a = new logistic_order_view();
    		a.showscene();
    	}
    	catch(Exception e) {e.printStackTrace();}
    }

    @FXML
    void jump_product(MouseEvent event) {
    	try{
    		logistic a = new logistic();
    		a.showscene();
    	}catch(Exception e) {e.printStackTrace();}
    }

    @FXML
    void jump_reply(MouseEvent event) {
    	try{
    		student_reply a = new student_reply();
    		a.showscene();
    	}catch(Exception e) {e.printStackTrace();}
    }
    
    
    //数据库相关
    public static final String dburl = "jdbc:mysql://sh-cdb-70hmlmby.sql.tencentcdb.com:58587/sms";
	public static final String user = "root";
	public static final String pwd = "py20021023";

    @FXML
    void all_reply(ActionEvent event) {
    	btn_all_reply.setOnAction(e->{
    		//proposal_list.getItems().clear();
    		proposal_list.setText("");
    		try {
				Connection conn = DriverManager.getConnection(dburl,user,pwd);
				Statement stmt = conn.createStatement();
				String sqlStr0 = "SELECT proposal_id,proposal_info,reply_info FROM proposal WHERE reply_info IS NOT NULL;";
				ResultSet resultSet0 = stmt.executeQuery(sqlStr0);   
				int k=0;
				while (resultSet0.next()){
					//Data_proposal a = new Data_proposal(resultSet0.getInt("proposal_id"),resultSet0.getString("proposal_info").toString(),resultSet0.getString("reply_info").toString());
					String a = resultSet0.getInt("proposal_id")+"";
					String b = resultSet0.getString("proposal_info").toString();
					String c = resultSet0.getString("reply_info").toString();
					if(!a.isEmpty() || !b.isEmpty() || !c.isEmpty())
						//proposal_list.getItems().add(a)
						proposal_list.setText(proposal_list.getText()+"编号： "+a+"\n"+b+"\n"+c+"\n");
					k++;
				}
				if(k==0)
    			{
    				Alert alert = new Alert(AlertType.INFORMATION);
    				alert.setTitle("通知");
    				alert.setHeaderText(null);
    				alert.setContentText("没有符合该条件的回复信息！");
    				alert.showAndWait();
    			}
				stmt.close();
				conn.close();
			} catch (SQLException e1){
				e1.printStackTrace();
			}
    	});
    }

    @FXML
    void all_unreply(ActionEvent event) {
    	btn_all_unreply.setOnAction(e->{
    		//proposal_list.getItems().clear();
    		proposal_list.setText("");
    		try {
				Connection conn = DriverManager.getConnection(dburl,user,pwd);
				Statement stmt = conn.createStatement();
				String sqlStr = "SELECT proposal_id,proposal_info,reply_info FROM proposal WHERE reply_info IS NULL;";
				ResultSet resultSet = stmt.executeQuery(sqlStr);
				int k=0;
				while (resultSet.next()){
					//Data_proposal b = new Data_proposal(resultSet.getInt("proposal_id"),resultSet.getString("proposal_info").toString(),"      ");
					String a = resultSet.getInt("proposal_id")+"";
					String b = resultSet.getString("proposal_info").toString();
					//String c = resultSet.getString("reply_info").toString();
					if(!a.isEmpty() || !b.isEmpty())
						//proposal_list.getItems().add(a);
						proposal_list.setText(proposal_list.getText()+"编号： "+a+"\n"+b+"\n");
					//if(b!=null)
						//proposal_list.getItems().add(b);
					k++;
				}
				if(k==0)
    			{
    				Alert alert = new Alert(AlertType.INFORMATION);
    				alert.setTitle("通知");
    				alert.setHeaderText(null);
    				alert.setContentText("没有符合该条件的回复信息！");
    				alert.showAndWait();
    			}	
				stmt.close();
				conn.close();
			} catch (SQLException e2){
				e2.printStackTrace();
			}
    	});

    }

    @FXML
    void myreply(ActionEvent event) {
    	btn_myreply.setOnAction(e->{
    		//proposal_list.getItems().clear();
    		proposal_list.setText("");
			try {
				Connection conn = DriverManager.getConnection(dburl,user,pwd);
				Statement stmt = conn.createStatement();
				String sqlStr = "SELECT proposal_id,proposal_info,reply_info FROM proposal WHERE reply_info IS NOT NULL AND logistics_id = 'log001';";
				ResultSet resultSet = stmt.executeQuery(sqlStr);
				int k=0;
				while (resultSet.next()){
					//Data_proposal c = new Data_proposal(resultSet.getInt("proposal_id"),resultSet.getString("proposal_info").toString(),"      ");
					String a = resultSet.getInt("proposal_id")+"";
					String b = resultSet.getString("proposal_info").toString();
					String c = resultSet.getString("reply_info").toString();
					if(!a.isEmpty() || !b.isEmpty() || !c.isEmpty())
						//proposal_list.getItems().add(a);
						proposal_list.setText(proposal_list.getText()+"编号： "+a+"\n"+b+"\n"+c+"\n");
					//k++;
					//if(c!=null)
						//proposal_list.getItems().add(c);
					k++;
				}					
				if(k==0)
    			{
    				Alert alert = new Alert(AlertType.INFORMATION);
    				alert.setTitle("通知");
    				alert.setHeaderText(null);
    				alert.setContentText("没有符合该条件的回复信息！");
    				alert.showAndWait();
    			}	
				stmt.close();
				conn.close();
			} catch (SQLException e2){
				e2.printStackTrace();
			}
    	});
    }

	//筛选
    @FXML
    void reply_select(ActionEvent event) {
    	reply_select.setOnAction(e->{
    		//proposal_list.getItems().clear();
    		proposal_list.setText("");
    		if(!reply_search.getText().trim().isEmpty())
    		{
    			String keywords='%'+reply_search.getText().toString()+'%';
    			try {
    				Connection conn = DriverManager.getConnection(dburl,user,pwd);
    				Statement stmt = conn.createStatement();
    				String sqlStr = "SELECT proposal_id,proposal_info,reply_info FROM proposal WHERE proposal_info LIKE '"+keywords+"' ;";
    				ResultSet resultSet = stmt.executeQuery(sqlStr);
    				int k=0;
    				while (resultSet.next()){
    					//Data_proposal d = new Data_proposal(resultSet.getInt("proposal_id"),resultSet.getString("proposal_info").toString(),"      ");
    					//if(d!=null)
    						//proposal_list.getItems().add(d);
    					
    					String a = resultSet.getInt("proposal_id")+"";
    					String b = resultSet.getString("proposal_info");
    					String c = resultSet.getString("reply_info");
    					if(c==null)
    						c="#未回复";
    					if(!a.isEmpty() || !b.isEmpty())
    					{	
    						proposal_list.setText(proposal_list.getText()+"编号： "+a+"\n"+b+"\n"+c+"\n");
    					}
    					k++;
    				}					
    				if(k==0)
        			{
        				Alert alert = new Alert(AlertType.INFORMATION);
        				alert.setTitle("通知");
        				alert.setHeaderText(null);
        				alert.setContentText("没有符合该条件的回复信息！");
        				alert.showAndWait();
        			}	
    				stmt.close();
    				conn.close();
    			} catch (SQLException e2){
    				e2.printStackTrace();
    			}
    		}
    	});
    }

    //清空筛选
    @FXML
    void reply_undoselect(ActionEvent event) {
    	//proposal_list.getItems().clear();    	
    	proposal_list.setText("");
    	reply_search.setText(null);
    }

    @FXML
    void initialize() {    	
       /* proposal_list.setCellFactory(new Callback<ListView<Data_proposal>, ListCell<Data_proposal>>(){
        	@Override
        	public ListCell<Data_proposal> call(ListView<Data_proposal> param){
        		ListCell<Data_proposal> listcell = new ListCell<Data_proposal>() {
        			@Override
        			protected void updateItem(Data_proposal item, boolean empty) {
        				super.updateItem(item, empty);
        				if(empty == false) {
        					HBox hb = new HBox();
        					int i = item.id;
        					String s = item.pro_info;
        					String r = item.reply_info;
        					Label label0 = new Label(i+"");
        					Label label1 = new Label(s+"   ");  
        					Label label2 = new Label(r+"");      					
        					hb.getChildren().addAll(label0,label1,label2);
        					this.setGraphic(hb);
        				}
        			}
        		};
        		return listcell;
        	}
        });*/
    }

}
