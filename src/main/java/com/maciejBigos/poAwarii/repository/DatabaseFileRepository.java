package com.maciejBigos.poAwarii.repository;

import com.maciejBigos.poAwarii.model.DatabaseFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseFileRepository extends JpaRepository<DatabaseFile, String> {
}
