package exception.rating;

import cedeladela.hookabook.entity.Rating;

public class RatingNotFoundException extends RuntimeException {

    public RatingNotFoundException(Rating rating) {
        super("User with ID " + rating.getHbUser().getId() + " did not rate audiobook with ID " + rating.getAudiobook().getId() + ".");
    }

    public RatingNotFoundException(Long userId, Long audiobookId) {
        super("User with ID " + userId + " did not rate audiobook with ID " + audiobookId + ".");
    }
}
