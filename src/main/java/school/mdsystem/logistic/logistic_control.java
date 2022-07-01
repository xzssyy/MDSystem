package school.mdsystem.logistic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class logistic_control {

//view
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button bnt_search;

    @FXML
    private ComboBox<String> combotype;
        
    ObservableList<String> options=
    		FXCollections.observableArrayList(
    				"日用百货",
    				"乳品烘培",
    				"休闲零食",
    				"抗疫物资"
    				);
    
    @FXML
    private Hyperlink order;

    @FXML
    private Hyperlink product;

    @FXML
    private ListView<String> product_list;

    @FXML
    private TextField product_name_text;

    @FXML
    private VBox product_show;

    @FXML
    private Button btn_refresh;

    @FXML
    private Button btn_show;
    
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
    
    
    //数据库相关
        public static final String dburl = "jdbc:mysql://sh-cdb-70hmlmby.sql.tencentcdb.com:58587/sms";
		public static final String user = "root";
		public static final String pwd = "py20021023";
		
		
    //显示所有货物
    @FXML
    void show_all_products(ActionEvent event) {
    	product_list.getItems().clear(); //清空区域内容
		product_show.getChildren().clear();
		try {
			Connection conn = DriverManager.getConnection(dburl,user,pwd);
			Statement stmt = conn.createStatement();
			String sqlStr = "SELECT product_name FROM product";
			ResultSet resultSet = stmt.executeQuery(sqlStr);
			while (resultSet.next()){
				product_list.getItems().add((String)resultSet.getString("product_name"));
			}
			stmt.close();
			conn.close();
			} catch (SQLException e1){
				e1.printStackTrace();
			}
    }

    //进入货物信息更新界面
    @FXML
    void switch_renew(ActionEvent event) {
    	btn_refresh.setOnAction(e->{
    		logistic_rewrite_oreo s = new logistic_rewrite_oreo();
    		//String pro_name = product_name_text.getText().toString();
    		s.showscene();
    	});
    }


	@FXML
	void search(ActionEvent event) {
		bnt_search.setOnAction(e->
		{
			product_list.getItems().clear(); //清空区域内容
			product_show.getChildren().clear();
			//未进行筛选
			if(product_name_text.getText().trim().isEmpty()) {
				if(combotype.getValue().isEmpty())
				{
					try {
					Connection conn = DriverManager.getConnection(dburl,user,pwd);
					Statement stmt = conn.createStatement();
					String sqlStr = "SELECT product_name FROM product";
					ResultSet resultSet = stmt.executeQuery(sqlStr);
					while (resultSet.next()){
						product_list.getItems().add((String)resultSet.getString("product_name"));
					}
					stmt.close();
					conn.close();
					} catch (SQLException e1){
						e1.printStackTrace();
					}
				}
				else
				{
					String c = combotype.getValue().toString();
					try {
						Connection conn = DriverManager.getConnection(dburl,user,pwd);
						Statement stmt = conn.createStatement();
						String sqlStr = "SELECT product_name FROM product WHERE product_category='"+c+"';";
						ResultSet resultSet = stmt.executeQuery(sqlStr);
						while (resultSet.next()){
							product_list.getItems().add((String)resultSet.getString("product_name"));
						}
						stmt.close();
						conn.close();
						} catch (SQLException e1){
							e1.printStackTrace();
						}
				}
			}
			else //进行货物名称查询--精确查询
			{
				String pro_name = product_name_text.getText();  
				try{
					Connection conn = DriverManager.getConnection(dburl,user,pwd);
					Statement stmt = conn.createStatement();
					String sqlStr = "SELECT product_name FROM product WHERE product_name = '"+pro_name+"' ";
					ResultSet resultSet = stmt.executeQuery(sqlStr);
					while (resultSet.next()){
						product_list.getItems().add((String)resultSet.getString("product_name"));
					}
					stmt.close();
					conn.close();
				} catch (SQLException e1){
					e1.printStackTrace();
				}
				
				try{
					Connection conn = DriverManager.getConnection(dburl,user,pwd);
					Statement stmt = conn.createStatement();
					String sqlStr = "SELECT* FROM product WHERE product_name = '"+pro_name+"' ";
					ResultSet resultSet = stmt.executeQuery(sqlStr);
					while (resultSet.next()){
						Label id = new Label("编号"+resultSet.getString("product_id").toString());
						Label category = new Label(resultSet.getString("product_category").toString());
						//TextField info = new TextField(resultSet.getString("product_info").toString());
						TextArea info = new TextArea(resultSet.getString("product_info").toString());
						info.setPrefHeight(40);
						info.setWrapText(true);
						info.setEditable(false);
						Label number = new Label("库存"+resultSet.getString("product_num").toString());
						Label price = new Label("单价"+resultSet.getDouble("product_price")+"");
						product_show.getChildren().addAll(id,category,info,number,price);
						
						Blob pic = resultSet.getBlob("product_pic");
			            byte[] bytes = null;
			            if (pic != null) {
			                BufferedInputStream is = null;
			                try {
			                    is = new BufferedInputStream(pic.getBinaryStream());
			                    bytes = new byte[(int) pic.length()];
			                    int len = bytes.length;
			                    int offset = 0;
			                    int read = 0;

			                    while (offset < len && (read = is.read(bytes, offset, len - offset)) >= 0) {
			                        offset += read;
			                    }
			                    //is.close();
			                } catch (SQLException e2) {
			                    throw new RuntimeException(e2);
			                } catch (IOException e3) {
			                    throw new RuntimeException(e3);
			                }

			                if(bytes != null) {
                                InputStream p = new ByteArrayInputStream(bytes);
			                Image img = new Image(p);
			                ImageView img_view = new ImageView(img);
			                product_show.getChildren().add(img_view);
			                }
			            }
						
						product_show.setMargin(id,new Insets(5));
						product_show.setMargin(category,new Insets(5));
						product_show.setMargin(info,new Insets(5));
						product_show.setMargin(number,new Insets(5));
						product_show.setMargin(price,new Insets(5));
						
					}
					stmt.close();
					conn.close();
				} catch (SQLException e1){
					e1.printStackTrace();
				}
				
			}
		}
		);
	}
	
	@FXML
    void initialize() {
        assert bnt_search != null : "fx:id=\"bnt_search\" was not injected: check your FXML file 'products_view.fxml'.";
        assert combotype != null : "fx:id=\"combotype\" was not injected: check your FXML file 'products_view.fxml'.";
        assert product_list != null : "fx:id=\"product_list\" was not injected: check your FXML file 'products_view.fxml'.";
        assert product_name_text != null : "fx:id=\"product_name_text\" was not injected: check your FXML file 'products_view.fxml'.";
        assert product_show != null : "fx:id=\"product_show\" was not injected: check your FXML file 'products_view.fxml'.";
        combotype.setItems(options);
	}
	
	
    
}
