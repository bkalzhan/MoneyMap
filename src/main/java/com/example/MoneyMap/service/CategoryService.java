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

    public Category mergeCategories(Long categoryId1, Long categoryId2, String newCategoryName) {
        Category category1 = categoryRepo.findById(categoryId1)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId1));
        Category category2 = categoryRepo.findById(categoryId2)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId2));

        if(category1.getIsIncome() != category2.getIsIncome()){
            throw new IllegalArgumentException("Both categories must have the same income type");
        }

        Category newCategory = new Category();
        newCategory.setCategoryName(newCategoryName);
        newCategory.setIsIncome(category1.getIsIncome());

        newCategory = categoryRepo.save(newCategory);

        categoryRepo.updateTransactionCategories(newCategory, List.of(categoryId1, categoryId2));

        categoryRepo.delete(category1);
        categoryRepo.delete(category2);

        return newCategory;
    }
}
