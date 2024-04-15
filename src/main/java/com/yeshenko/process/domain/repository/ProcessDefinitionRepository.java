package com.yeshenko.process.domain.repository;

import com.yeshenko.process.domain.entity.ProcessDefinition;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessDefinitionRepository extends JpaRepository<ProcessDefinition, UUID> {

  Optional<ProcessDefinition> findByProcessName(String processName);
}
