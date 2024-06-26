package com.yeshenko.processserviceapi.domain.repository.custom;

import com.yeshenko.processserviceapi.domain.entity.ProcessEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessEntityRepository extends JpaRepository<ProcessEntity, UUID>,
    JpaSpecificationExecutor<ProcessEntity>, CustomProcessEntityRepository {

}
