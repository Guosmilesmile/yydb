package org.shiro.demo.controller.app.exception;

/**
 * 时间过期异常
 * @author Christy
 *
 */
public class TimeOutException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6454520084704589513L;

	public TimeOutException() {
		super();
	}

	public TimeOutException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TimeOutException(String message, Throwable cause) {
		super(message, cause);
	}

	public TimeOutException(String message) {
		super(message);
	}

	public TimeOutException(Throwable cause) {
		super(cause);
	}

	
}
