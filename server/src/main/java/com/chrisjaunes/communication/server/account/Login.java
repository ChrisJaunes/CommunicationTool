package com.chrisjaunes.communication.server.account;

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

import com.chrisjaunes.communication.server.Config;
import com.chrisjaunes.communication.server.utils.DBHelper;
import net.sf.json.JSONObject;

/**
 * @author ChrisJaunes
 * Servlet implementation class Login
 */
@WebServlet(name = "Login" , urlPatterns = {"/account/login"})
public class Login extends HttpServlet {
	private static final Logger Log = Logger.getLogger("Account Login");
	public static final String STR_ACCOUNT = "account";
	public static final String STR_PASSWORD = "password";
	static final public String STATUS_LOGIN_SUCCESSFUL = "login successful";
	static final public String STATUS_LOGIN_ACCOUNT_ERROR = "account is not exist";
	static final public String STATUS_LOGIN_PASSWORD_ERROR = "password is not correct";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		Log.log(Level.WARNING, String.format("����ͨ��GET���ʵ�¼�������, ��IPΪ%s", request.getRemoteAddr()));
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
		String account = request.getParameter(STR_ACCOUNT);
		String password = request.getParameter(STR_PASSWORD);
		assert null != account && null != password;
		Log.info(String.format("account : %s, password %s", account, password));

		JSONObject resJson = new JSONObject();
		try {
			String sqlQuery = String.format("select * from %s where account = ?", Config.TABLE_ACCOUNT);
			List<Object> params = new ArrayList<>();
			params.add(account);
			ResultSet result = DBHelper.executeQuery(sqlQuery, params);

			if (result.next()) {
				String correctPassword = result.getString(Config.STR_PASSWORD);
				if( password.equals(correctPassword) ) {
					resJson.put(Config.STR_STATUS, STATUS_LOGIN_SUCCESSFUL);
					JSONObject jsonO = new JSONObject();
					jsonO.put(Config.STR_ACCOUNT, result.getString(Config.STR_ACCOUNT));
					jsonO.put(Config.STR_NICKNAME, result.getString(Config.STR_NICKNAME));
					jsonO.put(Config.STR_AVATAR, result.getString(Config.STR_AVATAR));
					jsonO.put(Config.STR_TEXT_STYLE, result.getString(Config.STR_TEXT_STYLE));
					resJson.put(Config.STR_STATUS_DATA, jsonO);
					request.getSession().setAttribute(Config.STR_ACCOUNT, result.getString(Config.STR_ACCOUNT));
				} else {
					resJson.put(Config.STR_STATUS, STATUS_LOGIN_PASSWORD_ERROR);
				}
			} else {
				resJson.put(Config.STR_STATUS, STATUS_LOGIN_ACCOUNT_ERROR);
			}
			DBHelper.closeResource(result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//Log.info(resJson.toString());
		response.setContentType("application/json");
		response.getWriter().append(resJson.toString()).flush();
	}
}
