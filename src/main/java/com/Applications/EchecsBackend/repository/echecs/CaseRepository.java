package com.Applications.EchecsBackend.repository.echecs;

import com.Applications.EchecsBackend.models.echecs.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;





/**
 * Repository qui gère les Cases en Base de données.
 */
@Repository
public interface CaseRepository extends JpaRepository<Case, Long>{



    /**
     * Modifier une case.
     * @param entity
     * @return
     * @param <S>
     */
    @Override
    <S extends Case> S save(S entity);



    /**
     * Récupérer toutes les cases de l'échiquier.
     * @return
     */
    @Override
    List<Case> findAll();



    /**
     * Récupérer une case de l'échiquier via son id.
     * @param id
     * @return
     */
    @Query("SELECT c FROM Case c WHERE c.no_case = :id")
    Case recupererCasesParId(@Param("id") Long id);



    /**
     * Récupérer la case du roi via le paramètre "type" du roi.
     * @param type
     * @return
     */
    @Query("SELECT c FROM Case c WHERE c.piece.type = :type")
    Case recupererLaCaseDuRoi(@Param("type") String type);



    /**
     * Récupérer les cases des pièces dont la couleur du camp est passée en paramètre.
     * @param couleur
     * @return
     */
    @Query("SELECT c FROM Case c WHERE c.piece.couleur.couleur = :couleur")
    List<Case>RecupererCasesCamp(@Param("couleur") String couleur);





}