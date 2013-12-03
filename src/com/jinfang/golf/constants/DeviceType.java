package com.jinfang.golf.constants;

public enum DeviceType {
	
	ANDROID("android"),
	IOS("ios");
	
	private String type;
	
	private DeviceType(String type){
		this.type=type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
