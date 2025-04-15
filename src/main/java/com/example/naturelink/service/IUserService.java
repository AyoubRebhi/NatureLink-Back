package com.example.naturelink.service;

import com.example.naturelink.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserService {

    List<User> getAllUsers();

    Optional<User> getUserById(Integer id);

    User addUser(User user);

    User updateUser(Integer id, Map<String, Object> updates);

    void deleteUser(Integer id);
    User blockUser(Integer id);
    User unblockUser(Integer id);
    User updateProfilePic(Integer id, String fileName);
}
