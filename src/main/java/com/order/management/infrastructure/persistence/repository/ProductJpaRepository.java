package com.order.management.infrastructure.persistence.repository;

import com.order.management.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT CASE WHEN EXISTS (" +
            "   SELECT 1 FROM ProductEntity p " +
            "   WHERE p.productId = :productId " +
            "   AND p.deleted = FALSE " +
            ") THEN TRUE ELSE FALSE END")
    Boolean existsByProductId(Long productId);

    Optional<ProductEntity> findByIdAndDeletedFalse(Long productId);
}
