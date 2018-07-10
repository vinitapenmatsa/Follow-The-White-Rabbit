package com.trustpilot.findthekey;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.trustpilot.findthekey.exception.NotAValidDifficultyLevelException;
import com.trustpilot.findthekey.model.MinifiedDictionary;
import com.trustpilot.findthekey.service.KeyFinderService;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FindTheKey.class)
public class FindTheKeyTest {

	
	/** MD5 Hash clues */
	private static final String EASY_CLUE = "e4820b45d2277f3844eac66c903e84be";
	private static final String DIFFICULT_CLUE = "23170acc097c24edb98fc5488ab033fe";
	private static final String HARD_CLUE = "665e5bcb0c20062fe8abaaf4628bb154";

	MinifiedDictionary minifiedDictionary;
	KeyFinderService keyFinderService;
	ExecutorService executorService;
	FindTheKeyTask findTheKeyTask;
	Future<List<String>> future;


	@SuppressWarnings("unchecked")
	@Before
	public void setUp(){

		try {
			//mock all services
			minifiedDictionary = PowerMockito.mock(MinifiedDictionary.class);
			keyFinderService = PowerMockito.mock(KeyFinderService.class);
			executorService = PowerMockito.mock(ExecutorService.class);
			findTheKeyTask = PowerMockito.mock(FindTheKeyTask.class);
			future = PowerMockito.mock(Future.class);

			PowerMockito.whenNew(MinifiedDictionary.class).withAnyArguments().thenReturn(minifiedDictionary);
			PowerMockito.whenNew(KeyFinderService.class).withAnyArguments().thenReturn(keyFinderService);
		
			PowerMockito.mockStatic(Executors.class);
			PowerMockito.when(Executors.newFixedThreadPool(Mockito.anyInt())).thenReturn(executorService);
			PowerMockito.whenNew(FindTheKeyTask.class).withAnyArguments().thenReturn(findTheKeyTask);
			PowerMockito.when(executorService.submit(findTheKeyTask)).thenReturn(future);


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getKeyTestWhenKeyFound()  {
		try{
			String phrase = "test";

			PowerMockito.when(future.isDone()).thenReturn(true);
			PowerMockito.when(future.get()).thenReturn(Arrays.asList("key"));

			FindTheKey findTheKey = new FindTheKey();
			String key = findTheKey.getKey(phrase, "clue", 2, 5);

			Mockito.verify(executorService.submit(findTheKeyTask), Mockito.times(phrase.length()));
			assertTrue("key".equals(key));
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Test
	public void getKeyTestWhenKeyNotFound()  {
		try{
			String phrase = "test";
			
			PowerMockito.when(future.isDone()).thenReturn(true);
			PowerMockito.when(future.get()).thenReturn(new ArrayList<>());
			
			FindTheKey findTheKey = new FindTheKey();
			String key = findTheKey.getKey(phrase, "clue", 2, 5);

			Mockito.verify(executorService.submit(findTheKeyTask), Mockito.times(phrase.length()));
			assertTrue(key==null);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getClueTest() throws NotAValidDifficultyLevelException {
		
		assertTrue(EASY_CLUE.equals(FindTheKey.getClue("easy")));
		assertTrue(DIFFICULT_CLUE.equals(FindTheKey.getClue("difficuLT")));
		assertTrue(HARD_CLUE.equals(FindTheKey.getClue("HARD")));
		
	}
	
	@Test(expected=NotAValidDifficultyLevelException.class)
	public void getClueTestForInvalidDifficultyException() throws NotAValidDifficultyLevelException{
       FindTheKey.getClue("test");	
	}

}
