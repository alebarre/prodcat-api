package com.prodcat.service;

import com.prodcat.DTO.CategoryDTO;
import com.prodcat.Mapper.CategoryMapper;
import com.prodcat.model.Category;
import com.prodcat.repository.CategoryRepository;
import com.prodcat.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final CategoryMapper mapper;

    public CategoryService(ProductRepository productRepo,
                           CategoryRepository categoryRepo,
                           CategoryMapper mapper) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.mapper = mapper;
    }

    public List<Category> list() {
        return categoryRepo.findAll();

    }

    public List<String> listParents() {
        return categoryRepo.findTopLevelCategoryNames();

    }

    public CategoryDTO save(CategoryDTO dto) {
        Category entity = mapper.toEntity(dto);
        if (dto.getId() != null) {
            Category cat = categoryRepo.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            entity.setParent(cat);
        }
        Category saved = categoryRepo.save(entity);
        return mapper.toDTO(saved);
    }

    public void delete(Long id) {
        categoryRepo.deleteById(id);
    }

    public CategoryDTO get(Long id) {
        return categoryRepo.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
