package com.Applications.EchecsBackend.repository.echecs;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Piece;
import org.springframework.data.jpa.repository.JpaRepository;




/**
 * Repository qui gère les Pièces en Base de données.
 */
public interface PieceRepository extends JpaRepository<Piece, Long> {

    // Modifier un user :
    @Override
    <S extends Piece> S save(S entity);


}
