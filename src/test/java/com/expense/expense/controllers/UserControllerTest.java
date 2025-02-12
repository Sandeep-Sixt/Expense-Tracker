// UserControllerTest.java
package com.expense.expense.controllers;

import com.expense.expense.controller.UserController;
import com.expense.expense.dao.UserDao;
import com.expense.expense.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldCallDaoAndReturnSuccessMessage() {
        // Given
        String userName = "John Doe";

        // When
        String result = userController.createUser(userName);

        // Then
        verify(userDao).createUser(eq(userName));
        assertEquals("User created successfully", result);
    }

    @Test
    void getUsers_ShouldReturnListFromDao() {
        // Given
        User user1 = new User();
        user1.setName("Alice");
        User user2 = new User();
        user2.setName("Bob");
        List<User> expected = Arrays.asList(user1, user2);

        when(userDao.getUsers()).thenReturn(expected);

        // When
        List<User> result = userController.getUsers();

        // Then
        assertEquals(expected, result);
        verify(userDao).getUsers();
    }
}