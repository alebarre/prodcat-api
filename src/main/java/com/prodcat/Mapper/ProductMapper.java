package com.prodcat.Mapper;

import com.prodcat.DTO.ProductDTO;
import com.prodcat.model.Category;
import com.prodcat.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setAvailable(product.isAvailable());
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
            dto.setCategoryPath(product.getCategoryPath());
        }
        return dto;
    }

    public Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setAvailable(dto.isAvailable());
        if (dto.getCategoryId() != null) {
            // only set the ID here; full Category will be fetched in the service
            Category cat = new Category();
            cat.setId(dto.getCategoryId());
            product.setCategory(cat);
        }
        return product;
    }
}
