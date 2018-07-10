package com.trustpilot.findthekey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.trustpilot.findthekey.exception.NotAValidDifficultyLevelException;
import com.trustpilot.findthekey.model.MinifiedDictionary;
import com.trustpilot.findthekey.service.KeyFinderService;
import com.trustpilot.findthekey.util.StringUtils;

public class FindTheKey {

	/** MD5 Hash clues */
	private static final String EASY_CLUE = "e4820b45d2277f3844eac66c903e84be";
	private static final String DIFFICULT_CLUE = "23170acc097c24edb98fc5488ab033fe";
	private static final String HARD_CLUE = "665e5bcb0c20062fe8abaaf4628bb154";


	/**
	 * Main class takes in 
	 * - difficulty level 
	 * - max number of words per anagram
	 * - minimum length for each word
	 * and finds the key
	 * @param args
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args){

		Scanner sc = new Scanner(System.in);

		try {	  	    

			String phrase = "poultry outwits ants";
			System.out.println("Finding key using the phrase : " + phrase);

			phrase = StringUtils.removeSpaces(phrase);

			// collecting user inputs
			System.out.println("Enter a difficulty level : easy / difficult / hard "); 
			String levelOfDifficulty = sc.nextLine();

			System.out.println("Enter minimum length for each word.."); 
			int minWordLength = sc.nextInt();

			System.out.println("Enter number of words per anagram.."); 
			int numberOfWords = sc.nextInt();

			FindTheKey findTheKey = new FindTheKey();
			String key;

			long startTime = System.currentTimeMillis();

			key = findTheKey.getKey(phrase,getClue(levelOfDifficulty),numberOfWords,minWordLength);

			if(key!=null) {
				System.out.println("Key is ==> " + key);
				long endTime = System.currentTimeMillis();
				System.out.println("Find the Key took " + (endTime - startTime)/1000 + " seconds");				
			}else {
				System.out.println("Key not found , Try changing words per anangram / minimum word length");
			}

		} catch (NotAValidDifficultyLevelException e){
			System.out.println(e.getMessage());
		}catch(InputMismatchException e){
			System.out.println("Invalid number! Please re-try entering a positive interger..");
		}catch(IOException e){
			System.out.println("There was an issue reading WordList :" + e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			sc.close();
		}
	}

	/**
	 * Method to get the secret key
	 * - Creates a minified dictioany with possible words
	 * - Creates phrase.length number of threads to concurrently call keyfinderService.findKey() 
	 * - returns key if found
	 * - null if  not 
	 * @param phrase
	 * @param clue
	 * @param maxNumberOfWords
	 * @param minWordLength
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws IOException 
	 */
	String getKey(String phrase , String clue , int numberOfWords , int minWordLength) throws InterruptedException , ExecutionException, IOException {

		System.out.println("Entering the rabbit hole!!");
		List<String> keys = new ArrayList<>();

		// Initialize thread pool size to length of phrase
		ExecutorService executorService = Executors.newFixedThreadPool(phrase.length());		
		List<Future<List<String>>> results = new ArrayList<>();

		//create minified dictionary with possible words only
		MinifiedDictionary minifiedDictionary;	
		minifiedDictionary = new MinifiedDictionary(phrase, minWordLength);

		KeyFinderService keyFinder = new KeyFinderService(minifiedDictionary, clue);

		try {

			System.out.println("Depending on the word length, this might take a while. Please be patient...");

			//create and execute sub tasks for each letter as prefix
			for(int i = 0 ; i< phrase.length(); i++) {
				String prefix = phrase.substring(i, i+1);
				results.add(
						executorService.submit(
								new FindTheKeyTask(
										keyFinder,
										numberOfWords, 
										minWordLength, 
										prefix )));
			}

			//aggregate results to find the key
			for(Future<List<String>> future : results)
				keys.addAll(future.get());

			if(keys.size()>0)
				return  keys.get(0);

			return null;

		}finally {
			executorService.shutdown();
		}

	}

	/**
	 * Utility method returns MD5 hash clue corresponding to difficulty level
	 * @param levelOfDifficulty
	 * @return
	 * @throws NotAValidDifficultyLevelException
	 */
	static String getClue(String levelOfDifficulty) throws NotAValidDifficultyLevelException {

		switch (levelOfDifficulty.toUpperCase()) {
		case "EASY":
			return EASY_CLUE;
		case "DIFFICULT":
			return DIFFICULT_CLUE;
		case "HARD":
			return HARD_CLUE;
		default:
			throw new NotAValidDifficultyLevelException("Invalid difficulty level. Please try again");

		}
	}

}
