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
import com.chrisjaunes.communication.server.utils_db.DBHelper;

import net.sf.json.JSONObject;

/**
 * @author ChrisJaunes
 * Servlet implementation class RegisterServlet
 */
@WebServlet(name = "Register" , urlPatterns = {"/account/register"})
public class Register extends HttpServlet {
	private static final Logger Log = Logger.getLogger("Account Register");

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		Log.log(Level.WARNING, String.format("发现通过GET访问z注册界面的人, 其IP为%s", request.getRemoteAddr()));
		response.setContentType("text/html;charset=UTF-8;");
		response.getWriter().append("[ERROR] 不应该使用GET访问");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String account = request.getParameter(Config.STR_ACCOUNT);
		String password = request.getParameter(Config.STR_PASSWORD);
		String nickname  = request.getParameter(Config.STR_NICKNAME);
		String avatar = request.getParameter(Config.STR_AVATAR);
		String textStyle = request.getParameter(Config.STR_TEXT_STYLE);
		assert null != account && null != password;
		Log.info(String.format("account : %s, password %s, nickname %s, textStyle %s", account, password, nickname, textStyle));

		JSONObject resJson = new JSONObject();
		try {
			String sqlQuery = String.format("select * from %s where account = ?", Config.TABLE_ACCOUNT);
			List<Object> params = new ArrayList<>();
			params.add(account);
			ResultSet result = DBHelper.executeQuery(sqlQuery, params);
			if (result.next()) {
				resJson.put(Config.STR_STATUS, Config.STATUS_REGISTER_ACCOUNT_EXIST);
			} else {
				sqlQuery = String.format("insert into %s (%s, %s, %s, %s, %s) values (?,?,?,?,?)",
						Config.TABLE_ACCOUNT,
						Config.STR_ACCOUNT,
						Config.STR_PASSWORD,
						Config.STR_NICKNAME,
						Config.STR_AVATAR,
						Config.STR_TEXT_STYLE);
				params.add(password);
				params.add(nickname);
				params.add(avatar);
				params.add(textStyle);
				DBHelper.executeOperate(sqlQuery, params);
				resJson.put(Config.STR_STATUS, Config.STATUS_REGISTER_SUCCESSFUL);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Log.info(resJson.toString());
		response.setContentType("application/json");
		response.getWriter().append(resJson.toString()).flush();
	}
}
