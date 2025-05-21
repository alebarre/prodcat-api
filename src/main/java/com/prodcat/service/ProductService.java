package com.prodcat.service;

import com.prodcat.DTO.ProductDTO;
import com.prodcat.Mapper.ProductMapper;
import com.prodcat.model.Category;
import com.prodcat.model.Product;
import com.prodcat.repository.CategoryRepository;
import com.prodcat.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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

        if (dto.getId() != null) {
            Optional<Product> productExist = productRepo.findById(dto.getId());
            if (productExist.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found 🙁");
            } else {
                return getProductDTO(dto);
            }

        }
        return getProductDTO(dto);
    }

    private ProductDTO getProductDTO(ProductDTO dto) {
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
        if (id == null || productRepo.findById(id).isEmpty()) {
            throw new RuntimeException("Product not found");
        } else {
            productRepo.deleteById(id);
        }
    }

    public ProductDTO get(Long id) {
        return productRepo.findById(id)
                .map(productMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
