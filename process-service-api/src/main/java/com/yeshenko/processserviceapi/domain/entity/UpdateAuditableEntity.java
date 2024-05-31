package com.yeshenko.processserviceapi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@Audited
@EqualsAndHashCode(callSuper = true)
public abstract class UpdateAuditableEntity extends AuditableEntity {

    @CreatedDate
    @Column(name = "updated_at", nullable = false, updatable = false)
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "updated_by", nullable = false, updatable = false)
    private String updatedBy;
}
