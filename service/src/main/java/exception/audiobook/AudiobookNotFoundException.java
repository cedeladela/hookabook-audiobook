package exception.audiobook;

public class AudiobookNotFoundException extends RuntimeException {

    public AudiobookNotFoundException(Long audiobookId) {
        super("Audiobook with ID " + audiobookId + " not found");
    }
}
