package cn.simple.token;

import java.util.Random;

/**
 * 生成token 全局单例
 * 
 * @author ldm
 * @version 20160526
 */
public class MakeToken {

	private Random random = new Random();
	private static MakeToken makeToken;

	/**
	 * 禁用外部生成
	 */
	private MakeToken() {
	}

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static MakeToken getInstance() {
		if (makeToken == null) {
			makeToken = new MakeToken();
		}
		return makeToken;
	}

	/**
	 * 获取token
	 * 
	 * @return
	 */
	public String getToken() {
		String raw = System.currentTimeMillis() + random.nextInt(9999) + "";
		return raw;
	}
}
