package com.maciejBigos.poAwarii.repository;

import com.maciejBigos.poAwarii.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
}
