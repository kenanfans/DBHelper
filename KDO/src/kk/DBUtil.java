package kk;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DBUtil {
	private static  String driver = null;
	private static  String url = null;
	private static  String username = null;
	private static  String password = null;
	
	private CallableStatement callableStatement = null;//����CallableStatement����      
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rst = null;
	
/*	static {    
        try {    
            // �������ݿ���������    
            Class.forName(driver);    
        } catch (ClassNotFoundException e) {    
            System.out.println("������������");    
            System.out.println(e.getMessage());    
        }    
    } */   
    
	public DBUtil(String driver,String url ,String username,String password) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
    /**  
     * �������ݿ�����  
     * @return ���ݿ�����  
     */    
    public Connection getConnection() {    
        try {    
        	 // �������ݿ���������    
            try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				System.out.println("������������");    
	            System.out.println(e.getMessage());  
				e.printStackTrace();
			} 
            // ��ȡ����    
        	conn = DriverManager.getConnection(url, username,    
        			password);    
        } catch (SQLException e) {    
            System.out.println(e.getMessage());    
        }    
        return conn;    
    }    
    
    /**  
     * insert update delete SQL����ִ�е�ͳһ����  
     * @param sql SQL���  
     * @param params �������飬��û�в�����Ϊnull  
     * @return ��Ӱ�������  
     */    
    public int executeUpdate(String sql, Object[] params) {    
        // ��Ӱ�������    
        int affectedLine = 0;    
            
        try {    
            // �������    
            conn = this.getConnection();    
            // ����SQL     
            pst = conn.prepareStatement(sql);    
                
            // ������ֵ    
            if (params != null) {    
                for (int i = 0; i < params.length; i++) {    
                	pst.setObject(i + 1, params[i]);    
                }    
            }    
            /*�ڴ� PreparedStatement ������ִ�� SQL ��䣬
                                          ����������һ�� SQL ���ݲ������ԣ�Data Manipulation Language��DML����䣬���� INSERT��UPDATE �� DELETE 
                                          ��䣻�������޷������ݵ� SQL ��䣬���� DDL ��䡣    */
            // ִ��    
            affectedLine = pst.executeUpdate();
    
        } catch (SQLException e) {    
            System.out.println(e.getMessage());    
        } finally {    
            // �ͷ���Դ    
            closeAll();    
        }    
        return affectedLine;    
    }    
    
    /**  
     * SQL ��ѯ����ѯ���ֱ�ӷ���ResultSet��  
     * @param sql SQL���  
     * @param params �������飬��û�в�����Ϊnull  
     * @return �����  
     */    
    private ResultSet executeQueryRS(String sql, Object[] params) {    
        try {    
            // �������    
            conn = this.getConnection();    
                
            // ����SQL    
            pst = conn.prepareStatement(sql);    
                
            // ������ֵ    
            if (params != null) {    
                for (int i = 0; i < params.length; i++) {    
                	pst.setObject(i + 1, params[i]);    
                }    
            }    
                
            // ִ��    
            rst = pst.executeQuery();    
    
        } catch (SQLException e) {    
            System.out.println(e.getMessage());    
        }     
    
        return rst;    
    }    
        
    /**  
     * SQL ��ѯ����ѯ�����һ��һ��  
     * @param sql SQL���  
     * @param params �������飬��û�в�����Ϊnull  
     * @return �����  
     */    
    public Object executeQuerySingle(String sql, Object[] params) {    
        Object object = null;    
        try {    
            // �������    
            conn = this.getConnection();    
                
            // ����SQL    
            pst = conn.prepareStatement(sql);    
                
            // ������ֵ    
            if (params != null) {    
                for (int i = 0; i < params.length; i++) {    
                	pst.setObject(i + 1, params[i]);    
                }    
            }    
                
            // ִ��    
            rst = pst.executeQuery();    
    
            if(rst.next()) {    
                object = rst.getObject(1);    
            }    
                
        } catch (SQLException e) {    
            System.out.println(e.getMessage());    
        } finally {    
            closeAll();    
        }    
    
        return object;    
    }    
    
    /**  
     * ��ȡ������������������List��  
     *   
     * @param sql  SQL���  
     *         params  ������û����Ϊnull   
     * @return List  
     *                       �����  
     */    
    public List<Object> excuteQuery(String sql, Object[] params) {    
        // ִ��SQL��ý����    
        ResultSet rs = executeQueryRS(sql, params);    
            
        // ����ResultSetMetaData����    
        ResultSetMetaData rsmd = null;    
            
        // ���������    
        int columnCount = 0;    
        try {    
            rsmd = rs.getMetaData();    
                
            // ��ý��������    
            columnCount = rsmd.getColumnCount();    
        } catch (SQLException e1) {    
            System.out.println(e1.getMessage());    
        }    
    
        // ����List    
        List<Object> list = new ArrayList<Object>();    
    
        try {    
            // ��ResultSet�Ľ�����浽List��    
            while (rs.next()) {    
                Map<String, Object> map = new HashMap<String, Object>();    
                for (int i = 1; i <= columnCount; i++) {    
                    map.put(rsmd.getColumnLabel(i), rs.getObject(i));    
                }    
                list.add(map);//ÿһ��map����һ����¼�������м�¼����list��    
            }    
        } catch (SQLException e) {    
            System.out.println(e.getMessage());    
        } finally {    
            // �ر�������Դ    
            closeAll();    
        }    
    
        return list;    
    }    
        
    /**  
     * �洢���̴���һ����������ķ���  
     * @param sql �洢�������  
     * @param params ��������  
     * @param outParamPos �������λ��  
     * @param SqlType �����������  
     * @return ���������ֵ  
     */    
    public Object excuteQuery(String sql, Object[] params,int outParamPos, int SqlType) {    
        Object object = null;    
        conn = this.getConnection();    
        try {    
            // ���ô洢����    
        	// prepareCall:����һ�� CallableStatement �������������ݿ�洢���̡�
            callableStatement = conn.prepareCall(sql);    
                
            // ��������ֵ    
            if(params != null) {    
                for(int i = 0; i < params.length; i++) {    
                    callableStatement.setObject(i + 1, params[i]);    
                }    
            }    
                
            // ע���������    
            callableStatement.registerOutParameter(outParamPos, SqlType);    
                
            // ִ��    
            callableStatement.execute();    
                
            // �õ��������    
            object = callableStatement.getObject(outParamPos);    
                
        } catch (SQLException e) {    
            System.out.println(e.getMessage());    
        } finally {    
            // �ͷ���Դ    
            closeAll();    
        }    
            
        return object;    
    }    
    
    /**  
     * �ر�������Դ  
     */    
    private void closeAll() {    
        // �رս��������    
        if (rst != null) {    
            try {    
                rst.close();    
            } catch (SQLException e) {    
                System.out.println(e.getMessage());    
            }    
        }    
    
        // �ر�PreparedStatement����    
        if (pst != null) {    
            try {    
                pst.close();    
            } catch (SQLException e) {    
                System.out.println(e.getMessage());    
            }    
        }    
            
        // �ر�CallableStatement ����    
        if (callableStatement != null) {    
            try {    
                callableStatement.close();    
            } catch (SQLException e) {    
                System.out.println(e.getMessage());    
            }    
        }    
    
        // �ر�Connection ����    
        if (conn != null) {    
            try {    
                conn.close();    
            } catch (SQLException e) {    
                System.out.println(e.getMessage());    
            }    
        }       
    }    

}
