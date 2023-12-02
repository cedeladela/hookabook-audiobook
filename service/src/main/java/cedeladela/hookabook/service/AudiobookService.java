package cedeladela.hookabook.service;

import cedeladela.hookabook.entity.Audiobook;
import cedeladela.hookabook.repository.AudiobookRepository;
import exception.audiobook.AudiobookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AudiobookService {

    private final AudiobookRepository audiobookRepository;

    @Autowired
    public AudiobookService(AudiobookRepository audiobookRepository) {
        this.audiobookRepository = audiobookRepository;
    }

    public List<Audiobook> getAll() {
        return (List<Audiobook>) audiobookRepository.findAll();
    }

    public Audiobook getById(Long id) {
        Optional<Audiobook> existingAudiobookOptional = audiobookRepository.findById(id);
        if (existingAudiobookOptional.isPresent()) {
            return existingAudiobookOptional.get();
        } else {
            throw new AudiobookNotFoundException(id);
        }
    }

    public Audiobook save(Audiobook audiobook) {
        return audiobookRepository.save(audiobook);
    }

    public Audiobook update(Audiobook audiobook) {
        Optional<Audiobook> existingAudiobookOptional = audiobookRepository.findById(audiobook.getId());
        if (existingAudiobookOptional.isPresent()) {
            Audiobook existingAudiobook = existingAudiobookOptional.get();

            existingAudiobook.setTitle(audiobook.getTitle());
            existingAudiobook.setAuthor(audiobook.getAuthor());
            existingAudiobook.setDescription(audiobook.getDescription());
            existingAudiobook.setFileUrl(audiobook.getFileUrl());
            existingAudiobook.setCoverImageUrl(audiobook.getCoverImageUrl());
            existingAudiobook.setReleaseDate(audiobook.getReleaseDate());
            existingAudiobook.setAverageRating(audiobook.getAverageRating());
            existingAudiobook.setTotalRatingsCount(audiobook.getTotalRatingsCount());
            existingAudiobook.setTotalCommentsCount(audiobook.getTotalCommentsCount());

            //TODO: comments, ratings, categories

            return audiobookRepository.save(existingAudiobook);
        } else {
            throw new AudiobookNotFoundException(audiobook.getId());
        }
    }

    public void delete(Long id) {
        Optional<Audiobook> existingAudiobookOptional = audiobookRepository.findById(id);
        if (existingAudiobookOptional.isPresent()) {
            audiobookRepository.deleteById(id);
        } else {
            throw new AudiobookNotFoundException(id);
        }
    }
}
