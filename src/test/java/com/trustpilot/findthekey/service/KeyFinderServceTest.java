package com.trustpilot.findthekey.service;

import org.junit.Before;
import org.junit.Test;

import com.trustpilot.findthekey.model.MinifiedDictionary;
import com.trustpilot.findthekey.util.AppConstants;
import com.trustpilot.findthekey.util.StringUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;


public class KeyFinderServceTest {


	KeyFinderService keyFinderService;
	MinifiedDictionary dictionary;

	@Before
	public void setUp() throws IOException {

		AppConstants.setWordFileLocation("/wordlistTest");

		String phrase = "pen tens";
		String md5Hash = StringUtils.getHash(phrase);

		dictionary  = new MinifiedDictionary("ten pens", 3);

		keyFinderService = new KeyFinderService(dictionary, md5Hash);

	}

	@Test
	public void findKeysTest() {

		//Testing find keys method with 1 parameter
		//key not found test
		assertTrue(keyFinderService.findKeys(4).size()==0);

		//key found test
		List<String> key1 = keyFinderService.findKeys(2);
		assertTrue(key1.size()==1);
		assertEquals("pen tens" , key1.get(0));

	}

	@Test
	public void findKeysWithMoreParamsTest() {
		//Testing find keys method with all parameters
		//key not found test
		assertTrue(keyFinderService.findKeys(3, 3, "").size()==0);

		//key found test
		List<String> key2 = keyFinderService.findKeys(2, 3, "");
		assertTrue(key2.size()==1);
		assertEquals("pen tens" , key2.get(0));		
	}

}
