package xxx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class HelloJDBC {
	//實作Driver類別 Java寫好的
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	//資料庫位置URL
	public static final String URL="jdbc:mysql://localhost:3306/jdbcsample?serverTimezone=Asia/Taipei";
	public static final String USER="David";
	public static final String PASSWORD="123456";
	
	public static void main(String[]args) {
		Connection con=null;
		Statement stmt=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		Scanner sc =new Scanner(System.in);
		System.out.println("請輸入新增的編號");
		int deptno = sc.nextInt();
		System.out.println("請輸入新增的名稱");
		String dname = sc.next();
		System.out.println("請輸入新增的所在地");
		String loc = sc.next();

		
		try {
			//step 1:載入驅動
			Class.forName(DRIVER); //JDBC
			System.out.println("載入成功");
			
			//step2: 建立連線
			con = DriverManager.getConnection(URL, USER, PASSWORD);//JDBC
			System.out.println("連線成功");
			
			//step3:送出SQL指令
			//prepareStatement動態改變的方法 對於未知的資料可以先用?代替
			//只能針對SQL指令的'值'做問號未知的表示
			pstmt=con.prepareStatement("INSERT INTO DEPARTMENT VALUES(?, ?, ?)");
			pstmt.setInt(1, deptno);//1:第一個問號(JDBC 索引從1開始)
			pstmt.setString(2, dname);
			pstmt.setString(3, loc);
			
			pstmt.executeUpdate();//不需要加參數(因為SQL指令已經先交給資料庫了)
			
			//Statement靜態查詢
//			stmt=con.createStatement();
//			//executeQuery查詢方法
//			rs=stmt.executeQuery("SELECT * FROM DEPARTMENT");
//			//移動游標next()方法
//			while(rs.next()) {
//				int deptno=rs.getInt("DEPTNO"); //rs.getInt(1); 給欄位索引值 JDBC索引從1開始 
//				String dname =rs.getString("DNAME");//rs.getInt(2);給第二的欄位 
//				String loc= rs.getString("LOC");//rs.getInt(3); 給第三個欄位
//
//				System.out.println("DEPTNO="+deptno);
//				System.out.println("DNAME="+dname);
//				System.out.println("LOC="+loc);
//				System.out.println("================");
//			}
			//Statement靜態新增
			//executeUpdate新增 刪除 修改資料庫 的方法
//			stmt=con.createStatement();
//			int count=stmt.executeUpdate("INSERT INTO DEPARTMENT VALUES(50, '會計部', '南京復興')");
//			System.out.println(count+"row(s) updated.");
			
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}catch(SQLException se) {
			se.printStackTrace();
		}finally { //一定會處理，所以建議關閉資源寫在finally
			if(rs!=null) {
				try {
					rs.close(); //關閉Statement(越晚建立，越早歸還)
				}catch(SQLException se) {
					se.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close(); //關閉Statement(越晚建立，越早歸還)
				}catch(SQLException se) {
					se.printStackTrace();
				}
			}
			if(stmt!=null) {
				try {
					stmt.close(); //關閉Statement(越晚建立，越早歸還)
				}catch(SQLException se) {
					se.printStackTrace();
				}
			}
			if(con!=null) {
				try {
					con.close(); //關閉連線
				}catch(SQLException se) {
					se.printStackTrace();
				}
			}
			
		}
	}
}
