package kk.DB;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import kk.Util.Config;


public class DBHelper {
	public static Map<String,DBConn> _DBConnList = new HashMap<String,DBConn>();
	private static String _DefaultConnName="";
	public static Boolean _Inited = false; 
	
	public static DBConn GetDefaultConn() throws Exception
	{
		if(!_Inited) {
			throw new Exception("数据库连接尚未初始化");
		}
		return _DBConnList.get(_DefaultConnName);
	}
	
	public static void InitDBHelper()  {
		System.out.println("InitDBHelper");
		String patternDBType = Config.Instance.GetPattern("dbtype");
		Properties dbDrivers = Config.Instance.GetDBDriverProperties();
		Properties dbConns = Config.Instance.GetConnProperties();
		
		Iterator it = dbConns.entrySet().iterator();
		while(it.hasNext()){
		    Map.Entry entry=(Map.Entry)it.next();
		    String key = entry.getKey().toString();
		    String prefix = key.substring(0,6);
		    String value = entry.getValue().toString();
		    if(prefix.equalsIgnoreCase("DBURL_")) {
		    	String dbName = key.substring(6);
		    	System.out.println(dbName);
		    	String dbUserName = dbConns.getProperty("DBUSERNAME_" + dbName);
		    	String dbUserPass = dbConns.getProperty("DBUSERPASS_" + dbName);
		    	
		    	String dcString = GetDBDriverClass(patternDBType,value);
		    	String dbClassName = dbDrivers.getProperty(dcString);
		    	DBConn dbConn = new DBConn(value, dbUserName, dbUserPass, dbClassName);
		    	dbConn.GetConn();
		    	if(!_Inited) {
		    		_Inited = true;
		    		_DefaultConnName = dbName;
		    	}
		    	
		    	System.out.println(dbName + ":" + dbUserName + "," + dbUserPass + " - " + dbClassName);
		    }
		}
	}
	
	private static String GetDBDriverClass(String _pattern, String _url) {
		String ResultString = "";
		try {
			Pattern regex = Pattern.compile(_pattern);
			Matcher regexMatcher = regex.matcher(_url);
			if (regexMatcher.find()) {
				ResultString = regexMatcher.group();
			} 
			System.out.println(ResultString);
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
			System.out.println(ex.getMessage());
		}
		
		return ResultString;
	}
	
	
}
