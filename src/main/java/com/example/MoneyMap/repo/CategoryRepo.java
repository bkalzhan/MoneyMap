package com.example.MoneyMap.repo;

import com.example.MoneyMap.model.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    List<Category> findByIsIncome(boolean isIncome);

    @Transactional
    @Modifying
    @Query("UPDATE Transaction t SET t.category = :newCategory WHERE t.category.categoryId IN :categoryIds")
    void updateTransactionCategories(@Param("newCategory") Category category, @Param("categoryIds") List<Long> categoryIds);
}
