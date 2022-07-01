package school.mdsystem.student.model;

import java.sql.Connection;
import java.time.format.DateTimeFormatter;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
public class Model {

    static Connection db;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

}
