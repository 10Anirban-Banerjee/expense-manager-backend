package com.anirban.expensemanager.integration;

import com.anirban.expensemanager.entity.Expense;
import com.anirban.expensemanager.repository.ExpenseRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
@SpringBootTest
@Testcontainers
public class ExpenseRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    @Autowired
    private ExpenseRepository expenseRepository;

    @Test
    void shouldSaveExpense() {

        Expense expense = new Expense();

        expense.setTitle("Test Expense");

        Expense savedExpense =
                expenseRepository.save(expense);

        assertEquals(
                "Test Expense",
                savedExpense.getTitle()
        );
    }
}