package cn.simple.token;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 双客户端验证
 * @author ldm
 * @Date 2016年6月16日
 */
@Service("clientTokenProcesser")
public class ClientTokenProcesser extends TokenProcesser {

	@Autowired
	HttpServletResponse response;

	@Override
	public boolean validToken(HttpServletRequest request) {
		String formToken = request.getParameter(getTokenField()).toString();
		System.out.println("formToken:"+formToken);
		if(StringUtils.isEmpty(formToken))
		{
			printException("表单中没有token");
			return false;
		}
		Cookie[] cookies = request.getCookies();
		if(cookies==null)
		{
			printException("cookie 中没有token");
		}
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals(getTokenKey(request)))
			{
				String cookieValue = cookie.getValue();
				System.out.println("cookieToken:"+cookieValue);
				if(cookieValue.equals(formToken))
				{
					return true;
				}
			}
		}
		return false;
	}

	private void printException(String msg) {
		Exception e= new RuntimeException(msg);
		e.printStackTrace();
	}

	@Override
	public String getTokenKey(HttpServletRequest request) {
		String cookieKey = getTokenField() + "_cookie";
		return cookieKey;
	}

	@Override
	public void saveToken(HttpServletRequest request) {
		String token = MakeToken.getInstance().getToken();
		request.setAttribute(getTokenField(), token);
		if (response == null) {
			throw new RuntimeException("HttpServletResponse is null");
		}
		Cookie cookie = new Cookie(getTokenKey(request), token);
		response.addCookie(cookie);
	}

	@Override
	public String getClientToken(HttpServletRequest request) {
		Object token = request.getParameter(getTokenField());
		if (token == null) {
			return null;
		} else {
			return token.toString();
		}

	}

}
