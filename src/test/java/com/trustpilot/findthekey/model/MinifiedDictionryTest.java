package com.trustpilot.findthekey.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.trustpilot.findthekey.util.AppConstants;

/**
 * class to test minified dictionary creation and operations
 * @author vinitapenmatsa
 *
 */
public class MinifiedDictionryTest {
	
    static MinifiedDictionary dictionary;
	
	@BeforeClass
	public static void init() throws IOException {
		AppConstants.setWordFileLocation("/wordlistTest");
      	dictionary = new MinifiedDictionary("ones" ,3);
	}
	
	@Test
	public void processWordListTest() throws IOException {
		AppConstants.setWordFileLocation("/wordlistTest");
		MinifiedDictionary testDictionary = new MinifiedDictionary("one two" ,3);
		
		assertEquals(2,testDictionary.getNumberOfWords());
	}
	
	
	@Test
	public void isPossibleWordTest() {
		
		//test for possible word
		assertTrue(dictionary.isPossibleWord("son"));	
		
		//test for words with length < minWordLength
		assertFalse(dictionary.isPossibleWord("so"));	
		
		//test for long words
		assertFalse(dictionary.isPossibleWord("sones"));	

		//test for in equal number of character counts characters
	    assertFalse(dictionary.isPossibleWord("sons"));	
	}
	
	
	@Test
	public void countCharactersTest() {
		
		int charOccurances[] = dictionary.getCharOccurances();
		
	    //testing for positive cases
		assertEquals(1 , charOccurances['o']);
		assertEquals(1 , charOccurances['s']);
		
		//testing for negative cases
		assertEquals(0 , charOccurances['z']);
		assertEquals(0 , charOccurances['q']);
		assertEquals(0 , charOccurances['p']);
	}
	
	@Test
	public void getNumberOfWordsTest() throws IOException {
		AppConstants.setWordFileLocation("/wordlistTest");
		MinifiedDictionary testDictionary = new MinifiedDictionary("one two" ,3);
		assertTrue(testDictionary.getNumberOfWords()==2);
	}
	
	@Test
	public void getPhraseTest() {
		assertTrue("ones".equals(dictionary.getPhrase()));
	}
	
	@Test
	public void getRootTest() {
		
		//root with possible children
		assertTrue(dictionary.getRoot().getChildren().containsKey('o'));
		
		//root with impossible children
	    assertFalse(dictionary.getRoot().getChildren().containsKey('z')); 
	}
	

}
