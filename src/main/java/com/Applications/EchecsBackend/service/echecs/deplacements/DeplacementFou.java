package com.Applications.EchecsBackend.service.echecs.deplacements;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Piece;
import com.Applications.EchecsBackend.repository.echecs.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;





/**
 * Service qui gère les fonctionnalités de déplacement des Fous.
 */
@Service
public class DeplacementFou {





    // ********************* Dépendances *********************
    Piece fou = new Piece();
    private final CaseRepository caseRepository;





    // ********************* Constructeur ******************** :
    @Autowired
    public DeplacementFou(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }





    // ********************* Méthodes ******************** :
    /**
     * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
     * d'une pièce de type Fou.
     * @return boolean
     * @throws Exception
     */
    public boolean deplacementFou(Case caseDepart, Case caseDestination, List<Case> echiquier) throws Exception
    {
        // Récupération des numéros des cases :
        int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
        int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());
        String plus = "plus";
        String moins = "moins";
        Case caseDepartPlus9 = fou.calculCaseIntermediaire(caseDepart, 8L, echiquier, plus);
        Case caseDepartPlus18 = fou.calculCaseIntermediaire(caseDepart, 17L, echiquier, plus);
        Case caseDepartPlus27 = fou.calculCaseIntermediaire(caseDepart, 26L, echiquier, plus);
        Case caseDepartPlus36 = fou.calculCaseIntermediaire(caseDepart, 35L, echiquier, plus);
        Case caseDepartPlus45 = fou.calculCaseIntermediaire(caseDepart, 44L, echiquier, plus);
        Case caseDepartPlus54 = fou.calculCaseIntermediaire(caseDepart, 53L, echiquier, plus);
        Case caseDepartPlus63 = fou.calculCaseIntermediaire(caseDepart, 62L, echiquier, plus);

        Case caseDepartMoins9 = fou.calculCaseIntermediaire(caseDepart, 10L, echiquier, moins);
        Case caseDepartMoins18 = fou.calculCaseIntermediaire(caseDepart, 19L, echiquier, moins);
        Case caseDepartMoins27 = fou.calculCaseIntermediaire(caseDepart, 28L, echiquier, moins);
        Case caseDepartMoins36 = fou.calculCaseIntermediaire(caseDepart, 37L, echiquier, moins);
        Case caseDepartMoins45 = fou.calculCaseIntermediaire(caseDepart, 46L, echiquier, moins);
        Case caseDepartMoins54 = fou.calculCaseIntermediaire(caseDepart, 55L, echiquier, moins);
        Case caseDepartMoins63 = fou.calculCaseIntermediaire(caseDepart, 64L, echiquier, moins);

        Case caseDepartPlus7 = fou.calculCaseIntermediaire(caseDepart, 6L, echiquier, plus);
        Case caseDepartPlus14 = fou.calculCaseIntermediaire(caseDepart, 13L, echiquier, plus);
        Case caseDepartPlus21 = fou.calculCaseIntermediaire(caseDepart, 20L, echiquier, plus);
        Case caseDepartPlus28 = fou.calculCaseIntermediaire(caseDepart, 27L, echiquier, plus);
        Case caseDepartPlus35 = fou.calculCaseIntermediaire(caseDepart, 34L, echiquier, plus);
        Case caseDepartPlus42 = fou.calculCaseIntermediaire(caseDepart, 41L, echiquier, plus);
        Case caseDepartPlus49 = fou.calculCaseIntermediaire(caseDepart, 48L, echiquier, plus);

        Case caseDepartMoins7 = fou.calculCaseIntermediaire(caseDepart, 8L, echiquier, moins);
        Case caseDepartMoins14 = fou.calculCaseIntermediaire(caseDepart, 15L, echiquier, moins);
        Case caseDepartMoins21 = fou.calculCaseIntermediaire(caseDepart, 22L, echiquier, moins);
        Case caseDepartMoins28 = fou.calculCaseIntermediaire(caseDepart, 29L, echiquier, moins);
        Case caseDepartMoins35 = fou.calculCaseIntermediaire(caseDepart, 36L, echiquier, moins);
        Case caseDepartMoins42 = fou.calculCaseIntermediaire(caseDepart, 43L, echiquier, moins);
        Case caseDepartMoins49 = fou.calculCaseIntermediaire(caseDepart, 50L, echiquier, moins);

