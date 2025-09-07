package com.tableorder.server.controller;

import com.tableorder.server.entity.Category;
import com.tableorder.server.service.CategoryService; // Repository -> Service
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return service.findAllCategories();
    }
}