package com.example.naturelink.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "`like`")

@Entity
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters et setters
}