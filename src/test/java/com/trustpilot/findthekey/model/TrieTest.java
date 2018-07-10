package com.trustpilot.findthekey.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Class to test trie creation and operations
 * @author vinitapenmatsa
 *
 */
public class TrieTest {
	
	Trie trie;

	@Test
	public void getRootTest() {
		trie = new Trie();
		assertTrue(trie.getRoot() instanceof TrieNode);
		
		TrieNode root = trie.getRoot();
		assertEquals(0, root.getChildren().size());
		
	}
	
	@Test
	public void addTest() {
		trie = new Trie();
		
		//testing word add
		trie.add("hello");
		
		assertEquals(1, trie.getRoot().getChildren().size());
		assertTrue(trie.getRoot().getChildren().containsKey('h'));
		assertFalse(trie.getRoot().getChildren().containsKey('e'));
		
		trie.add("hero");
		assertEquals(1, trie.getRoot().getChildren().size());
		
		trie.add("apple");
		assertEquals(2, trie.getRoot().getChildren().size());
		assertTrue(trie.getRoot().getChildren().containsKey('a'));
		
		assertTrue(trie.containsWord("hello"));
		assertTrue(trie.containsWord("apple"));	
		
	}
	
	@Test
	public void addAllTest() {
		trie = new Trie();
		
		//testing trie add with a collection
		List<String> words = new ArrayList<>();
		words.add("1");
		words.add("2");
		words.add("3");
		words.add("4");
		words.add("5");
		
		trie.addAll(words);
		
		assertEquals(words.size(), trie.getRoot().getChildren().size());
		for(String word : words) {
			assertTrue(trie.containsWord(word));
		}
		
	}
	
	@Test
	public void isPrefixTest() {
		
		trie = new Trie();
		
		//testing for prefixes.
		trie.add("prefix");
		trie.add("pregame");
		
		//testing valid prefixes.
		assertTrue(trie.isPrefix("pre"));
		
		//testing invalid prefixes.
		assertFalse(trie.isPrefix("pret"));
		assertFalse(trie.isPrefix("refix"));
		
	}
	
	@Test
	public void containsWordTest() {
		
       trie = new Trie();
		
		//testing for contains word.
		trie.add("prefix");
		trie.add("pregame");
		
		//testing words trie should contain
		assertTrue(trie.containsWord("prefix"));
		assertTrue(trie.containsWord("pregame"));
		
		//testing words trie should not contain
		assertFalse(trie.containsWord("pre"));
		assertFalse(trie.containsWord("refix"));
		assertFalse(trie.containsWord("random"));
		
	}

}
