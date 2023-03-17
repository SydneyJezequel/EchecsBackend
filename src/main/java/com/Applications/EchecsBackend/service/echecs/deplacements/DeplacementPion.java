package com.Applications.EchecsBackend.service.echecs.deplacements;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Piece;
import com.Applications.EchecsBackend.repository.echecs.CaseRepository;
import com.Applications.EchecsBackend.repository.echecs.PieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;





/**
 * Service qui gère les fonctionnalités de déplacement des Pions.
 */
@Service
public class DeplacementPion {





    // ********************* Dépendances *********************
    Piece pion = new Piece();
    private final CaseRepository caseRepository;
    private final PieceRepository pieceRepository;





    // ********************* Constructeur ******************** :
    @Autowired
    public DeplacementPion(CaseRepository caseRepository, PieceRepository pieceRepository) {
        this.caseRepository = caseRepository;
        this.pieceRepository = pieceRepository;
    }





    // ********************* Méthodes ******************** :
    /**
     * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
     * d'une pièce de type Pion.
     * @return boolean
     * @throws Exception
     */
    public boolean deplacementPion(Case caseDepart, Case caseDestination, Piece piece, List<Case> echiquier) throws Exception
    {
        // Attributs :
        boolean deplacementPionAutorise;
        int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
        int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());
        Case caseIntermediaire;

        // Contrôles :
        if (piece.getCouleur().getCouleur().equals("blanc"))
        {
            caseIntermediaire = echiquier.get(Math.toIntExact(caseDepart.getNo_case()-2L));
        }else
        {
            caseIntermediaire = echiquier.get(Math.toIntExact(caseDepart.getNo_case()));
        }

        // Contrôle des déplacements possibles :
        if((noCaseDestination == noCaseDepart + 1 && caseDestination.getPiece() == null)
                || (noCaseDestination == noCaseDepart -1 && caseDestination.getPiece() == null)
                || (noCaseDestination == noCaseDepart +2 && caseDestination.getPiece()== null && piece.getCouleur().getCouleur().equals("noir") && (caseDepart.getNo_case() == 2L || caseDepart.getNo_case() == 10L || caseDepart.getNo_case() == 18L || caseDepart.getNo_case() == 26L || caseDepart.getNo_case() == 34L || caseDepart.getNo_case() == 42L || caseDepart.getNo_case() == 50L || caseDepart.getNo_case() == 58L) && caseIntermediaire.getPiece() == null)
                || (noCaseDestination == noCaseDepart -2 && caseDestination.getPiece()== null && piece.getCouleur().getCouleur().equals("blanc") && (caseDepart.getNo_case() == 7L || caseDepart.getNo_case() == 15L || caseDepart.getNo_case() == 23L || caseDepart.getNo_case() == 31L || caseDepart.getNo_case() == 39L || caseDepart.getNo_case() == 47L || caseDepart.getNo_case() == 55L|| caseDepart.getNo_case() == 63L) && caseIntermediaire.getPiece() == null)
                || (noCaseDestination == noCaseDepart +9 && caseDestination.getPiece() != null)
                || (noCaseDestination == noCaseDepart -9 && caseDestination.getPiece() != null)
                || (noCaseDestination == noCaseDepart +7 && caseDestination.getPiece() != null)
                || (noCaseDestination == noCaseDepart -7 && caseDestination.getPiece() != null)
        )
        {
            // Si le pion dépasse les bordures de l'échiquier :
            if(borduresPion(caseDepart, caseDestination))
            {
                deplacementPionAutorise = false;
            }
            // Si le pion ne dépasse les bordures de l'échiquier :
            else
            {
                // Si la pièce sur la case de destination n'est pas du même camp que le pion :
                if (pion.verificationCampPieceCaseDestination(caseDepart, caseDestination))
                {
                    deplacementPionAutorise = true;
                }
                // Si la pièce sur la case de destination est du même camp que le pion :
                else
                {
                    deplacementPionAutorise = false;
                }
            }
        }
        // Si la façon dont se déplace le pion est incorrect :
        else
        {
            deplacementPionAutorise = false;
        }
        return deplacementPionAutorise;
    }



    /**
     * Méthode qui définit les limites de l'échiquier pour un Pion.
     * @return boolean
     */
    public boolean borduresPion(Case caseDepart, Case caseDestination)
    {
        // Attributs :
        boolean bordureDepasse = false;
        int ligneCaseDepart = caseDepart.getLigne();

        // Contrôles :
        switch (ligneCaseDepart)
        {
            case 8:
                if(caseDestination.getNo_case()==caseDepart.getNo_case()-1L)
                {
                    bordureDepasse = true;
                }
                break;
            case 1:
                if(caseDestination.getNo_case()==caseDepart.getNo_case()+1L)
                {
                    bordureDepasse = true;
                }
                break;
            default:
                bordureDepasse = false;
        }
        return  bordureDepasse;
    }




    /**
     * Cette méthode remplace le pion par une des pièce choisie.
     * @param caseDestination : Contient la case de Destination du pion et le type de la nouvelle Pièce.
     * @throws Exception
     */
    public void transformationPion(Case caseDestination) throws Exception
    {
        // Création de la pièce :
        Piece pionTransforme = caseDestination.getPiece();
        String typeNouvellePiece = caseDestination.getPiece().getType();

        // Choix de la nouvelle pièce :
        switch(typeNouvellePiece)
        {
            case "reine":
                // Création Reine :
                if(pionTransforme.getCouleur().getCouleur().equals("noir"))
                {
                    pionTransforme.setType("reine noir");
                }else
                {
                    pionTransforme.setType("reine blanc");
                }
                break;
            case "tour":
                // Création tour :
                if(pionTransforme.getCouleur().getCouleur().equals("noir"))
                {
                    pionTransforme.setType("tour noir");
                }else
                {
                    pionTransforme.setType("tour blanc");
                }
                break;
            case "fou":
                // Création fou :
                if(pionTransforme.getCouleur().getCouleur().equals("noir"))
                {
                    pionTransforme.setType("fou noir");
                }else
                {
                    pionTransforme.setType("fou blanc");
                }
                break;
            case "cavalier":
                if(pionTransforme.getCouleur().getCouleur().equals("noir"))
                {
                    pionTransforme.setType("cavalier noir");
                }else
                {
                    pionTransforme.setType("cavalier blanc");
                }
            default: // Pion
                // Création du pion
                if(pionTransforme.getCouleur().getCouleur().equals("noir"))
                {
                    pionTransforme.setType("pion noir");
                }else
                {
                    pionTransforme.setType("pion blanc");
                }
        }
        pieceRepository.save(pionTransforme);
    }





}
