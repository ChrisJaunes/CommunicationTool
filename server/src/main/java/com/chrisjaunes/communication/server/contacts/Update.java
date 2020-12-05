package com.chrisjaunes.communication.server.contacts;

import com.chrisjaunes.communication.server.Config;
import com.chrisjaunes.communication.server.utils.DBHelper;
import com.chrisjaunes.communication.server.utils.TimeHelper;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ChrisJaunes
 */
@WebServlet(name = "ContactsUpdate" , urlPatterns = {"/contacts/update"})
public class Update extends HttpServlet {
    static final Logger Log = Logger.getLogger("Contacts Update");



    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Log.log(Level.WARNING, String.format("����ͨ��GET���ʸ��½������, ��IPΪ%s", request.getRemoteAddr()));
        response.setContentType("text/html;charset=UTF-8;");
        response.getWriter().append("[ERROR] ��Ӧ��ʹ��GET����");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String account = (String)request.getSession().getAttribute(Config.STR_ACCOUNT);
        String account2 = request.getParameter(ContactsConfig.STR_CONTACTS_ACCOUNT);
        String requestTime = request.getParameter(ContactsConfig.STR_TIME);
        String requestOperation = request.getParameter(ContactsConfig.STR_OPERATION);
        Log.info(String.format("account : %s, account2 : %s, time : %s, operation : %s", account, account2, requestTime, requestOperation));

        JSONObject resJson = new JSONObject();
        if (null == account) {
            resJson.put(Config.STR_STATUS, Config.STATUS_ACCOUNT_NOT_LOGIN);
        } else if (null == account2 || null == requestTime || null == requestOperation) {
            resJson.put(Config.STR_STATUS, Config.STATUS_ILLEGAL_PARAMETER);
        } else {
            requestTime = TimeHelper.timeToStdTime(requestTime);
            String sqlOpera = null;
            List<Object> params = new ArrayList<>();
            switch (requestOperation) {
                case ContactsConfig.CONTACTS_FRIEND_REQUEST : {
                    sqlOpera = String.format("insert into `%s` (`%s`, `%s`, `%s`, `%s`) value(?, ?, ?, ?)",
                            ContactsConfig.TABLE_CONTACTS, ContactsConfig.STR_ACCOUNT1, ContactsConfig.STR_ACCOUNT2, ContactsConfig.STR_TIME, ContactsConfig.STR_OPERATION);
                    params.add(account);params.add(account2);
                    params.add(requestTime);
                    params.add(ContactsConfig.CONTACTS_FRIENDS_REQUEST_CODE);
                    break;
                }
                case ContactsConfig.CONTACTS_FRIENDS_AGREE: {
                    // TODO request ( A -> B), now account is B, account1 = A(account2) and account2 = B(account)
                    sqlOpera = String.format("update `%s` set `%s` = ?, `%s` = ? where `%s` = ? and `%s` = ?",
                            ContactsConfig.TABLE_CONTACTS,
                            ContactsConfig.STR_TIME, ContactsConfig.STR_OPERATION,
                            ContactsConfig.STR_ACCOUNT1, ContactsConfig.STR_ACCOUNT2);
                    params.add(requestTime);
                    params.add(ContactsConfig.CONTACTS_FRIENDS_AGREE_CODE);
                    params.add(account2);params.add(account);
                    break;
                }
                case ContactsConfig.CONTACTS_FRIENDS_REJECT: {
                    sqlOpera = String.format("delete from %s where (%s = ? and %s = ?) ",
                            ContactsConfig.TABLE_CONTACTS,
                            ContactsConfig.STR_ACCOUNT1, ContactsConfig.STR_ACCOUNT2);
                    params.add(account2);params.add(account);
                    break;
                }
                default: break;
            }
            Log.info("" + sqlOpera);
            if (null == sqlOpera) {
                resJson.put(Config.STR_STATUS, Config.STATUS_ILLEGAL_PARAMETER);
            } else {
                int count;
                try {
                    count = DBHelper.executeOperate(sqlOpera, params);
                    Log.info("" + sqlOpera+"|"+count+"|"+ContactsConfig.CONTACTS_FRIENDS_AGREE_CODE);
                    if (count == 1) {
                        resJson.put(Config.STR_STATUS, ContactsConfig.STATUS_UPDATE_SUCCESSFUL);
                    } else{
                        resJson.put(Config.STR_STATUS, Config.STATUS_ILLEGAL_PARAMETER);
                    }
                }catch (SQLException e) {
                    e.printStackTrace();
                    resJson.put(Config.STR_STATUS, Config.STATUS_DB_ILLEGAL_PARAMETER);
                }
            }
        }
        Log.info(resJson.toString());
        response.setContentType("application/json");
        response.getWriter().append(resJson.toString()).flush();
    }
}