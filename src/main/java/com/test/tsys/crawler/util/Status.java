package com.test.tsys.crawler.util;

public enum Status {

	SUBMITTED("S","Submitted"),
	INPROGRESS("V","In Progress"),
	PROCESSED("P","Processed"),
	FAILED("F","Failed");
	
	Status(String value, String description){
		this.enumValue=value;
		this.enumDescr=description;
	}
	
	private String enumValue;
	private String enumDescr;
	
	public String getEnumValue() {
		return enumValue;
	}
	public void setEnumValue(String enumValue) {
		this.enumValue = enumValue;
	}
	public String getEnumDescr() {
		return enumDescr;
	}
	public void setEnumDescr(String enumDescr) {
		this.enumDescr = enumDescr;
	}
}
