package com.example.naturelink.Controller;
import com.example.naturelink.Entity.Post;
import com.example.naturelink.Service.PostService;
import com.example.naturelink.Service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController
@RequestMapping("/api/posts")

public class PostController {
    private final PostService postServices;
    private final StorageService storageService;

    public PostController(PostService postService, StorageService storageService) {
        this.postServices = postService;
        this.storageService = storageService;
    }


    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Post> createPost(
            @RequestParam("content") String content,
            @RequestParam("userId") Integer userId,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        Post post = postService.createPost(content, image, userId);
        return ResponseEntity.ok(post);
    }


    @Autowired
    private  PostService postService;


    // Créer un post



    // Mettre à jour un post
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        Post updatedPost = postService.updatePost(id, postDetails);
        return ResponseEntity.ok(updatedPost);
    }
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }
    // Supprimer un post
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting post");
        }
    }
}