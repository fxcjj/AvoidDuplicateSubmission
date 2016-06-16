package cn.simple.token;

import javax.servlet.http.HttpServletRequest;

public abstract class TokenProcesser {
	/**
	 * token的名字字段 用于页面获取的键值
	 */
	private static final String TOKEN_NAME_FIELD = "submission_token";

	/**
	 * 获取页面token键
	 * 
	 * @return
	 */
	public String getTokenField() {
		return TOKEN_NAME_FIELD;
	}

	/**
	 * 获取客户端发送过来的token
	 * 
	 * @return
	 */
	public abstract String getClientToken(HttpServletRequest request);

	/**
	 * 判断当前请求是否合法
	 * 
	 * @return
	 */
	public abstract boolean validToken(HttpServletRequest request);

	/**
	 * 获取验证端token的key
	 * 
	 * @param request
	 * @return
	 */
	public abstract String getTokenKey(HttpServletRequest request);

	/**
	 * 保存token
	 * 
	 * @param request
	 */
	public abstract void saveToken(HttpServletRequest request);
}