        // Déplacements possibles :
        // Contrôle diagonale (droite/gauche/haut/bas) :
        if (caseDestination.getNo_case() == caseDepart.getNo_case() + 9
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 18 && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 27 && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 36 && caseDepartPlus27.getPiece() == null && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 45 && caseDepartPlus36.getPiece()== null && caseDepartPlus27.getPiece() == null && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 54 && caseDepartPlus45.getPiece() == null  && caseDepartPlus36.getPiece() == null && caseDepartPlus27.getPiece() == null && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 63 && caseDepartPlus54.getPiece() == null && caseDepartPlus45.getPiece() == null  && caseDepartPlus36.getPiece() == null && caseDepartPlus27.getPiece() == null && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 72 && caseDepartPlus63.getPiece() == null && caseDepartPlus54.getPiece() == null && caseDepartPlus45.getPiece() == null  && caseDepartPlus36.getPiece() == null && caseDepartPlus27.getPiece() == null && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 7
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 14 && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 21 && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 28 && caseDepartPlus21.getPiece() == null && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 35 && caseDepartPlus28.getPiece() == null && caseDepartPlus21.getPiece() == null && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 42 && caseDepartPlus35.getPiece() == null && caseDepartPlus28.getPiece() == null && caseDepartPlus21.getPiece() == null && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 49 && caseDepartPlus42.getPiece() == null && caseDepartPlus35.getPiece() == null && caseDepartPlus28.getPiece() == null && caseDepartPlus21.getPiece() == null && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 56 && caseDepartPlus49.getPiece() == null && caseDepartPlus42.getPiece() == null && caseDepartPlus35.getPiece() == null && caseDepartPlus28.getPiece() == null && caseDepartPlus21.getPiece() == null && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null

                || caseDestination.getNo_case() == caseDepart.getNo_case() - 9
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 18 && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 27 && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 36 && caseDepartMoins27.getPiece() == null && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 45 && caseDepartMoins36.getPiece() == null && caseDepartMoins27.getPiece() == null && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 54 && caseDepartMoins45.getPiece() == null && caseDepartMoins36.getPiece() == null && caseDepartMoins27.getPiece() == null && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 63 && caseDepartMoins54.getPiece() == null && caseDepartMoins45.getPiece() == null&& caseDepartMoins36.getPiece() == null && caseDepartMoins27.getPiece() == null && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 72 && caseDepartMoins63.getPiece() == null && caseDepartMoins54.getPiece() == null && caseDepartMoins45.getPiece() == null && caseDepartMoins36.getPiece() == null && caseDepartMoins27.getPiece() == null && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 7
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 14 && caseDepartMoins7.getPiece()== null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 21 && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 28 && caseDepartMoins21.getPiece()== null && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 35 && caseDepartMoins28.getPiece() == null && caseDepartMoins21.getPiece() == null && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 42 && caseDepartMoins35.getPiece() == null && caseDepartMoins28.getPiece() == null && caseDepartMoins21.getPiece() == null && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 49 && caseDepartMoins42.getPiece() == null && caseDepartMoins35.getPiece() == null && caseDepartMoins28.getPiece() == null && caseDepartMoins21.getPiece() == null && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 56 && caseDepartMoins49.getPiece() == null && caseDepartMoins42.getPiece() == null && caseDepartMoins35.getPiece() == null && caseDepartMoins28.getPiece() == null && caseDepartMoins21.getPiece() == null && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
        )
        {
            if (fou.verificationCampPieceCaseDestination(caseDepart, caseDestination))
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
     * Méthode qui définit les limites de l'échiquier pour un Fou.
     * @return boolean
     */
    public boolean borduresFou(Case caseDepart, Case caseDestination)
    {
        Piece piece = caseDepart.getPiece();
        List<String> nomPiece = List.of(piece.getType().split(" "));
        String typeDePiece = nomPiece.get(0);
        if(typeDePiece.equals("fou")
                && caseDepart.getNo_case() == 24L
                || caseDepart.getNo_case() == 48L
                || caseDepart.getNo_case() == 17L
                || caseDepart.getNo_case() == 41L
                && caseDestination.getNo_case() == 17L
                || caseDestination.getNo_case() == 10L
                || caseDestination.getNo_case() == 3L
                || caseDestination.getNo_case() == 33L
                || caseDestination.getNo_case() == 57L
                || caseDestination.getNo_case() == 41L
                || caseDestination.getNo_case() == 34L
                || caseDestination.getNo_case() == 27L
                || caseDestination.getNo_case() == 20L
                || caseDestination.getNo_case() == 13L
                || caseDestination.getNo_case() == 6L
                || caseDestination.getNo_case() == 24L
                || caseDestination.getNo_case() == 31L
                || caseDestination.getNo_case() == 38L
                || caseDestination.getNo_case() == 45L
                || caseDestination.getNo_case() == 52L
                || caseDestination.getNo_case() == 59L
                || caseDestination.getNo_case() == 8L
                || caseDestination.getNo_case() == 48L
                || caseDestination.getNo_case() == 55L
                || caseDestination.getNo_case() == 62L
                || caseDestination.getNo_case() == 32L
                || caseDestination.getNo_case() == 23L
                || caseDestination.getNo_case() == 14L
                || caseDestination.getNo_case() == 5L
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
