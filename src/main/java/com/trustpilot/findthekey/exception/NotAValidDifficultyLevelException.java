package com.trustpilot.findthekey.exception;

import org.omg.CORBA.UserException;

@SuppressWarnings("serial")
public class NotAValidDifficultyLevelException extends UserException {
	
	public NotAValidDifficultyLevelException(String errorMsg) {
		super(errorMsg);
	}

}
