package cedeladela.hookabook;

import cedeladela.hookabook.entity.*;
import cedeladela.hookabook.repository.AudiobookRepository;
import cedeladela.hookabook.repository.CategoryRepository;
import cedeladela.hookabook.repository.RatingRepository;
import cedeladela.hookabook.repository.HbUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {

    private final AudiobookRepository audiobookRepository;
    private final CategoryRepository categoryRepository;
//    private final CommentRepository commentRepository;
    private final RatingRepository ratingRepository;
    private final HbUserRepository hbUserRepository;


    @Autowired
    public DataLoader(AudiobookRepository audiobookRepository, CategoryRepository categoryRepository, RatingRepository ratingRepository, HbUserRepository hbUserRepository) {
        this.audiobookRepository = audiobookRepository;
        this.categoryRepository = categoryRepository;
//        this.commentRepository = commentRepository;
        this.ratingRepository = ratingRepository;
        this.hbUserRepository = hbUserRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        insertMockUserData();
        insertMockCategoryData();
        insertMockAudiobookData();
        insertMockRatings();
//        insertMockComments();

    }

    private void insertMockUserData() {
        List<HbUser> users = Arrays.asList(
                new HbUser(null, "test", "test", "test@hookabook.com", "John", "Doe", false),
                new HbUser(null, "john_doe", "password123", "john@hookabook.com", "John", "Doe", false),
                new HbUser(null, "jane_doe", "password456", "jane@hookabook.com", "Jane", "Doe", false),
                new HbUser(null, "bob_smith", "password789", "bob@hookabook.com", "Bob", "Smith", false)
        );

        // Save all users in a single call
        hbUserRepository.saveAll(users);
    }

    private void insertMockCategoryData() {
        Category fictionCategory = new Category(null, "Fiction");
        Category classicCategory = new Category(null, "Classic");
        Category dystopianCategory = new Category(null, "Dystopian");

        categoryRepository.saveAll(Arrays.asList(fictionCategory, classicCategory, dystopianCategory));
    }

    private void insertMockAudiobookData() {

        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);

        List<Audiobook> audiobooks = Arrays.asList(
                new Audiobook(null, "The Great Gatsby", "F. Scott Fitzgerald", "A timeless classic that delves into the opulent and mysterious world of Jay Gatsby. Set against the backdrop of the Roaring Twenties, Fitzgerald's masterpiece explores themes of love, wealth, and the American Dream. Gatsby's extravagant parties and his unrequited love for Daisy Buchanan unfold in prose that is both poetic and poignant.", null, null, Date.valueOf("1925-04-10"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(1)))),
                new Audiobook(null, "To Kill a Mockingbird", "Harper Lee", "A powerful exploration of racial injustice in the American South. Through the eyes of Scout Finch, the novel confronts issues of morality and compassion, as her father, lawyer Atticus Finch, defends an innocent Black man accused of rape. Lee's narrative is a timeless reminder of the enduring struggle for justice.", null, null, Date.valueOf("1960-07-11"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(0)))),
                new Audiobook(null, "1984", "George Orwell", "A chilling dystopian novel that explores the dangers of totalitarianism and the erosion of individual freedoms. Set in a world dominated by the Party and its leader Big Brother, the novel follows Winston Smith's rebellion against a society where truth is subjective and surveillance is omnipresent. Orwell's cautionary tale remains eerily relevant in today's world.", null, null, Date.valueOf("1949-06-08"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(2)))),
                new Audiobook(null, "The Catcher in the Rye", "J.D. Salinger", "A coming-of-age novel that captures the angst and alienation of adolescence. Through the eyes of Holden Caulfield, readers experience a journey through New York City as he grapples with the phoniness of the adult world. Salinger's exploration of identity and the loss of innocence continues to resonate with readers of all ages.", null, null, Date.valueOf("1951-07-16"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(0)))),
                new Audiobook(null, "The Great Gatsby", "F. Scott Fitzgerald", "A timeless classic that delves into the opulent and mysterious world of Jay Gatsby. Set against the backdrop of the Roaring Twenties, Fitzgerald's masterpiece explores themes of love, wealth, and the American Dream. Gatsby's extravagant parties and his unrequited love for Daisy Buchanan unfold in prose that is both poetic and poignant.", null, null, Date.valueOf("1925-04-10"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(1)))),
                new Audiobook(null, "To Kill a Mockingbird", "Harper Lee", "A powerful exploration of racial injustice in the American South. Through the eyes of Scout Finch, the novel confronts issues of morality and compassion, as her father, lawyer Atticus Finch, defends an innocent Black man accused of rape. Lee's narrative is a timeless reminder of the enduring struggle for justice.", null, null, Date.valueOf("1960-07-11"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(0)))),
                new Audiobook(null, "1984", "George Orwell", "A chilling dystopian novel that explores the dangers of totalitarianism and the erosion of individual freedoms. Set in a world dominated by the Party and its leader Big Brother, the novel follows Winston Smith's rebellion against a society where truth is subjective and surveillance is omnipresent. Orwell's cautionary tale remains eerily relevant in today's world.", null, null, Date.valueOf("1949-06-08"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(2)))),
                new Audiobook(null, "The Catcher in the Rye", "J.D. Salinger", "A coming-of-age novel that captures the angst and alienation of adolescence. Through the eyes of Holden Caulfield, readers experience a journey through New York City as he grapples with the phoniness of the adult world. Salinger's exploration of identity and the loss of innocence continues to resonate with readers of all ages.", null, null, Date.valueOf("1951-07-16"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(0)))),
                new Audiobook(null, "The Great Gatsby", "F. Scott Fitzgerald", "A timeless classic that delves into the opulent and mysterious world of Jay Gatsby. Set against the backdrop of the Roaring Twenties, Fitzgerald's masterpiece explores themes of love, wealth, and the American Dream. Gatsby's extravagant parties and his unrequited love for Daisy Buchanan unfold in prose that is both poetic and poignant.", null, null, Date.valueOf("1925-04-10"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(1)))),
                new Audiobook(null, "To Kill a Mockingbird", "Harper Lee", "A powerful exploration of racial injustice in the American South. Through the eyes of Scout Finch, the novel confronts issues of morality and compassion, as her father, lawyer Atticus Finch, defends an innocent Black man accused of rape. Lee's narrative is a timeless reminder of the enduring struggle for justice.", null, null, Date.valueOf("1960-07-11"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(0)))),
                new Audiobook(null, "1984", "George Orwell", "A chilling dystopian novel that explores the dangers of totalitarianism and the erosion of individual freedoms. Set in a world dominated by the Party and its leader Big Brother, the novel follows Winston Smith's rebellion against a society where truth is subjective and surveillance is omnipresent. Orwell's cautionary tale remains eerily relevant in today's world.", null, null, Date.valueOf("1949-06-08"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(2)))),
                new Audiobook(null, "The Catcher in the Rye", "J.D. Salinger", "A coming-of-age novel that captures the angst and alienation of adolescence. Through the eyes of Holden Caulfield, readers experience a journey through New York City as he grapples with the phoniness of the adult world. Salinger's exploration of identity and the loss of innocence continues to resonate with readers of all ages.", null, null, Date.valueOf("1951-07-16"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(0))))
        );


        // Save all audiobooks in a single call
        audiobookRepository.saveAll(audiobooks);
    }

    private void insertMockRatings() {


        List<HbUser> users = new ArrayList<>();
        hbUserRepository.findAll().forEach(users::add);

        List<Audiobook> audiobooks = new ArrayList<>();
        audiobookRepository.findAll().forEach(audiobooks::add);

        List<Rating> ratings = Arrays.asList(
                new Rating(null, users.get(0), audiobooks.get(1), 4),
                new Rating(null, users.get(1), audiobooks.get(1), 5),
                new Rating(null, users.get(2), audiobooks.get(1), 3),

                new Rating(null, users.get(0), audiobooks.get(2), 4),
                new Rating(null, users.get(1), audiobooks.get(2), 5),
                new Rating(null, users.get(2), audiobooks.get(2), 3),

                new Rating(null, users.get(0), audiobooks.get(3), 4),
                new Rating(null, users.get(1), audiobooks.get(3), 1),
                new Rating(null, users.get(2), audiobooks.get(3), 2),
                new Rating(null, users.get(0), audiobooks.get(3), 4),
                new Rating(null, users.get(1), audiobooks.get(3), 5),
                new Rating(null, users.get(2), audiobooks.get(3), 3)

        );

        ratingRepository.saveAll(ratings);
    }

