package org.shiro.demo.controller.app;

/**
 * 数据返回接口
 * @author Christy
 *
 */
public class ReturnData {
	
	public final static Integer TIMEOUT = 60*10;//接口过期时间  

	public final static Integer SUCCESS = 100;//成功
	
	public final static Integer FAIL = 200;//失败
	
	private Integer code;
	
	private String message;
	
	private String data;

	public ReturnData() {
		super();
	}

	public ReturnData(Integer code, String message, String data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	
	
}
