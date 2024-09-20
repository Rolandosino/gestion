package com.mediasoft.gestion_produit.repositories;

import com.mediasoft.gestion_produit.models.Jwt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface JwtRepository extends CrudRepository<Jwt, Long> {

    @Query("SELECT j FROM Jwt j WHERE j.valeur =:valeur AND j.desactive = :desactive AND j.expire = :expire")
    Optional<Jwt> findByValeurAndDesactiveAndExpire(String valeur, boolean desactive, boolean expire);

    @Query("SELECT j FROM Jwt j WHERE j.expire = :expire AND j.desactive = :desactive AND j.user.emailAddress = :email")
    Optional<Jwt> findUtilisateurValidToken(boolean expire, boolean desactive, String email);

    @Query("SELECT j FROM Jwt j WHERE j.expire = false AND j.desactive = false" +
            "  AND j.user.id = :idUser")
    List<Jwt> findAllValidTokenByUser(@Param(value = "idUser") Long idUser);

    @Query("SELECT j FROM Jwt j WHERE j.user.emailAddress = :email")
    Stream<Jwt> findUtilisateur(String email);

    @Query("SELECT j FROM Jwt j WHERE j.refreshToken.valeur = :valeur")
    Optional<Jwt> findByRefreshToken(String valeur);

    void deleteAllByExpireAndDesactive(boolean expire, boolean desactive);
}
