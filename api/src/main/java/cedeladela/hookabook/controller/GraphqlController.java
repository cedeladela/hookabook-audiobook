package cedeladela.hookabook.controller;
import cedeladela.hookabook.entity.Audiobook;
import cedeladela.hookabook.repository.AudiobookRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class GraphqlController {
    private final AudiobookRepository audiobookRepository;
    public GraphqlController(AudiobookRepository audiobookRepository) {
        this.audiobookRepository = audiobookRepository;
    }

    @QueryMapping
    public List<Audiobook> getTopRatedAudiobooks(@Argument int limit) {
        Iterable<Audiobook> iterable = audiobookRepository.findAll();
        List<Audiobook> audiobooks = new ArrayList<>();
        iterable.forEach(audiobooks::add);

        List<Audiobook> ratedAudiobooks = audiobooks.stream()
                .filter(audiobook -> audiobook.getAverageRating() != null)
                .sorted(Comparator.comparing(Audiobook::getAverageRating).reversed()) // Sort in descending order
                .limit(limit) // Limit to the top n audiobooks
                .collect(Collectors.toList());

        return ratedAudiobooks;
    }
}