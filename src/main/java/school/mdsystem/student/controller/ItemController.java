package school.mdsystem.student.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import school.mdsystem.student.listener.ProductCardListener;
import school.mdsystem.student.model.Product;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
public class ItemController extends Controller{
    @FXML
    private Label nameLabel;

    @FXML
    private Label numsLabel;

    @FXML
    private ImageView picture;

    @FXML
    private Label priceLabel;

    @FXML
    private AnchorPane panel;

    @FXML
    void itemClick(MouseEvent event) {
        listener.OnClickListener(product);
    }

    private ProductCardListener listener;

    private Product product;


    public void setData(Product product, ProductCardListener myListener) {
        this.product = product;
        this.listener = myListener;

        nameLabel.setText(this.product.getName());
        priceLabel.setText(this.product.getPrice() + "元");
        numsLabel.setText(this.product.getNum()+"件");

        //byte转inputstream
        if (product.getPicture() != null) {
            InputStream pic = new ByteArrayInputStream(product.getPicture());
            Image image = new Image(pic);
            picture.setImage(image);
        }
        else
            picture = null;
    }


}
