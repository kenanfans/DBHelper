package kk.DB;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Properties;


public class DBHelper {
	public static ArrayList<DBConn> _DBConnList = new ArrayList<DBConn>();
	public static Boolean _Inited = false; 
	
	public static DBConn GetDefaultConn() throws Exception
	{
		if(!_Inited) {
			throw new Exception("数据库连接尚未初始化");
		}
		return _DBConnList.get(0);
	}
	
	public static void InitDBHelper(String _basePath) {
		
	}
	
	
}
