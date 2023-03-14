package com.Applications.EchecsBackend.repository.echecs;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Repository qui gère les Cases en Base de données.
 */
@Repository
public interface CaseRepository extends JpaRepository<Case, Long>{


    // Modifier un user :
    @Override
    <S extends Case> S save(S entity);


    // Récupérer tout l'échiquier :
    @Override
    List<Case> findAll();

    // Récupérer une case via son id :
    @Query("SELECT c FROM Case c WHERE c.no_case = :id")
    Case RecupererCasesAutourDuRoi(@Param("id") Long id);


    // Récupérer la case du roi via le paramètre "type" du roi :
    // Case findCaseByPieceIs(Piece piece);
    @Query("SELECT c FROM Case c WHERE c.piece.type = :type")
    Case recupererLaCaseDuRoi(@Param("type") String type);


    // Récupéer les cases des pièces de la couleur adverse :
    @Query("SELECT c FROM Case c WHERE c.piece.couleur.couleur = :couleur")
    List<Case>RecupererCasesCampAdverse(@Param("couleur") String couleur);


}