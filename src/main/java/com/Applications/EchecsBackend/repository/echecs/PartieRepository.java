package com.Applications.EchecsBackend.repository.echecs;

import com.Applications.EchecsBackend.models.echecs.Partie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * Repository qui gère les Pièces en Base de données.
 */
public interface PartieRepository extends JpaRepository<Partie, Long> {


    // Enregistrer / Modifier une partie :
    @Override
    <S extends Partie> S save(S entity);

    // Enregistrer / Modifier une partie :
    void save(Optional<Partie> partie);
}
