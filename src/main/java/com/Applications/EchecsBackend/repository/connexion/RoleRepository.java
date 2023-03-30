package com.Applications.EchecsBackend.repository.connexion;

import com.Applications.EchecsBackend.models.connexion.ERole;
import com.Applications.EchecsBackend.models.connexion.Role;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;





/**
 * Repository qui gère les rôles en Base de données.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {



    /**
     * Vérification de l'existence du rôle via son name.
     * @param name
     * @return
     */
    Optional<Role> findByName(ERole name);



    /**
     * Récupération de tous les rôles.
     * @param example
     * @return
     * @param <S>
     */
    @Override
    <S extends Role> List<S> findAll(Example<S> example);





}