package school.mdsystem.delivery;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:
 */
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import school.mdsystem.ssql;


public class del_func extends ssql {

    int ai=0;
    boolean aq=false;
    static String aacString;

    public int fid_delrec(String bill_id) throws SQLException{
        int c=0;
        ssql sjk=new ssql();
        aacString=new String(bill_id);

        sjk.getConn();
        String sql ="select * from bill where bill_id=?";
        String[] params = new String[]{bill_id};
        ResultSet rs = sjk.executeQuery(sql,params);

        if(rs.next()){
            System.out.println("he");
            c=1;
        }

        sjk.closeAll();
        return c;
    }

    public int up_delrec(String delman_id) throws SQLException{/*更新配送员接单情况*/
        String asd=aacString;
        System.out.println(asd);
        ssql sjk=new ssql();
        sjk.getConn();

        String sql="update delivery set deliveryman_id=? where bill_id=?";
        String[] params={delman_id,asd};
        int nums=sjk.executeUpdate(sql, params);
        sjk.closeAll();
        return nums;
    }

    public int up_delsta(String state,String billid) throws SQLException/*更新配送单的配送状态*/
    {
        ssql sjk=new ssql();
        sjk.getConn();
        String sql="update delivery set del_state= ? where bill_id=?";
        String[] params={state,billid};
        int nums=sjk.executeUpdate(sql, params);
        sjk.closeAll();
        return nums;
    }

    public int up_ordsta(String billid) throws SQLException/*更新订单表的配送接单状态*/
    {
        ssql sjk=new ssql();
        sjk.getConn();
        String sql="update bill set state=1 where bill_id=?";
        String[] params={billid};
        int nums=sjk.executeUpdate(sql, params);
        sjk.closeAll();
        return nums;
    }

    public  ObservableList<deliveryinfo> findbills() throws SQLException/*返回配送员接单表，直接在我写的view里找*/
    {
        ssql sjk=new ssql();
        ObservableList<deliveryinfo> list = FXCollections.observableArrayList();
        sjk.getConn();
        String sql ="select * from delivery_view";
        ResultSet rs = sjk.executeQuery(sql,null);

        try {
            while(rs.next()){
                deliveryinfo c=new  deliveryinfo();
                c.set_billid(rs.getString(1));
                c.set_sname(rs.getString(2));
                c.set_saddress(rs.getString(3));
                c.set_sphonenum(rs.getString(4));
                list.add(c);
                ai=1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            sjk.closeAll();
        }
        return list;
    }

    public String findbills2(String billid) throws SQLException/*返回配送员接单表，直接宰我写的view里找*/
    {
        String res="";
        ssql sjk=new ssql();
        ObservableList<deliveryinfo> list = FXCollections.observableArrayList();
        sjk.getConn();
        String sql ="SELECT bill_id,s_name,s_address,s_phonenum FROM student S,bill B WHERE S.student_id=B.student_id AND bill_id=?";
        String[] params={billid};
        ResultSet rs = sjk.executeQuery(sql,params);

        try {
            while(rs.next()){
                res=" 订单号："+rs.getString(1)+"\n 联系人："+rs.getString(2)+"\n 配送地址："+rs.getString(3)+"\n 联系方式："+rs.getString(4);
                ai=1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            sjk.closeAll();
        }
        return res;
    }

    public  ObservableList<String> fidudelb(String delman_id) throws SQLException/*返回配送员未完成表,delivery表里找*/
    {
        ssql sjk=new ssql();
        ObservableList<String> list = FXCollections.observableArrayList();
        sjk.getConn();
        String sql ="select bill_id from delivery where del_state=0 and deliveryman_id=?";
        String[] params={delman_id};
        ResultSet rs = sjk.executeQuery(sql,params);

        try {
            while(rs.next()){
                list.add(rs.getString(1));
                ai=1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            sjk.closeAll();
        }
        return list;
    }

    public  ObservableList<String> fiddelb(String delman_id) throws SQLException/*返回配送员完成表*/
    {
        ssql sjk=new ssql();
        ObservableList<String> list = FXCollections.observableArrayList();
        sjk.getConn();
        String sql ="select bill_id from delivery where del_state = 1 and deliveryman_id=?";
        String[] params={delman_id};
        ResultSet rs = sjk.executeQuery(sql,params);

        try {
            while(rs.next()){
                list.add(rs.getString(1));
                ai=1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            sjk.closeAll();
        }
        return list;
    }

    public  ObservableList<delsort> del_sort() throws SQLException/*根据完成订单量列出配送员排名，与前面不同的是这里需要先用deliverytot_flush刷新确保准确*/
    {
        ssql sjk=new ssql();
        ObservableList<delsort> list = FXCollections.observableArrayList();
        sjk.getConn();
        String cs = "{CALL deliverytot_flush}";
        String sql ="SELECT deltot,deliveryman_id FROM deliveryman ORDER BY deltot DESC";
        sjk.executeQuery_ctt(cs, null);
        ResultSet rs = sjk.executeQuery(sql,null);

        int cnt = 1;

        try {
            while(rs.next()){
                delsort c=new delsort();
                c.set_deliveryman_id(rs.getString(1));
                c.set_deltot(rs.getString(2));
                c.set_rank(""+cnt);
                cnt++;
                list.add(c);
                ai=1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            sjk.closeAll();
        }
        return list;
    }

}
