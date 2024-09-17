package com.mediasoft.i_collect.repositories;

import com.mediasoft.i_collect.models.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.role " +
            " WHERE u.username =:username AND u.isDeleted = false")
    Optional<UserEntity> findByUsername(@Param("username") String username);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.role " +
            " WHERE u.emailAddress =:emailAddress AND u.isDeleted = false")
    Optional<UserEntity> findByEmail(@Param("emailAddress") String emailAddress);
}
