package school.mdsystem.student.model;

import org.jetbrains.annotations.NotNull;
import school.mdsystem.student.SysException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
public class Order extends Model{
    private String o_id;

    private String p_id;

    private String s_id;

    private LocalDate time;

    private String p_name;

    private int amount;

    private double singlePrice;

    private double allPrice;

    private String s_allPrice;


    public Order(String o_id, String p_id, String s_id, LocalDate time, String p_name, int amount, double singlePrice, double allPrice) {
        this.o_id = o_id;
        this.p_id = p_id;
        this.s_id = s_id;
        this.time = time;
        this.p_name = p_name;
        this.amount = amount;
        this.singlePrice = singlePrice;
        this.allPrice = allPrice;

        this.s_allPrice = String.format("%.2f", allPrice);
    }

    public Order(String p_id, String s_id, LocalDate time, String p_name, int amount, double singlePrice, double allPrice) {
        this.p_id = p_id;
        this.s_id = s_id;
        this.time = time;
        this.p_name = p_name;
        this.amount = amount;
        this.singlePrice = singlePrice;
        this.allPrice = allPrice;
        this.s_allPrice = String.format("%.2f", allPrice);
    }


    public static @NotNull List<Order> getAllOrder(LocalDate date, String id) throws SQLException {
        List<Order> ls = new ArrayList<>();
        String formatDate = null;

        formatDate = date.format(formatter);



        //根据日期查询该用户的order

        db = ConnectionFactory.getConnection();
        PreparedStatement stmt = db.prepareStatement("select order_id, student_id, orders.product_id, time, state, amount,  product_name, product_price from orders ,product where orders.product_id = product.product_id and orders.time = ? and orders.student_id = ?;");

        //java.sql.Date dateSql = new java.sql.Date(date.getTime());
        //stmt.setDate(1, dateSql);
        stmt.setString(1, formatDate);
        stmt.setString(2, id);

        ResultSet rs = stmt.executeQuery();




        while (rs.next()) {
            String o_id = rs.getString("order_id");
            String s_id = rs.getString("student_id");
            String p_id = rs.getString("product_id");

            //Date 转 localDate
            java.util.Date t_date = rs.getDate("time");
            LocalDate time = Instant.ofEpochMilli(t_date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();


            int amount = rs.getInt("amount");
            double singlePrice = rs.getDouble("product_price");
            String product_name = rs.getString("product.product_name");
            double allPrice = amount * singlePrice;

            Order order = new Order(o_id, p_id, s_id, time, product_name, amount,singlePrice, allPrice);

            ls.add(order);
        }

        if (rs != null)
            rs.close();

        db.close();

        return ls;
    }

    public String getO_id() {
        return o_id;
    }

    public String getP_id() {
        return p_id;
    }

    public String getS_id() {
        return s_id;
    }

    public LocalDate getTime() {
        return time;
    }

    public String getP_name() {
        return p_name;
    }

    public int getAmount() {
        return amount;
    }

    public double getSinglePrice() {
        return singlePrice;
    }

    public double getAllPrice() {
        return allPrice;
    }

    public String getS_allPrice() {
        return s_allPrice;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "o_id='" + o_id + '\'' +
                ", p_id='" + p_id + '\'' +
                ", s_id='" + s_id + '\'' +
                ", time=" + time +
                ", p_name='" + p_name + '\'' +
                ", amount=" + amount +
                ", singlePrice=" + singlePrice +
                ", allPrice=" + allPrice +
                '}';
    }

    /**
     * @Description: 新order上传数据库,并返回最新的id
     */
    public static String post(Order newOrder) throws SQLException {
        db = ConnectionFactory.getConnection();

        String u_id = newOrder.getS_id();
        String p_id= newOrder.getP_id();

        LocalDate t_time = newOrder.getTime();
        String time = formatter.format(t_time);
        int amount = newOrder.getAmount();

        PreparedStatement stmt = db.prepareStatement("insert into orders values (default,?, ?, ?, 0, ?);");
        stmt.setString(1, u_id);
        stmt.setString(2, p_id);
        stmt.setString(3, time);
        stmt.setInt(4,amount);
        stmt.executeUpdate();

        stmt = db.prepareStatement("select LAST_INSERT_ID() as backId;");
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getString("backId");
    }

    /**
     * @Description: 学生端：修改订单amount数量
     */
    public int update(int newAmount) throws SQLException, SysException{

        String productId= this.getP_id(); //当前order对应商品id
        String orderId = this.getO_id();
        int oldAmount = this.getAmount();   //旧购买数量
        int oldProductNum;                  //旧商品数量
        int newProductNum;                  //新商品数量

        db = ConnectionFactory.getConnection();


        //获取当前更改订单对应的商品数量
        PreparedStatement stmt = db.prepareStatement("select product_num from product where product_id = ? ");
        stmt.setString(1, this.getP_id());
        ResultSet rs = stmt.executeQuery();
        rs.next();
        oldProductNum = rs.getInt("product_num");
        rs.close();
        stmt.close();


        if(newAmount < 0 || newAmount > oldProductNum+oldAmount)
            throw new SysException("数量小于0或库存不够");

        //更新商品数量
        newProductNum = oldProductNum+oldAmount-newAmount;

        //修改订单模型
        this.amount = newAmount;
        this.allPrice = this.amount*this.singlePrice;
        this.s_allPrice = String.format("%.2f", this.allPrice);

        //修改订单数据库
        String formatDate = this.getTime().format(formatter);
        stmt = db.prepareStatement("update orders set amount = ? where ? = order_id and time = ? ");
        stmt.setInt(1,newAmount);
        stmt.setString(2,orderId);
        stmt.setString(3,formatDate);
        stmt.executeUpdate();
        stmt.close();

        //修改商品数据库
        stmt = db.prepareStatement("update product set product_num = ? where ? = product_id; ");
        stmt.setInt(1,newProductNum);
        stmt.setString(2,productId);
        stmt.executeUpdate();
        stmt.close();



        db.close();

        return oldAmount-newAmount;
    }

    public int delete() throws SQLException, SysException {
        System.out.println("[delete]"+this.toString());
        int diffNum = 0;
        try{diffNum = this.update(0);}catch (Exception e){
            System.out.println("更新出错!");
        }



        db = ConnectionFactory.getConnection();

        String order_id = this.getO_id();

        String formatDate = this.getTime().format(formatter);
        System.out.println(this.getTime().toString());
        PreparedStatement stmt = db.prepareStatement("DELETE FROM orders WHERE order_id = ? and time = ?;");
        stmt.setString(1, order_id);
        stmt.setString(2, formatDate);
        stmt.executeUpdate();
        stmt.close();

        db.close();

        return diffNum;
    }

    static void test() throws SQLException, IOException, ParseException {

        //测试订单获取
        System.out.println("测试订单获取");
        LocalDate date = LocalDate.of(2022, 6, 15);

        String id = "10204507402";

        List<Order> ls = getAllOrder(date, id);

        for (Order t : ls) {
            System.out.println(t.toString());
        }
        System.out.println("ok");

        //测试订单创建
        System.out.println("测试订单创建");
        Order t = ls.get(0);
        t.s_id = "xzssyy";
        t.amount+=1;

        System.out.println(post(t));

        //测试订单更新
        System.out.println("测试订单更新");
        try{
            t.update(30);
        }catch (ExceptionInInitializerError e){
            System.out.println("超出库存啦！");
        } catch (SysException e) {

        }


    }

    public static void main(String[] args) throws SQLException, IOException, ParseException {
        test();
    }
}
