package com.trustpilot.findthekey;

import org.junit.Before;
import org.junit.Test;

import com.trustpilot.findthekey.service.KeyFinderService;

import static org.mockito.Mockito.*;

public class FindTheKeyTaskTest {
	 
	private KeyFinderService keyFinderService;
	private String startsWith;
	private int maxNumberOfWords;
	private int minWordLength;
	
	
	@Before
	public void setUp() {
		
		keyFinderService = mock(KeyFinderService.class);
		startsWith = "a";
		maxNumberOfWords = 15;
		minWordLength = 1;
	}
	
	@Test
	public void callCheckTest() throws Exception {
		
		FindTheKeyTask findTheKeyTask = new FindTheKeyTask(keyFinderService, maxNumberOfWords, minWordLength, startsWith);
		findTheKeyTask.call();
		
		verify(keyFinderService,times(1)).findKeys(maxNumberOfWords, minWordLength, startsWith);
		
	}

}
