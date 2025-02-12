// ExpenseControllerTest.java
package com.expense.expense.controllers;

import com.expense.expense.controller.ExpenseController;
import com.expense.expense.dao.ExpenseDao;
import com.expense.expense.dao.UserDao;
import com.expense.expense.model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ExpenseControllerTest {

    @Mock
    private ExpenseDao expenseDao;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private ExpenseController expenseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addExpense_ShouldCallDaoAndReturnSuccessMessage() {
        // Given
        Long userId = 1L;
        BigDecimal amount = new BigDecimal("100.50");
        String description = "Groceries";
        Date date = Date.valueOf(LocalDate.now());

        // When
        String result = expenseController.addExpense(userId, amount, description, date);

        // Then
        verify(expenseDao).addExpense(eq(userId), eq(amount), eq(description), eq(date));
        assertEquals("Expense added successfully", result);
    }

    @Test
    void getExpenses_ShouldReturnListFromDao() {
        // Given
        Long userId = 1L;
        Expense expense1 = new Expense();
        expense1.setUserId(userId);
        Expense expense2 = new Expense();
        expense2.setUserId(userId);
        List<Expense> expected = Arrays.asList(expense1, expense2);

        when(expenseDao.getExpensesByUser(userId)).thenReturn(expected);

        // When
        List<Expense> result = expenseController.getExpenses(userId);

        // Then
        assertEquals(expected, result);
        verify(expenseDao).getExpensesByUser(userId);
    }

    @Test
    void getSumOfExpenses_ShouldReturnSumFromDao() {
        // Given
        Long userId = 1L;
        Date startDate = Date.valueOf("2024-01-01");
        Date endDate = Date.valueOf("2024-01-31");
        BigDecimal expectedSum = new BigDecimal("1500.75");

        when(expenseDao.getSumOfExpenses(userId, startDate, endDate)).thenReturn(expectedSum);

        // When
        BigDecimal result = expenseController.getSumOfExpenses(userId, startDate, endDate);

        // Then
        assertEquals(expectedSum, result);
        verify(expenseDao).getSumOfExpenses(userId, startDate, endDate);
    }
}