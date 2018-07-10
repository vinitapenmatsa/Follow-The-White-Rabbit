package com.trustpilot.findthekey.model;

import java.util.Collection;

/**
 * Representation of Trie Data Structure
 * contains functions to :
 * getRoot / add word / delete word / check if prefix / check if contains word
 * 
 * @author vinitapenmatsa
 *
 */
public class Trie {


	private TrieNode root;

	public Trie(){
		root = new TrieNode();
	}

	/**
	 * Returns Trie root node
	 * @return
	 */
	public TrieNode getRoot() {
		return root;
	}
	
    /**
     * inserts a given word.
     * @param word
     */
	public void add(String word) {

		TrieNode currentNode = root;
		for(int i=0 ; i< word.length() ; i++) {
			currentNode = currentNode.getChildren().computeIfAbsent(word.charAt(i), c -> new TrieNode());
		}
		currentNode.setEnd(true);		
	}

	/**
	 * inserts a collection of words
	 * @param words
	 */
	public void addAll(Collection<String> words) {
		for(String word : words)
			add(word);
	}

    /**
     * Checks if a given string is a possible prefix.
     * Example re-peat , re-enter hence re is prefix.
     * @param prefix
     * @return
     */
	public boolean isPrefix(String prefix) {
		
		TrieNode node = getLastNode(prefix);

		if(node == null)
			return false;
		
		return true;
	}
	
	/**
	 * checks if a given string is a word.
	 * @param word
	 * @return
	 */

	public boolean containsWord(String word) {

		TrieNode node = getLastNode(word);
		
		if(node==null)
			return false;
		
		return node.isEnd();
	}
	
    /**
     * Iterates through the given string and returns node at the end of the string,
     * null if node not found
     * @param prefix
     * @return
     */
	 private TrieNode getLastNode(String prefix) {
		
		TrieNode currentNode = root;

		for(int i=0; i < prefix.length() ; i++) {
			char c =  prefix.charAt(i);
			TrieNode node = currentNode.getChildren().get(c);
			if(node == null)
				return null;

			currentNode = node;
		}		
		return currentNode;
		
	}


}
