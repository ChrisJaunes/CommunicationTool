package com.chrisjaunes.communication.server.group;

import com.chrisjaunes.communication.server.Config;
import com.chrisjaunes.communication.server.utils.DBHelper;
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
 * @date 2020/11/30
 * only use post, need login
 * @status : XXX
 */
@WebServlet(name = "QueryGroup", urlPatterns = "/group/query")
public class Query extends HttpServlet {
    private static final Logger Log = Logger.getLogger("Group Query");
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
     * such as : login_account:111;
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
        Log.info(String.format("account : %s", account));

        JSONObject resJson = new JSONObject();
        if (null == account) {
            resJson.put(Config.STR_STATUS, Config.STATUS_ACCOUNT_NOT_LOGIN);
        } else {
            try {
                String sqlQuery = String.format("select `%s`, `%s`, `%s` from (%s) as A left join `%s` on A.`group_id` = `%s`.`%s`",
                        Config.STR_GROUP, Config.STR_GROUP_NAME,Config.STR_GROUP_AVATAR,
                        String.format("select `%s` as `group_id` from `%s` where `%s` = ?", Config.STR_GROUP, Config.TABLE_GROUP_MEMBER, Config.STR_ACCOUNT),
                        Config.TABLE_GROUP, Config.TABLE_GROUP, Config.STR_GROUP
                );
                Log.info(sqlQuery);
                List<Object> params = new ArrayList<>();
                params.add(account);
                ResultSet result = DBHelper.executeQuery(sqlQuery, params);
                JSONArray jsonA = new JSONArray();
                DBHelper.getResToJsonArray(result, jsonA);
                resJson.put(Config.STR_STATUS, Config.STATUS_QUERY_GROUP_SUCCESSFUL);
                resJson.put(Config.STR_STATUS_DATA, jsonA);
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
                resJson.put(Config.STR_STATUS, Config.STATUS_DB_ILLEGAL_PARAMETER);
            }
        }
        Log.info(resJson.toString());
        response.setContentType("application/json");
        response.getWriter().append(resJson.toString()).flush();
    }
}
