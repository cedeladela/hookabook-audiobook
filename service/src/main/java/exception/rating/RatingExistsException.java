package exception.rating;

import cedeladela.hookabook.entity.Rating;

public class RatingExistsException extends RuntimeException {

    public RatingExistsException(Rating rating) {
        super("User with ID " + rating.getHbUser().getId() + " already rated audiobook with ID " + rating.getAudiobook().getId() + "");
    }
}
