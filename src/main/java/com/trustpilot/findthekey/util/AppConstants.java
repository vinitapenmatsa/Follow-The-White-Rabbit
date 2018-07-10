package com.trustpilot.findthekey.util;

public class AppConstants {
	
	/** default wordlist file location */
	private static String WORD_FILE_LOCATION = "/wordlist";

	public static String getWordFileLocation() {
		return WORD_FILE_LOCATION;
	}

	public static void setWordFileLocation(String wordFileLocation) {
		WORD_FILE_LOCATION = wordFileLocation;
	}

}
