package com.example.naturelink.Service;

import com.example.naturelink.Entity.Like;
import com.example.naturelink.Entity.Post;
import com.example.naturelink.Entity.User;
import com.example.naturelink.Repository.IPostRepository;
import com.example.naturelink.Repository.IUserRepository;
import com.example.naturelink.Repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private final LikeRepository likeRepository;

    @Autowired
    private final IPostRepository postRepository;

    @Autowired
    private final IUserRepository userRepository;

    public LikeService(LikeRepository likeRepository,
                       IPostRepository postRepository,
                       IUserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public int toggleLike(Long postId, Integer userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Post not found with id: " + postId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id: " + userId));

        Optional<Like> existingLike = likeRepository.findByPostAndUser(post, user);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
        } else {
            Like newLike = new Like();
            newLike.setPost(post);
            newLike.setUser(user);
            likeRepository.save(newLike);
        }

        return likeRepository.countByPost(post);
    }
    @Transactional

    public int getLikeCount(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Post not found with id: " + postId);
        }
        return likeRepository.countByPostId(postId);
    }
    @Transactional

    public boolean hasUserLikedPost(Long postId, Integer userId) {
        if (!postRepository.existsById(postId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Post not found with id: " + postId);
        }
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found with id: " + userId);
        }
        return likeRepository.existsByPostIdAndUserId(postId, userId);
    }
}