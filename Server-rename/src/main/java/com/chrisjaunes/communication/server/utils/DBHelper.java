package com.chrisjaunes.communication.server.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * JDBC���ݿ����������
 *
 * @author ChrisJaunes
 * DRIVER_CLASS ���ݿ�����
 * DB_URL ���ݿ����ӵ�ַ
 * DB_USER ���ݿ��û�����
 * DB_PASSWORD ���ݿ��û�����
 */
public class DBHelper {
    private static final Logger Log = Logger.getLogger("DBHelper");
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;

    /*
     * ��̬�������������ļ���Ϣ�����ݿ�������
     */
    static{
        try{
            DB_URL = "jdbc:mysql://localhost:3306/Communication?serverTimezone=Hongkong&useUnicode=true&characterEncoding=utf8&useSSL=false";
	        DB_USER = "root";
	        DB_PASSWORD = "Passwd123!";
//	        DB_PASSWORD = "123456";

            Class.forName(DRIVER_CLASS);
            Log.log(Level.INFO, "�������سɹ�");
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    /**
     * ��ȡ���ݿ�����
     * @return ���ݿ����Ӷ���
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return conn;
    }

    /**
     * ��ѯ�б�
     * @param sql    ��ѯSQL���
     * @param params ��������
     * @return �����
     */
    public static ResultSet executeQuery(String sql, List<Object> params) {
        Connection conn = null;
        PreparedStatement preStmt = null;
        ResultSet res = null;
        try {
            conn = getConnection();
            preStmt = conn.prepareStatement(sql);
            setParams(preStmt, params);
            res = preStmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            closeResource(preStmt, conn);
        }
        return res;
    }

    /**
     * ִ�в���
     * @param sql    ִ��SQL���
     * @param params ��������
     * @return ��Ӱ������
     */
    public static int executeOperate(String sql, List<Object> params) throws SQLException{
        int count = 0;
        Connection conn = null;
        PreparedStatement preStmt = null;
        try {
            conn = getConnection();
            preStmt = conn.prepareStatement(sql);
            setParams(preStmt, params);
            count = preStmt.executeUpdate();
        } finally {
            closeResource(preStmt, conn);
        }
        return count;
    }

    /**
     * �ͷ���Դ
     * @param res ResultSet����
     */
    public static void closeResource(ResultSet res) {
        closeResource(res, null, null);
    }

    /**
     * �ͷ���Դ
     * @param stmt Statement����
     * @param conn Connection����
     */
    public static void closeResource(Statement stmt, Connection conn) {
        closeResource(null, stmt, conn);
    }

    /**
     * �ͷ���Դ
     * @param res  ResultSet����
     * @param stmt Statement����
     * @param conn Connection����
     */
    public static void closeResource(ResultSet res, Statement stmt, Connection conn) {
        try {
            if (null != res) {
                res.close();
            }
            if (null != stmt) {
                stmt.close();
            }
            if (null != conn) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ����Ԥ����Ĳ���
     * @param preStmt Ԥ����
     * @param params  ��������
     * @throws SQLException SQL�쳣
     */
    public static void setParams(PreparedStatement preStmt, List<Object> params) throws SQLException {
        if (null != params && params.size() > 0) {
            for (int i = 0; i < params.size(); i++) {
                preStmt.setObject(i + 1, params.get(i));
            }
        }
    }

    /**
     * ��SQL��ѯ������� JSONArray ����
     * @param res ��ѯ�������
     * @param jsonA jsonA ����
     * @throws SQLException SQL�쳣
     */
    public static void getResToJsonArray(ResultSet res, JSONArray jsonA) throws SQLException{
    	assert null != res && !res.isClosed();
        assert null != jsonA;
        while(res.next()) {
            ResultSetMetaData metaData = res.getMetaData();
            int columnCount = metaData.getColumnCount();
            JSONObject jsonO = new JSONObject();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnLabel(i);
                int columnType = metaData.getColumnType(i);
                if (null != res.getString(columnName) && !"".equals(res.getString(columnName))) {
                    switch (columnType) {
                        case Types.INTEGER:
                            jsonO.put(columnName, res.getInt(columnName));
                            break;
                        case Types.BIT:
                            jsonO.put(columnName, res.getBoolean(columnName));
                            break;
                        default:
                            jsonO.put(columnName, res.getString(columnName));
                    }
                } else {
                    jsonO.put(columnName, null);
                }
            }
            jsonA.add(jsonO);
        }
    }
}