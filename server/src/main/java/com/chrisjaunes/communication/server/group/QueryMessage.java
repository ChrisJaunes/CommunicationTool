package com.chrisjaunes.communication.server.group;

import com.chrisjaunes.communication.server.Config;
import com.chrisjaunes.communication.server.utils.DBHelper;
import com.chrisjaunes.communication.server.utils.TimeHelper;
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
 * @author ChrisJauns
 * @version 1
 * @date 2020/12/1
 * only use post, need login
 * @status : XXX
 */
@WebServlet(name = "GroupQuery", urlPatterns = "/group/queryMessage")
public class QueryMessage extends HttpServlet {
    private static final Logger Log = Logger.getLogger("Group Add Message");
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
     * group : id of group
     * time : request time of request
     * such as : login_account:111; host : ?group=1&time=2020-11-17 00:00:00
     * @return : Config.STATUS_ACCOUNT_NOT_LOGIN
     *           Config.STATUS_DB_ILLEGAL_PARAMETER
     *           Config.STATUS_SUCCESSFUL
     * @unit_test : test -> group
     * @status : XXX
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String account = (String)request.getSession().getAttribute(Config.STR_ACCOUNT);
        String groupId = request.getParameter(Config.STR_GROUP);
        String requestTime = request.getParameter(Config.STR_TIME);
        Log.info(String.format("account : %s, group : %s, time : %s", account, groupId, requestTime));

        JSONObject resJson = new JSONObject();
        if (null == account) {
            resJson.put(Config.STR_STATUS, Config.STATUS_ACCOUNT_NOT_LOGIN);
        } else if (null == requestTime) {
            resJson.put(Config.STR_STATUS, Config.STATUS_ILLEGAL_PARAMETER);
        } else {
            requestTime = TimeHelper.timeToStdTime(requestTime);
            Log.info(requestTime);
            try {
                String sqlQuery = String.format("select `%s`, `%s`, `%s`, `%s`, `%s` from `%s` where `%s` >= ? and `%s` = ?",
                        Config.STR_GROUP, Config.STR_ACCOUNT, Config.STR_SEND_TIME, Config.STR_CONTENT_TYPE,Config.STR_CONTENT,
                        Config.TABLE_GROUP_MESSAGES,
                        Config.STR_SEND_TIME, Config.STR_GROUP);
                Log.info(sqlQuery);
                List<Object> params = new ArrayList<>();
                params.add(requestTime);
                params.add(groupId);
                ResultSet result = DBHelper.executeQuery(sqlQuery, params);
                JSONArray jsonA = new JSONArray();
                DBHelper.getResToJsonArray(result, jsonA);
                resJson.put(Config.STR_STATUS, Config.STATUS_UPDATE_SUCCESSFUL);
                resJson.put(Config.STR_STATUS_DATA, jsonA);
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
                resJson.put(Config.STR_STATUS, Config.STATUS_DB_ILLEGAL_PARAMETER);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.info(resJson.toString());
        response.setContentType("application/json");
        response.getWriter().append(resJson.toString()).flush();
    }
}

