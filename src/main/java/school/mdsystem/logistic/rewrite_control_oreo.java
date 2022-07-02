package school.mdsystem.logistic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import school.mdsystem.student.SysException;
import school.mdsystem.student.utility.*;

public class rewrite_control_oreo {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane rewrite_pane;
    
    @FXML
    private TextField add_product_id; //输入id

    @FXML
    private TextField add_product_name; //输入名称
    
    @FXML
    private TextField pro_price; //价格

    @FXML
    private CheckBox ckb_add; //按钮-状态
    
    @FXML
    private ComboBox<String> product_choose; //选择货物名称

    @FXML
    private Button rewrite_btn_change; //修改

    @FXML
    private Button btn_content_delete; //清空填写内容

    @FXML
    private Label rewrite_product; //产品

    @FXML
    private TextField text_information; //product_info

    @FXML
    private TextField text_number; //product_num

    @FXML
    private TextField image_url; //图片路径

    @FXML
    private Button rewrite_oreo_btn_image; //上传图片
    
    @FXML
    private ImageView image; //图片

    @FXML
    private Button rewrite_oreo_btn_vedio; //上传视频

    @FXML
    private TextField vedio_url; //视频路径   
    
    @FXML
    private ComboBox<String> type_choose;
    ObservableList<String> options=
    		FXCollections.observableArrayList(
    				"日用百货",
    				"乳品烘培",
    				"休闲零食",
    				"抗疫物资"
    				);
    
    //清空填写内容
    @FXML
    void content_delete(ActionEvent event) {
    	add_product_name.setText(null);
    	add_product_id.setText(null);
    	text_information.setText(null);
    	text_number.setText(null);
    	image.setImage(null);
    	image_url.setText(null);
    	vedio_url.setText(null);
    	pro_price.setText(null);
    	product_choose.setValue(null);
    	type_choose.setValue(null);
    	ckb_add.setSelected(false);
    }
    
    
//上传图片
    @FXML
    void image_upload(ActionEvent event) {
    	rewrite_oreo_btn_image.setOnAction(e->{
    		Stage stage = new Stage();
    		FileChooser fc = new FileChooser();
    		final String[] a = {new String()};
    		
    		fc.setTitle("上传图片");
    		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("图片类型", "*.jpg","*.bmp","*.png","*jpeg"));
    		fc.setInitialDirectory(new File("C:\\"));
    		List<File> list = fc.showOpenMultipleDialog(stage);
    		list.forEach(new Consumer<File>() {
    			@Override
    			public void accept(File file) {
    				a[0] = file.getAbsolutePath();
    				image_url.setText(a[0]);
    				Image pic = new Image("file:\\"+a[0],80,80,false,false);
    				image.setImage(pic);
    			}
    		});
    	});
    	
    }
    
//数据库    
    public static final String dburl = "jdbc:mysql://sh-cdb-70hmlmby.sql.tencentcdb.com:58587/sms";
	public static final String user = "root";
	public static final String pwd = "py20021023";
	
