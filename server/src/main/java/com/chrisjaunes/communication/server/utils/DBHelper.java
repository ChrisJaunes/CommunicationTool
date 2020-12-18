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
 * JDBC数据库操作公共类
 *
 * @author ChrisJaunes
 * DRIVER_CLASS 数据库驱动
 * DB_URL 数据库连接地址
 * DB_USER 数据库用户名称
 * DB_PASSWORD 数据库用户密码
 */
public class DBHelper {
    private static final Logger Log = Logger.getLogger("DBHelper");
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;

    /*
     * 静态代码块加载配置文件信息与数据库驱动类
     */
    static{
        try{
            DB_URL = "jdbc:mysql://localhost:3306/Communication?serverTimezone=Hongkong&useUnicode=true&characterEncoding=utf8&useSSL=false";
	        DB_USER = "root";
	        DB_PASSWORD = "Passwd123!";
//	        DB_PASSWORD = "123456";

            Class.forName(DRIVER_CLASS);
            Log.log(Level.INFO, "驱动加载成功");
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     * @return 数据库连接对象
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
     * 查询列表
     * @param sql    查询SQL语句
     * @param params 参数集合
     * @return 结果集
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
     * 执行操作
     * @param sql    执行SQL语句
     * @param params 参数集合
     * @return 受影响条数
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
     * 释放资源
     * @param res ResultSet对象
     */
    public static void closeResource(ResultSet res) {
        closeResource(res, null, null);
    }

    /**
     * 释放资源
     * @param stmt Statement对象
     * @param conn Connection对象
     */
    public static void closeResource(Statement stmt, Connection conn) {
        closeResource(null, stmt, conn);
    }

    /**
     * 释放资源
     * @param res  ResultSet对象
     * @param stmt Statement对象
     * @param conn Connection对象
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
     * 设置预处理的参数
     * @param preStmt 预处理
     * @param params  参数集合
     * @throws SQLException SQL异常
     */
    public static void setParams(PreparedStatement preStmt, List<Object> params) throws SQLException {
        if (null != params && params.size() > 0) {
            for (int i = 0; i < params.size(); i++) {
                preStmt.setObject(i + 1, params.get(i));
            }
        }
    }

    /**
     * 用SQL查询结果生成 JSONArray 对象
     * @param res 查询结果参数
     * @param jsonA jsonA 数组
     * @throws SQLException SQL异常
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