package com.common.system.exception;

import java.util.HashMap;

/**
 * controller层异常
 * @author 李安达
 * @createtime 2019-01-15
 * @updatetime 2019-01-15
 */
@SuppressWarnings("serial")
public class ControllerException extends BaseException {

	public ControllerException() {
		super("操作异常");

	}

	public ControllerException(String msg) {
		super(msg);

	}

	public ControllerException(String msg, String detail) {
		super(msg, detail);

	}

	public ControllerException(String msg, HashMap<String, Object> info) {
		super(msg, info);

	}
}
