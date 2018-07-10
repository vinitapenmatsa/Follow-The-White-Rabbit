package com.trustpilot.findthekey.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.trustpilot.findthekey.util.AppConstants;
import com.trustpilot.findthekey.util.StringUtils;

/**
 * Dictionary minified to contain only words formed from a given phrase
 * @author vinitapenmatsa
 *
 */
public class MinifiedDictionary{
    
	
	private Trie trie;

	private String phrase;
	
	private static final int MAX_CHAR = 256;
	
	/** Array to maintain number of occurrences of each char in given phrase*/
	private int charOccurrences[] = new int[MAX_CHAR];
	
	/** number of possible words */
	private int numberOfWords = 0;
	
	private int minWordLength;

	/**
	 * @param phrase
	 * @throws IOException 
	 */
	public MinifiedDictionary(String phrase, int minWordLength) throws IOException {
	    
		this.phrase = StringUtils.removeSpaces(phrase);
		this.minWordLength = minWordLength;
		
		countCharacters(phrase);
		this.trie = processWordList();

	}
    
	/**
	 * Takes a given word file , eliminates all impossible words 
	 * Adds remaining to a trie.
	 * 
	 * @return
	 * @throws IOException 
	 */
	public Trie processWordList() throws IOException{

		BufferedReader br;

		try {
			System.out.println("Creating a dictionary...");
			Trie trie = new Trie();
			String word;
            
			InputStream inputStream = getClass().getResourceAsStream(AppConstants.getWordFileLocation());
			br = new BufferedReader(new InputStreamReader(inputStream));
			
			System.out.println("Discarding impossible words...");
			while((word = br.readLine())!=null) {
				if(isPossibleWord(word))
					trie.add(word);	
			}
            
			System.out.println("Number of possible words - " + numberOfWords);
			br.close();
			return trie;

		} catch (IOException e) {
			throw new IOException(e);
		}
	}
	
	/**
	 * Returns phrase
	 * @return
	 */
	public String getPhrase() {
		return phrase;
	}
	
	/**
	 * Returns Dictionary's root.
	 * @return
	 */
	public TrieNode getRoot() {
		return trie.getRoot();
	}
	
	/**
	 * checks if a given word
	 * 1. Has all characters from the given phrase
	 * 2. has length <= given phrase
	 * 3. character occurrences in word <= number of occurrences in the given phrase
	 * @param word
	 * @return
	 */
	boolean isPossibleWord(String word) {	
		
		int charOccurences[] = new int[MAX_CHAR];

		if(word.length() < minWordLength || word.length() > phrase.length())
			return false;

		for (int i = 0; i < word.length() ; i++) {
			char c = word.charAt(i);
			int index = phrase.indexOf(c);
			if(index<0)
				return false;
			
			charOccurences[c]++;
			if(charOccurences[c]>this.charOccurrences[c])
			    return false;
		}
        numberOfWords++;
		return true;
	}
	
	
	/**
	 * return number of words in the dictionary
	 * @return
	 */
	int getNumberOfWords() {
		return this.numberOfWords;
	}
	
	int[] getCharOccurances() {
		return this.charOccurrences;
	}

	/**
	 * fills charOccurences array with a count of characters in the given phrase
	 * Example input="pppp" will set charOccurences['p'] = 4
	 * @param phrase
	 */
	private void countCharacters(String phrase) {
		
		for(int i=0 ; i < phrase.length(); i++ )
			charOccurrences[phrase.charAt(i)]++;
		
	}
	
}
