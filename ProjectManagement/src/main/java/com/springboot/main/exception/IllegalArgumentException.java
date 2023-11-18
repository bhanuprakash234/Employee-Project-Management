package com.springboot.main.exception;

public class IllegalArgumentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String messgae;

	public IllegalArgumentException(String messgae) {
		super();
		this.messgae = messgae;
	}

	public String getMessgae() {
		return messgae;
	}

	public void setMessgae(String messgae) {
		this.messgae = messgae;
	}

	
	

}
