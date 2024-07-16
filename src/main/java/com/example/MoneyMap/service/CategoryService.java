package com.example.MoneyMap.service;

import com.example.MoneyMap.model.Category;
import com.example.MoneyMap.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    public Category getCategoryById(Long id) {
        Category category = categoryRepo.findById(id).orElse(null);
        return category;
    }

    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    public List<Category> getIncomeCategories() {
        return categoryRepo.findByIsIncome(true);
    }

    public List<Category> getOutcomeCategories() {
        return categoryRepo.findByIsIncome(false);
    }

    public Category saveCategory(Category category) {
        return categoryRepo.save(category);
    }

    public void deleteCategory(Long categoryId) {
        categoryRepo.deleteById(categoryId);
    }

    public void mergeCategories() {
    }
}