//    private void insertMockComments() {
//        List<HbUser> users = new ArrayList<>();
//        hbUserRepository.findAll().forEach(users::add);
//
//        List<Audiobook> audiobooks = new ArrayList<>();
//        audiobookRepository.findAll().forEach(audiobooks::add);
//
//        List<Comment> comments = Arrays.asList(
//                new Comment(null, users.get(0), audiobooks.get(1), "Great audiobook!", new Timestamp(System.currentTimeMillis())),
//                new Comment(null, users.get(1), audiobooks.get(1), "I really enjoyed it.", new Timestamp(System.currentTimeMillis())),
//                new Comment(null, users.get(2), audiobooks.get(1), "Not bad.", new Timestamp(System.currentTimeMillis())),
//
//                new Comment(null, users.get(0), audiobooks.get(2), "One of the best!", new Timestamp(System.currentTimeMillis())),
//                new Comment(null, users.get(1), audiobooks.get(2), "Highly recommended.", new Timestamp(System.currentTimeMillis())),
//                new Comment(null, users.get(2), audiobooks.get(2), "Average.", new Timestamp(System.currentTimeMillis())),
//
//                new Comment(null, users.get(0), audiobooks.get(3), "Couldn't finish it.", new Timestamp(System.currentTimeMillis())),
//                new Comment(null, users.get(1), audiobooks.get(3), "Disappointing.", new Timestamp(System.currentTimeMillis())),
//                new Comment(null, users.get(2), audiobooks.get(3), "It was okay.", new Timestamp(System.currentTimeMillis())),
//                new Comment(null, users.get(0), audiobooks.get(3), "Loved it!", new Timestamp(System.currentTimeMillis())),
//                new Comment(null, users.get(1), audiobooks.get(3), "Must-read.", new Timestamp(System.currentTimeMillis())),
//                new Comment(null, users.get(2), audiobooks.get(3), "Interesting.", new Timestamp(System.currentTimeMillis()))
//        );
//
//        commentRepository.saveAll(comments);
//    }




}
