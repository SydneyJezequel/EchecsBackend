package com.Applications.EchecsBackend.repository.echecs;

import com.Applications.EchecsBackend.models.connexion.User;
import com.Applications.EchecsBackend.models.echecs.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




/**
 * Repository qui gère les Cases en Base de données.
 */
@Repository
public interface CaseRepository extends JpaRepository<Case, Long>{


    // Modifier un user :
    @Override
    <S extends Case> S save(S entity);




}