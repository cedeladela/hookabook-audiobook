package exception.rating;

public class RatingNotFoundException extends RuntimeException {

    public RatingNotFoundException(Long ratingId) {
        super("Comment with ID " + ratingId + " not found");
    }
}
