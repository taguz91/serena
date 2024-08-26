package com.taguz91.api_serena.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "created_by", nullable = true)
    private String createdBy;

    @Column(name = "updated_by", nullable = true)
    private String updatedBy;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT  'false' ")
    private boolean isDeleted;

    @JsonIgnore
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @PrePersist
    public void logAddUserCreate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object auth = authentication.getPrincipal();
        if (auth instanceof Teacher) {
            setCreatedBy(((Teacher) auth).getId());
        }
    }

    @PreUpdate
    public void logAddUserUpdate() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object auth = authentication.getPrincipal();
        if (auth instanceof Teacher) {
            setUpdatedBy(((Teacher) auth).getId());
        }
        setUpdatedAt(LocalDateTime.now());
    }
}
