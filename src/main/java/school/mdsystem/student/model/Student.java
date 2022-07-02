package school.mdsystem.student.model;

import school.mdsystem.ConnectionFactory;
import school.mdsystem.student.SysException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:学生模型
 */
public class Student extends Model{
    private String id;
    private String password;
    private String name;
    private String address;
    private String phoneNumber;

    public Student(String s_id, String s_password) throws SQLException, SysException {
        db = ConnectionFactory.getConnection();

        PreparedStatement stmt = db.prepareStatement("SELECT * FROM student where ? = student_id;");
        stmt.setString(1, s_id);
        ResultSet rs = stmt.executeQuery();

        //若无此人结束
        if (!rs.next()) {
            throw new SysException("没有这个人");
        }

        //密码不对结束
        String sys_password = rs.getString("student_password");
        if (!sys_password.equals(s_password)) {
            throw new SysException("密码不对");
        }


        id = rs.getString("student_id");
        password = rs.getString("student_password");
        name = rs.getString("s_name");
        address = rs.getString("s_address");
        phoneNumber = rs.getString("s_phonenum");

        if(rs != null)
            rs.close();

        db.close();

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public static void register(String id, String password, String name, String address, String phone) throws SQLException, SysException {
        db = ConnectionFactory.getConnection();

        PreparedStatement stmt = db.prepareStatement("SELECT * FROM student where ? = student_id;");
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();

        if(rs.next()){
            rs.close();
            throw new SysException("该用户已被注册！");
        }

        stmt = db.prepareStatement("insert into student values (?,?,?,?,?);");
        stmt.setString(1, id);
        stmt.setString(2, password);
        stmt.setString(3, name);
        stmt.setString(4, address);
        stmt.setString(5, phone);
        stmt.executeUpdate();

        stmt.close();
        db.close();
    }


}
