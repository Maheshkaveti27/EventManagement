package com.event.management.model;

public class StatusResponse {

	private String message;

	public StatusResponse(String msg) {
		message = msg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
