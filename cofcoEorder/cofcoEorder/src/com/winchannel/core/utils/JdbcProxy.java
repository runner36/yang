package com.winchannel.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcProxy {
	
	public static final Log log = LogFactory.getLog(JdbcProxy.class);
	
	private JdbcTemplate jdbcTemplate = null;
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;
    
    public JdbcProxy() throws SQLException {
    	this("jdbcTemplate");
    }

    public JdbcProxy(String jdbcTemplateName) throws SQLException {
    	jdbcTemplate = (JdbcTemplate) SpringContext.getBean(jdbcTemplateName);
    	conn = jdbcTemplate.getDataSource().getConnection();
    	stmt = conn.createStatement();
    }

    public void prepareStatement(String sql) throws SQLException {
        if (pstmt != null) {
            pstmt.close();
            pstmt = null;
        }
        pstmt = conn.prepareStatement(sql);
    }

    public ResultSet executeQuery(String sql) throws SQLException{
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        }
        catch (SQLException e) {
            log.error(e.getMessage() + " : " + sql);
            throw e;
        }
        return rs;
    }

    public <T> List<T> executeQuery(String sql, Class<T> clazz) throws SQLException {
		List<T> list = null;
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            list = getEntityList(rs, Long.MAX_VALUE, clazz);
        }
        catch (SQLException e) {
            log.error(e.getMessage() + " : " + sql);
            throw e;
        }
        finally {
        	if (rs != null) {
        		rs.close();
        	}
        }
        return list;
    }

    public <T> T executeUniqueQuery(String sql, Class<T> clazz) throws SQLException {
    	T object = null;
    	Iterator<T> it = executeQuery(sql, clazz).iterator();
    	if (it.hasNext()) {
    		object = it.next();
    	}
        return object;
    }

    public ResultSet executeQuery() throws SQLException{
        ResultSet rs = null;
        try {
            rs = pstmt.executeQuery();
        }
        catch (SQLException e) {
        	log.error(e.getMessage());
            throw e;
        }
        return rs;
    }

    public <T> List<T> executeQuery(Class<T> clazz) throws SQLException {
		List<T> list = null;
        ResultSet rs = null;
        try {
            rs = pstmt.executeQuery();
            list = getEntityList(rs, Long.MAX_VALUE, clazz);
        }
        catch (SQLException e) {
            log.error(e.getMessage());
            throw e;
        }
        finally {
        	if (rs != null) {
        		rs.close();
        	}
        }
        return list;
    }

    public <T> T executeUniqueQuery(Class<T> clazz) throws SQLException {
    	T object = null;
    	Iterator<T> it = executeQuery(clazz).iterator();
    	if (it.hasNext()) {
    		object = it.next();
    	}
        return object;
    }

    public int executeUpdate(String sql) throws SQLException{
        int n = 0;
        try {
            n = stmt.executeUpdate(sql);
        }
        catch (SQLException e) {
        	log.error(e.getMessage() + " : " + sql);
            throw e;
        }
        return n;
    }

    public int executeUpdate() throws SQLException{
        int n = 0;
        try {
            n = pstmt.executeUpdate();
        }
        catch (SQLException e) {
        	log.error(e.getMessage());
            throw e;
        }
        return n;
    }
    
    public static <T> List<T> getEntityList(ResultSet rs, long size, Class<T> clazz) throws SQLException {
    	List<T> list = new ArrayList<T>();
    	ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        String[] columns = new String[columnCount + 1];
        for (int i = 1; i <= columnCount; i++) {
        	columns[i] = meta.getColumnLabel(i);
//        	if (columns[i].indexOf("_") != -1) {
//        		columns[i] = makeMemberName(columns[i]);
//        	}
        }

        for (int i = 0; i < size && rs.next(); i++) {
        	try {
        		T bean = clazz.newInstance();
        		for (int j = 1; j <= columnCount; j++) {
        			BeanUtils.copyProperty(bean, columns[j], rs.getString(j));
        		}
        		list.add(bean);
        	} catch (InstantiationException e) {
        		e.printStackTrace();
        	} catch (IllegalAccessException e) {
        		e.printStackTrace();
        	} catch (InvocationTargetException e) {
        		e.printStackTrace();
        	}
       }
        return list;
    }

    public static <T> List<T> getEntityList(ResultSet rs, Class<T> clazz) throws SQLException {
    	return getEntityList(rs, Long.MAX_VALUE, clazz);
    }

    /*private static String makeMemberName(String name) {
        String memberName = name.toLowerCase();
        int i;
        while ((i = memberName.indexOf("_")) != -1) {
            java.lang.String tmp1 = memberName.substring(0, i);
            if (i + 1 < memberName.length()) {
                tmp1 += memberName.substring(i + 1, i + 2).toUpperCase();
            }
            if (i + 2 < memberName.length()) {
                tmp1 += memberName.substring(i + 2);
            }
            memberName = tmp1;
        }
        return memberName;
    }*/
    
    public static List<Object[]> getObjectArrayList(ResultSet rs, long size) throws SQLException {
    	List<Object[]> list = new ArrayList<Object[]>();
    	ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();
        for (int i = 0; i < size && rs.next(); i++) {
        	Object[] object = new Object[columnCount];
        	for (int j = 0; j < columnCount; j++) {
        		object[j] = rs.getObject(j + 1);
        	}
        	list.add(object);
        }
        return list;
    }
    
    public static List<Object[]> getObjectArrayList(ResultSet rs) throws SQLException {
    	return getObjectArrayList(rs, Long.MAX_VALUE);
    }

    public void setAutoCommit(boolean b) throws SQLException {
        conn.setAutoCommit(b);
    }

    public void commit() throws SQLException {
        conn.commit();
    }

    public void rollback() throws SQLException {
        conn.rollback();
    }

    public void setLong(int i, Long x) throws SQLException {
        pstmt.setLong(i, x);
    }

    public void setBlob(int i, Blob x) throws SQLException {
        pstmt.setBlob(i, x);
    }

    public void setString(int i, String x) throws SQLException {
        pstmt.setString(i, x);
    }

    public void setInt(int i, Integer x) throws SQLException {
        pstmt.setInt(i, x);
    }

    public void setDate(int i, Date x) throws SQLException {
        pstmt.setDate(i, x);
    }

    public void setTime(int i, Time x) throws SQLException {
        pstmt.setTime(i, x);
    }

    public void close() {
        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
                pstmt = null;
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}
    
}
