package com.chrisjaunes.communication.server.account;

import com.chrisjaunes.communication.server.Config;
import com.chrisjaunes.communication.server.utils_db.DBHelper;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateHeadPhoto
 * @author ChrisJaunes
 */
@WebServlet(name = "UpdateMessage" , urlPatterns = {"/account/updateMessage"})
public class UpdateMessage extends HttpServlet {
	private static final Logger Log = Logger.getLogger("Account Update");

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Log.log(Level.WARNING, String.format("发现通过GET访问登录界面的人, 其IP为%s", request.getRemoteAddr()));
		response.setContentType("text/html;charset=UTF-8;");
		response.getWriter().append("[ERROR] 不应该使用GET访问");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String account = (String)request.getSession().getAttribute(Config.STR_ACCOUNT);
		Log.info(String.format("account : %s", account));

		JSONObject resJson = new JSONObject();
		if (null != account) {
			try {
				String sqlQuery = String.format("select * from %s where account = ?", Config.TABLE_ACCOUNT);
				List<Object> params = new ArrayList<>();
				params.add(account);
				ResultSet result = DBHelper.executeQuery(sqlQuery, params);
				if (result.next()) {
					String nickname = request.getParameter(Config.STR_NICKNAME);
					if (null != nickname) {
						String sqlOperate = String.format("update %s set %s = ? where %s = ?", Config.TABLE_ACCOUNT, Config.STR_NICKNAME, Config.STR_ACCOUNT);
						params.clear();
						params.add(nickname);
						params.add(account);
						DBHelper.executeOperate(sqlOperate, params);
					}

					String avatar = request.getParameter(Config.STR_AVATAR);
					if(null != avatar) {
						String sqlOperate = String.format("update %s set %s = ? where %s = ?", Config.TABLE_ACCOUNT, Config.STR_AVATAR,  Config.STR_ACCOUNT);
						params.clear();
						params.add(avatar);
						params.add(account);
						DBHelper.executeOperate(sqlOperate, params);
					}

					String textStyle = request.getParameter(Config.STR_TEXT_STYLE);
					if (null != textStyle) {
						String sqlOperate = String.format("update %s set %s = ? where %s = ?", Config.TABLE_ACCOUNT, Config.STR_TEXT_STYLE, Config.STR_ACCOUNT);
						params.clear();
						params.add(textStyle);
						params.add(account);
						DBHelper.executeOperate(sqlOperate, params);
					}
					Log.info(String.format("nickname : %s , avatar : %s, textStyle : %s", nickname, avatar, textStyle));
					resJson.put(Config.STR_STATUS, Config.STATUS_UPDATE_SUCCESSFUL);
				} else{
					resJson.put(Config.STR_STATUS, Config.STATUS_UPDATE_FAIL);
				}
				DBHelper.closeResource(result);
			} catch (SQLException throwables) {
				throwables.printStackTrace();
			}
		} else{
			resJson.put(Config.STR_STATUS, Config.STATUS_ACCOUNT_NOT_LOGIN);
		}
		response.setContentType("application/json");
		response.getWriter().append(resJson.toString()).flush();
	}
}
