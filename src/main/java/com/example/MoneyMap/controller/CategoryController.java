package com.example.MoneyMap.controller;

import com.example.MoneyMap.model.Category;
import com.example.MoneyMap.repo.CategoryRepo;
import com.example.MoneyMap.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/getAllCategories")
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> categoryList = categoryService.getAllCategories();

            if(categoryList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getIncomeCategories")
    public ResponseEntity<List<Category>> getIncomeCategories() {
        try {
//            List<Category> incomeCategoryList = new ArrayList<>();
//            categoryRepo.findByIsIncome(true).forEach(incomeCategoryList::add);

            List<Category> incomeCategoryList = categoryService.getIncomeCategories();

            if(incomeCategoryList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(incomeCategoryList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getOutcomeCategories")
    public ResponseEntity<List<Category>> getOutcomeCategories() {
        try {
            List<Category> outcomeCategoryList = categoryService.getOutcomeCategories();

            if(outcomeCategoryList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(outcomeCategoryList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCategoryById/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId){
        Optional<Category> categoryData = Optional.ofNullable(categoryService.getCategoryById(categoryId));
        if(categoryData.isPresent()){
            return new ResponseEntity<>(categoryData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addCategory")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category categoryObj = categoryService.saveCategory(category);
        return new ResponseEntity<>(categoryObj, HttpStatus.OK);
    }

    @PostMapping("/updateCategoryById/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId,
                                                   @RequestBody Category newCategoryData) {
        Optional<Category> oldCategoryData = Optional.ofNullable(categoryService.getCategoryById(categoryId));
        if(oldCategoryData.isPresent()){
            Category updatedCategoryData = oldCategoryData.get();
            updatedCategoryData.setCategoryName(newCategoryData.getCategoryName());
            updatedCategoryData.setIsIncome((newCategoryData.getIsIncome()));

            Category categoryObj = categoryService.saveCategory(updatedCategoryData);
            return new ResponseEntity<>(categoryObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteCategoryById/{categoryId}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/category/merge")
    public ResponseEntity<Category> mergeCategories(
            @RequestParam Long categoryId1,
            @RequestParam Long categoryId2,
            @RequestParam String newCategoryName){
        try {
            Category category = categoryService.mergeCategories(categoryId1, categoryId2, newCategoryName);
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
