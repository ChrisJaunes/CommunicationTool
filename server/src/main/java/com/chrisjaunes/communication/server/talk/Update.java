package com.chrisjaunes.communication.server.talk;

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
 * Servlet implementation class AddMessages
 * @author ChrisJaunes
 */
@WebServlet(name = "TalkUpdate", urlPatterns = {"/talk/update"})
public class Update extends HttpServlet {
	private static final Logger Log = Logger.getLogger("Talk Update");
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
		String account = (String)request.getSession().getAttribute(Config.STR_ACCOUNT);
		String account2 = request.getParameter(Config.STR_ACCOUNT2);
		String sendTime = request.getParameter(Config.STR_SEND_TIME);
		String sendContentType = request.getParameter(Config.STR_CONTENT_TYPE);
		String sendContent = request.getParameter(Config.STR_CONTENT);
		Log.info(String.format("account : %s, account2 : %s, time : %s, content_type : %s, content : %s", account, account2, sendTime, sendContentType, sendContent));

		JSONObject resJson = new JSONObject();
		if (null == account) {
			resJson.put(Config.STR_STATUS, Config.STATUS_ACCOUNT_NOT_LOGIN);
		} else if (null == account2 || null == sendTime || null == sendContentType || null == sendContent) {
			resJson.put(Config.STR_STATUS, Config.STATUS_ILLEGAL_PARAMETER);
		}else {
			try {
				sendTime = TimeHelper.timeToStdTime(sendTime);
				Log.info(sendTime);
				String sqlQuery = String.format("insert into %s (%s, %s, %s, %s, %s) values (?, ?, ?, ?, ?)",
						Config.TABLE_TALK_MESSAGES,
						Config.STR_ACCOUNT1, Config.STR_ACCOUNT2, Config.STR_SEND_TIME, Config.STR_CONTENT_TYPE,Config.STR_CONTENT);
				Log.info(sqlQuery);
				List<Object> params = new ArrayList<>();
				params.add(account);
				params.add(account2);
				params.add(sendTime);
				params.add(Integer.valueOf(sendContentType));
				params.add(sendContent);
				int cnt = DBHelper.executeOperate(sqlQuery, params);
				if(cnt <= 0) {
					resJson.put(Config.STR_STATUS, Config.STATUS_ILLEGAL_PARAMETER);
				} else {
					resJson.put(Config.STR_STATUS, Config.STATUS_SUCCESSFUL);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				resJson.put(Config.STR_STATUS, Config.STATUS_DB_ILLEGAL_PARAMETER);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				resJson.put(Config.STR_STATUS, Config.STATUS_ILLEGAL_PARAMETER);
			}
		}
		Log.info(resJson.toString());
		response.setContentType("application/json");
		response.getWriter().append(resJson.toString()).flush();
	}

}
