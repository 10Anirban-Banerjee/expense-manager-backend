package com.anirban.expensemanager.controller;

import com.anirban.expensemanager.entity.Expense;
import com.anirban.expensemanager.service.ExpenseService;
import com.anirban.expensemanager.dto.ExpenseRequestDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public Expense createExpense(@Valid @RequestBody ExpenseRequestDto dto) {
        return expenseService.saveExpense(dto);
    }

    @PutMapping("/{id}")
    public Expense updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequestDto dto) {

        return expenseService.updateExpense(id, dto);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id) {

        expenseService.deleteExpense(id);

        return "Expense deleted successfully";
    }

    @GetMapping
    public List<Expense> getAllExpenses() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return expenseService.getAllExpenses(email);
    }

    @GetMapping("/paginated")
    public Page<Expense> getExpensesWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return expenseService.getExpensesWithPagination(
                page,
                size,
                sortBy
        );
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminOnlyApi() {

        return "Welcome Admin!";
    }
}