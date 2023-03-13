package com.Applications.EchecsBackend.service.echecs;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Couleur;
import com.Applications.EchecsBackend.models.echecs.Piece;
import com.Applications.EchecsBackend.repository.echecs.CaseRepository;
import com.Applications.EchecsBackend.repository.echecs.PartieRepository;
import com.Applications.EchecsBackend.repository.echecs.PieceRepository;
import com.Applications.EchecsBackend.service.echecs.deplacements.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;





/**
 * Service qui gère les fonctionnalités pour jouer une partie d'échecs.
 */
@Service
public class JouerService {





    // ********************* Dépendances *********************

    private final CaseRepository caseRepository;
    private final DemarrerUnePartieService demarrerUnePartieService;
    private final PieceRepository pieceRepository;
    private final PartieRepository partieRepository;
    private final DeplacementRoi deplacementRoi;
    private final DeplacementDame deplacementDame;
    private final DeplacementTour deplacementTour;
    private final DeplacementFou deplacementFou;
    private final DeplacementCavalier deplacementCavalier;
    private final DeplacementPion deplacementPion;





    // ********************* Constructeur *********************
    @Autowired
    public JouerService(CaseRepository caseRepository,
                        DemarrerUnePartieService demarrerUnePartieService,
                        PieceRepository pieceRepository,
                        PartieRepository partieRepository,
                        DeplacementRoi deplacementRoi,
                        DeplacementDame deplacementDame,
                        DeplacementTour deplacementTour,
                        DeplacementFou deplacementFou,
                        DeplacementCavalier deplacementCavalier, DeplacementPion deplacementPion)
    {
        this.caseRepository = caseRepository;
        this.demarrerUnePartieService = demarrerUnePartieService;
        this.pieceRepository = pieceRepository;
        this.partieRepository = partieRepository;
        this.deplacementRoi = deplacementRoi;
        this.deplacementDame = deplacementDame;
        this.deplacementTour = deplacementTour;
        this.deplacementFou = deplacementFou;
        this.deplacementCavalier = deplacementCavalier;
        this.deplacementPion = deplacementPion;
    }





    // ****************************************** Méthodes ******************************************



    /**
     * Méthode qui renvoie toutes les cases
     * @return caseRepository.findAll()
     */
    public List<Case> findAllCases()
    {
        return caseRepository.findAll();
    }



    /**
     * Méthode qui renvoie toutes les cases de l'échiquier suite au déplacement
     * @return echiquierMaj
     */
    public List<Case> getEchiquier() {
        List<Case> echiquierMaj = caseRepository.findAll();

        return echiquierMaj;
    }



    /**
     * Méthode qui déplace les pièces.
     * Cette méthode va appeler les méthodes qui réalisent les contrôles
     * pour les déplacements de chaque pièce (ex : deplacementRoi(), deplacementReine(), etc.) :
     * Si les conditions sont vérifiés, la méthode : "echiquierMaj(List<Case> casesDeplacement)"
     * est appelée pour déplacer la pièce et renvoyer l'échiquier mis à jour.
     * @return echiquierMaj
     */
    public List<Case> deplacerPiece(List<Case> casesDeplacement) throws Exception {

        List<Case> echiquierMaj = new ArrayList<Case>();

        // 1- Récupération des cases et de la pièce :
        Case caseDepart = caseRepository.findById(casesDeplacement.get(0).getNo_case()).
                orElseThrow(() -> new Exception("Case not exist with id: " + casesDeplacement.get(0).getNo_case()));
        Case caseDestination = caseRepository.findById(casesDeplacement.get(1).getNo_case()).
                orElseThrow(() -> new Exception("Case not exist with id: " + casesDeplacement.get(1).getNo_case()));
        Piece piece = pieceRepository.findById(casesDeplacement.get(0).getPiece().getNo_piece()).
                orElseThrow(() -> new Exception("Piece not exist with id: " + casesDeplacement.get(0).getPiece().getNo_piece()));
        List<Case> echiquier = caseRepository.findAll();
        echiquier.sort(Comparator.comparing(Case::getNo_case));

        // 2- Contrôle des Règles de gestion :
        List<String> nomPiece = List.of(piece.getType().split(" "));
        String typeDePiece = nomPiece.get(0);
        switch (typeDePiece) {
            case "roi":
                // Déplacement de la pièce :
                if (deplacementRoi.deplacementRoi(caseDepart, caseDestination, echiquier) /*&& borduresRoi(caseDepart, caseDestination)*/) {
                    // Contrôles à exécuter sur le Roi :
                        //echecAuRoi();
                        // echecEtPat();
                    echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                }
                else if (deplacementRoi.Roque(caseDepart, caseDestination, echiquier)) {
                    if (caseDestination.getNo_case() == 56L) {
                        deplacementRoi.petitRoqueBlanc(caseDepart, caseDestination, piece, echiquier);
                    }else if(caseDestination.getNo_case()==49L)
                    {
                        deplacementRoi.petitRoqueNoir(caseDepart, caseDestination, piece, echiquier);
                    }else if(caseDestination.getNo_case()==24L)
                    {
                        deplacementRoi.grandRoqueBlanc(caseDepart, caseDestination, piece, echiquier);

                    }else if(caseDestination.getNo_case()==17L)
                    {
                        deplacementRoi.grandRoqueNoir(caseDepart, caseDestination, piece, echiquier);
                    }
                    echiquierMaj = caseRepository.findAll();
                }
                else
                {
                    echiquierMaj = caseRepository.findAll();
                }
                break;
            case "reine":
                // Déplacement de la pièce :
                if(deplacementDame.deplacementDame(caseDepart, caseDestination, echiquier) /*&& borduresDame(caseDepart, caseDestination)*/)
                {
                    // Contrôles à executer sur la Reine :
                    deplacementRoi.echecAuRoi();
                    deplacementRoi.echecEtMat();
                    echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                } else {
                    echiquierMaj = caseRepository.findAll();
                }
                break;
            case "tour":
                // Déplacement de la pièce :
                if(deplacementTour.deplacementTour(caseDepart, caseDestination, echiquier) /*&& borduresTour(caseDepart, caseDestination)*/)
                {
                    // Contrôles à exécuter sur la tour :
                    deplacementRoi.echecAuRoi();
                    deplacementRoi.echecEtMat();
                    echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                } else {
                    echiquierMaj = caseRepository.findAll();
                }
                break;
            case "fou":
                // Déplacement de la pièce :
                if(deplacementFou.deplacementFou(caseDepart, caseDestination, echiquier) /*&& borduresFou(caseDepart, caseDestination)*/)
                {
                    // Contrôles à exécuter sur le Fou :
                    deplacementRoi.echecAuRoi();
                    deplacementRoi.echecEtMat();
                    echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                } else {
                    echiquierMaj = caseRepository.findAll();
                }
                break;
            case "cavalier":
                // Déplacement de la pièce :
                if(deplacementCavalier.deplacementCavalier(caseDepart, caseDestination, echiquier) /*&& borduresCavalier(caseDepart, caseDestination)*/) {
                    // Contrôles à exécuter sur le Cavalier :
                    deplacementRoi.echecAuRoi();
                    deplacementRoi.echecEtMat();
                    echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                } else {
                    echiquierMaj = caseRepository.findAll();
                }
                break;
            case "pion":
                // Déplacement de la pièce :
                if(deplacementPion.deplacementPion(caseDepart, caseDestination, piece, echiquier) /*&& borduresPion(caseDepart, caseDestination)*/) {
                    // Contrôles à exécuter sur le Pion :
                    deplacementRoi.echecAuRoi();
                    deplacementRoi.echecEtMat();
                    echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                } else {
                    echiquierMaj = caseRepository.findAll();
                }
                break;
            default: // Si aucune pièce n'est sélectionnée :
                    echiquierMaj = caseRepository.findAll();
        }
        // 4- Renvoie de l'échiquier maj
        return echiquierMaj;
    }



