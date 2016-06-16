package cn.simple.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防止重复提交拦截器
 * 
 * @author ldm
 * @version 20160526
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AvoidDuplicateSubmission {
	/**
	 * 在包含有表单的请求地址需要生成并保存当前请求token 需要为相应的方法加上注解
	 * 
	 * @return
	 */
	boolean saveToken() default false;

	/**
	 * 在一次提交后，完成验证后，需要移除当前请求的token
	 * 
	 * @return
	 */
	boolean removeToken() default false;
}
