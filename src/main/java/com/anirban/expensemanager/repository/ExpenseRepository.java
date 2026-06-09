package com.anirban.expensemanager.repository;

import com.anirban.expensemanager.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import com.anirban.expensemanager.entity.User;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
}
