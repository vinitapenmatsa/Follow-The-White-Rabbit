package com.trustpilot.findthekey.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;



/**
 * Class tests all the string utils functions
 * @author vinitapenmatsa
 */
public class StringUtilsTest {
	

	@Test
	public void getHashTest() {
		
		//test md5 length
		assertEquals(32, StringUtils.getHash("hello world").length());
		
		//test empty string
		assertEquals(32, StringUtils.getHash("").length());
		
	}
	

	@Test
	public void removeSpacesTest(){
		
		//test with empty string
		assertEquals("",StringUtils.removeSpaces(""));
		
		//test string with no spaces
		assertEquals("hello",StringUtils.removeSpaces("hello"));
		
		//test string with spaces
		assertEquals("hellohowareyou",StringUtils.removeSpaces("hello how are you"));
		
		//test string with continuous spaces
		assertEquals("hellohowareyou",StringUtils.removeSpaces("hello     how are you"));
		
		//test string with leading and trailing spaces
		assertEquals("hellohowareyou",StringUtils.removeSpaces("   hello how are you    "));
		
	}

}
