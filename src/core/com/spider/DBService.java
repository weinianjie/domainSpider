package com.spider;

import java.sql.*;

import com.spider.entity.DomainEntity;

/**
 * Created by IntelliJ IDEA.
 * User: wnj
 * Date: 2010-11-26
 * Time: 13:48:45
 * To change this template use File | Settings | File Templates.
 */
public class DBService {
    public static final ThreadLocal<Connection> _conn = new ThreadLocal<Connection>();

    public static int save(DomainEntity domain){
        Connection con=null;
        PreparedStatement pst=null;
        try{
            con=getConnection();
            pst=con.prepareStatement("insert into domain(`name`,`org`,`owner`,`bdate`,`edate`,`cts`,`uts`) values (?,?,?,?,?,now(),now())");
            int index=1;
            pst.setString(index++, domain.getName() == null? "":domain.getName());
            pst.setString(index++, domain.getOrg() == null? "":domain.getOrg());
            pst.setString(index++, domain.getOwner() == null? "":domain.getOwner());
            pst.setDate(index++, (Date) domain.getBdate());
            pst.setDate(index++, (Date) domain.getEdate());
            
            return pst.executeUpdate();
        }catch(Throwable th){
            th.printStackTrace();
        }finally{
            try{
                if(pst!=null){
                    pst.close();
                }
                clearAll();
            }catch(Throwable th){
                th.printStackTrace();
            }
        }
        return -1;
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = _conn.get();
        if(conn == null){
            conn = DriverManager.getConnection("proxool.mess");
            _conn.set(conn);
        }
        return conn;
    }

    public static void clearAll() throws SQLException{
       closeConn();
    }

    private static void closeConn() throws SQLException{
		Connection conn = _conn.get();
		_conn.set(null);
		if (conn != null) {
            if (!conn.isClosed()) {
    			conn.close();
            }
		}
	}    
}
