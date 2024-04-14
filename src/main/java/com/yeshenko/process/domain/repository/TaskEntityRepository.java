package com.yeshenko.process.domain.repository;

import com.yeshenko.process.domain.entity.TaskEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskEntityRepository extends JpaRepository<TaskEntity, UUID> {

  Optional<TaskEntity> findByFlowableTaskId(UUID flowableTaskId);
}
