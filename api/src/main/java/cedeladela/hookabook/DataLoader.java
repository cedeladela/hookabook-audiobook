package cedeladela.hookabook;

import cedeladela.hookabook.entity.*;
import cedeladela.hookabook.repository.AudiobookRepository;
import cedeladela.hookabook.repository.CategoryRepository;
import cedeladela.hookabook.repository.RatingRepository;
import cedeladela.hookabook.repository.HbUserRepository;
import cedeladela.hookabook.service.AudiobookService;
import io.minio.errors.MinioException;
import jakarta.transaction.Transactional;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.*;

@Component
public class DataLoader implements CommandLineRunner {

    private final AudiobookRepository audiobookRepository;
    private final CategoryRepository categoryRepository;
//    private final CommentRepository commentRepository;
    private final RatingRepository ratingRepository;
    private final HbUserRepository hbUserRepository;

    private final AudiobookService audiobookService;





    @Autowired
    public DataLoader(AudiobookRepository audiobookRepository, CategoryRepository categoryRepository, RatingRepository ratingRepository, HbUserRepository hbUserRepository, AudiobookService audiobookService) {
        this.audiobookRepository = audiobookRepository;
        this.categoryRepository = categoryRepository;
//        this.commentRepository = commentRepository;
        this.ratingRepository = ratingRepository;
        this.hbUserRepository = hbUserRepository;
        this.audiobookService = audiobookService;
    }

    @Override
    @Transactional
    public void run(String... args) {

        insertMockUserData();
        insertMockCategoryData();
        insertMockAudiobookData();
        addCoverImages();
//        insertMockRatings();
//        insertMockComments();

    }

    private void insertMockUserData() {
        List<HbUser> users = Arrays.asList(
                new HbUser(null, "admin", "admin", "admin@hookabook.com", "Admin", "Admin", true, true),
                new HbUser(null, "bookGuru", "password123", "john@hookabook.com", "John", "Doe", false, false),
                new HbUser(null, "janez", "janez", "jane@hookabook.com", "Janez", "Novak", false, false),
                new HbUser(null, "bob_smith", "password789", "bob@hookabook.com", "Bob", "Smith", false, false)
        );

        // Save all users in a single call
        hbUserRepository.saveAll(users);
    }

    private void insertMockCategoryData() {
        categoryRepository.saveAll(Arrays.asList(
                new Category(null, "Fiction"),
                new Category(null, "Classic"),
                new Category(null, "Dystopian"),
                new Category(null, "Crime"),
                new Category(null, "Fable"),
                new Category(null, "Humor"),
                new Category(null, "Action"),
                new Category(null, "History"),
                new Category(null, "Fantasy"),
                new Category(null, "Mystery"),
                new Category(null, "Romance"),
                new Category(null, "Horror"),
                new Category(null, "Science fiction"),
                new Category(null, "Adventure"),
                new Category(null, "Thriller"),
                new Category(null, "Biography"),
                new Category(null, "Historical Fiction")
        ));
    }

