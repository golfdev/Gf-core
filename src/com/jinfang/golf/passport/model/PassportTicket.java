package com.jinfang.golf.passport.model;

import com.jinfang.golf.utils.UnitOfTime;

public class PassportTicket implements Signable {

	public static final int DEFAULT_TICKET_LIFETIME = UnitOfTime.MILLS_1WEEK;
	
	private int userId;
	private long createTime;
	private long lifeTime;

	private String signatureText;
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public long getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(long lifeTime) {
		this.lifeTime = lifeTime;
	}
	
	@Override
	public String getContentText() {
		return "" + userId + createTime + lifeTime;
	}

	@Override
	public String getSignatureText() {
		return signatureText;
	}

	@Override
	public void setSignatureText(String signatureText) {
		this.signatureText = signatureText;
	}
	
}
