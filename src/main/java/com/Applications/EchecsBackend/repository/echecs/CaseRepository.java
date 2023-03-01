package com.Applications.EchecsBackend.repository.echecs;

import com.Applications.EchecsBackend.models.connexion.User;
import com.Applications.EchecsBackend.models.echecs.Case;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository qui gère les Cases en Base de données.
 */
@Repository
public interface CaseRepository extends JpaRepository<Case, Long>{

    // Modifier un user :
    @Override
    <S extends Case> S save(S entity);

    // Récupérer tous l'échiquier :
    @Override
    List<Case> findAll();



}