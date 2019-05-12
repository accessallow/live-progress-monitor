package progressMonitor;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBUtils {
	private static String oracleDriver = "";
	private static String oracleUser 						= "";
	private static String oraclePpassword 					= "";
    private static String oracleURL 						= "";
    
	public static Connection getDB2Connection() {
		Connection db2Conn = null;
		try {
			Class.forName(oracleDriver);
			DriverManager.setLoginTimeout(0);
			db2Conn = DriverManager.getConnection(oracleURL, oracleUser,oraclePpassword);
		} catch (Exception e) {
			System.out.println("Error in OrcaleConnection creation");
			e.printStackTrace();
		}
		return db2Conn;
	}
	
}
