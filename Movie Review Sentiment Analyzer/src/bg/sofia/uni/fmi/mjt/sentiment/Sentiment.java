package bg.sofia.uni.fmi.mjt.sentiment;

public enum Sentiment {
    POSITIVE(4, "positive"),
    SOMEWHAT_POSITIVE(3, "somewhat positive"),
    NEUTRAL(2, "neutral"),
    SOMEWHAT_NEGATIVE(1, "somewhat negative"),
    NEGATIVE(0, "negative"),
    UNKNOWN(-1, "unknown");

    public final int value;
    public final String text;

    private Sentiment(int value, String text) {
        this.value = value;
        this.text = text;
    }
}
