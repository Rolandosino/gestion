package com.mediasoft.i_collect.repositories;

import com.mediasoft.i_collect.enums.TypeDeRole;
import com.mediasoft.i_collect.models.Role;
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
