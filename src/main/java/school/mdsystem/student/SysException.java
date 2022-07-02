package school.mdsystem.student;

import javafx.scene.control.Alert;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:学生端异常
 */
public class SysException extends Throwable{
    private String inf;

    public SysException(String i){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("错误");
        alert.setContentText(i);
        alert.showAndWait();
    }

    @Override
    public String toString() {
        return inf;
    }
}
