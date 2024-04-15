package com.yeshenko.process.domain.repository;

import com.yeshenko.process.domain.entity.Document;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {

  List<Document> findAllByProcessEntityId(UUID processEntityId);
}
