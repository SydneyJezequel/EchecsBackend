package com.Applications.EchecsBackend.repository.connexion;

import java.util.List;
import java.util.Optional;
import com.Applications.EchecsBackend.models.connexion.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Repository;





/**
 * Repository qui gère les users en Base de données.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {



    /**
     * Récupération du user par son username.
     * @param username
     * @return
     */
    Optional<User> findByUsername(String username);



    /**
     * Vérification de l'existence du user.
     * @param username
     * @return
     */
    Boolean existsByUsername(String username);



    /**
     * Vérification de l'existence du user via son email.
     */
    Boolean existsByEmail(String email);



    /**
     *  Suppression d'un user.
     * @param aLong
     */
    @Override
    void deleteById(Long aLong);



    /**
     * Récupération de tous les users.
     * @param example
     * @return
     * @param <S>
     */
    @Override
    <S extends User> List<S> findAll(Example<S> example);



    /**
     * Récupérer un User via son Id.
     * @param aLong
     * @return
     */
    @Override
    @Secured("ROLE_ADMIN")
    Optional<User> findById(Long aLong);



    /**
     * Modifier un user.
     * @param entity
     * @return
     * @param <S>
     */
    @Override
    <S extends User> S save(S entity);





}