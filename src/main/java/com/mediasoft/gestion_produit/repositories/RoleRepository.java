package com.mediasoft.gestion_produit.repositories;

import com.mediasoft.gestion_produit.enums.TypeDeRole;
import com.mediasoft.gestion_produit.models.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query("select r from Role r " +
            " where r.libelle = :typeRole")
    Role getRoleByTypeRole(@Param(value = "typeRole") TypeDeRole typeRole);
}
