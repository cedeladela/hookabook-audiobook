package cedeladela.hookabook.controller;

import cedeladela.hookabook.entity.Category;
import cedeladela.hookabook.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@Tag(name = "Category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find Category by ID", description = "Returns a category as per the ID.")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/get-all")
    @Operation(summary = "Get All Categories", description = "Returns a list of all categories.")
    public ResponseEntity<Object> getAll() {
        try {
            List<Category> categories = categoryService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(categories);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping
    @Operation(summary = "Create Category", description = "Creates a new category.")
    public ResponseEntity<Object> create(@RequestBody Category category) {
        try {
            Category createdCategory = categoryService.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Category", description = "Updates an existing category.")
    public ResponseEntity<Object> update(@RequestBody Category category) {
        try {
            Category updatedCategory = categoryService.update(category);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Category", description = "Deletes a category by ID.")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            categoryService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Category with ID " + id + " deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
