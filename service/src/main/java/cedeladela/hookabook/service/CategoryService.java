package cedeladela.hookabook.service;

import cedeladela.hookabook.entity.Category;
import cedeladela.hookabook.repository.CategoryRepository;
import exception.category.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return (List<Category>) categoryRepository.findAll();
    }

    public Category getById(Long id) {
        Optional<Category> existingCategoryOptional = categoryRepository.findById(id);
        if (existingCategoryOptional.isPresent()) {
            return existingCategoryOptional.get();
        } else {
            throw new CategoryNotFoundException(id);
        }
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Category category) {
        Optional<Category> existingCategoryOptional = categoryRepository.findById(category.getId());
        if (existingCategoryOptional.isPresent()) {
            Category existingCategory = existingCategoryOptional.get();

            existingCategory.setName(category.getName());

            return categoryRepository.save(existingCategory);
        } else {
            throw new CategoryNotFoundException(category.getId());
        }
    }

    public void delete(Long id) {
        Optional<Category> existingCategoryOptional = categoryRepository.findById(id);
        if (existingCategoryOptional.isPresent()) {
            categoryRepository.deleteById(id);
        } else {
            throw new CategoryNotFoundException(id);
        }
    }
}
