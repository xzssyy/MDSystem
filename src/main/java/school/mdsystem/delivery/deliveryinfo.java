package school.mdsystem.delivery;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
import javafx.beans.property.*;

public class deliveryinfo {
    private StringProperty bill_id;
    private StringProperty s_name;
    private StringProperty s_address;
    private StringProperty s_phonenum;
    public deliveryinfo(){}

    public deliveryinfo(String bd,String se,String ss,String sm)
    {
        super();
        this.bill_id = new SimpleStringProperty(bd);
        this.s_name = new SimpleStringProperty(se);
        this.s_address =new SimpleStringProperty(ss);
        this.s_phonenum =new SimpleStringProperty(sm);
    }

    public StringProperty get_billid() {
        return bill_id;
    }

    public void set_billid(String sd) {
        this.bill_id = new SimpleStringProperty(sd);
    }

    public StringProperty get_sname() {
        return s_name;
    }

    public void set_sname(String se) {
        this.s_name = new SimpleStringProperty(se);
    }

    public StringProperty get_saddress() {
        return s_address;
    }

    public void set_saddress(String ss) {
        this.s_address = new SimpleStringProperty(ss);
    }

    public StringProperty get_sphonenum() {
        return s_phonenum;
    }

    public void set_sphonenum(String sm) {
        this.s_phonenum = new SimpleStringProperty(sm);
    }
}

