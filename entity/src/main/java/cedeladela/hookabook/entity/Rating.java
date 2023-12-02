package cedeladela.hookabook.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Rating {
    @Id
    @GeneratedValue
    @Column(name="id", nullable = false)
    private Long id;

    @ManyToOne // Many ratings belong to one user
    @JoinColumn(name = "user_id")
    private HbUser user;

    @ManyToOne // Many ratings belong to one audiobook
    @JoinColumn(name = "audiobook_id")
    private Audiobook audiobook;

    @Column(name = "rating_value")
    private Integer ratingValue;
}
