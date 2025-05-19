package com.prodcat.prodcat.service;

import com.prodcat.prodcat.DTO.CategoryDTO;
import com.prodcat.prodcat.Mapper.CategoryMapper;
import com.prodcat.prodcat.model.Category;
import com.prodcat.prodcat.repository.CategoryRepository;
import com.prodcat.prodcat.repository.ProductRepository;
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
