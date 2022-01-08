package com.maciejBigos.poAwarii.repository;

import com.maciejBigos.poAwarii.model.Malfunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MalfunctionRepository extends JpaRepository<Malfunction, Long> {
}
