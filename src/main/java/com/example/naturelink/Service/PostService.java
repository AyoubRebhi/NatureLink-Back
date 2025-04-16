package com.example.naturelink.Service;

import com.example.naturelink.Entity.Post;
import com.example.naturelink.Entity.User;
import com.example.naturelink.Repository.IPostRepository;
import com.example.naturelink.Repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final IPostRepository postRepository;
    private final IUserRepository userRepository;

    // Créer un post




    private final Path rootLocation = Paths.get("uploads");

    public Post createPost(String content, MultipartFile image, Integer userId) {
        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            try {
                if (!Files.exists(rootLocation)) {
                    Files.createDirectories(rootLocation);
                }

                String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
                Files.copy(image.getInputStream(), this.rootLocation.resolve(filename));
                imageUrl = "/uploads/" + filename;
            } catch (IOException e) {
            }
        }

        Post post = Post.builder()
                .content(content)
                .ImageUrl(imageUrl)
                .user(userId != null ? userRepository.findById(userId).orElse(null) : null)
                .dateCreated(LocalDateTime.now())
                .build();

        return postRepository.save(post);
    }
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Mettre à jour un post
    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setContent(postDetails.getContent());
        return postRepository.save(post);
    }

    // Supprimer un post
    public void deletePost(Long id) {
        // Option 1: Suppression via cascade (automatique avec JPA)
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);}
}