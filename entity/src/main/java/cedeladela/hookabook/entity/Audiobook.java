package cedeladela.hookabook.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "audiobooks")
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Audiobook {

    @Id
    @GeneratedValue
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @Column(name = "release_date") // Correct the column name
    private Date releaseDate;

    @Column(name = "average_rating")
    private BigDecimal averageRating;

    @Column(name = "total_ratings_count")
    private Integer totalRatingsCount;

    @Column(name = "total_comments_count")
    private Integer totalCommentsCount;

    @ManyToMany
    @JoinTable(
            name = "audiobook_category",
            joinColumns = @JoinColumn(name = "audiobook_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();
}
