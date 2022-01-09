package com.maciejBigos.poAwarii.controller;

import com.maciejBigos.poAwarii.model.Category;
import com.maciejBigos.poAwarii.model.messeges.ResponseCategory;
import com.maciejBigos.poAwarii.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> addCategory(@RequestParam String name,  Authentication authentication) {
        categoryService.addCategory(name, authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ResponseCategory>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategories());
    }

}
