package com.anirban.expensemanager.service;

import com.anirban.expensemanager.dto.ExpenseRequestDto;
import com.anirban.expensemanager.entity.Expense;
import com.anirban.expensemanager.entity.User;
import com.anirban.expensemanager.exception.ResourceNotFoundException;
import com.anirban.expensemanager.repository.ExpenseRepository;
import com.anirban.expensemanager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;



import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    private static final Logger logger =
            LoggerFactory.getLogger(ExpenseService.class);

    public ExpenseService(
            ExpenseRepository expenseRepository,
            UserRepository userRepository) {

        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    @CacheEvict(value = "expenses", allEntries = true)
    public Expense saveExpense(ExpenseRequestDto dto) {

        logger.info("Saving new expense with title: {}", dto.getTitle());

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Expense expense = new Expense();

        expense.setTitle(dto.getTitle());
        expense.setAmount(dto.getAmount());
        expense.setExpenseDate(dto.getExpenseDate());

        // Attach logged-in user
        expense.setUser(user);

        Expense savedExpense = expenseRepository.save(expense);

        logger.info("Expense saved successfully with id: {}", savedExpense.getId());

        return savedExpense;
    }

    @CacheEvict(value = "expenses", allEntries = true)
    public Expense updateExpense(Long id, ExpenseRequestDto dto) {

        logger.info("Updating expense with id: {}", id);

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found"));

        expense.setTitle(dto.getTitle());
        expense.setAmount(dto.getAmount());
        expense.setExpenseDate(dto.getExpenseDate());

        Expense updatedExpense = expenseRepository.save(expense);

        logger.info("Expense updated successfully with id: {}", updatedExpense.getId());

        return updatedExpense;
    }

    @CacheEvict(value = "expenses", allEntries = true)
    public void deleteExpense(Long id) {

        logger.info("Deleting expense with id: {}", id);

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found"));

        expenseRepository.delete(expense);

        logger.info("Expense deleted successfully with id: {}", id);
    }

    @Cacheable(value = "expenses", key = "#email")
    public List<Expense> getAllExpenses(String email) {

        logger.info("Fetching all expenses");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return expenseRepository.findByUser(user);
    }

    public Page<Expense> getExpensesWithPagination(
            int page,
            int size,
            String sortBy) {

        logger.info("Fetching expenses with pagination");

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortBy).descending()
        );

        return expenseRepository.findAll(pageable);
    }
}