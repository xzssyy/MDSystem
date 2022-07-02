package school.mdsystem.student.validator;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:今天是否购买过验证器
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
