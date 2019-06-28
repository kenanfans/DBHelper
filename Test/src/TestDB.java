import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import kk.DB.DBConn;
import kk.DB.DBHelper;
import kk.Util.Config;


public class TestDB {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		String connPropFilePath =  Thread.currentThread().getContextClassLoader().getResource("").getPath()+"conn.properties";
//		System.out.println(connPropFilePath);
//		Properties connProp = new Properties();
//		try {
//			connProp.load(new FileInputStream(connPropFilePath));
//			System.out.println(connProp.getProperty("UserMgrDB"));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("1");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("2");
//		}
//		String ResultString = "";
//		try {
//			Pattern regex = Pattern.compile("(?<=:)[a-zA-Z0-9]+?(?=:)");
//			Matcher regexMatcher = regex.matcher("jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=dbname");
//			if (regexMatcher.find()) {
//				ResultString = regexMatcher.group();
//			} 
//			System.out.println(ResultString);
//		} catch (PatternSyntaxException ex) {
//			// Syntax error in the regular expression
//			System.out.println(ex.getMessage());
//		}
		//DBHelper.InitDBHelper(Thread.currentThread().getContextClassLoader().getResource("").getPath());
		Config.Init(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "KKConfig.properties");
		DBHelper.InitDBHelper();
		DBConn conn = DBHelper.GetDefaultConn();
		ResultSet rs = conn.ExecQueryRS("SELECT * FROM `UserInfo`");
		if(rs.next()) {
			System.out.println(rs.getString(0));
		}
		else
		{
			System.out.println("未查询到数据");
		}
		conn.ExecUpdate("INSERT INTO `t1` VALUES (?,?)",2,3);
		conn.CloseAll();
	}

}
