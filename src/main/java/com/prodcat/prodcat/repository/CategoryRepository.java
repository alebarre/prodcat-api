package com.prodcat.prodcat.repository;

import com.prodcat.prodcat.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
