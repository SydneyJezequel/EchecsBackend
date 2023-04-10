package com.Applications.EchecsBackend.service.echecs.deplacements.serviceImpl;

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
     * Méthode qui contrôle que le déplacement correspond aux règles de déplacement.
     * d'une pièce de type Cavalier.
     * @return boolean
     * @throws Exception
     */
    public boolean deplacementCavalier(Case caseDepart, Case caseDestination, List<Case> echiquier) throws Exception
    {
        // 1- Attributs :
        boolean deplacementCavalierAutorise;
        int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
        int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());

        // 2- Contrôle des déplacements possibles :
        // Si déplacement du cavalier Ok :
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
           if(borduresCavalier(caseDepart, caseDestination))
            {
                deplacementCavalierAutorise = false;
            }
            // Si le cavalier ne dépasse les bordures de l'échiquier :
            else
            {
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
            }
        }
        // Si déplacement du cavalier Nok :
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
        // Attributs :
        int ligneCaseDeDepart = caseDepart.getLigne();
        boolean bordureDepasse = false;
        // Contrôle :
        switch(ligneCaseDeDepart)
        {
            case 8:
                if(caseDestination.getNo_case() == caseDepart.getNo_case()+6L
                || caseDestination.getNo_case() == caseDepart.getNo_case()-10L
                || caseDestination.getNo_case() == caseDepart.getNo_case()+15L
                || caseDestination.getNo_case() == caseDepart.getNo_case()-17L)
                {
                    bordureDepasse = true;
                }
                break;
            case 7:
                if(caseDestination.getNo_case() == caseDepart.getNo_case()+6L
                || caseDestination.getNo_case() == caseDepart.getNo_case()-10L)
                {
                    bordureDepasse = true;
                }
                break;
            case 2:
                if(caseDestination.getNo_case() == caseDepart.getNo_case()-6L
                || caseDestination.getNo_case() == caseDepart.getNo_case()+10L)
                {
                    bordureDepasse = true;
                }
                break;
            case 1:
                if(caseDestination.getNo_case() == caseDepart.getNo_case()-6L
                || caseDestination.getNo_case() == caseDepart.getNo_case()+10L
                || caseDestination.getNo_case() == caseDepart.getNo_case()-15L
                || caseDestination.getNo_case() == caseDepart.getNo_case()+17L
                || caseDestination.getNo_case() == caseDepart.getNo_case()-17L
                )
                {
                    bordureDepasse = true;
                }
                break;
            default:
                bordureDepasse = false;
        }
        return bordureDepasse;
    }





}
