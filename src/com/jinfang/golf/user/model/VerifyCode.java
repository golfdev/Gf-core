package com.jinfang.golf.user.model;

import java.io.Serializable;
import java.util.Date;

/**
 * VerifyCode对象 实现序列化
 * @author zhangkui
 *
 */
public class VerifyCode implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String number;
	private String code;
	private Date expriedTime;
	
	
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    /**
	 * number get方法
	 * @return 返回number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * number set方法
	 * @param number number电话号码
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * code get方法
	 * @return 返回code码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * code set方法
	 * @param code code码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * expriedTime get方法
	 * @return 返回 expriedTime 过期时间
	 */
	public Date getExpriedTime() {
		return expriedTime;
	}
	/**
	 * expriedTime set方法
	 * @param expriedTime expriedTime过期时间
	 */
	public void setExpriedTime(Date expriedTime) {
		this.expriedTime = expriedTime;
	}
	
	
	
	
}
