package com.example.naturelink.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String createdAt;  // Date de création du commentaire

}
