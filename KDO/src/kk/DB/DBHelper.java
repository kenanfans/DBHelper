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
			throw new Exception("���ݿ�������δ��ʼ��");
		}
		return _DBConnList.get(0);
	}
	
	public static void InitDBHelper(String _basePath) {
		
	}
	
	
}
