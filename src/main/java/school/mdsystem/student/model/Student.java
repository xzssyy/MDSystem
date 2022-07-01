package school.mdsystem.student.model;

import school.mdsystem.student.SysException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
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


    //异常处理
    public String nullOfUser() {
        return "没有这个人！";
    }


    //测试
    public static void main(String[] args) {
        try {
            Student st = new Student("10204507405", "ds");
        } catch (SysException e) {
            System.out.println(e.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
