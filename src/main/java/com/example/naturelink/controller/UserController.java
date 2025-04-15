package com.example.naturelink.controller;

import com.example.naturelink.entity.Role;
import com.example.naturelink.entity.User;
import com.example.naturelink.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Map;
import java.util.List;
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
    public ResponseEntity<String> uploadProfilePicture(
            @PathVariable Integer id,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String fileName = file.getOriginalFilename();
            String newFileName = id + "_" + Instant.now().getEpochSecond() + "_" + fileName;

            Path uploadDir = Paths.get("uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Files.copy(file.getInputStream(), uploadDir.resolve(newFileName));
            userService.updateProfilePic(id, newFileName);

            return ResponseEntity.ok(newFileName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