    private void insertMockAudiobookData() {

        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll().forEach(categories::add);

        List<Audiobook> audiobooks = Arrays.asList(
                new Audiobook(null, "The Great Gatsby", "F. Scott Fitzgerald", "A timeless classic that delves into the opulent and mysterious world of Jay Gatsby. Set against the backdrop of the Roaring Twenties, Fitzgerald's masterpiece explores themes of love, wealth, and the American Dream. Gatsby's extravagant parties and his unrequited love for Daisy Buchanan unfold in prose that is both poetic and poignant.", null, null, Date.valueOf("1925-04-10"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(1)))),
                new Audiobook(null, "To Kill a Mockingbird", "Harper Lee", "A powerful exploration of racial injustice in the American South. Through the eyes of Scout Finch, the novel confronts issues of morality and compassion, as her father, lawyer Atticus Finch, defends an innocent Black man accused of rape. Lee's narrative is a timeless reminder of the enduring struggle for justice.", null, null, Date.valueOf("1960-07-11"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(0)))),
                new Audiobook(null, "1984", "George Orwell", "A chilling dystopian novel that explores the dangers of totalitarianism and the erosion of individual freedoms. Set in a world dominated by the Party and its leader Big Brother, the novel follows Winston Smith's rebellion against a society where truth is subjective and surveillance is omnipresent. Orwell's cautionary tale remains eerily relevant in today's world.", null, null, Date.valueOf("1949-06-08"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(2)))),
                new Audiobook(null, "The Catcher in the Rye", "J.D. Salinger", "A coming-of-age novel that captures the angst and alienation of adolescence. Through the eyes of Holden Caulfield, readers experience a journey through New York City as he grapples with the phoniness of the adult world. Salinger's exploration of identity and the loss of innocence continues to resonate with readers of all ages.", null, null, Date.valueOf("1951-07-16"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(0)))),
                new Audiobook(null, "Good Girls Don’t Die", "Christina Henry", " A sharp-edged, supremely twisty thriller about three women who find themselves trapped inside stories they know aren’t their own, from the author of Alice and Near the Bone. Celia wakes up in a house that’s supposed to be hers. There’s a little girl who claims to be her daughter and a man who claims to be her husband, but Celia knows this family—and this life—is not hers... Allie is supposed to be on a fun weekend trip—but then her friend’s boyfriend unexpectedly invites the group to a remote cabin in the woods. No one else believes Allie, but she is sure that something about this trip is very, very wrong… Maggie just wants to be home with her daughter, but she’s in a dangerous situation and she doesn’t know who put her there or why. She’ll have to fight with everything she has to survive… Three women. Three stories. Only one way out. This captivating novel will keep readers guessing until the very end..", null, null, Date.valueOf("2023-11-14"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(1)))),
                new Audiobook(null, "The Scarlet Letter", "Nathaniel Hawthorne", "Set in 17th-century Puritan Massachusetts, 'The Scarlet Letter' explores the consequences of adultery through the life of Hester Prynne. The scarlet letter 'A' becomes a symbol of her sin, as secrets and guilt unravel in this classic tale.", null, null, Date.valueOf("1850-03-16"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(1)))),
                new Audiobook(null, "The Maltese Falcon", "Dashiell Hammett", "Sam Spade, a private detective, becomes entangled in a web of crime and deceit when a mysterious woman asks for his help in finding a valuable statuette. 'The Maltese Falcon' is a classic crime novel that set the standard for noir fiction.", null, null, Date.valueOf("1930-02-14"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(3)))),
                new Audiobook(null, "Aesop's Fables", "Aesop", "A timeless collection of fables, attributed to the ancient Greek storyteller Aesop. These short tales feature animals with human-like qualities and convey moral lessons. 'Aesop's Fables' has been cherished for centuries for its wisdom and simplicity.", null, null, Date.valueOf("0001-11-11"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(4)))),
                new Audiobook(null, "Pride and Prejudice", "Jane Austen", "Jane Austen's classic 'Pride and Prejudice' explores the complex dynamics of love and social class in 19th-century England. The headstrong Elizabeth Bennet navigates societal expectations and misconceptions on her journey to find true love.", null, null, Date.valueOf("1813-01-28"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(1)))),
                new Audiobook(null, "Brave New World", "Aldous Huxley", "A dystopian classic, 'Brave New World' envisions a future where society is controlled through genetic engineering and psychological conditioning. Huxley explores themes of individuality, freedom, and the consequences of a utopian vision gone wrong.", null, null, Date.valueOf("1932-06-14"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(2)))),
                new Audiobook(null, "The Godfather", "Mario Puzo", "Mario Puzo's 'The Godfather' is a crime novel that delves into the powerful and ruthless world of the Mafia. The story follows the Corleone family and their patriarch, Vito Corleone, as they navigate loyalty, power, and the consequences of a life in organized crime.", null, null, Date.valueOf("1969-03-10"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(3)))),
                new Audiobook(null, "The Hitchhiker's Guide to the Galaxy", "Douglas Adams", "A humorous science fiction classic, 'The Hitchhiker's Guide to the Galaxy' follows the misadventures of Arthur Dent as he is unexpectedly swept off Earth just before its destruction. The novel explores absurdity, philosophy, and the unpredictability of the universe.", null, null, Date.valueOf("1979-10-12"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(12)))),
                new Audiobook(null, "The Count of Monte Cristo", "Alexandre Dumas", "Set in early 19th-century France, 'The Count of Monte Cristo' is a classic adventure novel that follows Edmond Dantès as he seeks revenge against those who wronged him. The novel explores themes of justice, betrayal, and the consequences of unchecked power.", null, null, Date.valueOf("1844-08-28"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(7)))),
                new Audiobook(null, "Great Expectations", "Charles Dickens", "Charles Dickens' 'Great Expectations' is a classic novel that follows the life of an orphan named Pip. The story explores themes of social class, identity, and the consequences of unattainable expectations. Dickens weaves a tale of self-discovery and the complexities of human relationships.", null, null, Date.valueOf("1861-08-30"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(1))))
                );

