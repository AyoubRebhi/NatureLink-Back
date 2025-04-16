package com.example.naturelink.Repository;

import com.example.naturelink.Entity.Reaction;
import com.example.naturelink.Entity.ReactionType;
import com.example.naturelink.Entity.User;
import com.example.naturelink.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Optional<Reaction> findByPostAndUser(Post post, User user);
    int countByPostAndType(Post post, ReactionType type);

    // Alternative avec IDs si nécessaire
    Optional<Reaction> findByPostIdAndUserId(Long postId, Long userId);
    int countByPostIdAndType(Long postId, ReactionType type);
    List<Reaction> findAllByPost(Post post);

}