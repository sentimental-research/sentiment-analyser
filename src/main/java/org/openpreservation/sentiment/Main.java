/**
 *
 */
package org.openpreservation.sentiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.twitter.Regex;

/**
 * @author Gary Macindoe
 */
public final class Main {

	/**
	 * Index of tweeter in CSV row.
	 */
	public static final int TWEET_USER = 0;

	/**
	 * Index of date in CSV row.
	 */
	public static final int TWEET_DATE = 1;

	/**
	 * Index of tweet content in CSV row.
	 */
	public static final int TWEET_TEXT = 2;

	/**
	 * Index of tweet ID in CSV row.
	 */
	public static final int TWEET_ID = 3;

	public static void main(String[] args) {
		// First argument is input CSV file (optional - default is to eat CSV
		// from
		// stdin)
		// Second argument is output CSV file (optional - default is to poo CSV
		// to stdout)
		if (args.length > 2) {
			System.err.println("Usage: annotate.jar [input.csv] [output.csv]");
			System.exit(1);
		}

		// Create reader from filename in args[0] or stdin if args is empty
		// Use ';' as field separator, '"' as field quote character, skip
		// first
		// line
		// as header
		try (Reader reader = new BufferedReader(
				(args.length > 0) ? new FileReader(args[0]) : new InputStreamReader(System.in));
				Writer writer = new BufferedWriter(
						(args.length > 1) ? new FileWriter(args[1]) : new OutputStreamWriter(System.out))) {
			annotateCsv(reader, writer);
		} catch (IOException e) {
			System.out.println(e.toString());
			System.exit(1);
		}

		System.exit(0);
	}

	public static void annotateCsv(final Reader input, final Writer output) throws IOException {
		// Use ';' as field separator, '"' as field quote character
		// skip first line as header
		try (CSVReader reader = new CSVReader(input, ',', '"', 1); CSVWriter writer = new CSVWriter(output, ',', '"')) {
			// Loop over all the tweets in the file
			String[] csvLine;
			while ((csvLine = reader.readNext()) != null) {
				// Skip malformed lines
				if (csvLine.length < 3)
					continue;
				// Extract the tweet text (3rd field)
				String tweet = csvLine[TWEET_TEXT];

				// Clean up the tweet
				tweet = cleanup(tweet);
				if ((tweet == null) || tweet.isEmpty())
					continue;

				// Calculate the score for the tweet
				int score = SentimentAnalyser.calculateSentiment(tweet);

				// Append the score to the CSV row
				List<String> tweetList = new ArrayList<>(Arrays.asList(csvLine));
				tweetList.add(Integer.toString(score));

				// Write out the line annotated with the score
				writer.writeNext(tweetList.toArray(new String[0]));
			}
		}
	}

  /**
   * Removes @usernames, #hashtags and http(s)://urls from a string using
   * regular expressions.
   *
   * @param text  the input string
   *
   * @return the input string with @usernames, #hashtags and http(s)://urls
   * removed.
   */
  public static String cleanup(String text) {
    text = Regex.VALID_HASHTAG.matcher(text).replaceAll("");
    text = Regex.VALID_MENTION_OR_LIST.matcher(text).replaceAll("");
    text = Regex.VALID_REPLY.matcher(text).replaceAll("");
    text = Regex.VALID_URL.matcher(text).replaceAll("");
    return text;
  }

}
