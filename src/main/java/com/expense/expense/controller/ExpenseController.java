package com.expense.expense.controller;

import com.expense.expense.dao.ExpenseDao;
import com.expense.expense.dao.UserDao;
import com.expense.expense.model.Expense;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseDao expenseDao;
    private final UserDao userDao;

    public ExpenseController(ExpenseDao expenseDao, UserDao userDao) {
        this.expenseDao = expenseDao;
        this.userDao = userDao;
    }

    @PostMapping
    public String addExpense(
            @RequestParam Long userId,
            @RequestParam BigDecimal amount,
            @RequestParam String description,
            @RequestParam Date expenseDate
    ) {
        expenseDao.addExpense(userId, amount, description, expenseDate);
        return "Expense added successfully";
    }

    @GetMapping("/{userId}")
    public List<Expense> getExpenses(@PathVariable Long userId) {
        return expenseDao.getExpensesByUser(userId);
    }

    @GetMapping("/sum")
    public BigDecimal getSumOfExpenses(
            @RequestParam Long userId,
            @RequestParam Date startDate,
            @RequestParam Date endDate
    ) {
        return expenseDao.getSumOfExpenses(userId, startDate, endDate);
    }
}
