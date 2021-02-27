package com.chrisjaunes.communication.server.group;

import com.chrisjaunes.communication.server.Config;
import com.chrisjaunes.communication.server.utils.DBHelper;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ChrisJaunes
 * @version 1
 * @date 2020/11/30
 * only use post, need login and other 2 params
 * @status : XXX
 */
@WebServlet(name = "GroupAdd", urlPatterns = "/group/add")
public class Add extends HttpServlet {
    private static final Logger Log = Logger.getLogger("Group ADD");
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
     * group_name : name label of group
     * member_list : a list of the group's member, and the list hasn't self account
     * such as : login_account:111; host : ?nickname=group_name&group_member_list=["222", "333"];
     * @return : Config.STATUS_ACCOUNT_NOT_LOGIN
     *           Config.STATUS_ILLEGAL_PARAMETER
     *           Config.STATUS_DB_ILLEGAL_PARAMETER
     *           Config.STATUS_ADD_SUCCESSFUL
     * @unit_test : test -> group
     * @status : XXX
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String account = (String)request.getSession().getAttribute(Config.STR_ACCOUNT);
        String groupName = request.getParameter(Config.STR_GROUP_NAME);
        String groupMemberList = request.getParameter(Config.STR_GROUP_MEMBER_LIST);
        Log.info(String.format("account : %s, groupName : %s, groupMemberList : %s", account, groupName, groupMemberList));
        JSONObject resJson = new JSONObject();

        assert null == account;
        if (null == groupName || null == groupMemberList) {
            resJson.put(Config.STR_STATUS, Config.STATUS_ILLEGAL_PARAMETER);
        }else {
            Connection conn = null;
            PreparedStatement preStmt = null;
            try {
                // DONE create a new group and get generated key of database
                String sql = String.format("insert into `%s` (`%s`) values(?)", Config.TABLE_GROUP, Config.STR_GROUP_NAME);
                List<Object> params = new ArrayList<>();
                params.add(groupName);
                conn = DBHelper.getConnection();
                preStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                DBHelper.setParams(preStmt, params);
                preStmt.executeUpdate();
                ResultSet rs = preStmt.getGeneratedKeys();
                rs.next();
                // DONE generate a sql and log
                JSONArray jsonArray = JSONArray.fromObject(groupMemberList);
                StringBuilder sqlOperation = new StringBuilder(
                        String.format("insert into `%s` (`%s`, `%s`) values (%s, ?)",
                                Config.TABLE_GROUP_MEMBER, Config.STR_GROUP, Config.STR_ACCOUNT,
                                rs.getString(1))
                );
                String sql_row = String.format(", (%s, ?)", rs.getString(1));
                params.clear();
                params.add(account);
                for (Object o : jsonArray) {
                    sqlOperation.append(sql_row);
                    params.add(o);
                }
                Log.info(sql + "|" + rs.getString(1) + "|" + sqlOperation.toString()+"|"+params.size());
                // DONE insert all member;
                preStmt = conn.prepareStatement(sqlOperation.toString());
                DBHelper.setParams(preStmt, params);
                preStmt.executeUpdate();
                resJson.put(Config.STR_STATUS, Config.STATUS_ADD_SUCCESSFUL);
            } catch (JSONException e) {
                e.printStackTrace();
                resJson.put(Config.STR_STATUS, Config.STATUS_ILLEGAL_PARAMETER);
            }catch(SQLException e) {
                e.printStackTrace();
                resJson.put(Config.STR_STATUS, Config.STATUS_DB_ILLEGAL_PARAMETER);
            } finally {
                DBHelper.closeResource(preStmt, conn);
            }
        }
        Log.info(resJson.toString());
        response.setContentType("application/json");
        response.getWriter().append(resJson.toString()).flush();
    }
}
