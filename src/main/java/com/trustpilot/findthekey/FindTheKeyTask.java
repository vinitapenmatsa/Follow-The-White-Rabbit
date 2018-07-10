package com.trustpilot.findthekey;

import java.util.List;
import java.util.concurrent.Callable;

import com.trustpilot.findthekey.service.KeyFinderService;

/**
 * Callable task to find Keys
 * @author vinitapenmatsa
 */

public class FindTheKeyTask implements Callable<List<String>>{
	
	private KeyFinderService keyFinder;
	private String startsWith;
	private int maxNumberOfWords;
	private int minWordLength;
	
	public FindTheKeyTask(KeyFinderService keyFinder, int maxNumberOfWords ,int minWordLength, String startsWith) {
		this.keyFinder = keyFinder;
		this.minWordLength = minWordLength;
		this.maxNumberOfWords = maxNumberOfWords;
		this.startsWith = startsWith;
	}
	
	@Override
	public List<String> call() throws Exception {
		return keyFinder.findKeys(maxNumberOfWords,minWordLength, startsWith);
	}

}
