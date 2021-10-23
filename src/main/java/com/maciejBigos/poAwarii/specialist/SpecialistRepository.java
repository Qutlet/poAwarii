package com.maciejBigos.poAwarii.specialist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialistRepository extends JpaRepository<SpecialistProfile, Long> {
}
