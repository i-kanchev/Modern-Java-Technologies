package bg.sofia.uni.fmi.mjt.sentiment;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class MovieReviewSentimentAnalyzer implements SentimentAnalyzer {
    private static final double PRECISION = 1000D;
    private static final int RATING_POSITION = 0;
    private static final int WITHOUT_RATING_PLUS_SPACE = 2;
    private static final String ATTRIBUTE_DELIMITER = " -";

    private final Map<String, List<Integer>> wordsSentiment;
    private final Map<String, Integer> wordsFrequency;
    private final Set<String> stopWords;
    private final Writer file;

    public MovieReviewSentimentAnalyzer(Reader stopwordsIn, Reader reviewsIn, Writer reviewsOut) {
        wordsSentiment = new HashMap<>();
        wordsFrequency = new HashMap<>();
        stopWords = new HashSet<>();

        Scanner scannerStopWords = new Scanner(stopwordsIn);
        Scanner scannerReviews = new Scanner(reviewsIn);

        while (scannerStopWords.hasNextLine()) {
            this.stopWords.add(scannerStopWords.nextLine().toLowerCase());
        }

        while (scannerReviews.hasNextLine()) {
            String currReview = scannerReviews.nextLine().toLowerCase();

            int currSentiment = Character.getNumericValue(currReview.charAt(RATING_POSITION));
            currReview = currReview.substring(WITHOUT_RATING_PLUS_SPACE);

            Set<String> currWords = extractWords(currReview);

            for (String word : currWords) {
                if (!wordsSentiment.containsKey(word)) {
                    wordsSentiment.put(word, new ArrayList<>());
                }
                wordsSentiment.get(word).add(currSentiment);
            }
        }

        file = reviewsOut;
    }

    @Override
    public double getReviewSentiment(String review) {
        if (review == null || review.isBlank()) {
            throw new IllegalArgumentException("Review cannot be null or blank");
        }

        Set<String> reviewWords = extractWords(review.toLowerCase());
        double sentimentScore = 0.0;
        int sentimentCount = 0;

        for (String word : reviewWords) {
            if (wordsSentiment.containsKey(word)) {
                sentimentScore += average(word);
                sentimentCount++;
            }
        }

        if (sentimentCount == 0) {
            return Sentiment.UNKNOWN.value;
        }

        return Math.round(sentimentScore / sentimentCount * PRECISION) / PRECISION;
    }

    @Override
    public String getReviewSentimentAsName(String review) {
        double sentimentValue = Math.round(getReviewSentiment(review));

        for (Sentiment sentiment : Sentiment.values()) {
            if (sentiment.value == sentimentValue) {
                return sentiment.text;
            }
        }

        throw new RuntimeException();
    }

    @Override
    public double getWordSentiment(String word) {
        if (word == null || word.isBlank()) {
            throw new IllegalArgumentException("Word cannot be null or blank");
        }

        if (!wordsSentiment.containsKey(word)) {
            return Sentiment.UNKNOWN.value;
        }

        return average(word);
    }

    @Override
    public int getWordFrequency(String word) {
        if (word == null || word.isBlank()) {
            throw new IllegalArgumentException("Word cannot be null or blank");
        }

        if (!wordsFrequency.containsKey(word)) {
            return 0;
        }

        return wordsFrequency.get(word);
    }

    @Override
    public List<String> getMostFrequentWords(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("N cannot be negative");
        }

        return wordsFrequency.keySet().stream()
            .sorted((w1, w2) -> Integer.compare(wordsFrequency.get(w2), wordsFrequency.get(w1)))
            .limit(n)
            .toList();
    }

    @Override
    public List<String> getMostPositiveWords(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("N cannot be negative");
        }

        return wordsSentiment.keySet().stream()
            .sorted((w1, w2) -> Double.compare(average(w2), average(w1)))
            .limit(n)
            .toList();
    }

    @Override
    public List<String> getMostNegativeWords(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("N cannot be negative");
        }

        return wordsSentiment.keySet().stream()
            .sorted((w1, w2) -> Double.compare(average(w1), average(w2)))
            .limit(n)
            .toList();
    }

    @Override
    public boolean appendReview(String review, int sentiment) {
        if (review == null || review.isBlank()) {
            throw new IllegalArgumentException("Review cannot be null or blank");
        }
        if (sentiment < Sentiment.NEGATIVE.value || sentiment > Sentiment.POSITIVE.value) {
            throw new IllegalArgumentException("Sentiment cannot be negative");
        }

        try {
            file.append(System.lineSeparator() + sentiment + " " + review);
            file.close();
        } catch (IOException e) {
            return false;
        }

        Set<String> newWords = extractWords(review.toLowerCase());

        for (String word : newWords) {
            if (!wordsSentiment.containsKey(word)) {
                wordsSentiment.put(word, new ArrayList<>());
            }
            wordsSentiment.get(word).add(sentiment);
        }

        return true;
    }

    @Override
    public int getSentimentDictionarySize() {
        return wordsSentiment.size();
    }

    @Override
    public boolean isStopWord(String word) {
        if (word == null || word.isBlank()) {
            throw new IllegalArgumentException("Word cannot be null or blank");
        }

        return stopWords.contains(word.toLowerCase());
    }

    private Set<String> extractWords(String review) {
        StringTokenizer tokens = new StringTokenizer(review, ATTRIBUTE_DELIMITER);

        Set<String> words = new HashSet<>();

        while (tokens.hasMoreTokens()) {
            String currToken = tokens.nextToken();

            if (Pattern.matches("\\p{IsPunctuation}", currToken) || stopWords.contains(currToken)) {
                continue;
            }

            words.add(currToken);

            if (!wordsFrequency.containsKey(currToken)) {
                wordsFrequency.put(currToken, 0);
            }

            wordsFrequency.put(currToken, wordsFrequency.get(currToken) + 1);
        }

        return words;
    }

    private double average(String word) {
        double sum = 0;
        for (int sentimentScore : wordsSentiment.get(word)) {
            sum += sentimentScore;
        }

        return sum / wordsSentiment.get(word).size();
    }
}
