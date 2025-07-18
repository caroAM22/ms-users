package com.pragma.plazacomida.infrastructure.output.repository;

import com.pragma.plazacomida.infrastructure.output.entity.ObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IObjectRepository extends JpaRepository<ObjectEntity, Long> {
} 