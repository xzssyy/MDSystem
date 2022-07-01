package school.mdsystem.logistic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

class Data{
	String pro_id; //货物id
	String pro_name; //货物名称
	String pro_amout; //未配货的数量总和
	public Data(String id, String name, String amount) {
		this.pro_id=id;
		this.pro_name = name;
		this.pro_amout = amount;
	}
}

public class order_control {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bnt_search;
    
    @FXML
    private Button btn_delete_search;

    @FXML
    private TextArea order_list;

    @FXML
    private TextArea order_selllist;
    
    @FXML
    private DatePicker time;
    
    @FXML
    private Label pickbill_order; //订单界面跳转

    @FXML
    private Label pickbill_product; //货物界面跳转

    @FXML
    private Label pickbill_reply; //回复界面跳转
    
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
    

    //数据库    
    public static final String dburl = "jdbc:mysql://localhost:3306/java_data";
	public static final String user = "root";
	public static final String pwd = "Mysql@2021";
	
	//清空
    @FXML
    void delete_search(ActionEvent event) {
    	//order_list.getItems().clear(); 
    	//order_selllist.getItems().clear();
    	order_list.setText("");
    	order_selllist.setText("");
    	time.setValue(null);
    }
	
    @FXML
    void search(ActionEvent event) {
    	LocalDate order_time = time.getValue();
    	order_list.setText("");
    	order_selllist.setText("");
    	int k = 0, m = 0;
    	//当日未配货
    	try {
   			Connection conn = DriverManager.getConnection(dburl,user,pwd);
    		Statement stmt = conn.createStatement();
    		String sqlStr = "SELECT  product.product_id, product_name,SUM(amount) AS product_amount FROM product, orders WHERE orders.state = 0 AND product.product_id = orders.product_id AND " + 
    		"orders.time ='"+order_time+"' GROUP BY product_id";
   			stmt.execute(sqlStr);
   			ResultSet resultSet = stmt.executeQuery(sqlStr);
   			
			while (resultSet.next()){
				String a = resultSet.getString("product_name").toString();
				String b = resultSet.getString("product_id").toString();
				String c = resultSet.getString("product_amount").toString();
				//Data a = new Data(resultSet.getString("product_name").toString(),resultSet.getString("product_id").toString(),resultSet.getString("product_amount").toString());
				//order_list.getItems().add(a);
				order_list.setText(order_list.getText().toString()+a+"    货物ID："+b+"\n"+"未配货数量："+c+"\n"+"\n");
				k++;
			}
    		stmt.close();
   			conn.close();
   		} catch (SQLException change_e){
   			change_e.printStackTrace();
   		}
    	
    	//当日商品销售
    	try {
   			Connection conn = DriverManager.getConnection(dburl,user,pwd);
    		Statement stmt = conn.createStatement();
    		String sqlStr = "SELECT  product.product_id, product_name,SUM(amount) AS product_amount FROM product, orders WHERE product.product_id = orders.product_id AND " + 
    		"orders.time ='"+order_time+"' GROUP BY product_id";
   			stmt.execute(sqlStr);
   			ResultSet resultSet = stmt.executeQuery(sqlStr);
   			
			while (resultSet.next()){
				String a = resultSet.getString("product_name").toString();
				String b = resultSet.getString("product_id").toString();
				String c = resultSet.getString("product_amount").toString();
				//Data a = new Data(resultSet.getString("product_name").toString(),resultSet.getString("product_id").toString(),resultSet.getString("product_amount").toString());
				//order_list.getItems().add(a);
				order_selllist.setText(order_selllist.getText().toString()+a+"    货物ID："+b+"\n"+"销售总量： "+c+"\n"+"\n");
				m++;
			}
    		stmt.close();
   			conn.close();
   		} catch (SQLException change_e){
   			change_e.printStackTrace();
   		}
    	//弹窗
		
		if(k==0||m==0)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("通知");
			alert.setHeaderText(null);

			if(m==0)
				alert.setContentText("当日没有用户购买货物！");
			else		
				if(k==0)
					alert.setContentText("当日的配货已经全部完成！");

			alert.showAndWait();
		}
			
    }

    @FXML
    void initialize() {

    }
}
