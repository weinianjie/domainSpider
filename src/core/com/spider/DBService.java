package com.spider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.logicalcobwebs.proxool.configuration.PropertyConfigurator;

import com.spider.entity.DomainEntity;
import com.spider.entity.StatusEntity;

/**
 * Created by IntelliJ IDEA. User: wnj Date: 2010-11-26 Time: 13:48:45 To change
 * this template use File | Settings | File Templates.
 */
public class DBService {
	public static final ThreadLocal<Connection> _conn = new ThreadLocal<Connection>();

	public static void init() {
		Properties dbProps = new Properties();
		try {
			dbProps.load(MainService.class
					.getResourceAsStream("/datasource.properties"));
			PropertyConfigurator.configure(dbProps);
			
			DBService.clearStatus();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int saveDomain(DomainEntity domain) {
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = getConnection();
			pst = con
					.prepareStatement("insert into domain_major(`name`,`org`,`scanCount`,`cts`,`uts`) values (?,?,1,now(),now())"
							+ " on duplicate key update scanCount=scanCount+1,uts=now()");
			int index = 1;
			pst.setString(index++,
					domain.getName() == null ? "" : domain.getName());
			pst.setString(index++,
					domain.getOrg() == null ? "" : domain.getOrg());
			return pst.executeUpdate();
		} catch (Throwable th) {
			th.printStackTrace();
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				clearAll();
			} catch (Throwable th) {
				th.printStackTrace();
			}
		}
		return -1;
	}

	public static int saveMemory(StatusEntity entity) {
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = getConnection();
			pst = con
					.prepareStatement("insert into server_status_memory(`mkey`,`mval`,`descr`,`cts`,`uts`) values (?,?,?,now(),now())"
							+ " on duplicate key update mval=?,uts=now()");
			int index = 1;
			pst.setString(index++, entity.getMkey());
			pst.setString(index++, entity.getMval());
			pst.setString(index++, entity.getDescr());
			pst.setString(index++, entity.getMval());
			return pst.executeUpdate();
		} catch (Throwable th) {
			th.printStackTrace();
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				clearAll();
			} catch (Throwable th) {
				th.printStackTrace();
			}
		}
		return -1;
	}

	public static int saveStatus(StatusEntity entity) {
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = getConnection();
			pst = con
					.prepareStatement("insert into server_status_tmp(`mkey`,`mval`,`descr`,`cts`,`uts`) values (?,?,?,now(),now())"
							+ " on duplicate key update mval=?,uts=now()");
			int index = 1;
			pst.setString(index++, entity.getMkey());
			pst.setString(index++, entity.getMval());
			pst.setString(index++, entity.getDescr());
			pst.setString(index++, entity.getMval());
			return pst.executeUpdate();
		} catch (Throwable th) {
			th.printStackTrace();
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				clearAll();
			} catch (Throwable th) {
				th.printStackTrace();
			}
		}
		return -1;
	}

	public static void clearStatus() {
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = getConnection();
			pst = con.prepareStatement("truncate table server_status_tmp");
			pst.execute();
		} catch (Throwable th) {
			th.printStackTrace();
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
				clearAll();
			} catch (Throwable th) {
				th.printStackTrace();
			}
		}
	}

	public static Connection getConnection() throws SQLException {
		Connection conn = _conn.get();
		if (conn == null) {
			conn = DriverManager.getConnection("proxool.mess");
			_conn.set(conn);
		}
		return conn;
	}

	public static void clearAll() throws SQLException {
		closeConn();
	}

	private static void closeConn() throws SQLException {
		Connection conn = _conn.get();
		_conn.set(null);
		if (conn != null) {
			if (!conn.isClosed()) {
				conn.close();
			}
		}
	}
}
