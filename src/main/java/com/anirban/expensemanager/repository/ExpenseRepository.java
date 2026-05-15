package com.anirban.expensemanager.repository;

import com.anirban.expensemanager.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}