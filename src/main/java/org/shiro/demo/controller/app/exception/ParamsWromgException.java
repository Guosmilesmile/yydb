package org.shiro.demo.controller.app.exception;

/**
 * 接口数据不全
 * @author Christy
 *
 */
public class ParamsWromgException extends RuntimeException{

	public ParamsWromgException() {
		super();
	}

	public ParamsWromgException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ParamsWromgException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParamsWromgException(String message) {
		super(message);
	}

	public ParamsWromgException(Throwable cause) {
		super(cause);
	}

}
