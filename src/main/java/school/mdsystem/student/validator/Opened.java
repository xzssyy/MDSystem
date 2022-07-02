package school.mdsystem.student.validator;

import school.mdsystem.student.model.Open_time;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:今天是否开启购买验证器
 */
public class Opened {
    static LocalDate latestTime = null;
    static boolean value = true;


    public static void ini(LocalDate validateDte) throws SQLException {
        latestTime = Open_time.getLatest();

        if(validateDte.compareTo(latestTime) == 0){
            value = false;
        }
    }

    public static Boolean isValidate() {
        return !value;
    }
}
