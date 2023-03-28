package com.example.shop.service;

import com.example.shop.entity.User;

import java.util.List;

public interface UserService {

    User create(User user);

    User readById(long id);

    User update(User user, String newPassword);

    void delete(long id);

    List<User> getAll();

}
