package org.shiro.demo.controller.app.exception;

/**
 * 加密错误
 * @author Christy
 *
 */
public class EncryptWrongExcetion extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4592104116878389251L;

	public EncryptWrongExcetion() {
		super();
	}

	public EncryptWrongExcetion(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EncryptWrongExcetion(String message, Throwable cause) {
		super(message, cause);
	}

	public EncryptWrongExcetion(String message) {
		super(message);
	}

	public EncryptWrongExcetion(Throwable cause) {
		super(cause);
	}

}
