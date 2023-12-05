//package cedeladela.hookabook.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.sql.Timestamp;
//
//@Entity
//@Table
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//@EqualsAndHashCode
//public class Comment {
//    @Id
//    @GeneratedValue
//    @Column(name="id", nullable = false)
//    private Long id;
//
//    @ManyToOne // Many comments belong to one user
//    @JoinColumn(name = "user_id")
//    private HbUser user;
//
//    @ManyToOne // Many comments belong to one audiobook
//    @JoinColumn(name = "audiobook_id")
//    private Audiobook audiobook;
//
//    @Column(name = "comment_text")
//    private String commentText;
//
//    @Column(name = "comment_date", length = 5000)
//    private Timestamp commentDate;
//}
