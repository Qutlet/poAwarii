package com.maciejBigos.poAwarii.repository;

import com.maciejBigos.poAwarii.model.Deadline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeadlineRepository extends JpaRepository<Deadline, Long> {

//    @Query("select sDid.specialist_profile_specialist_profileid from specialist_profile_deadline_ids sDid where deadline_ids = :dId")
//    Optional<Long> findByDeadlineId(Long dId);

    Optional<Deadline> findByMalfunctionId(Long malfunctionId);

}
