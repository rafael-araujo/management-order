package com.hexagonal.template.infrastructure.external.persistence.repository;

import com.hexagonal.template.infrastructure.external.persistence.entity.ExampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleJpaRepository extends JpaRepository<ExampleEntity, Long> {
}