//        new Audiobook(null, "The Great Gatsby", "F. Scott Fitzgerald", "A timeless classic that delves into the opulent and mysterious world of Jay Gatsby. Set against the backdrop of the Roaring Twenties, Fitzgerald's masterpiece explores themes of love, wealth, and the American Dream. Gatsby's extravagant parties and his unrequited love for Daisy Buchanan unfold in prose that is both poetic and poignant.", null, null, Date.valueOf("1925-04-10"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(1)))),
//                new Audiobook(null, "To Kill a Mockingbird", "Harper Lee", "A powerful exploration of racial injustice in the American South. Through the eyes of Scout Finch, the novel confronts issues of morality and compassion, as her father, lawyer Atticus Finch, defends an innocent Black man accused of rape. Lee's narrative is a timeless reminder of the enduring struggle for justice.", null, null, Date.valueOf("1960-07-11"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(0)))),
//                new Audiobook(null, "1984", "George Orwell", "A chilling dystopian novel that explores the dangers of totalitarianism and the erosion of individual freedoms. Set in a world dominated by the Party and its leader Big Brother, the novel follows Winston Smith's rebellion against a society where truth is subjective and surveillance is omnipresent. Orwell's cautionary tale remains eerily relevant in today's world.", null, null, Date.valueOf("1949-06-08"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(2)))),
//                new Audiobook(null, "The Catcher in the Rye", "J.D. Salinger", "A coming-of-age novel that captures the angst and alienation of adolescence. Through the eyes of Holden Caulfield, readers experience a journey through New York City as he grapples with the phoniness of the adult world. Salinger's exploration of identity and the loss of innocence continues to resonate with readers of all ages.", null, null, Date.valueOf("1951-07-16"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(0)))),
//                new Audiobook(null, " Good Girls Don’t Die", "Christina Henry", " A sharp-edged, supremely twisty thriller about three women who find themselves trapped inside stories they know aren’t their own, from the author of Alice and Near the Bone. Celia wakes up in a house that’s supposed to be hers. There’s a little girl who claims to be her daughter and a man who claims to be her husband, but Celia knows this family—and this life—is not hers... Allie is supposed to be on a fun weekend trip—but then her friend’s boyfriend unexpectedly invites the group to a remote cabin in the woods. No one else believes Allie, but she is sure that something about this trip is very, very wrong… Maggie just wants to be home with her daughter, but she’s in a dangerous situation and she doesn’t know who put her there or why. She’ll have to fight with everything she has to survive… Three women. Three stories. Only one way out. This captivating novel will keep readers guessing until the very end..", null, null, Date.valueOf("2023-11-14"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(1)))),
//                new Audiobook(null, "The Scarlet Letter", "Nathaniel Hawthorne", "Set in 17th-century Puritan Massachusetts, 'The Scarlet Letter' explores the consequences of adultery through the life of Hester Prynne. The scarlet letter 'A' becomes a symbol of her sin, as secrets and guilt unravel in this classic tale.", null, null, Date.valueOf("1850-03-16"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(1)))),
//                new Audiobook(null, "The Maltese Falcon", "Dashiell Hammett", "Sam Spade, a private detective, becomes entangled in a web of crime and deceit when a mysterious woman asks for his help in finding a valuable statuette. 'The Maltese Falcon' is a classic crime novel that set the standard for noir fiction.", null, null, Date.valueOf("1930-02-14"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(3)))),
//                new Audiobook(null, "Aesop's Fables", "Aesop", "A timeless collection of fables, attributed to the ancient Greek storyteller Aesop. These short tales feature animals with human-like qualities and convey moral lessons. 'Aesop's Fables' has been cherished for centuries for its wisdom and simplicity.", null, null, Date.valueOf("620 BCE"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(4)))),
//                new Audiobook(null, "Pride and Prejudice", "Jane Austen", "Jane Austen's classic 'Pride and Prejudice' explores the complex dynamics of love and social class in 19th-century England. The headstrong Elizabeth Bennet navigates societal expectations and misconceptions on her journey to find true love.", null, null, Date.valueOf("1813-01-28"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(1)))),
//                new Audiobook(null, "Brave New World", "Aldous Huxley", "A dystopian classic, 'Brave New World' envisions a future where society is controlled through genetic engineering and psychological conditioning. Huxley explores themes of individuality, freedom, and the consequences of a utopian vision gone wrong.", null, null, Date.valueOf("1932-06-14"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(2)))),
//                new Audiobook(null, "The Godfather", "Mario Puzo", "Mario Puzo's 'The Godfather' is a crime novel that delves into the powerful and ruthless world of the Mafia. The story follows the Corleone family and their patriarch, Vito Corleone, as they navigate loyalty, power, and the consequences of a life in organized crime.", null, null, Date.valueOf("1969-03-10"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(3)))),
//                new Audiobook(null, "The Hitchhiker's Guide to the Galaxy", "Douglas Adams", "A humorous science fiction classic, 'The Hitchhiker's Guide to the Galaxy' follows the misadventures of Arthur Dent as he is unexpectedly swept off Earth just before its destruction. The novel explores absurdity, philosophy, and the unpredictability of the universe.", null, null, Date.valueOf("1979-10-12"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(12)))),
//                new Audiobook(null, "The Count of Monte Cristo", "Alexandre Dumas", "Set in early 19th-century France, 'The Count of Monte Cristo' is a classic adventure novel that follows Edmond Dantès as he seeks revenge against those who wronged him. The novel explores themes of justice, betrayal, and the consequences of unchecked power.", null, null, Date.valueOf("1844-08-28"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(7)))),
//                new Audiobook(null, "Great Expectations", "Charles Dickens", "Charles Dickens' 'Great Expectations' is a classic novel that follows the life of an orphan named Pip. The story explores themes of social class, identity, and the consequences of unattainable expectations. Dickens weaves a tale of self-discovery and the complexities of human relationships.", null, null, Date.valueOf("1861-08-30"), null, 0, 0, new HashSet<>(Collections.singletonList(categories.get(1)))),
//                );


        // Save all audiobooks in a single call
        audiobookRepository.saveAll(audiobooks);
    }

    private void addCoverImages() {
        List<Audiobook> audiobooks = new ArrayList<>();
        audiobookRepository.findAll().forEach(audiobooks::add);

        for (Audiobook audiobook : audiobooks) {
            try {
                // upload cover image if file exists
                if (Files.exists(new File("api/src/main/resources/cover_images/" + audiobook.getId() + " " + audiobook.getTitle() + ".jpg").toPath()))
                    uploadCoverImage(audiobook);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (MinioException | NoSuchAlgorithmException | InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void uploadCoverImage(Audiobook audiobook) throws IOException, MinioException, NoSuchAlgorithmException, InvalidKeyException {
        // Build the upload URL
        String uploadUrl = "http://localhost:8081/api/audiobook/upload-audiobook/" + audiobook.getId();
        File coverImageFile = new File("api/src/main/resources/cover_images/" + audiobook.getId() + " " + audiobook.getTitle() + ".jpg");
        MultipartFile multipartFile = new MockMultipartFile(audiobook.getId() + ".jpg", new FileInputStream(coverImageFile));

        audiobookService.uploadCoverImage(audiobook.getId(), multipartFile);

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
