package school.mdsystem.student.model;

import school.mdsystem.ConnectionFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:商品模型
 */
public class Product extends Model{
    private String id;

    private String name;

    private String inf;

    private String category;

    private int num;

    private double price;

    //数据库限制图片大小最大为100K
    private byte[] picture;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getInf() {
        return inf;
    }

    public String getCategory() {
        return category;
    }

    public int getNum() {
        return num;
    }

    public double getPrice() {
        return price;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Product(String id, String name, String inf, String category, int num, double price, byte[] picture) {
        this.id = id;
        this.name = name;
        this.inf = inf;
        this.category = category;
        this.num = num;
        this.price = price;
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", inf='" + inf + '\'' +
                ", category='" + category + '\'' +
                ", num=" + num +
                ", price=" + price +
                '}';
    }

    public static List<Product> getAllProducts() throws SQLException, IOException {
        db = ConnectionFactory.getConnection();

        PreparedStatement stmt = db.prepareStatement("SELECT * FROM product");
        ResultSet rs = stmt.executeQuery();

        List<Product> ls = new ArrayList<>();

        while (rs.next()) {
            String id = rs.getString("product_id");
            String name = rs.getString("product_name");
            String inf = rs.getString("product_info");
            String category = rs.getString("product_category");
            int num = rs.getInt("product_num");
            double price = rs.getDouble("product_price");

            //图片转字节流
            Blob pic = rs.getBlob("product_pic");
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
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    is.close();
                    is = null;
                }

            }

            Product temp = new Product(id, name, inf, category, num, price, bytes);

            ls.add(temp);
        }
        if(rs != null)
            rs.close();

        db.close();


        return ls;
    }

    /**
     * @Description: 学生端主要功能更改商品数量
     */
    public void update() throws SQLException {
        db = ConnectionFactory.getConnection();

        PreparedStatement stmt = db.prepareStatement("update product set product_num = ? where ? = product_id; ");
        stmt.setInt(1, this.getNum());
        stmt.setString(2, this.getId());
        stmt.executeUpdate();

        stmt.close();
        db.close();
    }

    public void reloadNums() throws SQLException {

        db = ConnectionFactory.getConnection();
        String pid = this.getId();

        PreparedStatement stmt = db.prepareStatement("select product_num from product where product_id = ?");
        stmt.setString(1, pid);
        ResultSet rs = stmt.executeQuery();
        rs.next();

        this.setNum(rs.getInt("product_num"));
        rs.close();
        stmt.close();
        db.close();

    }

    //测试单元
    static void test() throws SQLException, IOException {
        List<Product> ls = getAllProducts();

        for (Product t : ls) {
            System.out.println(t.toString());
            t.reloadNums();
        }
        System.out.println("ok");

    }

    public static void main(String[] args) throws SQLException, IOException {
        test();
    }
}
