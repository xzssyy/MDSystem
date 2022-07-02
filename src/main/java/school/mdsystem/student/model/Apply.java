package school.mdsystem.student.model;

import school.mdsystem.ConnectionFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:学生物资请求模型
 */
public class Apply extends Model{
    private String id;

    private String s_id;

    private String l_id;

    private String type;

    private String info;

    private String reply;

    public Apply(String id, String s_id, String l_id, String type, String info, String reply) {
        this.id = id;
        this.s_id = s_id;
        this.l_id = l_id;
        this.type = type;
        this.info = info;
        this.reply = reply;
    }

    public Apply(String s_id, String type, String info, String reply) {
        this.s_id = s_id;
        this.type = type;
        this.info = info;
        this.reply = reply;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public void setL_id(String l_id) {
        this.l_id = l_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getId() {
        return id;
    }

    public String getS_id() {
        return s_id;
    }

    public String getL_id() {
        return l_id;
    }

    public String getType() {
        return type;
    }

    public String getInfo() {
        return info;
    }

    public String getReply() {
        return reply;
    }

    @Override
    public String toString() {
        return "Apply{" +
                "id='" + id + '\'' +
                ", s_id='" + s_id + '\'' +
                ", l_id='" + l_id + '\'' +
                ", type='" + type + '\'' +
                ", info='" + info + '\'' +
                ", reply='" + reply + '\'' +
                '}';
    }

    public static void submit(Apply apply) throws SQLException {
        db = ConnectionFactory.getConnection();

        PreparedStatement stmt = db.prepareStatement("insert into proposal values (default,?,?,?,?,null);");
        stmt.setString(1, apply.getS_id());
        stmt.setString(2, apply.getL_id());
        stmt.setString(3, apply.getType());
        stmt.setString(4, apply.getInfo());
        //stmt.setString(5, apply.getReply());

        stmt.executeUpdate();
        stmt.close();
        db.close();
    }

    public static List<Apply> getAllApplies(String s_id) throws SQLException, IOException {
        db = ConnectionFactory.getConnection();

        List<Apply> ls = new ArrayList<>();

        PreparedStatement stmt = db.prepareStatement("SELECT * FROM proposal where student_id = ?");
        stmt.setString(1, s_id);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String tid = rs.getString("proposal_id");
            String sID = rs.getString("student_id");
            String lID = rs.getString("logistics_id");
            String type = rs.getNString("proposal_type");
            String info = rs.getNString("proposal_info");
            String reply = rs.getNString("reply_info");

            Apply temp = new Apply(tid, sID, lID, type, info, reply);
            ls.add(temp);

        }

        if (rs != null)
            rs.close();
        stmt.close();
        db.close();

        return ls;
    }

    static void test() throws SQLException {
        Apply apply = new Apply("xzssyy", "log001", "apply", "coco");
        Apply.submit(apply);
    }

    public static void main(String[] args) throws SQLException {
        test();
    }
}
