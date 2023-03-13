package com.Applications.EchecsBackend.service.echecs.deplacements;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Piece;
import com.Applications.EchecsBackend.repository.echecs.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;





/**
 * Service qui gère les fonctionnalités de déplacement des Tours.
 */
@Service
public class DeplacementTour {





    // ********************* Dépendances *********************
    Piece tour = new Piece();
    private final CaseRepository caseRepository;





    // ********************* Constructeur ******************** :
    @Autowired
    public DeplacementTour(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }





    // ********************* Méthodes ******************** :
    /**
     * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
     * d'une pièce de type Tour.
     * @return boolean
     * @throws Exception
     */
    public boolean deplacementTour(Case caseDepart, Case caseDestination, List<Case> echiquier) throws Exception
    {
        // Récupération des numéros des cases :
        int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
        int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());
        String plus = "plus";
        String moins = "moins";
        Case caseDepartPlus1 = tour.calculCaseIntermediaire(caseDepart, 1L, echiquier, plus);
        Case caseDepartPlus2 = tour.calculCaseIntermediaire(caseDepart, 2L, echiquier, plus);
        Case caseDepartPlus3 = tour.calculCaseIntermediaire(caseDepart, 3L, echiquier, plus);
        Case caseDepartPlus4 = tour.calculCaseIntermediaire(caseDepart, 4L, echiquier, plus);
        Case caseDepartPlus5 = tour.calculCaseIntermediaire(caseDepart, 5L, echiquier, plus);
        Case caseDepartPlus6 = tour.calculCaseIntermediaire(caseDepart, 6L, echiquier, plus);

        Case caseDepartMoins1 = tour.calculCaseIntermediaire(caseDepart, 2L, echiquier, moins);
        Case caseDepartMoins2 = tour.calculCaseIntermediaire(caseDepart, 3L, echiquier, moins);
        Case caseDepartMoins3 = tour.calculCaseIntermediaire(caseDepart, 4L, echiquier, moins);
        Case caseDepartMoins4 = tour.calculCaseIntermediaire(caseDepart, 5L, echiquier, moins);
        Case caseDepartMoins5 = tour.calculCaseIntermediaire(caseDepart, 6L, echiquier, moins);
        Case caseDepartMoins6 = tour.calculCaseIntermediaire(caseDepart, 7L, echiquier, moins);

        Case caseDepartPlus8 = tour.calculCaseIntermediaire(caseDepart, 9L, echiquier, plus);
        Case caseDepartPlus16 = tour.calculCaseIntermediaire(caseDepart, 15L, echiquier, plus);
        Case caseDepartPlus24 = tour.calculCaseIntermediaire(caseDepart, 23L, echiquier, plus);
        Case caseDepartPlus32 = tour.calculCaseIntermediaire(caseDepart, 31L, echiquier, plus);
        Case caseDepartPlus40 = tour.calculCaseIntermediaire(caseDepart, 39L, echiquier, plus);
        Case caseDepartPlus48 = tour.calculCaseIntermediaire(caseDepart, 47L, echiquier, plus);

        Case caseDepartMoins8 = tour.calculCaseIntermediaire(caseDepart, 9L, echiquier, moins);
        Case caseDepartMoins16 = tour.calculCaseIntermediaire(caseDepart, 17L, echiquier, moins);
        Case caseDepartMoins24 = tour.calculCaseIntermediaire(caseDepart, 25L, echiquier, moins);
        Case caseDepartMoins32 = tour.calculCaseIntermediaire(caseDepart, 33L, echiquier, moins);
        Case caseDepartMoins40 = tour.calculCaseIntermediaire(caseDepart, 41L, echiquier, moins);
        Case caseDepartMoins48 = tour.calculCaseIntermediaire(caseDepart, 49L, echiquier, moins);

