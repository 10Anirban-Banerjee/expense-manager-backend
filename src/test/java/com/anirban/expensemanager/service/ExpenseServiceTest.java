package com.anirban.expensemanager.service;

import com.anirban.expensemanager.entity.Expense;
import com.anirban.expensemanager.repository.ExpenseRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Disabled
@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    void shouldReturnAllExpenses() {

        Expense expense = new Expense();
        expense.setTitle("Food");

        when(expenseRepository.findAll())
                .thenReturn(List.of(expense));

        List<Expense> expenses =
                expenseService.getAllExpenses("");

        assertEquals(1, expenses.size());

        assertEquals(
                "Food",
                expenses.get(0).getTitle()
        );
    }

    @Test
    void shouldReturnEmptyList() {

        when(expenseRepository.findAll())
                .thenReturn(List.of());

        List<Expense> expenses =
                expenseService.getAllExpenses("");

        assertEquals(0, expenses.size());
    }
}