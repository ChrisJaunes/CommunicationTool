package com.chrisjaunes.communication.server.contacts;

import com.chrisjaunes.communication.server.Config;
import com.chrisjaunes.communication.server.utils_db.DBHelper;
import com.chrisjaunes.communication.server.utils_db.TimeHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ChrisJaunes
 */
@WebServlet(name = "Query" , urlPatterns = {"/contacts/query"})
public class Query extends HttpServlet {
    private static final Logger Log = Logger.getLogger("Contacts Query");
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Log.log(Level.WARNING, String.format("发现通过GET访问查询界面的人, 其IP为%s", request.getRemoteAddr()));
        response.setContentType("text/html;charset=UTF-8;");
        response.getWriter().append("[ERROR] 不应该使用GET访问");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String account = (String)request.getSession().getAttribute(Config.STR_ACCOUNT);
        String requestTime = request.getParameter(Config.STR_TIME);
        Log.info(String.format("account : %s, time : %s", account, requestTime));

        JSONObject resJson = new JSONObject();
        if (null == account) {
            resJson.put(Config.STR_STATUS, Config.STATUS_ACCOUNT_NOT_LOGIN);
        } else if (null == requestTime) {
            resJson.put(Config.STR_STATUS, Config.STATUS_ILLEGAL_PARAMETER);
        }else {
            requestTime = TimeHelper.timeToStdTime(requestTime);
            Log.info(requestTime);
            try {
                String sqlQuery = String.format("select %s, %s, %s, %s, %s from %s as `A`, %s as `C` where `A`.`%s` = `C`.`contacts`",
                        Config.STR_ACCOUNT, Config.STR_NICKNAME, Config.STR_AVATAR, Config.STR_TEXT_STYLE, Config.STR_TIME,
                        Config.TABLE_ACCOUNT,
                        String.format("(select if(`%s` = ?, `%s`, `%s`) as `contacts`, `time` from %s where %s >= ? and %s = ? and (%s = ? or %s = ?))",
                                Config.STR_ACCOUNT1, Config.STR_ACCOUNT2, Config.STR_ACCOUNT1,
                                Config.TABLE_CONTACTS,
                                Config.STR_TIME, Config.STR_OPERATION, Config.STR_ACCOUNT1, Config.STR_ACCOUNT2),
                        Config.STR_ACCOUNT
                );
                Log.info(sqlQuery);
                List<Object> params = new ArrayList<>();
                params.add(account);
                params.add(requestTime);
                params.add(Config.CONTACTS_FRIENDS_AGREE_CODE);
                params.add(account);
                params.add(account);
                ResultSet result = DBHelper.executeQuery(sqlQuery, params);
                JSONArray jsonA = new JSONArray();
                DBHelper.getResToJsonArray(result, jsonA);
                resJson.put(Config.STR_STATUS, Config.STATUS_SUCCESSFUL);
                resJson.put(Config.STR_STATUS_DATA, jsonA);
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Log.info(resJson.toString());
        response.setContentType("application/json");
        response.getWriter().append(resJson.toString()).flush();
    }
}
