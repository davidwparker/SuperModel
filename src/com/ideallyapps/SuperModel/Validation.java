package com.ideallyapps.SuperModel;

/**
 * Validation is a simple holder class to be used with SuperModel.
 * The class holds whether there is an error or not and a given message string.
 * 
 * @see com.ideallyapps.SuperModel.SuperModel
 * 
 * @author davidwparker
 * @version 0.1
 */
public class Validation {

	public Validation() {
		// default values
		this.msgString = "";
		this.hasErrors = false;
	}

	private String msgString;
	private boolean hasErrors;

	public String getMsgString() {
		return msgString;
	}

	public void setMsgString(String msgString) {
		this.msgString = msgString;
	}

	public boolean isHasErrors() {
		return hasErrors;
	}

	public void setHasErrors(boolean hasErrors) {
		this.hasErrors = hasErrors;
	}
}
