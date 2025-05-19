package com.hexagonal.template.infrastructure.persistence.repository;

import com.hexagonal.template.infrastructure.persistence.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductJpaRepository extends JpaRepository<OrderProductEntity, Long> {
    
}
