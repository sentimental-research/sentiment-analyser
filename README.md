# sentiment-analyser
Prototype Java application that parses a CSV file of the form:

    userid ; date ; tweet ; twitterId

The app then uses a Natural Language Processing algorithm to perform sentiment
analysis on the tweet portion of the CSV and outputs another CSV of the form:

    userid ; date ; tweet ; twitterId ; sentimetScore

## What this needs to do

 1. Read the tweets in from CSV using opencsv.sourceforge.net
 2. Extract the content of the tweet from other metadata (ID, geolocation, username, etc.)
 3. Cleanup the tweet content using https://github.com/twitter/twitter-text
 4. Annotate the tweet content using the Stanford NLP library (annotation = single integer value containing the "positive-ness" of the sentiment conveyed by the tweet)
 5. Write out the CSV in the original format with an additional column containing the annotation (again using opencsv)

## Uses

 - [Stanford NLP Library](http://stanfordnlp.github.io/CoreNLP/index.html)
 - [OpenCSV](http://opencsv.sourceforge.net/)

## Issues / Problems

 - There's not much variety in the sentiment scores given out by the Stanford library, range in testing 1-4
 - No checking of Tweet bias, e.g. release notices and the like written by the development team are often positive
 - Little variation in the score for neutral comments vs. unfavourable ones, they all get low scores
  - CSV parsing is over-simple, if there's a problem with a line it's just skipped
