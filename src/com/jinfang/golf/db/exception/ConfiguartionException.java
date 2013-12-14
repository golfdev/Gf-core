package com.jinfang.golf.db.exception;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * 配置错误或缺少时，抛出的异常
 * @author 
 *
 */
public class ConfiguartionException extends NestableRuntimeException{

	private static final long serialVersionUID = -5488452009928180142L;
	
	/**
	 * 创建一个配置异常的实例
	 * @param msg 配置错误信息的描述
	 */
	public ConfiguartionException(String msg) {
        super(msg);
    }
	
	/**
	 * 创建一个配置异常的实例
	 * @param msg 配置错误信息的描述
	 * @param cause 引起该异常的异常
	 */
	public ConfiguartionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
