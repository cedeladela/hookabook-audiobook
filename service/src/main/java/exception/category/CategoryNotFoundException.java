package exception.category;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long categoryId) {
        super("Category with ID " + categoryId + " not found");
    }
}
