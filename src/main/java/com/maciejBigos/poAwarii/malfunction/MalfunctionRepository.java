package com.maciejBigos.poAwarii.malfunction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MalfunctionRepository extends JpaRepository<Malfunction, Long> {
}
