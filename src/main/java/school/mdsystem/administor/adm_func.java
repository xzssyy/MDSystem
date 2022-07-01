package school.mdsystem.administor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import school.mdsystem.ssql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class adm_func extends ssql {

	int ai=0;
	boolean aq=false;
	static String aacString;
	
	/******************添加用户操作****************/
	public void add_student(String id,String psw,String name,String addr,String pho) throws SQLException//添学生
	{	
		ssql sjk=new ssql();
		sjk.getConn();		 
		String sql ="INSERT INTO student VALUES(?,?,?,?,?)"; 
		String[] params = new String[]{id,psw,name,addr,pho};
		sjk.executeUpdate(sql, params);
	    sjk.closeAll();
	}
	
	public void add_logistics(String id,String psw) throws SQLException//添加后勤
	{	
		ssql sjk=new ssql();
		sjk.getConn();		 
		String sql ="INSERT INTO logistics VALUES(?,?)"; 
		String[] params = new String[]{id,psw};
		sjk.executeUpdate(sql, params);
	    sjk.closeAll();
	}
	
	public void add_deliveryman(String id,String psw,String pho) throws SQLException//添加配送员
	{	
		ssql sjk=new ssql();
		sjk.getConn();		 
		String sql ="INSERT INTO deliveryman VALUES(?,?,?,0)"; 
		String[] params = new String[]{id,psw,pho};
		sjk.executeUpdate(sql, params);
	    sjk.closeAll();
	}
	
	/******************删除用户操作****************/
	public void delt_student(String id) throws SQLException//删除学生
	{	
		ssql sjk=new ssql();
		sjk.getConn();		 
		String sql ="DELETE FROM student WHERE student_id=?"; 
		String[] params = new String[]{id};
		sjk.executeUpdate(sql, params);
	    sjk.closeAll();
	}
	
	public void delt_logistics(String id) throws SQLException//删除后勤
	{	
		ssql sjk=new ssql();
		sjk.getConn();		 
		String sql ="DELETE FROM logistics WHERE logistics_id=?"; 
		String[] params = new String[]{id};
		sjk.executeUpdate(sql, params);
	    sjk.closeAll();
	}
	
	public void delt_deliveryman(String id) throws SQLException//删除配送员
	{	
		ssql sjk=new ssql();
		sjk.getConn();		 
		String sql ="DELETE FROM deliveryman WHERE deliveryman_id=?"; 
		String[] params = new String[]{id};
		sjk.executeUpdate(sql, params);
	    sjk.closeAll();
	}
	
	/******************修改用户信息操作****************/
	public int up_stu_psw(String id,String psw) throws SQLException{/*修改学生表，更新学生密码*/
		
		ssql sjk=new ssql();
		sjk.getConn();
		String sql="UPDATE student SET student_password=? where student_id=?";
		String[] params={psw,id};
		int nums=sjk.executeUpdate(sql, params);
	    sjk.closeAll();
	    return nums;	
	}
	
	
	
	/******************查看查找用户信息操作****************/
	public String fid_stu(String id) throws SQLException//查找学生信息
	{	
		String res="";
		ssql sjk=new ssql();
		sjk.getConn();		 
		String sql ="SELECT * FROM student WHERE student_id=?";
		String[] params={id};
		ResultSet rs = sjk.executeQuery(sql,params);
			
		try {
				while(rs.next())
				{
					res=" 学生id："+rs.getString(1)+"\n 密码："+rs.getString(2)+"\n 学生姓名："+rs.getString(3)
					+"\n 学生住址："+rs.getString(4)+"\n 联系方式："+rs.getString(5);
					ai=1;
				}
			} 
		catch (SQLException e) {
				e.printStackTrace();
			}
		finally{
				sjk.closeAll();
			}
		return res;
	}
	
	public String fid_log(String id) throws SQLException//查找后勤信息
	{	
		String res="";
		ssql sjk=new ssql();
		sjk.getConn();		 
		String sql ="SELECT * FROM logistics WHERE logistics_id=?";
		String[] params={id};
		ResultSet rs = sjk.executeQuery(sql,params);
			
		try {
				while(rs.next())
				{
					res=" 后勤id："+rs.getString(1)+"\n 密码："+rs.getString(2);
					ai=1;
				}
			} 
		catch (SQLException e) {
				e.printStackTrace();
			}
		finally{
				sjk.closeAll();
			}
		return res;
	}
	
	public String fid_delman(String id) throws SQLException//查找配送员信息
	{	
		String res="";
		ssql sjk=new ssql();
		sjk.getConn();		 
		String sql ="SELECT * FROM deliveryman WHERE deliveryman_id=?";
		String[] params={id};
		ResultSet rs = sjk.executeQuery(sql,params);
			
		try {
				while(rs.next())
				{
					res=" 配送员id："+rs.getString(1)+"\n 密码："+rs.getString(2)+"\n 联系方式："+rs.getString(3)
					+"\n 总配送量："+rs.getString(4);
					ai=1;
				}
			} 
		catch (SQLException e) {
				e.printStackTrace();
			}
		finally{
				sjk.closeAll();
			}
		return res;
	}
	
	/******************货物的增删查改****************/
	
	public String fid_prod(String id) throws SQLException//查找货物信息
	{	
		String res="";
		ssql sjk=new ssql();
		sjk.getConn();		 
		String sql ="SELECT * FROM product WHERE product_id=?";
		String[] params={id};
		ResultSet rs = sjk.executeQuery(sql,params);
			
		try {
				while(rs.next())
				{
					res=" 货物id："+rs.getString(1)+"\n 货物名称："+rs.getString(2)+"\n 细节信息："+rs.getString(3)
					+"\n 产品种类："+rs.getString(4)+"\n 库存量："+rs.getString(5)+"\n 价格："+rs.getString(6);
					ai=1;
				}
			} 
		catch (SQLException e) {
				e.printStackTrace();
			}
		finally{
				sjk.closeAll();
			}
		return res;
	}
	
	public void delt_prod(String id) throws SQLException//下架货物
	{	
		ssql sjk=new ssql();
		sjk.getConn();		 
		String sql ="DELETE FROM product WHERE product_id=?"; 
		String[] params = new String[]{id};
		sjk.executeUpdate(sql, params);
	    sjk.closeAll();
	}
	
	public void add_prod(String id,String nam,String info,String cate,String num,String prc) throws SQLException//添加货物
	{	
		ssql sjk=new ssql();
		sjk.getConn();		 
		String sql ="INSERT INTO product VALUES(?,?,?,?,?,?,NULL,NULL)"; 
		String[] params = new String[]{id,nam,info,cate,num,prc};
		sjk.executeUpdate(sql, params);
	    sjk.closeAll();
	}
	
	public int up_prod_num(String id,String num) throws SQLException{//修改货物库存
		
		ssql sjk=new ssql();
		sjk.getConn();
		String sql="UPDATE product SET product_num=? where product_id=?";
		String[] params={num,id};
		int nums=sjk.executeUpdate(sql, params);
	    sjk.closeAll();
	    return nums;	
	}

	/******************申请过程监督****************/

	public  ObservableList<proposal> psal_list() throws SQLException/*返回配送员接单表，直接在我写的view里找*/
	{	
		ssql sjk=new ssql();
		ObservableList<proposal> list = FXCollections.observableArrayList();
		sjk.getConn();		 
		String sql ="SELECT proposal_id,student_id,logistics_id,proposal_info,reply_info FROM proposal"; 
		ResultSet rs = sjk.executeQuery(sql,null);
			
		try {
				while(rs.next()){
					proposal c=new proposal();
					c.set_proposal_id(rs.getString(1));
					c.set_stu_id(rs.getString(2));
					c.set_log_id(rs.getString(3));
					c.set_psal_info(rs.getString(4));
					c.set_rply_info(rs.getString(5));
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
	
	public void delt_proposal(String id) throws SQLException//删除异常申请
	{	
		ssql sjk=new ssql();
		sjk.getConn();		 
		String sql ="DELETE FROM proposal WHERE proposal_id=?"; 
		String[] params = new String[]{id};
		sjk.executeUpdate(sql, params);
	    sjk.closeAll();
	}
	
	/******************时间管理****************/
	public void delt_time(String id) throws SQLException//删除配送员
	{	
		ssql sjk=new ssql();
		sjk.getConn();		 
		String sql ="DELETE FROM availabletime WHERE id=?"; 
		String[] params = new String[]{id};
		sjk.executeUpdate(sql, params);
	    sjk.closeAll();
	}
	
	public void add_time(String tmi) throws SQLException//添加后勤
	{	
		ssql sjk=new ssql();
		sjk.getConn();		 
		String sql ="INSERT INTO availabletime(time) VALUES(?)"; 
		String[] params = new String[]{tmi};
		sjk.executeUpdate(sql, params);
	    sjk.closeAll();
	}
	
	public  ObservableList<avltime> time_list() throws SQLException/*返回配送员接单表，直接在我写的view里找*/
	{	
		ssql sjk=new ssql();
		ObservableList<avltime> list = FXCollections.observableArrayList();
		sjk.getConn();		 
		String sql ="SELECT * FROM availabletime"; 
		ResultSet rs = sjk.executeQuery(sql,null);
			
		try {
				while(rs.next()){
					avltime c=new avltime();
					c.set_time_id(rs.getString(1));
					c.set_time(rs.getString(2));
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
