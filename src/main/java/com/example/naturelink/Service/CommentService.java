package com.example.naturelink.Service;

import com.example.naturelink.Entity.Comment;
import com.example.naturelink.Entity.Post;
import com.example.naturelink.Entity.User;
import com.example.naturelink.Repository.ICommentRepository;
import com.example.naturelink.Repository.IPostRepository;
import com.example.naturelink.Repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    private  ICommentRepository commentRepository;
    @Autowired
    private  IPostRepository postRepository;
    @Autowired
    private  IUserRepository userRepository;


    // Créer un commentaire
    public Comment createComment(Comment comment, Long postId, Integer userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        comment.setPost(post);
        comment.setUser(user);
        comment.setCreatedAt(java.time.LocalDateTime.now().toString());  // Définir la date de création

        return commentRepository.save(comment);
    }

    // Récupérer tous les commentaires d'un post
    public List<Comment> getAllCommentsByPost(Long postId) {
        return commentRepository.findAll().stream()
                .filter(comment -> comment.getPost().getId().equals(postId))
                .collect(Collectors.toList());
    }

    // Mettre à jour un commentaire
    public Comment updateComment(Long id, Comment commentDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setContent(commentDetails.getContent());
        return commentRepository.save(comment);
    }

    // Supprimer un commentaire
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
