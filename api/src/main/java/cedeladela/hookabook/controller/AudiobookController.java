package cedeladela.hookabook.controller;

import cedeladela.hookabook.entity.Audiobook;
import cedeladela.hookabook.service.AudiobookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/audiobook")
@Tag(name = "Audiobook")
public class AudiobookController {

    private final AudiobookService audiobookService;

    @Autowired
    public AudiobookController(AudiobookService audiobookService) {
        this.audiobookService = audiobookService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find Audiobook by ID", description = "Returns an audiobook as per the ID.")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(audiobookService.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    @Operation(summary = "Get All Audiobooks", description = "Returns a list of all audiobooks.")
    public ResponseEntity<Object> getAll() {
        try {
            List<Audiobook> audiobooks = audiobookService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(audiobooks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping()
    @Operation(summary = "Create Audiobook", description = "Creates a new audiobook.")
    public ResponseEntity<Object> create(@RequestPart Audiobook audiobook) {
        try {
            Audiobook createdAudiobook = audiobookService.save(audiobook);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAudiobook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping( path = "/upload-audiobook/{audiobookId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload Audiobook", description = "Uploads an audiobook file.")
    public ResponseEntity<Object> uploadAudiobook(@PathVariable Long audiobookId, @RequestPart("audiobookFile") MultipartFile audiobookFile) {
        try {
            Audiobook audiobook = audiobookService.uploadAudiobook(audiobookId, audiobookFile);
            return ResponseEntity.status(HttpStatus.OK).body(audiobook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping( path = "/upload-coverimage/{audiobookId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload cover image", description = "Uploads audiobook cover image.")
    public ResponseEntity<Object> uploadCoverImage(@PathVariable Long audiobookId, @RequestPart("coverImage") MultipartFile coverImage) {
        try {
            Audiobook audiobook = audiobookService.uploadCoverImage(audiobookId, coverImage);
            return ResponseEntity.status(HttpStatus.OK).body(audiobook);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Audiobook", description = "Updates an existing audiobook.")
    public ResponseEntity<Object> update(@RequestBody Audiobook audiobook) {
        try {
            Audiobook updatedAudiobook = audiobookService.update(audiobook);
            return ResponseEntity.status(HttpStatus.OK).body(updatedAudiobook);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Audiobook", description = "Deletes an audiobook by ID.")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            audiobookService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Audiobook with ID " + id + " deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

}
