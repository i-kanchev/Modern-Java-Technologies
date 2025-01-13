package bg.sofia.uni.fmi.mjt.sentiment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieReviewSentimentAnalyzerTest {

    private static final String POSITIVE = "positive";
    private static final String SOMEWHAT_POSITIVE = "somewhat positive";
    private static final String NEUTRAL = "neutral";
    private static final String SOMEWHAT_NEGATIVE = "somewhat negative";
    private static final String NEGATIVE = "negative";

    private Path pathReviews, pathStopWords;
    private File fileReviews, fileStopWords;
    private Writer setUpReviews, setUpStopWords, reviewsOut;
    private Reader stopWordsIn, reviewsIn;
    private MovieReviewSentimentAnalyzer analyzer;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() throws IOException {
        pathReviews = tempDir.resolve("movieReviews.txt");
        pathStopWords = tempDir.resolve("stopwords.txt");

        fileReviews = pathReviews.toFile();
        fileStopWords = pathStopWords.toFile();

        setUpReviews = new BufferedWriter(new FileWriter(fileReviews));
        setUpStopWords = new BufferedWriter(new FileWriter(fileStopWords));
    }

    @AfterEach
    public void clear() throws IOException {
        reviewsOut.close();
        reviewsIn.close();
        stopWordsIn.close();
    }

    @Test
    void testReviewExcludePunctuation () throws IOException {
        setUpReviews.write("4 , ! one-two test ; ' TEST don't ? .");
        setUpReviews.close();
        setUpStopWords.close();

        createAnalyzer();

        assertEquals(4, analyzer.getSentimentDictionarySize(),
            "Punctuation should be excluded");
        assertEquals(1, analyzer.getWordFrequency("don't"),
            "Words with ' should remain intact");
        assertEquals(1, analyzer.getWordFrequency("one"),
            "Words with - should be separated");
        assertEquals(1, analyzer.getWordFrequency("two"),
            "Words with - should be separated");
    }

    @Test
    void testGetReviewSentimentIllegalArgument() throws IOException {
        setUpReviews.close();
        setUpStopWords.close();

        createAnalyzer();

        assertThrows(IllegalArgumentException.class,
            () -> analyzer.getReviewSentiment(null),
            "IllegalArgumentException should be thrown");

        assertThrows(IllegalArgumentException.class,
            () -> analyzer.getReviewSentiment(" "),
            "IllegalArgumentException should be thrown");
    }

    @Test
    void testGetReviewSentimentCorrect() throws IOException {
        setUpReviews.write("4 I like it ."
            + System.lineSeparator() + "4 I like it so much ."
            + System.lineSeparator() + "3 I like it but it could have been better ."
            + System.lineSeparator() + "4 I like it ."
            + System.lineSeparator() + "0 Don't watch it ."
            + System.lineSeparator() + "1 Meh ."
            + System.lineSeparator() + "2 Decent ."
            + System.lineSeparator() + "0 Much disappointed ."
            + System.lineSeparator() + "0 Expected to be better ."
            + System.lineSeparator() + "1 test ."
            + System.lineSeparator() + "3 test .");
        setUpReviews.close();
        setUpStopWords.write("i" + System.lineSeparator() + "it" + System.lineSeparator() + "so"
            + System.lineSeparator() + "to" + System.lineSeparator() + "be");
        setUpStopWords.close();

        createAnalyzer();

        assertEquals(2.875, analyzer.getReviewSentiment("Like test"),
            "GetReviewSentiment should return the correct sentiment score");
        assertEquals(1.5, analyzer.getReviewSentiment("meh test"),
            "GetReviewSentiment should return the correct sentiment score");
        assertEquals(1.75, analyzer.getReviewSentiment("Test BETTER"),
            "GetReviewSentiment should return the correct sentiment score");
        assertEquals(0.5, analyzer.getReviewSentiment("disappointed MEH"),
            "GetReviewSentiment should return the correct sentiment score");
        assertEquals(3.75, analyzer.getReviewSentiment("like something"),
            "GetReviewSentiment should return the correct sentiment score");
        assertEquals(2.583, analyzer.getReviewSentiment("A decent test to LIKE"),
            "GetReviewSentiment should return the correct sentiment score");
        assertEquals(0, analyzer.getReviewSentiment("very disappointed"),
            "GetReviewSentiment should return the correct sentiment score");
    }

    @Test
    void testGetReviewSentimentUnknown() throws IOException {
        setUpReviews.write("4 I like it ."
            + System.lineSeparator() + "0 Just don't watch it ."
            + System.lineSeparator() + "1 Meh ."
            + System.lineSeparator() + "2 Decent .");
        setUpReviews.close();
        setUpStopWords.write("i" + System.lineSeparator() + "it");
        setUpStopWords.close();

        createAnalyzer();

        assertEquals(-1.0, analyzer.getReviewSentiment("no info"),
            "GetReviewSentiment should return -1.0 for unknown since there is no information about the words");
    }

    @Test
    void testGetReviewSentimentAsNameIllegalArgument() throws IOException {
        setUpReviews.close();
        setUpStopWords.close();

        createAnalyzer();

        assertThrows(IllegalArgumentException.class,
            () -> analyzer.getReviewSentimentAsName(null),
            "IllegalArgumentException should be thrown");

        assertThrows(IllegalArgumentException.class,
            () -> analyzer.getReviewSentimentAsName(" "),
            "IllegalArgumentException should be thrown");
    }

    @Test
    void testGetReviewSentimentAsNameCorrect() throws IOException {
        setUpReviews.write("4 I like it ."
            + System.lineSeparator() + "4 I like it so much ."
            + System.lineSeparator() + "3 I like it but it could have been better ."
            + System.lineSeparator() + "4 I like it ."
            + System.lineSeparator() + "0 Don't watch it ."
            + System.lineSeparator() + "1 Meh ."
            + System.lineSeparator() + "2 Decent ."
            + System.lineSeparator() + "0 Much disappointed ."
            + System.lineSeparator() + "0 Expected to be better ."
            + System.lineSeparator() + "1 test ."
            + System.lineSeparator() + "3 test .");
        setUpReviews.close();
        setUpStopWords.write("i" + System.lineSeparator() + "it" + System.lineSeparator() + "so"
            + System.lineSeparator() + "to" + System.lineSeparator() + "be");
        setUpStopWords.close();

        createAnalyzer();

        assertEquals(SOMEWHAT_POSITIVE, analyzer.getReviewSentimentAsName("Like test"),
            "GetReviewSentimentAsName should return the correct sentiment by name");
        assertEquals(NEUTRAL, analyzer.getReviewSentimentAsName("meh test"),
            "GetReviewSentimentAsName should return the correct sentiment by name");
        assertEquals(NEUTRAL, analyzer.getReviewSentimentAsName("Test BETTER"),
            "GetReviewSentimentAsName should return the correct sentiment by name");
        assertEquals(SOMEWHAT_NEGATIVE, analyzer.getReviewSentimentAsName("disappointed MEH"),
            "GetReviewSentimentAsName should return the correct sentiment by name");
        assertEquals(POSITIVE, analyzer.getReviewSentimentAsName("like something"),
            "GetReviewSentimentAsName should return the correct sentiment by name");
        assertEquals(SOMEWHAT_POSITIVE, analyzer.getReviewSentimentAsName("A decent test to LIKE"),
            "GetReviewSentimentAsName should return the correct sentiment by name");
        assertEquals(NEGATIVE, analyzer.getReviewSentimentAsName("very disappointed"),
            "GetReviewSentimentAsName should return the correct sentiment by name");
    }

    @Test
    void testGetReviewSentimentAsNameUnknown() throws IOException {
        setUpReviews.write("4 I like it ."
            + System.lineSeparator() + "0 Just don't watch it ."
            + System.lineSeparator() + "1 Meh ."
            + System.lineSeparator() + "2 Decent .");
        setUpReviews.close();
        setUpStopWords.write("i" + System.lineSeparator() + "it");
        setUpStopWords.close();

        createAnalyzer();

        assertEquals("unknown", analyzer.getReviewSentimentAsName("no info"),
            "GetReviewSentimentAsName should return unknown since there is no information about the words");
    }

    @Test
    void testGetWordSentimentIllegalArgument() throws IOException {
        setUpReviews.close();
        setUpStopWords.close();

        createAnalyzer();

        assertThrows(IllegalArgumentException.class,
            () -> analyzer.getWordSentiment(null),
            "IllegalArgumentException should be thrown");

        assertThrows(IllegalArgumentException.class,
            () -> analyzer.getWordSentiment(" "),
            "IllegalArgumentException should be thrown");
    }

    @Test
    void testGetWordSentimentCorrect() throws IOException {
        setUpReviews.write("4 Test to test the TEST ."
            + System.lineSeparator() + "2 TesT a lot ."
            + System.lineSeparator() + "3 something ."
            + System.lineSeparator() + "0 negative test .");
        setUpReviews.close();
        setUpStopWords.write("a" + System.lineSeparator() + "to" + System.lineSeparator() + "the");
        setUpStopWords.close();

        createAnalyzer();

        assertEquals(2.0, analyzer.getWordSentiment("test"),
            "GetWordSentiment should return the correct word sentiment");
    }

    @Test
    void testGetWordSentimentUnknownWord() throws IOException {
        setUpReviews.write("4 yes .");
        setUpReviews.close();
        setUpStopWords.close();

        createAnalyzer();

        assertEquals(-1.0, analyzer.getWordSentiment("no"),
            "GetWordSentiment should return -1.0 if the word is missing");
    }

    @Test
    void testGetWordFrequencyIllegalArgument() throws IOException {
        setUpReviews.close();
        setUpStopWords.close();

        createAnalyzer();

        assertThrows(IllegalArgumentException.class,
            () -> analyzer.getWordFrequency(null),
            "IllegalArgumentException should be thrown");

        assertThrows(IllegalArgumentException.class,
            () -> analyzer.getWordFrequency(" "),
            "IllegalArgumentException should be thrown");
    }

    @Test
    void testGetWordFrequencyCorrect() throws IOException {
        setUpReviews.write("4 Test to test the TEST ."
            + System.lineSeparator() + "2 TesT a lot .");
        setUpReviews.close();
        setUpStopWords.write("a" + System.lineSeparator() + "to" + System.lineSeparator() + "the");
        setUpStopWords.close();

        createAnalyzer();

        assertEquals(4, analyzer.getWordFrequency("test"),
            "GetWordFrequency should return the correct word frequency");
    }

    @Test
    void testGetWordFrequencyUnknownWord() throws IOException {
        setUpReviews.write("4 yes .");
        setUpReviews.close();
        setUpStopWords.close();

        createAnalyzer();

        assertEquals(0, analyzer.getWordFrequency("no"),
            "GetWordFrequency should return 0 if the word is missing");
    }

    @Test
    void testGetMostFrequentWordsIllegalArgument() throws IOException {
        setUpReviews.close();
        setUpStopWords.close();

        createAnalyzer();

        assertThrows(IllegalArgumentException.class,
            () -> analyzer.getMostFrequentWords(-3),
            "IllegalArgumentException should be thrown");
    }

    @Test
    void testGetMostFrequentWordsCorrect() throws IOException {
        setUpReviews.write("4 Test tesT to test the TEST TesT ."
            + System.lineSeparator() + "2 a lot one ."
            + System.lineSeparator() + "2 a lot two ."
            + System.lineSeparator() + "2 a lot three ."
            + System.lineSeparator() + "2 a lot four ."
            + System.lineSeparator() + "1 Yes Papa ."
            + System.lineSeparator() + "1 yes yes something.");
        setUpReviews.close();
        setUpStopWords.write("a" + System.lineSeparator() + "to" + System.lineSeparator() + "the");
        setUpStopWords.close();

        createAnalyzer();

        List<String> expected = new ArrayList<>();
        expected.add("test");
        expected.add("lot");
        expected.add("yes");

        assertEquals(expected, analyzer.getMostFrequentWords(3),
            "GetMostFrequentWords should return the correct words");
    }

    @Test
    void testGetMostPositiveWordsIllegalArgument() throws IOException {
        setUpReviews.close();
        setUpStopWords.close();

        createAnalyzer();

        assertThrows(IllegalArgumentException.class,
            () -> analyzer.getMostPositiveWords(-1),
            "IllegalArgumentException should be thrown");
    }

    @Test
    void testGetMostPositiveWordsCorrect() throws IOException {
        setUpReviews.write("1 Test to test the TEST ."
            + System.lineSeparator() + "0 no ."
            + System.lineSeparator() + "1 MEH ."
            + System.lineSeparator() + "4 Positive test ."
            + System.lineSeparator() + "3 Good ."
            + System.lineSeparator() + "3 meh good .");
        setUpReviews.close();
        setUpStopWords.write("a" + System.lineSeparator() + "to" + System.lineSeparator() + "the");
        setUpStopWords.close();

        createAnalyzer();

        List<String> expected = new ArrayList<>();
        expected.add("positive");
        expected.add("good");
        expected.add("test");
        expected.add("meh");

        assertEquals(expected, analyzer.getMostPositiveWords(4),
            "GetMostPositiveWords should return the correct words");
    }

    @Test
    void testGetMostNegativeWordsIllegalArgument() throws IOException {
        setUpReviews.close();
        setUpStopWords.close();

        createAnalyzer();

        assertThrows(IllegalArgumentException.class,
            () -> analyzer.getMostNegativeWords(-2),
            "IllegalArgumentException should be thrown");
    }

    @Test
    void testGetMostNegativeWordsCorrect() throws IOException {
        setUpReviews.write("1 Test to test the TEST ."
            + System.lineSeparator() + "0 no ."
            + System.lineSeparator() + "1 MEH ."
            + System.lineSeparator() + "4 Positive test ."
            + System.lineSeparator() + "3 Good ."
            + System.lineSeparator() + "3 meh good .");
        setUpReviews.close();
        setUpStopWords.write("a" + System.lineSeparator() + "to" + System.lineSeparator() + "the");
        setUpStopWords.close();

        createAnalyzer();

        List<String> expected = new ArrayList<>();
        expected.add("no");
        expected.add("meh");
        expected.add("test");

        assertEquals(expected, analyzer.getMostNegativeWords(3),
            "GetMostNegativeWords should return the correct words");
    }

    @Test
    void testAppendReviewIllegalReview() throws IOException {
        setUpReviews.close();
        setUpStopWords.close();

        createAnalyzer();

        assertThrows(IllegalArgumentException.class,
            () -> analyzer.appendReview(null, 1),
            "IllegalArgumentException should be thrown");

        assertThrows(IllegalArgumentException.class,
            () -> analyzer.appendReview(" ", 1),
            "IllegalArgumentException should be thrown");
    }

    @Test
    void testAppendReviewIllegalSentiment() throws IOException {
        setUpReviews.close();
        setUpStopWords.close();

        createAnalyzer();

        assertThrows(IllegalArgumentException.class,
            () -> analyzer.appendReview("4 test .", -2),
            "IllegalArgumentException should be thrown");

        assertThrows(IllegalArgumentException.class,
            () -> analyzer.appendReview("3 test .", 5),
            "IllegalArgumentException should be thrown");
    }

    @Test
    void testAppendReviewCorrect() throws IOException {
        setUpReviews.write("4 test .");
        setUpReviews.close();
        setUpStopWords.close();

        createAnalyzer();

        assertTrue(analyzer.appendReview("Test .", 2),
            "AppendReview should be successfully added");

        assertEquals(3, analyzer.getWordSentiment("test"),
            "AppendReview should update the reviews in the collection");
    }

    @Test
    void testGetSentimentDictionarySizeCorrect() throws IOException {
        setUpReviews.write("4 Test to test the TEST ."
            + System.lineSeparator() + "3 TeSt something .");
        setUpReviews.close();
        setUpStopWords.write("a" + System.lineSeparator() + "to" + System.lineSeparator() + "the");
        setUpStopWords.close();

        createAnalyzer();

        assertEquals(2, analyzer.getSentimentDictionarySize(),
            "SentimentDictionarySize should be calculated correctly");
    }

    @Test
    void testIsStopWordIllegalArgument() throws IOException {
        setUpReviews.close();
        setUpStopWords.close();

        createAnalyzer();

        assertThrows(IllegalArgumentException.class,
            () -> analyzer.isStopWord(null),
            "IllegalArgumentException should be thrown");

        assertThrows(IllegalArgumentException.class,
            () -> analyzer.isStopWord(" "),
            "IllegalArgumentException should be thrown");
    }

    @Test
    void testIsStopWordCorrect() throws IOException {
        setUpReviews.close();
        setUpStopWords.write("yes");
        setUpStopWords.close();

        createAnalyzer();

        assertTrue(analyzer.isStopWord("yes"), "IsStopWord should work correctly");
        assertFalse(analyzer.isStopWord("no"), "IsStopWord should work correctly");
    }

    @Test
    void testIsStopWordCaseInsensitive() throws IOException {
        setUpReviews.close();
        setUpStopWords.write("YeS");
        setUpStopWords.close();

        createAnalyzer();

        assertTrue(analyzer.isStopWord("yes"), "Word should be stopword but it is not");
    }

    private void createAnalyzer() throws IOException {
        reviewsOut = new BufferedWriter(new FileWriter(fileReviews, true));
        reviewsIn = new BufferedReader(new FileReader(fileReviews));
        stopWordsIn = new BufferedReader(new FileReader(fileStopWords));

        analyzer = new MovieReviewSentimentAnalyzer(stopWordsIn, reviewsIn, reviewsOut);
    }
}