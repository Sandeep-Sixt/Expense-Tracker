package com.expense.expense.dao;

import com.expense.expense.model.Expense;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Repository
public class ExpenseDao {
    private final JdbcTemplate jdbcTemplate;

    public ExpenseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addExpense(Long userId, BigDecimal amount, String description, Date expenseDate) {
        return jdbcTemplate.update(
                "INSERT INTO expenses (user_id, amount, description, expense_date) VALUES (?, ?, ?, ?)",
                userId, amount, description, expenseDate
        );
    }

    public List<Expense> getExpensesByUser(Long userId) {
        return jdbcTemplate.query(
                "SELECT * FROM expenses WHERE user_id = ?",
                (rs, rowNum) -> {
                    Expense expense = new Expense();
                    expense.setId(rs.getLong("id"));
                    expense.setUserId(rs.getLong("user_id"));
                    expense.setAmount(rs.getBigDecimal("amount"));
                    expense.setDescription(rs.getString("description"));
                    expense.setExpenseDate(rs.getDate("expense_date").toLocalDate());
                    return expense;
                },
                userId
        );
    }

    public BigDecimal getSumOfExpenses(Long userId, Date startDate, Date endDate) {
        return jdbcTemplate.queryForObject(
                "SELECT COALESCE(SUM(amount), 0) FROM expenses WHERE user_id = ? AND expense_date BETWEEN ? AND ?",
                BigDecimal.class,
                userId, startDate, endDate
        );
    }
}
