package com.common.system.exception;

import java.util.HashMap;


/**
 * service层异常
 * @author 李安达
 * @createtime 2019-01-15
 * @updatetime 2019-01-15
 */
@SuppressWarnings("serial")
public class ServiceException extends BaseException {

	public ServiceException() {
		super("服务异常");

	}

	public ServiceException(String msg) {
		super(msg);

	}

	public ServiceException(String msg, String detail) {
		super(msg, detail);

	}

	public ServiceException(String msg, HashMap<String, Object> info) {
		super(msg, info);

	}
}
