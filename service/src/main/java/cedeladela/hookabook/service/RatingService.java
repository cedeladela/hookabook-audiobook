package cedeladela.hookabook.service;

import cedeladela.hookabook.entity.Rating;
import cedeladela.hookabook.repository.RatingRepository;
import exception.rating.RatingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<Rating> getAll() {
        return (List<Rating>) ratingRepository.findAll();
    }

    public Rating getById(Long id) {
        Optional<Rating> ratingOptional = ratingRepository.findById(id);
        if (ratingOptional.isPresent()) {
            return ratingOptional.get();
        } else {
            throw new RatingNotFoundException(id);
        }
    }

    public Rating create(Rating rating) {
        return ratingRepository.save(rating);
    }

    public Rating update(Rating rating) {
        Optional<Rating> existingRatingOptional = ratingRepository.findById(rating.getId());
        if (existingRatingOptional.isPresent()) {
            Rating existingRating = existingRatingOptional.get();

            existingRating.setUser(rating.getUser());
            existingRating.setAudiobook(rating.getAudiobook());
            existingRating.setRatingValue(rating.getRatingValue());

            return ratingRepository.save(existingRating);
        } else {
            throw new RatingNotFoundException(rating.getId());
        }
    }

    public void delete(Long id) {
        Optional<Rating> existingRatingOptional = ratingRepository.findById(id);
        if (existingRatingOptional.isPresent()) {
            ratingRepository.deleteById(id);
        } else {
            throw new RatingNotFoundException(id);
        }
    }
}
