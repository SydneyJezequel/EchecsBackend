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
        // Attributs :
        boolean deplacementCavalierAutorise;
        // Récupération des numéros des cases :
        int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
        int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());
        // Contrôle des déplacements possibles :
        // Si la façon dont se déplace le cavalier est correct :
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
            // Si le cavalier dépasse les bordures de l'échiquier :
           // if(borduresCavalier(caseDepart, caseDestination))
            //{
                deplacementCavalierAutorise = false;
            //}
            // Si le cavalier ne dépasse les bordures de l'échiquier :
            //else
            //{
                // Si la pièce sur la case de destination n'est pas du même camp que le cavalier :
                if (cavalier.verificationCampPieceCaseDestination(caseDepart, caseDestination))
                {
                    deplacementCavalierAutorise = true;
                }
                // Si la pièce sur la case de destination est du même camp que le cavalier :
                else
                {
                    deplacementCavalierAutorise = false;
                }
            //}
        }
        // Si la façon dont se déplace le cavalier est incorrect :
        else
        {
            deplacementCavalierAutorise = false;
        }
        return deplacementCavalierAutorise;
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


    // NOUVELLE VERSION BORDURES CAVALIER :
    public boolean borduresCavalierV2(Case caseDepart, Case caseDestination)
    {
        // Attributs :
        int ligneCaseDeDepart = caseDepart.getLigne();
        boolean bordureDepasse = false;
        // Contrôle :
        switch(ligneCaseDeDepart)
        {
            case 8:
                if(caseDestination.getNo_case()==1L)
                {
                    bordureDepasse = true;
                }
                break;
            case 7:
                if(caseDestination.getNo_case()==1L)
                {
                    bordureDepasse = true;
                }
                break;
            case 2:
                if(caseDestination.getNo_case()==1L)
                {
                    bordureDepasse = true;
                }
                break;
            case 1:
                if(caseDestination.getNo_case()==1L)
                {
                    bordureDepasse = true;
                }
                break;
            default:

        }
        return bordureDepasse;
    }

// ALGORITHME TEMPORAIRE :
/*


——————————————————
SI COLONNE 3 :
Empêcher les -6.
Empêcher les +10.

Tests :
-6 —> PB
+6 —> PAS PB
+10 —> PB
-10 —> PAS PB
+15 —> PAS PB
-15 —> PAS PB
-17 —> PAS PB
+17—> PAS PB
——————————————————



——————————————————
SI COLONNE 1 :
Empêcher les -6
Empêcher les +10
Empêcher les -15
Empêcher les +17

Tests :
-6 —> PB
+6 —> PAS PB
+10 —> PB
-10 —> PAS PB
+15 —> PAS PB
-15 —> PB
-17 —> PAS PB
+17—> PB
——————————————————



——————————————————
SI COLONNE 7 :
Empêcher les +6.
Empêcher les -10.

Tests :
-6 —> PAS PB
+6 —> PB
+10 —> PAS PB
-10 —> PB
+15 —> PAS PB
-15 —> PAS PB
-17 —> PAS PB
+17—> PAS PB
——————————————————



——————————————————
SI COLONNE 8 :
Empêcher les +6
Empêcher les -10
Empêcher les +15
Empêcher les -17

Tests :
-6 —> PAS PB
+6 —> PB
+10 —> PAS PB
-10 —> PB
+15 —> PB
-15 —> PAS PB
-17 —> PB
+17—> PAS PB
——————————————————



*/



}
