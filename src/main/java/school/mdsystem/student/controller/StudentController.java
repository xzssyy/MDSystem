package school.mdsystem.student.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import school.mdsystem.App;
import school.mdsystem.student.SysException;
import school.mdsystem.student.listener.ProductCardListener;
import school.mdsystem.student.listener.RefreshListener;
import school.mdsystem.student.model.Bill;
import school.mdsystem.student.model.Open_time;
import school.mdsystem.student.model.Order;
import school.mdsystem.student.model.Product;
import school.mdsystem.student.validator.Opened;
import school.mdsystem.student.validator.Purchased;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
public class StudentController extends Controller implements Initializable {
    @FXML
    private Label category;

    @FXML
    private VBox chosenCard;

    @FXML
    private GridPane grid;

    @FXML
    private ImageView img;

    @FXML
    private Label inf;

    @FXML
    private Label price;

    @FXML
    private Label productName;

    @FXML
    private ScrollPane scroll;


    @FXML
    private Pane shoppingCart;

    @FXML
    private TextField purchaseNums;

    @FXML
    private Button buy;

    @FXML
    private Label leftNums;

    @FXML
    private Label isBrought;

    @FXML
    private Label latestTime;

    private Product waitProduct = null;

    private List<Product> products = new ArrayList<>();

    private ProductCardListener listener;

    private RefreshListener listenerRefresh;


    @FXML
    private AnchorPane market;

    private Stage vStage;

    private MediaPlayer mediaPlayer;


    @FXML
    public void addOrder(javafx.scene.input.MouseEvent mouseEvent) throws SQLException, SysException {

        //当前选择的可以买的物品数量
        int max_num = waitProduct.getNum();

        String p_id = waitProduct.getId();
        String s_id = user.getId();
        LocalDate time = loginDate;
        String p_name = waitProduct.getName();
        int amount = 0;

        try{
            if(purchaseNums.getText() == "")
                throw new SysException("不得为空");
            amount = Integer.parseInt(purchaseNums.getText());
            //不写可以用来加货
            if(amount <= 0)
                throw new SysException("数量要大于0");
            if(amount > max_num)
                throw new SysException("买的太多啦！真能吃:<");


            double singlePrice = waitProduct.getPrice();
            double allPrice = amount*singlePrice;

            Order tempOrder = new Order(p_id, s_id, time, p_name, amount, singlePrice, allPrice);
            Order.post(tempOrder);

            waitProduct.setNum(max_num-amount);
            waitProduct.update();
            reloadCarriage();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("确认信息");
            alert.setContentText("已加入购物车");
            alert.show();

        }catch (SysException e){

        } catch (Exception e){
            System.out.println(e.getStackTrace());
        }

    }



    @FXML
    void shoppingCartClick(javafx.scene.input.MouseEvent mouseEvent) throws IOException {

        //在规定时间内
        Stage stageCart = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Cart.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 600, 327);

        stageCart.setScene(scene);
        stageCart.setResizable(false);
        try {
            stageCart.show();
        }catch (IllegalStateException e){

        }
        CartController cartController = fxmlLoader.getController();
        cartController.setListener(listenerRefresh);


    }


    private List<Product> getData() throws SQLException, IOException {

        return  Product.getAllProducts();
    }

    public void setChosenCard(@NotNull Product product){

        //当前选中的商品为待处理商品
        waitProduct = product;

        productName.setText(product.getName());
        price.setText(product.getPrice()+"元");
        inf.setText(product.getInf());
        category.setText(product.getCategory());
        leftNums.setText(Integer.toString(product.getNum()));


        if (product.getPicture() != null) {
            InputStream pic = new ByteArrayInputStream(product.getPicture());
            Image image = new Image(pic);
            img.setImage(image);
        }
        else
            img = null;
    }


    /**
     * @Description: 1.设置时间
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        //设置验证器
        try {
            if(!Bill.haveBill(loginDate, user.getId()))
                Purchased.ini(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        //设置时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date(System.currentTimeMillis());

        System.out.println(now);

        //设置监听器
        listenerRefresh = new RefreshListener() {
            @Override
            public void refreshCarriage() {
                reloadCarriage();
            }

        };


        reloadCarriage();

        //为实现视频加上
        vStage = new Stage();
        URL f = App.class.getResource("1.mp4");
        Media media = null;
        try {
            media = new Media(f.toURI().toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.pause();
        MediaView video = new MediaView(mediaPlayer);
        Pane root = new Pane();
        root.setPrefHeight(1000);
        root.setPrefWidth(2000);
        root.getChildren().add(video);
        Scene scene = new Scene(root);
        vStage.setScene(scene);
        vStage.setOnCloseRequest(event->{
            mediaPlayer.stop();
        });

        img.setOnMouseReleased(mouseEvent -> {
            vStage.show();
            mediaPlayer.play();
        });

    }

    private void reloadCarriage(){
        System.out.println("RELOAD CARRIAGE");

        //清空Grid子节点以及商品列表
        grid.getChildren().clear();
        products.clear();

        //显示最新购买时间
        LocalDate time = Open_time.getLatest();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        latestTime.setText(time.format(formatter));

        //购物区
        int col = 0;
        int row = 1;
        try {
            //获取所有商品
            products.addAll(getData());
            if(products.size() > 0 ){
                //若列表不空且无选中则选最后一个,若选择的数量为0，向前找
                if(waitProduct == null){
                    int index = products.size()-1;
                    while (index > 0 && products.get(index).getNum() == 0)
                        index--;
                    if (index != 0)
                        setChosenCard(products.get(index));
                }

                else
                    waitProduct.reloadNums();
                setChosenCard(waitProduct);
                listener = new ProductCardListener() {
                    @Override
                    public void OnClickListener(Product product) {
                        setChosenCard(product);
                    }
                };
            }


            for(int i = products.size()-1; i >= 0; i--){
                Product p = products.get(i);


                //若无货则不显示
                if(p.getNum() == 0)
                    continue;


                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(p, listener);


                if(col == 2){
                    col = 0;
                    row++;
                }
                grid.add(anchorPane, col++, row);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);

                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));


                //判断是否能够买
                if(!Opened.isValidate()){
                    System.out.println("可购买时间"+Open_time.getLatest());
                    buy.setVisible(false);
                    shoppingCart.setVisible(false);
                }

                if(Opened.isValidate() && !Purchased.isValidate()){
                    isBrought.setText("今天你已经买过了！");
                    buy.setVisible(false);
                    shoppingCart.setVisible(false);
                    System.out.println("brought");
                }


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
