package cedeladela.hookabook.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/googlebooks")
public class GoogleBookApiController {

    private final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q=inauthor:{author}&key=AIzaSyCOSV_i2mWOHGogOgB8fAn0pjP9b1c7mi4";

    @GetMapping("/{author}")
    public List<String> getBooksByAuthor(@PathVariable String author) {
        String apiUrl = GOOGLE_BOOKS_API_URL.replace("{author}", author);

        // Make a request to the Google Books API
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(apiUrl, String.class);

        // Parse the result using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        Set<String> uniqueTitles = new HashSet<>();

        try {
            JsonNode rootNode = objectMapper.readTree(result);
            JsonNode itemsNode = rootNode.path("items");

            for (JsonNode item : itemsNode) {
                JsonNode volumeInfoNode = item.path("volumeInfo");
                String title = volumeInfoNode.path("title").asText();
                uniqueTitles.add(title);
            }
        } catch (Exception e) {
            // Handle parsing exception
            e.printStackTrace();
        }

        // Convert set to list if needed
        return new ArrayList<>(uniqueTitles);
    }
}
