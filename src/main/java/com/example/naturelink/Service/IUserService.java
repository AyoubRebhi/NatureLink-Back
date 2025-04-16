package com.example.naturelink.Service;

import com.example.naturelink.Entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> getAllUsers();

    Optional<User> getUserById(Integer id);

    User addUser(User user);

    User updateUser(Integer id, User user);

    void deleteUser(Integer id);
}
