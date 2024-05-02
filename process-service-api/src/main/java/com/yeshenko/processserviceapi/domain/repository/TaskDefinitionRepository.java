package com.yeshenko.processserviceapi.domain.repository;

import com.yeshenko.processserviceapi.domain.entity.TaskDefinition;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDefinitionRepository extends JpaRepository<TaskDefinition, UUID> {

  Optional<TaskDefinition> findByDefinitionKey(String taskDefinitionKey);
}
