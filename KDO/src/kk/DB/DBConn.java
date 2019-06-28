package kk.DB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConn {
	private static  String driver = null;
	private static  String url = null;
	private static  String username = null;
	private static  String password = null;
	
	private CallableStatement callableStatement = null;//创建CallableStatement对象      
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rst = null;
	
	public DBConn(String url ,String username,String password,String driver) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
    /**  
     * 建立数据库连接  
     * @return 数据库连接  
     */    
    public Connection GetConn() {    
        try {    
        	 // 加载数据库驱动程序    
            try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				System.out.println("加载驱动错误");    
	            System.out.println(e.getMessage());  
				e.printStackTrace();
			} 
            // 获取连接    
        	conn = DriverManager.getConnection(url, username, password);    
        } catch (SQLException e) {    
            System.out.println(e.getMessage() + ",Driver=" + driver);    
        }    
        return conn;    
    }    
}
