package school.mdsystem.student.utility;

import school.mdsystem.ConnectionFactory;
import school.mdsystem.student.model.Model;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @Author: 潘越 xzssyy@gmail.com
 * @Description:上传图片
 */
public class ImageTool extends Model {
    static Connection db = ConnectionFactory.getConnection();


    public static byte[] readPicture() throws IOException {
        System.out.println("输入绝对路径");
        Scanner reader = new Scanner(System.in);
        String path = reader.nextLine();
        path = path.replaceAll("\\\\","//");
        path = path.replaceAll("\"","");
        System.out.println(path);

        FileInputStream fis = new FileInputStream(path);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int len = 0;
        byte[] b = new byte[10240 * 1024];
        while ((len = fis.read(b)) != -1) {
            out.write(b, 0, len);
        }

        //接收out
        byte[] array = out.toByteArray();
        fis.close();
        out.close();

        return array;
    }

    public static byte[] readPicture(String path) throws IOException {

        FileInputStream fis = new FileInputStream(path);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int len = 0;
        byte[] b = new byte[10240 * 1024];
        while ((len = fis.read(b)) != -1) {
            out.write(b, 0, len);
        }

        //接收out
        byte[] array = out.toByteArray();
        fis.close();
        out.close();

        return array;
    }

    public static void upLoadPicture(String path,String p_name) throws IOException, SQLException {
        byte[] pic = readPicture(path);
        Blob blob = db.createBlob();
        blob.setBytes(1, pic);

        String sql = "update product set product_pic = ? where product_name = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        //String pid = "prod1001";
        ps.setBlob(1,blob);
        ps.setString(2,p_name);
        ps.executeUpdate();

        ps.close();
        db.close();
    }

    public static void main(String[] args) throws IOException, SQLException {
        //upLoadPicture();
    }
}
