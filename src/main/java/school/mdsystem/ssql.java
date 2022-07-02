package school.mdsystem;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;

/**
 * @Author: 刘佳怡
 * @Description: sql模块化
 */


public class ssql implements Serializable {

    private static final long serialVersionUID = 1L;
    Connection conn = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    CallableStatement ctt = null;

    public Connection getConn() {
        try {
            //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            this.conn = DriverManager.getConnection("jdbc:mysql://sh-cdb-70hmlmby.sql.tencentcdb.com:58587/sms", "root", "py20021023");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public ResultSet executeQuery(String sql, String[] param) {
        try {
            pstm = conn.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    //System.out.println(param[i]);
                    pstm.setString(i + 1, param[i]);
                }
            }
            rs = pstm.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet executeQuery_ctt(String sql, String[] param) {
        try {
            ctt = conn.prepareCall(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    pstm.setString(i + 1, param[i]);
                }
            }
            rs = ctt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }


    public int executeUpdate(String sql, String[] param) {
        int num = 0;
        try {
            pstm = conn.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    pstm.setString(i + 1, param[i]);
                }
            }
            num = pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }


    public void closeAll() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstm != null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

