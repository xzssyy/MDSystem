package school.mdsystem.logistic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class reply_coffee_control {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn_reply_return; //返回

    @FXML
    private ComboBox<String> proposal_choose;

    @FXML
    private TextArea proposal_content; //显示申请内容

    @FXML
    private Button reply; //回复
    
    @FXML
    private Button btn_reply_delete; //清空文本框内容

    @FXML
    private TextArea reply_textarea; //输入
    
    //数据库相关
    public static final String dburl = "jdbc:mysql://localhost:3306/java_data";
	public static final String user = "root";
	public static final String pwd = "Mysql@2021";
	

    @FXML
    void show_proposal(MouseEvent event) {
    	proposal_content.setOnMouseClicked(e->{
    		proposal_content.setText("");
    		String p_id = proposal_choose.getValue();
    		int id = Integer.parseInt(p_id);
    		
    		try {
				Connection conn = DriverManager.getConnection(dburl,user,pwd);
				Statement stmt = conn.createStatement();
				String sqlStr = "SELECT proposal_info FROM proposal WHERE proposal_id = '"+id+"';";
				ResultSet resultSet = stmt.executeQuery(sqlStr);
				while (resultSet.next()){
					proposal_content.setText(resultSet.getString("proposal_info"));
				}
				stmt.close();
				conn.close();
			} catch (SQLException e1){
				e1.printStackTrace();
			}
    	});
    }

    @FXML
    void reply(ActionEvent event) {
    	reply.setOnAction(e->{
    		String p_id = proposal_choose.getValue().toString();
    		int id = Integer.parseInt(p_id);
    
    		if(!reply_textarea.getText().trim().isEmpty())
    		{	try {
				Connection conn = DriverManager.getConnection(dburl,user,pwd);
				Statement stmt = conn.createStatement();
				String sqlStr = "UPDATE proposal SET reply_info ='"+reply_textarea.getText().toString()+"',logistics_id='log001' WHERE proposal_id = '"+id+"';";
				stmt.execute(sqlStr);
				stmt.close();
				conn.close();
			} catch (SQLException e1){
				e1.printStackTrace();
			}
   
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("通知");
    	alert.setHeaderText(null);
    	alert.setContentText("回复成功！");
    	alert.showAndWait();
    	}
    		});
    }
    

    @FXML
    void reply_delete(ActionEvent event) {
    	reply_textarea.clear();
    }

    @FXML
    void initialize() {
       
    	ObservableList<String> options_id = FXCollections.observableArrayList();
    	try {
			Connection conn = DriverManager.getConnection(dburl,user,pwd);
			Statement stmt = conn.createStatement();
			String sqlStr = "SELECT proposal_id FROM proposal WHERE reply_info IS NULL";
			ResultSet resultSet = stmt.executeQuery(sqlStr);
			while (resultSet.next()){
				options_id.add(resultSet.getInt("proposal_id")+"");
			}
			stmt.close();
			conn.close();
		} catch (SQLException change_e){
			change_e.printStackTrace();
		}
    	proposal_choose.setItems(options_id);
    	
        proposal_content.setWrapText(true);
        reply_textarea.setWrapText(true);
    }

}
