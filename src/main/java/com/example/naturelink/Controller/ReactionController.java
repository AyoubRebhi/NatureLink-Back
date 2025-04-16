package com.example.naturelink.Controller;

import com.example.naturelink.Entity.*;
import com.example.naturelink.Repository.IPostRepository;
import com.example.naturelink.Repository.IUserRepository;
import com.example.naturelink.Repository.ReactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reactions")
public class ReactionController {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private IUserRepository userRepository;

    @PostMapping
    public ResponseEntity<Reaction> reactToPost(
            @RequestBody ReactionRequest request) {

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(Math.toIntExact(request.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Reaction> existingReaction = reactionRepository.findByPostAndUser(post, user);

        if (existingReaction.isPresent()) {
            Reaction reaction = existingReaction.get();
            if (reaction.getType() != request.getType()) {
                reaction.setType(request.getType());
                return ResponseEntity.ok(reactionRepository.save(reaction));
            } else {
                reactionRepository.delete(reaction);
                return ResponseEntity.noContent().build();
            }
        }

        Reaction newReaction = new Reaction(post, user, request.getType());
        return ResponseEntity.ok(reactionRepository.save(newReaction));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Map<ReactionType, Long>> getPostReactions(@PathVariable Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Map<ReactionType, Long> counts = reactionRepository
                .findAllByPost(post)
                .stream()
                .collect(Collectors.groupingBy(
                        Reaction::getType,
                        Collectors.counting()
                ));

        return ResponseEntity.ok(counts);
    }

    public static class ReactionRequest {
        private Long postId;
        private Long userId;
        private ReactionType type;

        // Getters and setters
        public Long getPostId() { return postId; }
        public void setPostId(Long postId) { this.postId = postId; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public ReactionType getType() { return type; }
        public void setType(ReactionType type) { this.type = type; }
    }
}