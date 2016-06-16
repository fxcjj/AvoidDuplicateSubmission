package cn.simple.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务端用Session存储
 * 
 * @author ldm
 * @Date 2016年6月16日
 */
@Service("sessionTokenProcesser")
public class SessionTokenProcesser extends TokenProcesser {

	@Autowired
	HttpServletResponse response;

	@Override
	public boolean validToken(HttpServletRequest request) {

		String clientToken = getClientToken(request);
		if (StringUtils.isEmpty(clientToken)) {
			return false;
		}
		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}
		String tokenKey = getTokenKey(request);
		Object tokenObj = session.getAttribute(tokenKey);
		if(tokenObj==null)
		{
			rethrow("服务端不存在当前token，请重新请求表单");
		}
		String serverToken = tokenObj.toString();

		session.removeAttribute(tokenKey);
		System.out.println("remove server token:" + serverToken);
		return clientToken.equals(serverToken);

	}

	@Override
	public String getTokenKey(HttpServletRequest request) {
		return getTokenField();
	}

	@Override
	public void saveToken(HttpServletRequest request) {

		HttpSession session = request.getSession();
		String tokenKey = getTokenKey(request);
		Object tokenObj = session.getAttribute(tokenKey);
		String token;
		if (tokenObj == null) {
			token = MakeToken.getInstance().getToken();
			// 服务端保存token
			session.setAttribute(tokenKey, token);
		} else {
			token = tokenObj.toString();
		}
		System.out.println("current token:" + token);
		// 写入cookie
		Cookie cookie = new Cookie(getTokenField(), token);
		response.addCookie(cookie);
	}

	private void rethrow(String message) {
		RuntimeException e = new RuntimeException(message);
		throw e;
	}

	@Override
	public String getClientToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			rethrow("没有读取到客户端的cookie");
			return null;
		}
		
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(getTokenKey(request))) {
				String cookieValue = cookie.getValue();
				return cookieValue;
			}
		}
		rethrow("客户端cookie中没有存储token");
		return null;
	}

}
