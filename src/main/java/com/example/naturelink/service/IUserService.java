package com.example.naturelink.service;

import com.example.naturelink.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> getAllUsers();

    Optional<User> getUserById(Integer id);

    User addUser(User user);

    User updateUser(Integer id, User user);

    void deleteUser(Integer id);
}
