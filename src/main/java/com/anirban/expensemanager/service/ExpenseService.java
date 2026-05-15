package com.anirban.expensemanager.service;

import com.anirban.expensemanager.entity.Expense;
import com.anirban.expensemanager.repository.ExpenseRepository;
import com.anirban.expensemanager.dto.ExpenseRequestDto;
import com.anirban.expensemanager.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private static final Logger logger =
            LoggerFactory.getLogger(ExpenseService.class);

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense saveExpense(ExpenseRequestDto dto) {

        logger.info("+++++++++Saving new expense with title: {}", dto.getTitle());

        Expense expense = new Expense();

        expense.setTitle(dto.getTitle());
        expense.setAmount(dto.getAmount());

        Expense savedExpense = expenseRepository.save(expense);

        logger.info("++++++++++Expense saved successfully with id: {}", savedExpense.getId());

        return savedExpense;
    }

    public Expense updateExpense(Long id, ExpenseRequestDto dto) {

        logger.info("Updating expense with id: {}", id);

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found"));

        expense.setTitle(dto.getTitle());
        expense.setAmount(dto.getAmount());

        Expense updatedExpense = expenseRepository.save(expense);

        logger.info("Expense updated successfully with id: {}", updatedExpense.getId());

        return updatedExpense;
    }

    public void deleteExpense(Long id) {

        logger.info("Deleting expense with id: {}", id);

        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found"));

        expenseRepository.delete(expense);

        logger.info("Expense deleted successfully with id: {}", id);
    }

    public List<Expense> getAllExpenses() {

        logger.info("Fetching all expenses");

        return expenseRepository.findAll();
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