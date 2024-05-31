package com.yeshenko.processserviceapi.domain.repository.envers;

import com.yeshenko.processserviceapi.domain.entity.ProcessEntity;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProcessEntityRevisionRepository extends RevisionRepository<ProcessEntity, UUID, Integer> {
}
