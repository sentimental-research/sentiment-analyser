# sentiment-analyser
Tweet sentiment analyser

## What this needs to do

1. Read the tweets in from CSV using opencsv.sourceforge.net
2. Extract the content of the tweet from other metadata (ID, geolocation, username, etc.)
3. Cleanup the tweet content using https://github.com/twitter/twitter-text
4. Annotate the tweet content using the Stanford NLP library (annotation = single integer value containing the "positive-ness" of the sentiment conveyed by the tweet)
5. Write out the CSV in the original format with an additional column containing the annotation (again using opencsv)
