package cedeladela.hookabook.service;

import cedeladela.hookabook.entity.Audiobook;
import cedeladela.hookabook.entity.Rating;
import cedeladela.hookabook.repository.AudiobookRepository;
import cedeladela.hookabook.service.minio.MinioService;
import exception.audiobook.AudiobookNotFoundException;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class AudiobookService {

    private final AudiobookRepository audiobookRepository;

    private final MinioService minioService;

    @Autowired
    public AudiobookService(AudiobookRepository audiobookRepository, MinioService minioService) {
        this.audiobookRepository = audiobookRepository;
        this.minioService = minioService;
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

//    public Audiobook save(Audiobook audiobook, MultipartFile audiobookFile, MultipartFile coverImageFile) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
//
////        //generate random filename for audiobook file
////        String generatedFilename = java.util.UUID.randomUUID().toString();
////
////        minioService.uploadAudioBookZip(audiobookFile, generatedFilename);
////        minioService.uploadCoverImage(coverImageFile, generatedFilename);
////
////        audiobook.setFileUrl(minioService.getAudioBookZipUrl(generatedFilename));
////        audiobook.setCoverImageUrl(minioService.getCoverImageUrl(generatedFilename));
//
//        return audiobookRepository.save(audiobook);
//    }

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

    public void updateAverageRating(Long audiobookId, List<Rating> ratings) {
        Optional<Audiobook> existingAudiobookOptional = audiobookRepository.findById(audiobookId);

        if (existingAudiobookOptional.isPresent()) {
            Audiobook existingAudiobook = existingAudiobookOptional.get();

            if(ratings.isEmpty()) {
                existingAudiobook.setAverageRating(BigDecimal.valueOf(0.0));
                existingAudiobook.setTotalRatingsCount(0);
                audiobookRepository.save(existingAudiobook);
                return;
            }

            int totalRatingsCount = ratings.size();
            int totalRating = 0;

            for (Rating rating : ratings) {
                totalRating += rating.getRatingValue();
            }

            double averageRating = (double) totalRating / totalRatingsCount;

            existingAudiobook.setAverageRating(BigDecimal.valueOf(averageRating));
            existingAudiobook.setTotalRatingsCount(totalRatingsCount);

            audiobookRepository.save(existingAudiobook);
        } else {
            throw new AudiobookNotFoundException(audiobookId);
        }
    }

    public Audiobook uploadAudiobook(Long audiobookId, MultipartFile audiobookFile) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        //generate random filename for audiobook file
        String generatedFilename = java.util.UUID.randomUUID() + ".zip";
        minioService.uploadAudioBookZip(audiobookFile, generatedFilename);

        Audiobook audiobook = getById(audiobookId);
        audiobook.setFileUrl(minioService.getAudioBookZipUrl(generatedFilename));
        return audiobookRepository.save(audiobook);
    }

    public Audiobook uploadCoverImage(Long audiobookId, MultipartFile coverImageFile) throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        //generate random filename for audiobook file
        String generatedFilename = java.util.UUID.randomUUID() + ".jpg";
        minioService.uploadCoverImage(coverImageFile, generatedFilename);

        Audiobook audiobook = getById(audiobookId);
        audiobook.setFileUrl(minioService.getAudioBookZipUrl(generatedFilename));
        audiobook.setCoverImageUrl(minioService.getCoverImageUrl(generatedFilename));
        return audiobookRepository.save(audiobook);
    }
}
