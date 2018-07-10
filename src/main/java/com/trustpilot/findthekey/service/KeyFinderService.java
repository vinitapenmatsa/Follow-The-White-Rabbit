package com.trustpilot.findthekey.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import com.trustpilot.findthekey.model.MinifiedDictionary;
import com.trustpilot.findthekey.model.TrieNode;
import com.trustpilot.findthekey.util.StringUtils;

/**
 * Service class to 
 * Generate all possible anagrams of a given phrase
 * Compare with hash clues
 * Return Keys
 * @author vinitapenmatsa
 *
 */

public class KeyFinderService {
	
	/** Hash  */
	private String MD5_CLUE;
	
	/** Dictionary processed to eliminate unlikely words */
	private MinifiedDictionary dictionary;
	
	/** variable to return when key found */
	private boolean keyFound = false;
	
	public KeyFinderService(MinifiedDictionary dictionary,String MD5_CLUE) {
		this.MD5_CLUE = MD5_CLUE;
		this.dictionary = dictionary;
	}
	
	/**
	 * Generates anagrams with maxNumberOfWords to find the key
	 * @param maxNumberOfWords
	 * @return
	 */
	public List<String> findKeys(int maxNumberOfWords){
		return findKeys(maxNumberOfWords , 2 , "");
	}
	
	/**
	 * Generates anagrams with maxNumberOfWords && starting with a prefix to find the key.
	 * @param maxNumberOfWords
	 * @param startsWith
	 * @return
	 */
	public List<String> findKeys(int maxNumberOfWords , int minWordLength, String startsWith){
		setKeyFound(false);
		return findKeysHelper(dictionary.getPhrase(),"", startsWith,maxNumberOfWords-1,minWordLength, dictionary.getRoot());
	}
	
	
	/**
	 * Generates anagrams
	 * Compares with clue 
	 * Returns Keys.
	 * @param remainingPhrase
	 * @param possibleKey
	 * @param stratsWith
	 * @param numberOfWords
	 * @param node
	 * @return
	 */
    List<String> findKeysHelper(String remainingPhrase, String possibleKey , String stratsWith ,int numberOfWords,int minWordLength ,TrieNode node){
		
		List<String> keys = new ArrayList<String>();

		if(remainingPhrase.length()==0) { 		
			if(node.isEnd() && numberOfWords==0)
			{  
				String hash = StringUtils.getHash(possibleKey.toString());
				if(MD5_CLUE.equals(hash)) {
			      keys.add(possibleKey.toString());	
			      keyFound = true;
				}
			}
			return keys;
		}
			
		if(keyFound)
			return keys;
			
        
		// get all possible words in the trie , starting from root's children
		Set<Entry<Character, TrieNode>> children = node.getChildren().entrySet();
		for (Entry<Character, TrieNode> child : children) {
			Character c = child.getKey();
			int index = remainingPhrase.indexOf(c);
			if(index > -1) {
				if(possibleKey.length() == 0 || possibleKey.startsWith(stratsWith))
				 keys.addAll(
						 findKeysHelper(
								 new StringBuilder(remainingPhrase).deleteCharAt(index).toString(),
								 possibleKey+c,
								 stratsWith, 
								 numberOfWords,
								 minWordLength, 
								 node.getChildren().get(c)
								 )
						 );
			}
		}
		
		//if word then add it to the possible key sentence and add an extra space
		if(node.isEnd() && numberOfWords > 0) {
		   String[] words = possibleKey.split(" ");
		   if(words[words.length-1].length() >= minWordLength)
              keys.addAll(
            		  findKeysHelper(
            				  remainingPhrase, 
            				  possibleKey+ " ",
            				  stratsWith, 
            				  numberOfWords-1,
            				  minWordLength, 
            				  dictionary.getRoot()
            				  )
            		  );
		}
		
		return keys;
	}

    /** set key found*/
	void setKeyFound(boolean keyFound) {
		this.keyFound = keyFound;
	}
	
	
}
