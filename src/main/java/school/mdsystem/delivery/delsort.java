package school.mdsystem.delivery;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
public class delsort {
    private StringProperty deliveryman_id;
    private StringProperty deltot;
    private StringProperty rank;

    public delsort(){}

    public delsort(String bd,String se,String ss)
    {
        super();
        this.deliveryman_id = new SimpleStringProperty(bd);
        this.deltot = new SimpleStringProperty(se);
        this.rank =new SimpleStringProperty(ss);
    }

    public StringProperty get_deliveryman_id() {
        return deliveryman_id;
    }

    public void set_deliveryman_id(String sd) {
        this.deliveryman_id = new SimpleStringProperty(sd);
    }

    public StringProperty get_deltot() {
        return deltot;
    }

    public void set_deltot(String se) {
        this.deltot = new SimpleStringProperty(se);
    }

    public StringProperty get_rank() {
        return rank;
    }

    public void set_rank(String ss) {
        this.rank = new SimpleStringProperty(ss);
    }

}
