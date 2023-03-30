package com.Applications.EchecsBackend.repository.echecs;

import com.Applications.EchecsBackend.models.echecs.Couleur;
import org.springframework.data.jpa.repository.JpaRepository;





/**
 * Repository qui gère les Couleurs des pièces en Base de données.
 */
public interface CouleurRepository extends JpaRepository<Couleur, Long> {



}
