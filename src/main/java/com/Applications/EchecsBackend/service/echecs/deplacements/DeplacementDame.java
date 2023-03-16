package com.Applications.EchecsBackend.service.echecs.deplacements;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Piece;
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



    /**
     * Méthode qui définit les limites de l'échiquier pour une Dame.
     * @return boolean
     */
    public boolean borduresDame(Case caseDepart, Case caseDestination)
    {
        if(deplacementFou.borduresFou(caseDepart, caseDestination) || deplacementTour.borduresTour(caseDepart, caseDestination))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    // ANCIEN ALGORITHME :
            /*
        Piece piece = caseDepart.getPiece();
        List<String> nomPiece = List.of(piece.getType().split(" "));
        String typeDePiece = nomPiece.get(0);
        if(typeDePiece.equals("reine")
                && caseDepart.getNo_case() == 32L
                || caseDepart.getNo_case() == 25L
                && caseDestination.getNo_case() == 25L
                || caseDestination.getNo_case() == 33L
                || caseDestination.getNo_case() == 41L
                || caseDestination.getNo_case() == 18L
                || caseDestination.getNo_case() == 50L
                || caseDestination.getNo_case() == 11L
                || caseDestination.getNo_case() == 4L
                || caseDestination.getNo_case() == 16L
                || caseDestination.getNo_case() == 24L
                || caseDestination.getNo_case() == 32L
                || caseDestination.getNo_case() == 7L
                || caseDestination.getNo_case() == 23L
                || caseDestination.getNo_case() == 39L
        )
        */





}
