package com.mediasoft.gestion_produit.communs.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    /**
     * The entity isDelete or not.
     */
    @Column(name = "is_deleted")
    private boolean isDeleted;

    /**
     * Date entity is created .
     */
    @CreatedDate
    @Column(name = "date_creation")
    private LocalDate dateCreation;

    /**
     * Date entity is update .
     */
    @LastModifiedDate
    @Column(name = "date_modif")
    private LocalDate dateModif;

    /**
     * The user who creates the entity .
     */
    @CreatedBy
    @Column(name = "created_by")
    private Long createdBy;

    /**
     * The user who update the entity .
     */
    @LastModifiedBy
    @Column(name = "update_by")
    private Long updateBy;
}
