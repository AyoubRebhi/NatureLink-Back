package com.example.naturelink.Controller;

import com.example.naturelink.Entity.Role;
import com.example.naturelink.Entity.User;
import com.example.naturelink.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;


    @GetMapping("/admin/all")
    public ResponseEntity<List<User>> getAllUsersAdmin() {
        List<User> allUsers = userService.getAllUsers();
        List<User> nonAdminUsers = allUsers.stream()
                .filter(user -> user.getRole() != Role.ADMIN)
                .toList();

        return ResponseEntity.ok(nonAdminUsers);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> updates
    ) {
        try {

            User updatedUser = userService.updateUser(id, updates);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }
    @PutMapping("/{id}/block")
    public ResponseEntity<User> blockUser(@PathVariable Integer id) {
        User blockedUser = userService.blockUser(id);
        return ResponseEntity.ok(blockedUser);
    }

    @PutMapping("/{id}/unblock")
    public ResponseEntity<User> unblockUser(@PathVariable Integer id) {
        User unblockedUser = userService.unblockUser(id);
        return ResponseEntity.ok(unblockedUser);
    }
    @PostMapping("/{id}/upload-profile-pic")
    public ResponseEntity<?> uploadProfilePicture(
            @PathVariable Integer id,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            // Validate file
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }
            if (!file.getContentType().startsWith("image/")) {
                return ResponseEntity.badRequest().body("Only image files are allowed");
            }

            // Create uploads directory if not exists
            Path uploadDir = Paths.get("uploads").toAbsolutePath().normalize();
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Generate unique filename
            String fileName = String.format("%d_%d_%s",
                    id,
                    System.currentTimeMillis(),
                    StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()))
                            .replace(" ", "_") // Replace spaces with underscores
                            .replace(",", "")  // Remove commas
            );
            // Save file
            Path targetPath = uploadDir.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // Update user profile picture
            User updatedUser = userService.updateProfilePic(id, fileName);

            return ResponseEntity.ok(updatedUser);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("File storage error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Server error: " + e.getMessage());
        }
    }
}
