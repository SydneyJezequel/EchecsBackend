package com.Applications.EchecsBackend.service.echecs.deplacements.serviceImpl;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Piece;
import com.Applications.EchecsBackend.repository.echecs.CaseRepository;
import com.Applications.EchecsBackend.repository.echecs.CouleurRepository;
import com.Applications.EchecsBackend.repository.echecs.PieceRepository;
import com.Applications.EchecsBackend.service.echecs.gestionPartie.serviceImpl.GestionDesParties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;





/**
 * Service qui gère les fonctionnalités de déplacement des Roi.
 */
@Service
public class DeplacementRoi {





    // ********************* Attributs ******************** :
    Piece roi = new Piece();
    private final CaseRepository caseRepository;
    private final PieceRepository pieceRepository;
    private final CouleurRepository couleurRepository;
    private final GestionDesParties gestionDesParties;
    private final DeplacementDame deplacementDame;
    private final DeplacementTour deplacementTour;
    private final DeplacementFou deplacementFou;
    private final DeplacementCavalier deplacementCavalier;
    private final DeplacementPion deplacementPion;





    // ********************* Constructeur ******************** :
    @Autowired
    public DeplacementRoi(CaseRepository caseRepository,
                          PieceRepository pieceRepository,
                          CouleurRepository couleurRepository,
                          GestionDesParties gestionDesParties,
                          DeplacementDame deplacementDame,
                          DeplacementTour deplacementTour,
                          DeplacementFou deplacementFou,
                          DeplacementCavalier deplacementCavalier,
                          DeplacementPion deplacementPion) {
        this.caseRepository = caseRepository;
        this.pieceRepository = pieceRepository;
        this.couleurRepository = couleurRepository;
        this.gestionDesParties = gestionDesParties;
        this.deplacementDame = deplacementDame;
        this.deplacementTour = deplacementTour;
        this.deplacementFou = deplacementFou;
        this.deplacementCavalier = deplacementCavalier;
        this.deplacementPion = deplacementPion;
    }





    // ********************* Méthodes ******************** :

    /**
     * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
     * d'une pièce de type Roi.
     * @return boolean
     * @throws Exception
     */
    public boolean deplacementRoi(Case caseDepart, Case caseDestination) throws Exception
    {
        // 1- Attributs :
        boolean deplacementRoiAutorise;
        int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
        int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());
        // 2- Contrôles du déplacement :
        // Si déplacement du Roi Ok :
        if(noCaseDestination == noCaseDepart+1
                || noCaseDestination == noCaseDepart-1
                || noCaseDestination == noCaseDepart+8
                || noCaseDestination == noCaseDepart-8
                || noCaseDestination == noCaseDepart+7
                || noCaseDestination == noCaseDepart-7
                || noCaseDestination == noCaseDepart+9
                || noCaseDestination == noCaseDepart-9
        )
        {
            // Si le roi dépasse les bordures de l'échiquier :
            if(borduresRoi(caseDepart, caseDestination))
            {
                deplacementRoiAutorise = false;
            }
            // Si le roi ne dépasse les bordures de l'échiquier :
            else
            {
                // Si la pièce sur la case de destination n'est pas du même camp que le roi :
                if (roi.verificationCampPieceCaseDestination(caseDepart, caseDestination))
                {
                    deplacementRoiAutorise = true;
                }
                // Si la pièce sur la case de destination est du même camp que le roi :
                else
                {
                    deplacementRoiAutorise = false;
                }
            }
        }
        // Si déplacement du roi Nok :
        else
        {
            deplacementRoiAutorise = false;
        }
        return deplacementRoiAutorise;
    }




    /**
     * Méthode qui définit les limites de l'échiquier pour un Roi.
     * @return boolean
     */
    public boolean borduresRoi(Case caseDepart, Case caseDestination)
    {
        // Attributs :
        boolean bordureDepasse = false;
        int ligneCaseDepart = caseDepart.getLigne();

        // Contrôles du déplacement :
        switch (ligneCaseDepart)
        {
            case 8:
                if(caseDestination.getNo_case()==caseDepart.getNo_case()+7L
                || caseDestination.getNo_case()==caseDepart.getNo_case()-1L
                || caseDestination.getNo_case()==caseDepart.getNo_case()-9L)
                {
                    bordureDepasse = true;
                }
                break;
            case 1:
                if(caseDestination.getNo_case()==caseDepart.getNo_case()-7L
                || caseDestination.getNo_case()==caseDepart.getNo_case()+1L
                || caseDestination.getNo_case()==caseDepart.getNo_case()+9L)
                {
                    bordureDepasse = true;
                }
                break;
            default:
                bordureDepasse = false;
        }
        return  bordureDepasse;
    }





}
