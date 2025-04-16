package com.example.naturelink.Repository;

import com.example.naturelink.Entity.Like;
import com.example.naturelink.Entity.Post;
import com.example.naturelink.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPostAndUser(Post post, User user);

    int countByPost(Post post);
    boolean existsByPostAndUser(Post post, User user);
    int countByPostId(Long postId);
    boolean existsByPostIdAndUserId(Long postId, Integer userId);
}