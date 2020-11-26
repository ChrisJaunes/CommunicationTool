package com.chrisjaunes.communication.server.utils_db;

import net.sf.json.JSONArray;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class DBHelperTest {
    private static Logger Log = Logger.getLogger("DBHelper");

    @Test
    public void getConnection() {
        Connection s = DBHelper.getConnection();
        assertNotNull(s);
    }

    @Test
    public void executeQuery1() throws SQLException {
        final String sql1 = "select * from test";
        ResultSet res = DBHelper.executeQuery(sql1, null);
        assert !res.isClosed();
        Log.log(Level.INFO, res.toString());
    }

    @Test
    public void executeOperate() {
        final String sql1 = "insert test values(?, ?)";
        List<Object> params = new ArrayList<>();
        params.add("666");
        params.add("666");
        int res = DBHelper.executeOperate(sql1, params);
        assert res == 1;
    }

    @Test
    public void getResToJsonArray() {
        final String sql1 = "select * from test where test_id = '111' or test_id = '123' ";
        ResultSet res = DBHelper.executeQuery(sql1, null);
        JSONArray jsonA = new JSONArray();
        try {
            DBHelper.getResToJsonArray(res, jsonA);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(jsonA.toString(), "[{\"test_id\":111,\"test_content\":\"111\"},{\"test_id\":123,\"test_content\":\"123\"}]");
    }
}