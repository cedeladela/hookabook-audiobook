package cedeladela.hookabook.controller;

import cedeladela.hookabook.entity.Comment;
import cedeladela.hookabook.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@Tag(name = "Comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find Comment by ID", description = "Returns a comment as per the ID.")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commentService.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    @Operation(summary = "Get All Comments", description = "Returns a list of all comments.")
    public ResponseEntity<Object> getAll() {
        try {
            List<Comment> comments = commentService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(comments);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Create Comment", description = "Creates a new comment.")
    public ResponseEntity<Object> create(@RequestBody Comment comment) {
        try {
            Comment createdComment = commentService.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Comment", description = "Updates an existing comment.")
    public ResponseEntity<Object> update(@RequestBody Comment comment) {
        try {
            Comment updatedComment = commentService.update(comment);
            return ResponseEntity.status(HttpStatus.OK).body(updatedComment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Comment", description = "Deletes a comment by ID.")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            commentService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Comment with ID " + id + " deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
