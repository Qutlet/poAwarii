package com.maciejBigos.poAwarii.repository;

import com.maciejBigos.poAwarii.model.SpecialistProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialistRepository extends JpaRepository<SpecialistProfile, Long> {

    @Query("select s from specialist_profile s where user_user_id = :userId")
    Optional<SpecialistProfile> findByUserId(String userId);
}
