package com.example.naturelink.Controller;

import com.example.naturelink.Entity.Comment;
import com.example.naturelink.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    // Constructor injection
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Créer un commentaire
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment,
                                                 @RequestParam Long postId,
                                                 @RequestParam Integer userId) {
        Comment createdComment = commentService.createComment(comment, postId, userId);
        return ResponseEntity.ok(createdComment);
    }

    // Récupérer tous les commentaires d'un post
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getAllCommentsByPost(@PathVariable Long postId) {
        List<Comment> comments = commentService.getAllCommentsByPost(postId);
        return ResponseEntity.ok(comments);
    }

    // Mettre à jour un commentaire
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment commentDetails) {
        Comment updatedComment = commentService.updateComment(id, commentDetails);
        return ResponseEntity.ok(updatedComment);
    }

    // Supprimer un commentaire
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
