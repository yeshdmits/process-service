package com.yeshenko.processserviceapi.domain.repository;

import com.yeshenko.processserviceapi.domain.entity.TestData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDataRepository extends JpaRepository<TestData, Long> {

}
