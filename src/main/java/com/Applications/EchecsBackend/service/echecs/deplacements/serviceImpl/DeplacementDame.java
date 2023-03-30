package com.Applications.EchecsBackend.service.echecs.deplacements.serviceImpl;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.repository.echecs.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;





/**
 * Service qui gère les fonctionnalités de déplacement des Dames.
 */
@Service
public class DeplacementDame {





    // ********************* Dépendances *********************
    @Autowired
    private final DeplacementTour deplacementTour;
    private final DeplacementFou deplacementFou;
    private final CaseRepository caseRepository;





    // ********************* Constructeur ******************** :
    @Autowired
    public DeplacementDame(CaseRepository caseRepository, DeplacementTour deplacementTour, DeplacementFou deplacementFou, DeplacementTour deplacementTour1, DeplacementFou deplacementFou1) {
        this.caseRepository = caseRepository;
        this.deplacementTour = deplacementTour1;
        this.deplacementFou = deplacementFou1;
    }





    // ********************* Méthodes ******************** :

    /**
     * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
     * d'une pièce de type Reine.
     * @return boolean
     * @throws Exception
     */
    public boolean deplacementDame(Case caseDepart, Case caseDestination, List<Case> echiquier) throws Exception
    {
        if(deplacementTour.deplacementTour(caseDepart, caseDestination, echiquier) || deplacementFou.deplacementFou(caseDepart, caseDestination, echiquier))
        {
            return true;
        } else
        {
            return false;
        }
    }





}
