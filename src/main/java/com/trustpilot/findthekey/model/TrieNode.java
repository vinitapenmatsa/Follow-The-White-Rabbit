package com.trustpilot.findthekey.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Representation of a Trie Node
 * @author vinitapenmatsa
 *
 */
public class TrieNode {
	
	private Map<Character,TrieNode> children;
	private boolean isEnd;
	
	public TrieNode() {
		this.children = new HashMap<>();
		this.isEnd = false;
	}

	public Map<Character, TrieNode> getChildren() {
		return this.children;
	}

	public boolean isEnd() {
		return this.isEnd;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	

}
