package com.Applications.EchecsBackend.repository.echecs;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Piece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Repository qui gère les Pièces en Base de données.
 */
public interface PieceRepository extends JpaRepository<Piece, Long> {

    // Modifier un user :
    @Override
    <S extends Piece> S save(S entity);


    @Query("SELECT p FROM Piece p WHERE p.no_piece = :numeroPiece")
    Piece recupererPieceParId(@Param("numeroPiece") Long numeroPiece);



}
