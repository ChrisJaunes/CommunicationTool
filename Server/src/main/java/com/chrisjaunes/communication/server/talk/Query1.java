package com.chrisjaunes.communication.server.talk;

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
 * Servlet implementation class QueryMessages
 * @author ChrisJaunes
 */
@WebServlet(name = "TalkQuery1", urlPatterns = "/talk/query1")
public class Query1 extends HttpServlet {
	private static final Logger Log = Logger.getLogger("Talk Query");
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Log.log(Level.WARNING, String.format("����ͨ��GET���ʲ�ѯ�������, ��IPΪ%s", request.getRemoteAddr()));
		response.setContentType("text/html;charset=UTF-8;");
		response.getWriter().append("[ERROR] ��Ӧ��ʹ��GET����");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String account = (String)request.getSession().getAttribute(Config.STR_ACCOUNT);
		String account2 = request.getParameter(Config.STR_ACCOUNT2);
		String requestTime = request.getParameter(Config.STR_TIME);
		Log.info(String.format("account : %s, accout2 : %s, time : %s", account, account2, requestTime));

		JSONObject resJson = new JSONObject();
		if (null == account) {
			resJson.put(Config.STR_STATUS, Config.STATUS_ACCOUNT_NOT_LOGIN);
		} else if (null == account2 || null == requestTime) {
			resJson.put(Config.STR_STATUS, Config.STATUS_ILLEGAL_PARAMETER);
		}else {
			requestTime = TimeHelper.timeToStdTime(requestTime);
			Log.info(requestTime);
			try {
				String sqlQuery = String.format("select %s, %s, %s, %s, %s from %s where %s >= ? and ((%s = ? and %s = ?) or (%s = ? and %s = ?))",
						Config.STR_ACCOUNT1, Config.STR_ACCOUNT2, Config.STR_SEND_TIME, Config.STR_CONTENT_TYPE,Config.STR_CONTENT,
						Config.TABLE_TALK_MESSAGES,
						Config.STR_SEND_TIME, Config.STR_ACCOUNT1, Config.STR_ACCOUNT2, Config.STR_ACCOUNT1, Config.STR_ACCOUNT2);
				Log.info(sqlQuery);
				List<Object> params = new ArrayList<>();
				params.add(requestTime);
				params.add(account);
				params.add(account2);
				params.add(account2);
				params.add(account);
				ResultSet result = DBHelper.executeQuery(sqlQuery, params);
				JSONArray jsonA = new JSONArray();
				DBHelper.getResToJsonArray(result, jsonA);
				resJson.put(Config.STR_STATUS, Config.STATUS_SUCCESSFUL);
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
