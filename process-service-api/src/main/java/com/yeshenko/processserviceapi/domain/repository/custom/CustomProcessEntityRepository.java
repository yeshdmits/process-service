package com.yeshenko.processserviceapi.domain.repository.custom;

import com.yeshenko.processserviceapi.domain.entity.ProcessEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

public interface CustomProcessEntityRepository {

    Map<String, List<Object>> findColumns(Specification<ProcessEntity> specification);
}
