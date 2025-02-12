package com.expense.expense.controller;

import com.expense.expense.dao.UserDao;
import com.expense.expense.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostMapping
    public String createUser(@RequestParam String name) {
        userDao.createUser(name);
        return "User created successfully";
    }

    @GetMapping
    public List<User> getUsers() {
        return userDao.getUsers();
    }
}
