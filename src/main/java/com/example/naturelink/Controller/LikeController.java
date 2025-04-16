package com.example.naturelink.Controller;

import com.example.naturelink.Service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/toggle/{postId}")
    public ResponseEntity<Map<String, Object>> toggleLike(
            @PathVariable Long postId,
            @RequestParam Integer userId) {
        try {
            int likeCount = likeService.toggleLike(postId, userId);
            boolean hasLiked = likeService.hasUserLikedPost(postId, userId);

            Map<String, Object> response = new HashMap<>();
            response.put("liked", hasLiked);
            response.put("count", likeCount);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/count/{postId}")
    public ResponseEntity<Integer> getLikeCount(
            @PathVariable Long postId) {
        try {
            int count = likeService.getLikeCount(postId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/check-like")
    public ResponseEntity<Boolean> hasUserLikedPost(
            @RequestParam Long postId,
            @RequestParam Integer userId) {
        try {
            boolean hasLiked = likeService.hasUserLikedPost(postId, userId);
            return ResponseEntity.ok(hasLiked);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}