package com.prodcat.controller;

import com.prodcat.DTO.CategoryDTO;
import com.prodcat.model.Category;
import com.prodcat.model.Role;
import com.prodcat.repository.UserRepository;
import com.prodcat.service.CategoryService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/category")
@CrossOrigin("*")
public class CategoryController {

    private final CategoryService categoryService;
    private final UserRepository userRepository;

    public CategoryController(CategoryService categoryService, UserRepository userRepository) {
        this.categoryService = categoryService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Category> list() {
        return categoryService.list();
    }

    @GetMapping("/parent")
    public List<String> listParents() {
        return categoryService.listParents();
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
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id, JwtAuthenticationToken token) {

        if (verifyAdminRole(token)) {
            categoryService.delete(id);
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
