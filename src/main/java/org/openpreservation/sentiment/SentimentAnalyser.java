/**
 * 
 */
package org.openpreservation.sentiment;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

/**
 * @author cfw
 *
 */
public final class SentimentAnalyser {
    static StanfordCoreNLP PIPELINE = new StanfordCoreNLP("stanfordNLP.properties");

    /**
     * @param tweet
     * @return an integer value based on the tweet sentiment, higher numbers == happier tweet
     */
	public static int calculateSentiment(String tweet) {
		if (tweet == null) throw new NullPointerException("Tweet cannot be null.");
		if (tweet.length() == 0) throw new IllegalArgumentException("Tweet cannot be empty");

        int mainSentiment = 0;
        int longest = 0;
        Annotation annotation = PIPELINE.process(tweet);
        for (CoreMap sentence : annotation
                .get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence
                    .get(SentimentAnnotatedTree.class);
            int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
            String partText = sentence.toString();
            if (partText.length() > longest) {
                mainSentiment = sentiment;
                longest = partText.length();
            }
        }
        return mainSentiment;
    }
}
