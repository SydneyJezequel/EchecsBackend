package com.Applications.EchecsBackend.service.echecs;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Couleur;
import com.Applications.EchecsBackend.models.echecs.Piece;
import com.Applications.EchecsBackend.repository.echecs.CaseRepository;
import com.Applications.EchecsBackend.repository.echecs.PartieRepository;
import com.Applications.EchecsBackend.repository.echecs.PieceRepository;
import com.Applications.EchecsBackend.service.echecs.coupSpeciaux.Echec;
import com.Applications.EchecsBackend.service.echecs.coupSpeciaux.Roque;
import com.Applications.EchecsBackend.service.echecs.deplacements.*;
import com.Applications.EchecsBackend.service.echecs.gestionPartie.GestionDesParties;

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
    private final GestionDesParties gestionDesParties;
    private final PieceRepository pieceRepository;
    private final DeplacementRoi deplacementRoi;
    private final DeplacementDame deplacementDame;
    private final DeplacementTour deplacementTour;
    private final DeplacementFou deplacementFou;
    private final DeplacementCavalier deplacementCavalier;
    private final DeplacementPion deplacementPion;
    private final Echec echec;
    private final Roque roque;





    // ********************* Constructeur *********************
    @Autowired
    public JouerService(CaseRepository caseRepository,
                        GestionDesParties gestionDesParties,
                        PieceRepository pieceRepository,
                        DeplacementRoi deplacementRoi,
                        DeplacementDame deplacementDame,
                        DeplacementTour deplacementTour,
                        DeplacementFou deplacementFou,
                        DeplacementCavalier deplacementCavalier,
                        DeplacementPion deplacementPion,
                        Echec echec,
                        Roque roque)
    {
        this.caseRepository = caseRepository;
        this.gestionDesParties = gestionDesParties;
        this.pieceRepository = pieceRepository;
        this.deplacementRoi = deplacementRoi;
        this.deplacementDame = deplacementDame;
        this.deplacementTour = deplacementTour;
        this.deplacementFou = deplacementFou;
        this.deplacementCavalier = deplacementCavalier;
        this.deplacementPion = deplacementPion;
        this.echec = echec;
        this.roque = roque;
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
     * Méthode qui exécute le déplacement des pièces et contrôle si le roi
     * est en échec
     */
    public boolean echecAuRoiPopUp(List<Case> casesDeplacement) throws Exception {

        // Attributs :
        boolean echecAuRoi = false;

        // Exécution des méthodes :
        if(echec.echecAuRoi(casesDeplacement))
        {
            echecAuRoi = true;
        }
        return echecAuRoi;
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

        //1- Attributs :
        List<Case> echiquierMaj = new ArrayList<Case>();
        List<Case> echiquier = caseRepository.findAll();
        echiquier.sort(Comparator.comparing(Case::getNo_case));

        // 2- Contrôle des Règles de gestion :
        // Si le déplacement est correct, on contrôle si le roi est en échec :
        if(!echec.roiToujoursEnEchec(casesDeplacement))
        {
            // Récupération des attributs :
            Case caseDepart = caseRepository.findById(casesDeplacement.get(0).getNo_case()).
                    orElseThrow(() -> new Exception("Case not exist with id: " + casesDeplacement.get(0).getNo_case()));
            Case caseDestination = caseRepository.findById(casesDeplacement.get(1).getNo_case()).
                    orElseThrow(() -> new Exception("Case not exist with id: " + casesDeplacement.get(1).getNo_case()));
            Piece piece = pieceRepository.findById(casesDeplacement.get(0).getPiece().getNo_piece()).
                    orElseThrow(() -> new Exception("Piece not exist with id: " + casesDeplacement.get(0).getPiece().getNo_piece()));
            List<String> nomPiece = List.of(piece.getType().split(" "));
            String typeDePiece = nomPiece.get(0);
            caseDepart.setPiece(piece);
            caseDestination.setPiece(null);

            // Contrôles du déplacement :
            switch (typeDePiece) {
                case "roi":
                    // Si la pièce est un Roi, on vérifie si son déplacement est correct :
                    if (deplacementRoi.deplacementRoi(caseDepart, caseDestination))
                    {
                        echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                    }
                    else if (roque.Roque(caseDepart, caseDestination, echiquier)) {
                        if (caseDestination.getNo_case() == 56L) {
                            roque.petitRoqueBlanc(caseDepart, caseDestination, piece, echiquier);
                        }else if(caseDestination.getNo_case()==49L)
                        {
                            roque.petitRoqueNoir(caseDepart, caseDestination, piece, echiquier);
                        }else if(caseDestination.getNo_case()==24L)
                        {
                            roque.grandRoqueBlanc(caseDepart, caseDestination, piece, echiquier);

                        }else if(caseDestination.getNo_case()==17L)
                        {
                            roque.grandRoqueNoir(caseDepart, caseDestination, piece, echiquier);
                        }
                        echiquierMaj = caseRepository.findAll();
                    }
                    else
                    {
                        echiquierMaj = caseRepository.findAll();
                    }
                    break;
                case "reine":
                    // Si la pièce est un Reine, on vérifie si son déplacement est correct :
                    if(deplacementDame.deplacementDame(caseDepart, caseDestination, echiquier))
                    {
                        echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                    }
                    else
                    {
                        echiquierMaj = caseRepository.findAll();
                    }
                    break;
                case "tour":
                    // Si la pièce est un Tour, on vérifie si son déplacement est correct :
                    if(deplacementTour.deplacementTour(caseDepart, caseDestination, echiquier))
                    {
                        echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                    }
                    else
                    {
                        echiquierMaj = caseRepository.findAll();
                    }
                    break;
                case "fou":
                    // Si la pièce est un Fou, on vérifie si son déplacement est correct :
                    if(deplacementFou.deplacementFou(caseDepart, caseDestination, echiquier))
                    {
                        echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);

                    } else {
                        echiquierMaj = caseRepository.findAll();
                    }
                    break;
                case "cavalier":
                    // Si la pièce est un Cavalier, on vérifie si son déplacement est correct :
                    if(deplacementCavalier.deplacementCavalier(caseDepart, caseDestination, echiquier))
                    {
                        echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                    }
                    else
                    {
                        echiquierMaj = caseRepository.findAll();
                    }
                    break;
                case "pion":
                    // Si la pièce est un Pion, on vérifie si son déplacement est correct :
                    if(deplacementPion.deplacementPion(caseDepart, caseDestination, piece, echiquier))
                    {
                        echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                    }
                    else
                    {
                        echiquierMaj = caseRepository.findAll();
                    }
                    break;
                default: // Si aucune pièce n'est sélectionnée :
                    echiquierMaj = caseRepository.findAll();
            }

            // 4- Mise à jour du nombre de tour :
            gestionDesParties.miseAJourDuNombreDeTour();
        }
        else
        {
            Long numCaseDepart = casesDeplacement.get(0).getNo_case();
            Long numCaseDestination = casesDeplacement.get(1).getNo_case();
            Piece piece = pieceRepository.recupererPieceParId(casesDeplacement.get(0).getPiece().getNo_piece());
            echiquierMaj = caseRepository.findAll();
            echiquierMaj.sort(Comparator.comparing(Case::getNo_case));

            for(int i=0; i<echiquierMaj.size(); i++)
            {
                if(echiquierMaj.get(i).getNo_case().equals(numCaseDestination))
                {
                    echiquierMaj.get(i).setPiece(null);
                }
                if(echiquierMaj.get(i).getNo_case().equals(numCaseDepart))
                {
                    echiquierMaj.get(i).setPiece(piece);
                }
            }
        }

        // 5- Renvoie de l'échiquier maj :
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













}
