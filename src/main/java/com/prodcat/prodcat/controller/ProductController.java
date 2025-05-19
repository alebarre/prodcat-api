package com.prodcat.prodcat.controller;

import com.prodcat.prodcat.DTO.ProductDTO;
import com.prodcat.prodcat.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {
    private final ProductService svc;
    public ProductController(ProductService svc) { this.svc = svc; }

    @GetMapping
    public Page<ProductDTO> list(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sort
    ) {
        Pageable pg = PageRequest.of(page, size, Sort.by(sort));
        return svc.list(filter, pg);
    }

    @GetMapping("/{id}")
    public ProductDTO get(@PathVariable Long id) {
        return svc.get(id);
    }

    @PostMapping
    public ProductDTO create(@RequestBody ProductDTO dto) {
        return svc.save(dto);
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        dto.setId(id);
        return svc.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        svc.delete(id);
    }
}
