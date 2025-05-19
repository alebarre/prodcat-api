package com.prodcat.prodcat.service;

import com.prodcat.prodcat.DTO.ProductDTO;
import com.prodcat.prodcat.Mapper.ProductMapper;
import com.prodcat.prodcat.model.Category;
import com.prodcat.prodcat.model.Product;
import com.prodcat.prodcat.repository.CategoryRepository;
import com.prodcat.prodcat.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepo,
                          CategoryRepository categoryRepo,
                          ProductMapper productMapper) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.productMapper = productMapper;
    }

    public Page<ProductDTO> list(String filter, Pageable pageable) {
        Specification<Product> spec = (root, cq, cb) ->
                filter != null && !filter.isBlank()
                        ? cb.like(cb.lower(root.get("name")), "%" + filter.toLowerCase() + "%")
                        : null;
        return productRepo.findAll(spec, pageable)
                .map(productMapper::toDTO);
    }

    public ProductDTO save(ProductDTO dto) {
        Product entity = productMapper.toEntity(dto);
        if (dto.getCategoryId() != null) {
            Category cat = categoryRepo.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            entity.setCategory(cat);
        }
        Product saved = productRepo.save(entity);
        return productMapper.toDTO(saved);
    }

    public void delete(Long id) {
        productRepo.deleteById(id);
    }

    public ProductDTO get(Long id) {
        return productRepo.findById(id)
                .map(productMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
