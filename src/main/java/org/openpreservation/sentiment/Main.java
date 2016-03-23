package org.openpreservation.sentiment;

import java.io.*;
import java.util.List;
import java.util.Arrays;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public final class Main {

  public static final int TWEET_USER = 0;
  public static final int TWEET_DATE = 1;
  public static final int TWEET_TEXT = 2;
  public static final int TWEET_ID   = 3;

  public static void main(String[] args) {
    // First argument is input CSV file (optional - default is to eat CSV from
    // stdin)
    // Second argument is output CSV file (optional - default is to poo CSV
    // to stdout)
    if (args.length == 0 || args.length > 2) {
      System.err.println("Usage: annotate.jar [input.csv] [output.csv]");
      System.exit(1);
    }

    Reader input = null;
    try {
      input = new BufferedReader((args.length > 0) ? new FileReader(args[0])
                                        : new InputStreamReader(System.in));
    } catch (FileNotFoundException e) {
      System.out.println(e.toString());
      System.exit(1);
    } catch (IOException e) {
      System.out.println(e.toString());
      System.exit(1);
    }

    Writer output = null;
    try {
      output = new BufferedWriter((args.length > 1) ? new FileWriter(args[1])
                                        : new OutputStreamWriter(System.out));
    } catch (IOException e) {
      System.out.println(e.toString());
      System.exit(1);
    } finally {
      try {
        input.close();
      } catch (IOException e) {
        System.out.println(e.toString());
        System.exit(1);
      }
    }

    // Use ';' as field separator, '"' as field quote character, skip first line
    // as header
    CSVReader reader = new CSVReader(input, ';', '"', 1);
    CSVWriter writer = new CSVWriter(output, ';', '"');

    // Loop over all the tweets in the file
    List<String> tweet = null;
    try {
      while ((tweet = Arrays.asList(reader.readNext())) != null) {
        // Extract the tweet text (3rd field)
        String text = tweet.get(TWEET_TEXT);

        // Clean up the tweet
        text = cleanup(text);

        // Calculate the score for the tweet
        int score = SentimentAnalyser.calculateSentiment(text);

        // Append the score to the CSV row
        tweet.add(Integer.toString(score));

        // Write out the line annotated with the score
        writer.writeNext((String[])tweet.toArray());
      }
    } catch (IOException e) {
      System.out.println(e.toString());
      System.exit(1);
    } finally {
      try {
        reader.close();
        writer.close();
      } catch (IOException e) {
        System.out.println(e.toString());
        System.exit(1);
      }
    }

    System.exit(0);
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
  private static String cleanup(String text) {
    return text;  // TODO - use String.replaceAll(String regex).replaceAll()...
  }

}
