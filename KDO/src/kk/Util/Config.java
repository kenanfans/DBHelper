package kk.Util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class Config {
	public static Config Instance;
	private static Properties _patternProperties;
	private static Properties _connProperties;
	private static Properties _dbdriverProperties;
	
	public static void Init(String _configPath) throws IOException {
		Instance = new Config(_configPath);
		
	}
	
	public String GetPattern(String _key) {
		return _patternProperties.getProperty(_key);
	}

	public Properties GetConnProperties() {
		return _connProperties;
	}

	public Properties GetDBDriverProperties() {
		return _dbdriverProperties;
	}
	
	private Config(String _configPath) throws IOException {
		InputStream in = new BufferedInputStream(new FileInputStream(_configPath));
		Properties tmpProp = new Properties();
		tmpProp.load(in);
		in.close();
		
		_patternProperties = new Properties();
		_connProperties = new Properties();
		_dbdriverProperties = new Properties();
		
		Iterator it = tmpProp.entrySet().iterator();
		while(it.hasNext()){
		    Map.Entry entry=(Map.Entry)it.next();
		    String key = entry.getKey().toString();
		    String value = entry.getValue().toString();
		    String prefix = GetPrefix(key);
		    key = GetKey(key);
//		    System.out.println("prefix：" + prefix + "，key：" + key);
		    
		    switch (prefix) {
			case "Pattern":
				_patternProperties.put(key, value);
				break;
			case "Conn":
				_connProperties.put(key, value);
				break;
			case "DBDriver":
				_dbdriverProperties.put(key, value);
				break;
			default:
				System.out.println("未识别的配置类：" + prefix);
				break;
			}
		}
	}
	private String GetPrefix(String _key) {
		return _key.substring(0,_key.indexOf("."));
	}
	private String GetKey(String _key) {
		return _key.substring(_key.indexOf(".") + 1);
	}
}
