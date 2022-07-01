package school.mdsystem.student.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
public class Open_time extends Model{
    private static ArrayList<LocalDate> timeList = new ArrayList<>();

    public static void ini() throws SQLException {
        db = ConnectionFactory.getConnection();

        PreparedStatement stmt = db.prepareStatement("select * from availabletime;");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()){
            LocalDate date = rs.getDate("time").toLocalDate();
            //System.out.println(date.toString());
            timeList.add(date);
        }

        if(rs!=null)
            rs.close();
        stmt.close();
        db.close();
    }

    public static LocalDate getLatest(){
        return timeList.get(timeList.size()-1);
    }

    private static void test() throws SQLException {
        Open_time.ini();
        System.out.println(Open_time.getLatest());
    }

    public static void setBuyDate(LocalDate date){
        timeList.add(date);
    }

    public static void main(String[] args) throws SQLException {
        test();
    }
}
