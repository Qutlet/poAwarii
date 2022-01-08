package com.maciejBigos.poAwarii.repository;

import com.maciejBigos.poAwarii.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
