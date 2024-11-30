package com.onlinevote.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@MappedSuperclass
public class BaseEntity {
    private String zoneId;

    private String userUtcTime;

    @CreatedDate
    @Column(updatable = false)
    private String createdAt;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(updatable = true)
    private String updatedAt;

    @LastModifiedBy
    @Column(updatable = true)
    private String updatedBy;
}
