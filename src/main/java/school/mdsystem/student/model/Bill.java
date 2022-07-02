package school.mdsystem.student.model;

import school.mdsystem.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:学生账单模型
 */
public class Bill extends Model{
    private String b_id;

    private String s_id;

    private LocalDate time;

    private double expense;

    private String s_expense;

    public Bill(String b_id, String s_id, LocalDate time, double expense) {
        this.b_id = b_id;
        this.s_id = s_id;
        this.time = time;
        this.expense = expense;

        this.s_expense = String.format("%.2f", expense);
    }

    public Bill(String s_id, LocalDate time, double expense) {
        this.s_id = s_id;
        this.time = time;
        this.expense = expense;

        this.s_expense = String.format("%.2f", expense);
    }

    public String getB_id() {
        return b_id;
    }

    public String getS_id() {
        return s_id;
    }

    public LocalDate getTime() {
        return time;
    }

    public double getExpense() {
        return expense;
    }

    public void setB_id(String b_id) {
        this.b_id = b_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public String getS_expense() {
        return s_expense;
    }

    public void setS_expense(String s_expense) {
        this.s_expense = s_expense;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "b_id='" + b_id + '\'' +
                ", s_id='" + s_id + '\'' +
                ", time=" + time +
                ", expense=" + expense +
                '}';
    }


    /**
     * @Description: 上传数据库
     */
    public static String submit(Bill bill) throws SQLException {
        db = ConnectionFactory.getConnection();

        String formatDate = bill.getTime().format(formatter);

        PreparedStatement stmt = db.prepareStatement("insert into bill values (default, ?,?,0,?)");
        stmt.setString(1, bill.getS_id());
        stmt.setString(2, formatDate);
        stmt.setDouble(3,bill.getExpense());
        stmt.executeUpdate();

        stmt.close();
        db.close();

        return null;
    }

    public static Boolean haveBill(LocalDate date, String s_id) throws SQLException {
        Boolean result = false;
        db = ConnectionFactory.getConnection();
        String formatDate = date.format(formatter);

        PreparedStatement stmt = db.prepareStatement("select bill_id from bill where time = ? and student_id = ?;");
        stmt.setString(1, formatDate);
        stmt.setString(2, s_id);
        ResultSet rs = stmt.executeQuery();

        if(rs.next())
            result = true;


        stmt.close();
        db.close();

        return result;
    }

    public static List<Bill> getAllBill(String s_id) throws SQLException {
        List<Bill> ls = new ArrayList<>();


        //根据s_id查询该用户的bill

        db = ConnectionFactory.getConnection();
        PreparedStatement stmt = db.prepareStatement("select bill_id, student_id, time, expense from bill where student_id = ?;");
        stmt.setString(1, s_id);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String b_id = rs.getString("bill_id");

            //Date 转 localDate
            Date t_date = rs.getDate("time");
            LocalDate time = Instant.ofEpochMilli(t_date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

            double exp = rs.getDouble("expense");

            Bill bill = new Bill(b_id, s_id, time, exp);

            ls.add(bill);
        }

        if (rs != null)
            rs.close();
        stmt.close();
        db.close();

        return ls;
    }
}
