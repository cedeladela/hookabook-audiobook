package cedeladela.hookabook.repository;


import cedeladela.hookabook.entity.Audiobook;
import cedeladela.hookabook.entity.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {

    Optional<Rating> findByHbUserIdAndAudiobookId(Long hbUserId, Long audiobookId);

    List<Rating> findAllByAudiobookId(Long audiobookId);
}
