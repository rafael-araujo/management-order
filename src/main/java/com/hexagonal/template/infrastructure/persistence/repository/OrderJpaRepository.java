package com.hexagonal.template.infrastructure.persistence.repository;

import com.hexagonal.template.infrastructure.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT CASE WHEN EXISTS (" +
            "   SELECT 1 FROM OrderEntity o " +
            "   WHERE o.signature = :signature " +
            "   AND o.createDate BETWEEN :timeMin AND :timeMax " +
            "   AND o.deleted = FALSE " +
            ") THEN TRUE ELSE FALSE END")
    Boolean findBySignature(String signature, Instant timeMin, Instant timeMax);

    Optional<OrderEntity> findByIdAndDeletedFalse(Long orderId);
}
