package com.yeshenko.processserviceapi.domain.repository.custom;

import com.yeshenko.processserviceapi.domain.entity.ProcessEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CustomProcessEntityRepositoryImpl implements CustomProcessEntityRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Map<String, List<Object>> findColumns(Specification<ProcessEntity> specification) {
        var resultMap = new HashMap<String, List<Object>>();

        for (var column : ColumnValues.values()) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object> criteriaQuery = cb.createQuery(Object.class);
            Root<ProcessEntity> root = criteriaQuery.from(ProcessEntity.class);
            criteriaQuery.select(root.get(column.value)).distinct(true).where(specification.toPredicate(root, criteriaQuery, cb));
            var results = entityManager.createQuery(criteriaQuery).getResultList();
            resultMap.put(column.getValue(), results);

        }
        return resultMap;
    }

    @RequiredArgsConstructor
    @Getter
    enum ColumnValues {
        STATUS("status"),
        CREATED_AT("createdAt"),
        CREATED_BY("createdBy"),
        UPDATED_AT("updatedAt"),
        UPDATED_BY("updatedBy");
        private final String value;

    }
}