    /**
     * Méthode qui met à jour l'échiquier :
     * @return echiquierMaj
     */
    public List<Case> miseAJourEchiquier(Case caseDepart, Case caseDestination, Piece piece) throws Exception
    {
        // Attributs :
        List<Case> echiquierMaj = new ArrayList<Case>();

        // 1- Mise à jour des cases :
        caseDestination.setPiece(piece);
        if(caseDestination.getCouleur().getCouleur().equals("blanc"))
        {
            caseDestination.setCouleur(new Couleur(1L,"blanc"));
        } else {
            caseDestination.setCouleur(new Couleur(2L,"noir"));
        }
        caseDepart.setPiece(null);
        if(caseDepart.getCouleur().getCouleur().equals("blanc"))
        {
            caseDepart.setCouleur(new Couleur(1L,"blanc"));
        } else {
            caseDepart.setCouleur(new Couleur(2L,"noir"));
        }
        // 2- Enregistrement des cases mises à jour :
        caseRepository.save(caseDepart);
        caseRepository.save(caseDestination);

        //3- Récupération de l'échiquier mis à jour :
        echiquierMaj = caseRepository.findAll();

        return echiquierMaj;
    }





    /********************* Méthodes qui contrôlent les déplacements spécifiques et les mises en échec *********************/

    // CETTE METHODE SERA PEUT ETRE A SUPPRIMER :
    /**
     * Méthode qui identifie si un pion a atteint le bout de l'échiquier :
     * @return
     * @throws Exception
     */
    /*
    public boolean pionBoutEchiquier(Piece piece, Case caseDestination) throws Exception
    {
        // Règles de contrôle pour transformer un pion
		if(piece.getCouleur().getCouleur().equals("noir")
            && caseDestination.getNo_case() == 8L
            || caseDestination.getNo_case() == 16L
            || caseDestination.getNo_case() == 24L
            || caseDestination.getNo_case() == 32L
            || caseDestination.getNo_case() == 40L
            || caseDestination.getNo_case() == 48L
            || caseDestination.getNo_case() == 56L
            || caseDestination.getNo_case() == 64L
           )
           {
               return true;
           }else if (piece.getCouleur().getCouleur().equals("blanc")
             && caseDestination.getNo_case() == 1L
             || caseDestination.getNo_case() == 9L
             || caseDestination.getNo_case() == 17L
             || caseDestination.getNo_case() == 25L
             || caseDestination.getNo_case() == 33L
             || caseDestination.getNo_case() == 41L
             || caseDestination.getNo_case() == 49L
             || caseDestination.getNo_case() == 57L
           )
           {
              return true;
           } else
           {
              return false;
                }
    }
    */





}
