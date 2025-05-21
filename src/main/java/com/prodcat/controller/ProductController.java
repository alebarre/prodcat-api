package com.prodcat.controller;

import com.prodcat.DTO.ProductDTO;
import com.prodcat.model.Role;
import com.prodcat.repository.UserRepository;
import com.prodcat.service.ProductService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {

    private final ProductService svc;
    private final UserRepository userRepository;
    private final ProductService productService;

    public ProductController(ProductService svc, UserRepository userRepository, ProductService productService) { this.svc = svc;
        this.userRepository = userRepository;
        this.productService = productService;
    }

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
    public ResponseEntity<Void> delete(@PathVariable Long id, JwtAuthenticationToken token) {

        if (verifyAdminRole(token)) {
            productService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    public boolean verifyAdminRole(@NotNull JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName()));

        return user.get().getRoles()
                .stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));
    }

}
