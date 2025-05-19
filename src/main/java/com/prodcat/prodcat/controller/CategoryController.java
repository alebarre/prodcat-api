package com.prodcat.prodcat.controller;

import com.prodcat.prodcat.DTO.CategoryDTO;
import com.prodcat.prodcat.model.Category;
import com.prodcat.prodcat.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@CrossOrigin("*")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) { this.categoryService = categoryService; }

    @GetMapping
    public List<Category> list() {
        return categoryService.list();
    }

    @GetMapping("/{id}")
    public CategoryDTO get(@PathVariable Long id) {
        return categoryService.get(id);
    }

    @PostMapping
    public CategoryDTO create(@RequestBody CategoryDTO dto) {
        return categoryService.save(dto);
    }

    @PutMapping("/{id}")
    public CategoryDTO update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        dto.setId(id);
        return categoryService.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
