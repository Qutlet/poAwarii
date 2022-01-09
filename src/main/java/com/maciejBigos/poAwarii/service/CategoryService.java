package com.maciejBigos.poAwarii.service;

import com.maciejBigos.poAwarii.model.Category;
import com.maciejBigos.poAwarii.model.User;
import com.maciejBigos.poAwarii.model.messeges.ResponseCategory;
import com.maciejBigos.poAwarii.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserService userService;

    public void addCategory(String name, Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());
        Category category = new Category();
        category.setName(name);
        category.setCreator(user);
        categoryRepository.save(category);
    }

    public List<ResponseCategory> getCategories() {
        return categoryRepository.findAll().stream().map(ResponseCategory::new).collect(Collectors.toList());
    }

}
