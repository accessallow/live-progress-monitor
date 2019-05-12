package progressMonitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class DataProvider {
	
	public static String getMigrationTotalProgressSQL(){
		String sql = "select"
				+ "(select ROUND(sum(file_size)/(1024*1024*1024),3) || ' GB' from ENTITY_TO_FILE_OBJ where container_name like '%_Prod_%') as TOTAL_SIZE,"
				+ "(select ROUND(sum(file_size)/(1024*1024*1024),3) || ' GB' from MIGRATION_OS_BACKUP_REPORT) as DOWNLOADED_SIZE, "
				+ "(select  ROUND(100 * ((select sum(file_size) from MIGRATION_OS_BACKUP_REPORT)/(select sum(file_size) from ENTITY_TO_FILE_OBJ where container_name like '%_Prod_%')),3) || ' %' as DOWNLOADED_PERCENTAGE from MIGRATION_OS_BACKUP_REPORT FETCH NEXT 1 ROWS ONLY) as DOWNLOADED_PERCENTAGE "
				+ "from MIGRATION_OS_BACKUP_REPORT FETCH NEXT 1 ROWS ONLY";
		return sql;
	}
	
	public static Map getMigrationStats() {
		Map<String,String> statsMap =  new HashMap<String,String>();
		
		statsMap.put("TOTAL_SIZE", "0");
		statsMap.put("DOWNLOADED_SIZE", "0");
		statsMap.put("DOWNLOADED_PERCENTAGE", "0");
		
		return statsMap;
	}
	
	public static Map getMigrationStats2() {
		Map<String,String> statsMap = new HashMap<String,String>();
		String totalSize = "NA";
		String downloadSize = "NA";
		String downloadPercentage ="NA";
		
		
		ResultSet rs				= null;
		Connection db2Conn1 		= null;
		PreparedStatement db2Stmt1 			= null;
		try{
			String dbQuery = getMigrationTotalProgressSQL();
			DevUtils.sqlLog(dbQuery);
			db2Conn1 		= DBUtils.getDB2Connection();	
			db2Stmt1 		= db2Conn1.prepareStatement(dbQuery);
			rs = db2Stmt1.executeQuery();
			while(rs.next()){
				totalSize = rs.getString("TOTAL_SIZE");
				downloadSize = rs.getString("DOWNLOADED_SIZE");
				downloadPercentage = rs.getString("DOWNLOADED_PERCENTAGE");
				
				statsMap.put("TOTAL_SIZE", totalSize);
				statsMap.put("DOWNLOADED_SIZE", downloadSize);
				statsMap.put("DOWNLOADED_PERCENTAGE", downloadPercentage);
			}
		}catch (Exception e){	
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null){
					rs.close();
				}
				if(db2Stmt1!=null){
					db2Stmt1.close();
				}
				if(db2Conn1!=null){
					db2Conn1.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();

			}
		}
		
		return statsMap;
	}
}