//信息
    @FXML
    void change(ActionEvent event) {
		rewrite_btn_change.setOnAction(e->{
			boolean state0 = false,state1 = false, state2=false;    
			boolean chk = ckb_add.isSelected();
		
		if(chk)  //增加
		{
			String pro_name = add_product_name.getText().toString();
			String pro_id = add_product_id.getText().toString();
			String info = text_information.getText();
			String type = type_choose.getValue().toString();
			String per = pro_price.getText();
			double price =Double.parseDouble(per);
			if(pro_price.getText().isEmpty())
				price=0.0;
			int num = Integer.parseInt (text_number.getText());
			if(text_number.getText().isEmpty())
				num=0;
			
			//信息		
			if(!add_product_name.getText().trim().isEmpty() && !add_product_id.getText().trim().isEmpty())
			{
				try {
					Connection conn = DriverManager.getConnection(dburl,user,pwd);
					Statement stmt = conn.createStatement();
					String sqlStr = "INSERT INTO product VALUES('"+pro_id+"','"+pro_name+"','"+info+"','"+type+"','"+num+"','"+price+"',NULL,NULL)";
					stmt.execute(sqlStr);
					stmt.close();
					conn.close();
				} catch (SQLException change_e){
					change_e.printStackTrace();
				}
				
			}
    	}
		else //修改
		{

			String product = null;
			try{
				//解决报错，逻辑好奇怪...
				product = product_choose.getValue();
				if(product == null)
					throw new SysException("请选择一个商品");
			}catch (SysException ex) {
			}

			//(product)

			//修改介绍信息		
			if(!text_information.getText().trim().isEmpty())
			{
				String info = text_information.getText();
				try {
					Connection conn = DriverManager.getConnection(dburl,user,pwd);
					Statement stmt = conn.createStatement();
					String sqlStr = "UPDATE product SET product_info= '"+info+"' WHERE product_name= '"+product+"' ";
					stmt.execute(sqlStr);
					stmt.close();
					conn.close();
				} catch (SQLException change_e){
					change_e.printStackTrace();
				}
			}
			//修改数量
			if(!text_number.getText().trim().isEmpty())
			{
				int num = Integer.parseInt (text_number.getText());
				try {
					Connection conn = DriverManager.getConnection(dburl,user,pwd);
					Statement stmt = conn.createStatement();
					String sqlStr = "UPDATE product SET product_num= '"+num+"' WHERE product_name= '"+product+"' ";
					stmt.execute(sqlStr);
					stmt.close();
					conn.close();
				} catch (SQLException change_e){
				change_e.printStackTrace();
				}
			
			}
		
			if(!pro_price.getText().trim().isEmpty())
			{
				double price = Double.parseDouble (pro_price.getText());
				try {
					Connection conn = DriverManager.getConnection(dburl,user,pwd);
					Statement stmt = conn.createStatement();
					String sqlStr = "UPDATE product SET product_price= '"+price+"' WHERE product_name= '"+product+"' ";
					stmt.execute(sqlStr);
					stmt.close();
					conn.close();
				} catch (SQLException change_e){
					change_e.printStackTrace();
				}
			
			}
		
			//数据库更新图片
			if(!image_url.getText().trim().isEmpty())
			{
				/*
				try {
					Connection conn = DriverManager.getConnection(dburl,user,pwd);
					Statement s = conn.createStatement();
					InputStream in = new FileInputStream(image_url.getText().toString());
					String sql0 = "UPDATE product SET product_pic='"+in+"' WHERE product_name = '"+product+"' ";
					s.execute(sql0);
				}catch(Exception img_e) {
					img_e.printStackTrace();
				}*/
				
				/**
				* @Description: 调用学生端图片上传功能
				*/
				try {
					ImageTool.upLoadPicture(image_url.getText().toString(), product);
				} catch (IOException ex) {
				} catch (SQLException ex) {
				}


			}
			
		}
	
		//弹窗-修改完成
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("通知");
		alert.setHeaderText(null);
		alert.setContentText("修改成功！");
		alert.showAndWait();
		});
   }
   
    
    @FXML
    void initialize() {
    	type_choose.setItems(options);
    	
    	ObservableList<String> options_pro=FXCollections.observableArrayList();
    	try {
			Connection conn = DriverManager.getConnection(dburl,user,pwd);
			Statement stmt = conn.createStatement();
			String sqlStr = "SELECT product_name FROM product";
			ResultSet resultSet = stmt.executeQuery(sqlStr);
			while (resultSet.next()){
				options_pro.add((String)resultSet.getString("product_name"));
			}
			stmt.close();
			conn.close();
		} catch (SQLException change_e){
			change_e.printStackTrace();
		}
    	
    	product_choose.setItems(options_pro);
    }
       
}
