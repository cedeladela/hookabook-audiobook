package cedeladela.hookabook.service;

import cedeladela.hookabook.entity.Audiobook;
import cedeladela.hookabook.entity.HbUser;
import cedeladela.hookabook.entity.Rating;
import cedeladela.hookabook.repository.AudiobookRepository;
import cedeladela.hookabook.repository.HbUserRepository;
import cedeladela.hookabook.repository.RatingRepository;
import exception.audiobook.AudiobookNotFoundException;
import exception.hbuser.HbUserNotFoundException;
import exception.rating.RatingExistsException;
import exception.rating.RatingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final HbUserRepository hbUserRepository;
    private final AudiobookRepository audiobookRepository;

    private final AudiobookService audiobookService;

    @Autowired
    public RatingService(RatingRepository ratingRepository, HbUserRepository hbUserRepository, AudiobookRepository audiobookRepository, AudiobookService audiobookService) {
        this.ratingRepository = ratingRepository;
        this.hbUserRepository = hbUserRepository;
        this.audiobookRepository = audiobookRepository;
        this.audiobookService = audiobookService;
    }

    public Rating findByUserIdAndAudiobookId(Long hbUserId, Long audiobookId) {
        return ratingRepository.findByHbUserIdAndAudiobookId(hbUserId, audiobookId)
                .orElseThrow(() -> new RatingNotFoundException(hbUserId, audiobookId));
    }
    public Rating addRating(Rating rating) {

        Optional<Rating> existingRatingOptional = ratingRepository.findByHbUserIdAndAudiobookId(rating.getHbUser().getId(), rating.getAudiobook().getId());
        if (existingRatingOptional.isPresent()) {
            throw new RatingExistsException(existingRatingOptional.get());
        }

        HbUser hbUser = hbUserRepository.findById(rating.getHbUser().getId())
                .orElseThrow(() -> new HbUserNotFoundException(rating.getHbUser().getId()));

        Audiobook audiobook = audiobookRepository.findById(rating.getAudiobook().getId())
                .orElseThrow(() -> new AudiobookNotFoundException(rating.getAudiobook().getId()));

        rating.setHbUser(hbUser);
        rating.setAudiobook(audiobook);

        Rating savedRating = ratingRepository.save(rating);

        audiobookService.updateAverageRating(savedRating.getAudiobook().getId(), getAllRatingsForAudiobook(savedRating.getAudiobook().getId()));

        return savedRating;
    }

    public Rating updateRating(Rating rating) {
        ratingRepository.findByHbUserIdAndAudiobookId(rating.getHbUser().getId(), rating.getAudiobook().getId())
                .orElseThrow(() -> new RatingNotFoundException(rating));

        Rating savedRating = ratingRepository.save(rating);

        audiobookService.updateAverageRating(savedRating.getAudiobook().getId(), getAllRatingsForAudiobook(savedRating.getAudiobook().getId()));

        return savedRating;
    }

    public void deleteRating(Long hbUserId, Long audiobookId) {

        Rating existingRating = ratingRepository.findByHbUserIdAndAudiobookId(hbUserId, audiobookId)
                .orElseThrow(() -> new RatingNotFoundException(hbUserId, audiobookId));

        ratingRepository.delete(existingRating);

        audiobookService.updateAverageRating(audiobookId, getAllRatingsForAudiobook(audiobookId));
    }

    public List<Rating> getAllRatingsForAudiobook(Long audiobookId) {
        return ratingRepository.findAllByAudiobookId(audiobookId);
    }
}
