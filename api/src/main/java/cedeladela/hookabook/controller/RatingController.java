package cedeladela.hookabook.controller;

import cedeladela.hookabook.entity.Rating;
import cedeladela.hookabook.metrics.RatingCounterMetrics;
import cedeladela.hookabook.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
@Tag(name = "Rating")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RatingController {

    private final RatingService ratingService;
    private final RatingCounterMetrics ratingCounterMetrics;

    @Autowired
    public RatingController(RatingService ratingService, RatingCounterMetrics ratingCounterMetrics) {
        this.ratingService = ratingService;
        this.ratingCounterMetrics = ratingCounterMetrics;
    }

    @GetMapping("/{hbUserId}/{audiobookId}")
    @Operation(summary = "Find rating by user and audiobook.", description = "Returns a rating as per the user and audiobook.")
    public ResponseEntity<Object> findByUserAndAudiobook(@PathVariable Long hbUserId, @PathVariable Long audiobookId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ratingService.findByUserIdAndAudiobookId(hbUserId, audiobookId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Add rating by user and audiobook.", description = "Adds a new user rating for audiobook. The rating must be between 1 and 5.")
    public ResponseEntity<Object> addRating(@RequestBody Rating rating) {
        try {
            // Increment the request count metric
            ratingCounterMetrics.incrementRequestCount();

            Rating createdRating = ratingService.addRating(rating);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    @PutMapping
    @Operation(summary = "Update rating by user and audiobook.", description = "Updates an existing Rating.")
    public ResponseEntity<Object> updateRating(@RequestBody Rating rating) {
        try {
            Rating updatedRating = ratingService.updateRating(rating);
            return ResponseEntity.status(HttpStatus.OK).body(updatedRating);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/{hbUserId}/{audiobookId}")
    @Operation(summary = "Delete rating by user and audiobook.", description = "Deletes a Rating by user and audiobook.")
    public ResponseEntity<Object> delete(@PathVariable Long hbUserId, @PathVariable Long audiobookId) {
        try {
            ratingService.deleteRating(hbUserId, audiobookId);
            return ResponseEntity.status(HttpStatus.OK).body("Rating of user " + hbUserId + " for audiobook " + audiobookId + " deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
