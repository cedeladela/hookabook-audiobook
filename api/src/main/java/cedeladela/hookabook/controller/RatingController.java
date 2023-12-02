package cedeladela.hookabook.controller;

import cedeladela.hookabook.entity.Rating;
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
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find Rating by ID", description = "Returns a Rating as per the ID.")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ratingService.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    @Operation(summary = "Get All Ratings", description = "Returns a list of all Ratings.")
    public ResponseEntity<Object> getAll() {
        try {
            List<Rating> ratings = ratingService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(ratings);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Create Rating", description = "Creates a new Rating.")
    public ResponseEntity<Object> create(@RequestBody Rating rating) {
        try {
            Rating createdRating = ratingService.create(rating);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Rating", description = "Updates an existing Rating.")
    public ResponseEntity<Object> update(@RequestBody Rating rating) {
        try {
            Rating updatedRating = ratingService.update(rating);
            return ResponseEntity.status(HttpStatus.OK).body(updatedRating);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Rating", description = "Deletes a Rating by ID.")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            ratingService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Rating with ID " + id + " deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
