package com.trustpilot.findthekey.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class contains string manipulation utilities for findTheKey
 * @author vinitapenmatsa
 *
 */
public class StringUtils {

	/**
	 * returns an MD5 has for a given string
	 * @param s
	 * @return 
	 */
	public static String getHash(String s) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.reset();
			md5.update(s.getBytes(), 0, s.length());
			return new BigInteger(1,md5.digest()).toString(16);		
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	/**
	 * Removes spaces from a given string.
	 * @param s
	 * @return
	 */
	public static String removeSpaces(String s) {
		return s.replaceAll("\\s+","");
	}

}