        // Déplacements possibles :
        // Contrôle ligne droite verticale :
        if (caseDestination.getNo_case() == caseDepart.getNo_case()+ 1
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 2 && caseDepartPlus1.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 3 && caseDepartPlus2.getPiece() == null && caseDepartPlus1.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 4 && caseDepartPlus3.getPiece() == null && caseDepartPlus2.getPiece() == null && caseDepartPlus1.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 5 && caseDepartPlus4.getPiece() == null && caseDepartPlus3.getPiece() == null && caseDepartPlus2.getPiece() == null && caseDepartPlus1.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 6 && caseDepartPlus5.getPiece() == null && caseDepartPlus4.getPiece() == null && caseDepartPlus3.getPiece() == null && caseDepartPlus2.getPiece() == null && caseDepartPlus1.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 7 && caseDepartPlus6.getPiece() == null && caseDepartPlus5.getPiece() == null && caseDepartPlus4.getPiece() == null && caseDepartPlus3.getPiece() == null && caseDepartPlus2.getPiece() == null && caseDepartPlus1.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 1
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 2 && caseDepartMoins1.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 3 && caseDepartMoins2.getPiece() == null && caseDepartMoins1.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 4 && caseDepartMoins3.getPiece() == null && caseDepartMoins2.getPiece()== null && caseDepartMoins1.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 5 && caseDepartMoins4.getPiece() == null && caseDepartMoins3.getPiece()== null && caseDepartMoins2.getPiece() == null && caseDepartMoins1.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 6 && caseDepartMoins5.getPiece() == null && caseDepartMoins4.getPiece() == null && caseDepartMoins3.getPiece() == null && caseDepartMoins2.getPiece() == null && caseDepartMoins1.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 7 && caseDepartMoins6.getPiece() == null && caseDepartMoins5.getPiece() == null && caseDepartMoins4.getPiece() == null && caseDepartMoins3.getPiece()== null && caseDepartMoins2.getPiece() == null && caseDepartMoins1.getPiece() == null

                // Contrôle ligne droite horizontale :
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 8
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 16 && caseDepartPlus8.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 24 && caseDepartPlus16.getPiece() == null && caseDepartPlus8.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 32 && caseDepartPlus24.getPiece() == null && caseDepartPlus16.getPiece() == null && caseDepartPlus8.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 40 && caseDepartPlus32.getPiece() == null && caseDepartPlus24.getPiece() == null && caseDepartPlus16.getPiece() == null && caseDepartPlus8.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 48 && caseDepartPlus40.getPiece() == null && caseDepartPlus32.getPiece() == null && caseDepartPlus24.getPiece() == null && caseDepartPlus16.getPiece() == null && caseDepartPlus8.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 56 && caseDepartPlus48.getPiece()== null && caseDepartPlus40.getPiece() == null && caseDepartPlus32.getPiece() == null && caseDepartPlus24.getPiece() == null && caseDepartPlus16.getPiece() == null && caseDepartPlus8.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case()- 8
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 16 && caseDepartMoins8.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 24 && caseDepartMoins16.getPiece()== null && caseDepartMoins8.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 32 && caseDepartMoins24.getPiece() == null && caseDepartMoins16.getPiece() == null && caseDepartMoins8.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 40 && caseDepartMoins32.getPiece() == null && caseDepartMoins24.getPiece() == null && caseDepartMoins16.getPiece() == null && caseDepartMoins8.getPiece()== null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 48 && caseDepartMoins40.getPiece() == null && caseDepartMoins32.getPiece() == null && caseDepartMoins24.getPiece() == null && caseDepartMoins16.getPiece() == null && caseDepartMoins8.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 56 && caseDepartMoins48.getPiece() == null && caseDepartMoins40.getPiece() == null && caseDepartMoins32.getPiece() == null && caseDepartMoins24.getPiece() == null && caseDepartMoins16.getPiece() == null && caseDepartMoins8.getPiece() == null
        )
        {
            if (tour.verificationCampPieceCaseDestination(caseDepart, caseDestination))
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
     * Méthode qui définit les limites de l'échiquier pour une Tour.
     * @return boolean
     */
    public boolean borduresTour(Case caseDepart, Case caseDestination)
    {
        Piece piece = caseDepart.getPiece();
        List<String> nomPiece = List.of(piece.getType().split(" "));
        String typeDePiece = nomPiece.get(0);
        if(typeDePiece.equals("tour")
                && caseDepart.getNo_case() == 8L
                || caseDepart.getNo_case() == 57L
                && caseDestination.getNo_case() == 9L
                || caseDestination.getNo_case() == 56L
                || caseDestination.getNo_case() == 50L
                || caseDestination.getNo_case() == 51L
                || caseDestination.getNo_case() == 52L
                || caseDestination.getNo_case() == 54L
                || caseDestination.getNo_case() == 55L
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