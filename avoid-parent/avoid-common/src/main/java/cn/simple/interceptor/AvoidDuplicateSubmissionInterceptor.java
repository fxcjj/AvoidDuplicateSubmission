package cn.simple.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.simple.annotation.AvoidDuplicateSubmission;
import cn.simple.token.TokenProcesser;

public class AvoidDuplicateSubmissionInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private TokenProcesser sessionTokenProcesser;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			AvoidDuplicateSubmission annotation = handlerMethod.getMethodAnnotation(AvoidDuplicateSubmission.class);
			if (annotation != null) {
				boolean saveToken = annotation.saveToken();
				if (saveToken) {
					// 包含表单的页面
					// 需要保存token
					sessionTokenProcesser.saveToken(request);
					System.out.println("save token");
				}

				boolean removeToken = annotation.removeToken();
				if (removeToken) {
					// 保存表单操作结果
					// 需要移除当前表单的token
					boolean validToken = sessionTokenProcesser.validToken(request);
					System.out.println("remove token");
					if (validToken == false) {
						return false;
					}
				}
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}
	}

}
