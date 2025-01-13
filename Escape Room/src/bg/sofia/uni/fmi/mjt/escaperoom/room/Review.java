package bg.sofia.uni.fmi.mjt.escaperoom.room;

public record Review(int rating, String reviewText) {
    public Review(int rating, String reviewText) {
        if (rating > 10.0 || rating < 0.0) {
            throw new IllegalArgumentException();
        }
        if (reviewText == null || reviewText.length() > 200) {
            throw new IllegalArgumentException();
        }
        this.rating = rating;
        this.reviewText = reviewText;
    }
}
