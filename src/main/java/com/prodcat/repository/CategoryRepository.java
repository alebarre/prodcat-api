package com.prodcat.repository;

import com.prodcat.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c.name from Category c where c.parent is null")
    List<String> findTopLevelCategoryNames();
}
