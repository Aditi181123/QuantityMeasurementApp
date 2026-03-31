package com.app.QuantityMeasurementApp.repository;

import com.app.QuantityMeasurementApp.model.QuantityMeasurementEntity;
import com.app.QuantityMeasurementApp.user.User;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {

    // 🔹 OLD (can keep if needed globally)
    List<QuantityMeasurementEntity> findByOperationIgnoreCase(String operation);

    List<QuantityMeasurementEntity> findByThisMeasurementTypeIgnoreCase(String measurementType);

    List<QuantityMeasurementEntity> findByCreatedAtAfter(LocalDateTime createdAt);

    @Query("select q from QuantityMeasurementEntity q where upper(q.operation) = upper(?1) and q.error = false")
    List<QuantityMeasurementEntity> findSuccessfulByOperation(String operation);

    long countByOperationIgnoreCaseAndErrorFalse(String operation);

    List<QuantityMeasurementEntity> findByErrorTrue();

    // 🔥 USER-SPECIFIC METHODS (IMPORTANT)

    List<QuantityMeasurementEntity> findByUser(User user);

    List<QuantityMeasurementEntity> findByUserAndOperationIgnoreCase(User user, String operation);

    List<QuantityMeasurementEntity> findByUserAndThisMeasurementTypeIgnoreCase(User user, String measurementType);

    List<QuantityMeasurementEntity> findByUserAndErrorTrue(User user);

    long countByUserAndOperationIgnoreCaseAndErrorFalse(User user, String operation);
}