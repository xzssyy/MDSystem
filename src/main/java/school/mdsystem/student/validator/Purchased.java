package school.mdsystem.student.validator;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
public class Purchased {
    static Boolean value = true;

    public static void ini(Boolean value) {
        Purchased.value = value;
    }

    public static Boolean isValidate(){
        return !value;
    }

}
