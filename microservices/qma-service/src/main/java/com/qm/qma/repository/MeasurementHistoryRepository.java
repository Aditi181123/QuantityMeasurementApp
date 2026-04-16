package com.qm.qma.repository;

import com.qm.qma.model.MeasurementHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementHistoryRepository extends JpaRepository<MeasurementHistory, Long> {
    
    /**
     * Find history by user ID, ordered by timestamp descending (newest first)
     */
    List<MeasurementHistory> findByUserIdOrderByTimestampDesc(String userId);
   
}
