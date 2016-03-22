/**
 * 
 */
package org.openpreservation.sentitment;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openpreservation.sentiment.SentimentAnalyser;

/**
 * @author cfw
 *
 */
public class SentitmentAnalyserTest {
	private static final String GOOD = "good";
	private static final String NOT_GOOD = "not good";

	/**
	 * Test method for
	 * {@link org.openpreservation.sentiment.SentimentAnalyser#calculateSentiment(java.lang.String)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testCalculateSentimentNullTweet() {
		SentimentAnalyser.calculateSentiment(null);
	}

	/**
	 * Test method for
	 * {@link org.openpreservation.sentiment.SentimentAnalyser#calculateSentiment(java.lang.String)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCalculateSentimentEmptyTweet() {
		SentimentAnalyser.calculateSentiment("");
	}
	
	@Test
	public void testCalculateSentiment() {
		int goodScore = SentimentAnalyser.calculateSentiment(GOOD);
		int notGoodScore = SentimentAnalyser.calculateSentiment(NOT_GOOD);
		assertTrue(goodScore > notGoodScore);
	}
}
