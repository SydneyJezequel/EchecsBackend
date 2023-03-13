package com.Applications.EchecsBackend.service.echecs.deplacements;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Piece;
import com.Applications.EchecsBackend.repository.echecs.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;





/**
 * Service qui gère les fonctionnalités de déplacement des Cavaliers.
 */
@Service
public class DeplacementCavalier {





    // ********************* Dépendances *********************
    Piece cavalier = new Piece();
    private final CaseRepository caseRepository;





    // ********************* Constructeur ******************** :
    @Autowired
    public DeplacementCavalier(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }





    // ********************* Méthodes ******************** :


    /**
     * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
     * d'une pièce de type Cavalier.
     * @return boolean
     * @throws Exception
     */
    public boolean deplacementCavalier(Case caseDepart, Case caseDestination, List<Case> echiquier) throws Exception
    {
        // VERSION TEMPORAIRE : AJOUTER UN BLOCAGE DE DEPLACEMENT SUR LES PIECES DU MEME CAMP.
        // Récupération des numéros des cases :
        int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
        int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());
        // Contrôle des déplacements possibles :
        if(
                noCaseDestination == noCaseDepart + 6
                        || noCaseDestination == noCaseDepart + 10
                        || noCaseDestination == noCaseDepart + 15
                        || noCaseDestination == noCaseDepart + 17
                        || noCaseDestination == noCaseDepart - 6
                        || noCaseDestination == noCaseDepart - 10
                        || noCaseDestination == noCaseDepart - 15
                        || noCaseDestination == noCaseDepart - 17
        )
        {
            if (cavalier.verificationCampPieceCaseDestination(caseDepart, caseDestination))
            {
                return true;
            }else
            {
                return false;
            }
        } else
        {
            return false;
        }
    }



    /**
     * Méthode qui définit les limites de l'échiquier pour un Cavalier.
     * @return boolean
     */
    public boolean borduresCavalier(Case caseDepart, Case caseDestination)
    {
        Piece piece = caseDepart.getPiece();
        List<String> nomPiece = List.of(piece.getType().split(" "));
        String typeDePiece = nomPiece.get(0);
        if(typeDePiece.equals("cavalier")
                && caseDepart.getNo_case() == 16L
                || caseDepart.getNo_case() == 49L
                || caseDepart.getNo_case() == 9L
                && caseDestination.getNo_case() == 1L
                || caseDestination.getNo_case() == 10L
                || caseDestination.getNo_case() == 26L
                || caseDestination.getNo_case() == 33L
                || caseDestination.getNo_case() == 41L
                || caseDestination.getNo_case() == 50L
                || caseDestination.getNo_case() == 15L
                || caseDestination.getNo_case() == 24L
                || caseDestination.getNo_case() == 64L
                || caseDestination.getNo_case() == 55L
                || caseDestination.getNo_case() == 39L
                || caseDestination.getNo_case() == 32L
        )
        {
            return false;
        }
        else
        {
            return true;
        }
    }




}
