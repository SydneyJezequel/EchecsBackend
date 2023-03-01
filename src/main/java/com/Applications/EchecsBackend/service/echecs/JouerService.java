package com.Applications.EchecsBackend.service.echecs;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Couleur;
import com.Applications.EchecsBackend.models.echecs.Partie;
import com.Applications.EchecsBackend.models.echecs.Piece;
import com.Applications.EchecsBackend.repository.echecs.CaseRepository;
import com.Applications.EchecsBackend.repository.echecs.PartieRepository;
import com.Applications.EchecsBackend.repository.echecs.PieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


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




    // ********************* Constructeur *********************

    @Autowired
    public JouerService(CaseRepository caseRepository, DemarrerUnePartieService demarrerUnePartieService, PieceRepository pieceRepository, PartieRepository partieRepository) {
        this.caseRepository = caseRepository;
        this.demarrerUnePartieService = demarrerUnePartieService;
        this.pieceRepository = pieceRepository;
        this.partieRepository = partieRepository;
    }














    // ****************************************** Méthodes ******************************************



    /**
     * Méthode qui renvoie toutes les cases
     */
    public List<Case> findAllCases() {
        return caseRepository.findAll();
    }




    /**
     * Méthode qui renvoie toutes les cases de l'échiquier suite au déplacement
     */
    public List<Case> getEchiquier() {
        List<Case> echiquierMaj = caseRepository.findAll();

        return echiquierMaj;
    }




    /**
     * Méthode qui déplace l'objet
     */
    public List<Case> echiquierMaj(List<Case> casesDeplacement) throws Exception {

        // 1- Récupération des cases et de la pièce :
        Case caseDepart = caseRepository.findById(casesDeplacement.get(0).getNo_case()).
                orElseThrow(() -> new Exception("Case not exist with id: " + casesDeplacement.get(0).getNo_case()));
        Case caseDestination = caseRepository.findById(casesDeplacement.get(1).getNo_case()).
                orElseThrow(() -> new Exception("Case not exist with id: " + casesDeplacement.get(1).getNo_case()));
        Piece piece = pieceRepository.findById(casesDeplacement.get(0).getPiece().getNo_piece()).
                orElseThrow(() -> new Exception("Piece not exist with id: " + casesDeplacement.get(0).getPiece().getNo_piece()));
        List<Case> echiquier = caseRepository.findAll();
        echiquier.sort(Comparator.comparing(Case::getNo_case));
        // **************************  TEST : A SUPPRIMER **************************
        // 2- Contrôle des Règles de gestion :
        List<String> nomPiece = List.of(piece.getType().split(" "));
        for(int i=0; i<nomPiece.size();i++)
        {
            System.out.println(nomPiece.get(i));
        }
        String typeDePiece = nomPiece.get(0);
        switch(typeDePiece) {
            case "roi":
                // Déplacement de la pièce :
                deplacementRoi(caseDepart, caseDestination, echiquier);
                Roque();
                echecEtPat();
                System.out.println("Déplacement roi");
                break;
            case "reine":
                // Déplacement de la pièce :
                deplacementReine(caseDepart, caseDestination, echiquier);
                // Contrôles :
                echecAuRoi();
                echecEtMat();
                System.out.println("Déplacement reine");
                break;
            case "tour":
                // Déplacement de la pièce :
                deplacementTour(caseDepart, caseDestination, echiquier);
                // Contrôles :
                echecAuRoi();
                echecEtMat();
                System.out.println("Déplacement tour");
                break;
            case "fou":
                // Déplacement de la pièce :
                deplacementFou(caseDepart, caseDestination, echiquier);
                // Contrôles :
                echecAuRoi();
                echecEtMat();
                System.out.println("Déplacement fou");
                break;
            case "cavalier":
                // Déplacement de la pièce :
                deplacementCavalier(caseDepart, caseDestination, echiquier);
                // Contrôles :
                echecAuRoi();
                echecEtMat();
                System.out.println("Déplacement cavalier");
                break;
            default: // Pion
                // Déplacement de la pièce :
                deplacementPion(caseDepart, caseDestination, echiquier);
                // Contrôles :
                echecAuRoi();
                echecEtMat();
                transformationPion();
                System.out.println("Déplacement pion");
        }

        // 3- Déplacement de la pièce :
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

        // 3- Enregistrement des cases mises à jour :
        caseRepository.save(caseDepart);
        caseRepository.save(caseDestination);

        // 4- Récupération de la BDD Mise à jour :
        List<Case> echiquierMaj = demarrerUnePartieService.getEchequier();
        return echiquierMaj;
    }









    // DEPLACEMENT DES PIECES (FUTURE VERSION) :

    /**
     * Méthode qui identifie la pièce.
     * Cette méthode va appeler les méthodes qui réalisent les contrôles
     * pour les déplacements de chaque pièce (deplacementRoi(), deplacementReine, etc.) :
     * Si les conditions sont vérifiés, la méthode : "echiquierMaj(List<Case> casesDeplacement)"
     * est appelée pour déplacer la pièce.
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
        switch(typeDePiece) {
            case "roi":
                // Déplacement de la pièce :
                if(deplacementRoi(caseDepart, caseDestination, echiquier))
                {
                    // Contrôles à exécuter sur le Roi :
                    // Roque();
                    // echecEtPat();
                    System.out.println("Déplacement roi");
                    echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                } else {
                    echiquierMaj = caseRepository.findAll();
                }
                break;
            case "reine":
                // Déplacement de la pièce :
                if(deplacementReine(caseDepart, caseDestination, echiquier))
                {
                    // Contrôles à executer sur la Reine :
                    echecAuRoi();
                    echecEtMat();
                    System.out.println("Déplacement reine");
                    echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                } else {
                    echiquierMaj = caseRepository.findAll();
                }
                break;
            case "tour":
                // Déplacement de la pièce :
                if(deplacementTour(caseDepart, caseDestination, echiquier))
                {
                    // Contrôles à exécuter sur la tour :
                    echecAuRoi();
                    echecEtMat();
                    echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                    System.out.println("Déplacement tour");
                } else {
                    echiquierMaj = caseRepository.findAll();
                }
                break;
            case "fou":
                // Déplacement de la pièce :
                if(deplacementFou(caseDepart, caseDestination, echiquier))
                {
                    // Contrôles à exécuter sur le Fou :
                    // echecAuRoi();
                    // echecEtMat();
                    echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                    System.out.println("Déplacement fou");
                } else {
                    echiquierMaj = caseRepository.findAll();
                }
                break;
            case "cavalier":
                // Déplacement de la pièce :
                if(deplacementCavalier(caseDepart, caseDestination, echiquier)) {
                    // Contrôles à exécuter sur le Cavalier :
                    // echecAuRoi();
                    // echecEtMat();
                    echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                    System.out.println("Déplacement cavalier");
                } else {
                    echiquierMaj = caseRepository.findAll();
                }
                break;
            case "pion":
                // Déplacement de la pièce :
                if(deplacementPion(caseDepart, caseDestination, echiquier)) {
                    // Contrôles à exécuter sur le Pion :
                    // echecAuRoi();
                    // echecEtMat();
                    // transformationPion();
                    echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);;
                    System.out.println("Déplacement pion");
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






















    /********************* Méthodes de contrôle du déplacement des pièces *********************/



        /**
         * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
         * d'une pièce de type Roi.
         * @return
         * @throws Exception
         */
    public boolean deplacementRoi(Case caseDepart, Case caseDestination, List<Case> echiquier) throws Exception
    {
        // VERSION TEMPORAIRE : AJOUTER UN BLOCAGE DE DEPLACEMENT SUR LES PIECES DU MEME CAMP.
        // Récupération des numéros des cases :
        int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
        int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());
        // Déplacements possibles :
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
            return true;
        } else
        {
            return false;
        }
        // VERSION FINALE :
        /*
        if (caseDestination.no_case == no_case + 1
        || caseDestination.no_case == no_case - 1
        || caseDestination.no_case == no_case + 8
        ||  caseDestination.no_case ==no_case - 8)
        {
	        boolean echecAuRoi = echecAuRoi();
	        if(!echecAuRoi)
	        {
		        return true;
	        }
        }
         */
    }



    /**
     * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
     * d'une pièce de type Reine.
     * @return
     * @throws Exception
     */
    public boolean deplacementReine(Case caseDepart, Case caseDestination, List<Case> echiquier) throws Exception
    {
      // Récupération des numéros des cases :
      int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
      int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());
      // Déplacements possibles :
      /*
      if (
      // Contrôle diagonale (droite/gauche/haut/bas) :
      caseDestination.getNo_case() == caseDepart.getNo_case() + 9L
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 18L && caseDepart.getNo_case() + 9L
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 27 && caseDepart.getNo_case().+ 18.piece == null && caseDepart.getNo_case().+ 9.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 36 && caseDepart.getNo_case().+ 27.piece == null && caseDepart.getNo_case().+ 18.piece == null && caseDepart.getNo_case().+ 9.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 45 && caseDepart.getNo_case().+ 36.piece == null && caseDepart.getNo_case().+ 27.piece == null && caseDepart.getNo_case().+ 18.piece == null && caseDepart.getNo_case().+ 9.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 54 && caseDepart.getNo_case().+ 45.piece == null && caseDepart.getNo_case().+ 36.piece == null && caseDepart.getNo_case().+ 27.piece == null && caseDepart.getNo_case().+ 18.piece == null && caseDepart.getNo_case()+ 9.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 63 && caseDepart.getNo_case().+ 54.piece == null && caseDepart.getNo_case().+ 45.piece == null  && caseDepart.getNo_case().+ 36.piece == null && caseDepart.getNo_case().+ 27.piece == null && caseDepart.getNo_case().+ 18.piece == null && caseDepart.getNo_case().+ 9.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 72 && caseDepart.getNo_case().+ 63.piece == null && caseDepart.getNo_case().+ 54.piece == null && caseDepart.getNo_case().+ 45.piece == null  && caseDepart.getNo_case().+ 36.piece == null && caseDepart.getNo_case().+ 27.piece == null && caseDepart.getNo_case().+ 18.piece == null && caseDepart.getNo_case().+ 9.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 7
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 14 && caseDepart.getNo_case().+ 7.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 21 && caseDepart.getNo_case().+ 14.piece == null && caseDepart.getNo_case().+ 7.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 28 && caseDepart.getNo_case().+ 21.piece == null && caseDepart.getNo_case().+ 14.piece == null && caseDepart.getNo_case().+ 7.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 35 && caseDepart.getNo_case().+ 28.piece == null && caseDepart.getNo_case().+ 21.piece == null && caseDepart.getNo_case().+ 14.piece == null && caseDepart.getNo_case().+ 7.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 42 && caseDepart.getNo_case().+ 35.piece == null && caseDepart.getNo_case().+ 28.piece == null && caseDepart.getNo_case().+ 21.piece == null && caseDepart.getNo_case().+ 14.piece == null && caseDepart.getNo_case().+ 7.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 49 && caseDepart.getNo_case().+ 42.piece == null && caseDepart.getNo_case().+ 35.piece == null && caseDepart.getNo_case().+ 28.piece == null && caseDepart.getNo_case().+ 21.piece == null && caseDepart.getNo_case().+ 14.piece == null && caseDepart.getNo_case().+ 7.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 56 && caseDepart.getNo_case().+ 49.piece == null && caseDepart.getNo_case().+ 42.piece == null && caseDepart.getNo_case().+ 35.piece == null && caseDepart.getNo_case().+ 28.piece == null && caseDepart.getNo_case().+ 21.piece == null && caseDepart.getNo_case().+ 14.piece == null && caseDepart.getNo_case().+ 7.piece == null

      || caseDestination.getNo_case() == caseDepart.getNo_case() - 9
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 18 && caseDepart.getNo_case().-9.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 27 && caseDepart.getNo_case().-18.piece == null && caseDepart.getNo_case().-9.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 36 && caseDepart.getNo_case().-27.piece == null && caseDepart.getNo_case().-18.piece == null && caseDepart.getNo_case().-9.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 45 && caseDepart.getNo_case().-36.piece == null && caseDepart.getNo_case().-27.piece == null && caseDepart.getNo_case().-18.piece == null && caseDepart.getNo_case().-9.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 54 && caseDepart.getNo_case().-45.piece == null && caseDepart.getNo_case().-36.piece == null && caseDepart.getNo_case().-27.piece == null && caseDepart.getNo_case().-18.piece == null && caseDepart.getNo_case().-9.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 63 && caseDepart.getNo_case().-54.piece == null && caseDepart.getNo_case().-45.piece == null&& caseDepart.getNo_case().-36.piece == null && caseDepart.getNo_case().-27.piece == null && caseDepart.getNo_case().-18.piece == null && caseDepart.getNo_case().-9.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 72 && caseDepart.getNo_case().-63.piece == null && caseDepart.getNo_case().-54.piece == null && caseDepart.getNo_case().-45.piece == null && caseDepart.getNo_case().-36.piece == null && caseDepart.getNo_case().-27.piece == null && caseDepart.getNo_case().-18.piece == null && caseDepart.getNo_case().-9.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 7
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 14 && caseDepart.getNo_case().- 7.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 21 && caseDepart.getNo_case().- 14.piece == null && caseDepart.getNo_case().- 7.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 28 && caseDepart.getNo_case().- 21.piece == null && caseDepart.getNo_case().- 14.piece == null && caseDepart.getNo_case().- 7.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 35 && caseDepart.getNo_case().- 28.piece == null && caseDepart.getNo_case().- 21.piece == null && caseDepart.getNo_case().- 14.piece == null && caseDepart.getNo_case().- 7.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 42 && caseDepart.getNo_case().- 35.piece == null && caseDepart.getNo_case().- 28.piece == null && caseDepart.getNo_case().- 21.piece == null && caseDepart.getNo_case().- 14.piece == null && caseDepart.getNo_case().- 7.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 49 && caseDepart.getNo_case().- 42.piece == null && caseDepart.getNo_case().- 35.piece == null && caseDepart.getNo_case().- 28.piece == null && caseDepart.getNo_case().- 21.piece == null && caseDepart.getNo_case().- 14.piece == null && caseDepart.getNo_case().- 7.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 56 && caseDepart.getNo_case().- 49.piece == null && caseDepart.getNo_case().- 42.piece == null && caseDepart.getNo_case().- 35.piece == null && caseDepart.getNo_case().- 28.piece == null && caseDepart.getNo_case().- 21.piece == null && caseDepart.getNo_case().- 14.piece == null && caseDepart.getNo_case().- 7.piece == null

      // Contrôle ligne droite verticale :
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 1
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 2 && caseDepart.getNo_case().+1.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 3 && caseDepart.getNo_case().+2.piece == null && caseDepart.getNo_case().+1.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 4 && caseDepart.getNo_case().+3.piece == null && caseDepart.getNo_case().+2piece == null && caseDepart.getNo_case().no_case.+1.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 5 && caseDepart.getNo_case().+4.piece == null && caseDepart.getNo_case().+3.piece == null && caseDepart.getNo_case().+2piece == null &&
    .+1.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 6 && caseDepart.getNo_case().+5.piece == null && caseDepart.getNo_case().+4.piece == null && caseDepart.getNo_case().+3.piece == null && caseDepart.getNo_case().+2piece == null && caseDepart.getNo_case().+1.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 7 && caseDepart.getNo_case().+6.piece == null && caseDepart.getNo_case().+5.piece == null && caseDepart.getNo_case().+4.piece == null && caseDepart.getNo_case().+3.piece == null && caseDepart.getNo_case().+2piece == null && caseDepart.getNo_case().+1.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 1
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 2 && caseDepart.getNo_case().-1.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 3 && caseDepart.getNo_case().-2.piece == null && caseDepart.getNo_case().-1.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 4 && caseDepart.getNo_case().-3.piece == null && caseDepart.getNo_case().-2piece == null && caseDepart.getNo_case().-1.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 5 && caseDepart.getNo_case().-4.piece == null && caseDepart.getNo_case().-3.piece == null && caseDepart.getNo_case().-2.piece == null && caseDepart.getNo_case().-1.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 6 && caseDepart.getNo_case().-5.piece == null && caseDepart.getNo_case().-4.piece == null && caseDepart.getNo_case().-3.piece == null && caseDepart.getNo_case().-2piece == null && caseDepart.getNo_case().-1.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 7 && caseDepart.getNo_case().-6.piece == null && caseDepart.getNo_case().-5.piece == null && caseDepart.getNo_case().-4.piece == null && caseDepart.getNo_case().-3.piece == null && caseDepart.getNo_case().-2.piece == null && caseDepart.getNo_case().-1.piece == null

      // Contrôle ligne droite horizontale :
      || caseDestination.getNo_case()== caseDepart.getNo_case() + 8
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 16 && caseDepart.getNo_case().+8.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 24 && caseDepart.getNo_case().+16.piece == null && caseDepart.getNo_case().+8.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 32 && caseDepart.getNo_case().+24.piece == null && caseDepart.getNo_case().+16.piece == null && caseDepart.getNo_case().+8.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 40 && caseDepart.getNo_case().+32.piece == null && caseDepart.getNo_case().+24.piece == null && caseDepart.getNo_case().+16.piece == null && caseDepart.getNo_case().+8.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 48 && caseDepart.getNo_case().+40.piece == null && caseDepart.getNo_case().+32.piece == null && caseDepart.getNo_case().+24.piece == null && caseDepart.getNo_case().+16.piece == null && caseDepart.getNo_case().+8.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() + 56 && caseDepart.getNo_case().+48.piece == null && caseDepart.getNo_case().+40.piece == null && caseDepart.getNo_case().+32.piece == null && caseDepart.getNo_case().+24.piece == null && caseDepart.getNo_case().+16.piece == null && caseDepart.getNo_case().+8.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 8
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 16 && caseDepart.getNo_case().-8.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 24 && caseDepart.getNo_case().-16.piece == null && caseDepart.getNo_case().-8.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 32 && caseDepart.getNo_case().-24.piece == null && caseDepart.getNo_case().-16.piece == null && caseDepart.getNo_case().-8.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 40 && caseDepart.getNo_case().-32.piece == null && caseDepart.getNo_case().-24.piece == null && caseDepart.getNo_case().-16.piece == null && caseDepart.getNo_case().-8.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 48 && caseDepart.getNo_case().-40.piece == null && caseDepart.getNo_case().-32.piece == null && caseDepart.getNo_case().-24.piece == null && caseDepart.getNo_case().-16.piece == null && caseDepart.getNo_case().-8.piece == null
      || caseDestination.getNo_case() == caseDepart.getNo_case() - 56 && caseDepart.getNo_case().-48.piece == null && caseDepart.getNo_case().-40.piece == null && caseDepart.getNo_case().-32.piece == null && caseDepart.getNo_case().-24.piece == null && caseDepart.getNo_case().-16.piece == null && caseDepart.getNo_case().-8.piece == null
      )
      {
	    return true;
      } else {
        return false;
      }
      */
        return true;
    }



    /**
     * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
     * d'une pièce de type Tour.
     * @return
     * @throws Exception
     */
    public boolean deplacementTour(Case caseDepart, Case caseDestination, List<Case> echiquier) throws Exception
    {
        // Récupération des numéros des cases :
        int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
        int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());
        // Déplacements possibles :
        /*
      If (
      // Contrôle ligne droite verticale :
      || caseDestination.no_case == caseDepart.no_case + 1
      || caseDestination.no_case == caseDepart.no_case + 2 && caseDepart.no_case.+1.piece == null
      || caseDestination.no_case == caseDepart.no_case + 3 && caseDepart.no_case.+2.piece == null && caseDepart.no_case.+1.piece == null
      || caseDestination.no_case == caseDepart.no_case + 4 && caseDepart.no_case.+3.piece == null && caseDepart.no_case.+2piece == null && caseDepart.no_case.+1.piece == null
      || caseDestination.no_case == caseDepart.no_case + 5 && caseDepart.no_case.+4.piece == null && caseDepart.no_case.+3.piece == null && caseDepart.no_case.+2piece == null && caseDepart.no_case.+1.piece == null
      || caseDestination.no_case == caseDepart.no_case + 6 && caseDepart.no_case.+5.piece == null && caseDepart.no_case.+4.piece == null && caseDepart.no_case.+3.piece == null && caseDepart.no_case.+2piece == null && caseDepart.no_case.+1.piece == null
      || caseDestination.no_case == caseDepart.no_case + 7 && caseDepart.no_case.+6.piece == null && caseDepart.no_case.+5.piece == null && caseDepart.no_case.+4.piece == null && caseDepart.no_case.+3.piece == null && caseDepart.no_case.+2piece == null && caseDepart.no_case.+1.piece == null
      || caseDestination.no_case == caseDepart.no_case - 1
      || caseDestination.no_case == caseDepart.no_case - 2 && caseDepart.no_case.-1.piece == null
      || caseDestination.no_case == caseDepart.no_case - 3 && caseDepart.no_case.-2.piece == null && caseDepart.no_case.-1.piece == null
      || caseDestination.no_case == caseDepart.no_case - 4 && caseDepart.no_case.-3.piece == null && caseDepart.no_case.-2piece == null && caseDepart.no_case.-1.piece == null
      || caseDestination.no_case == caseDepart.no_case - 5 && caseDepart.no_case.-4.piece == null && caseDepart.no_case.-3.piece == null && caseDepart.no_case.-2.piece == null && caseDepart.no_case.-1.piece == null
      || caseDestination.no_case == caseDepart.no_case - 6 && caseDepart.no_case.-5.piece == null && caseDepart.no_case.-4.piece == null && caseDepart.no_case.-3.piece == null && caseDepart.no_case.-2piece == null && caseDepart.no_case.-1.piece == null
      || caseDestination.no_case == caseDepart.no_case - 7 && caseDepart.no_case.-6.piece == null && caseDepart.no_case.-5.piece == null && caseDepart.no_case.-4.piece == null && caseDepart.no_case.-3.piece == null && caseDepart.no_case.-2.piece == null && caseDepart.no_case.-1.piece == null

      // Contrôle ligne droite horizontale :
      || caseDestination.no_case == caseDepart.no_case + 8
      || caseDestination.no_case == caseDepart.no_case + 16 && caseDepart.no_case.+8.piece == null
      || caseDestination.no_case == caseDepart.no_case + 24 && caseDepart.no_case.+16.piece == null && caseDepart.no_case.+8.piece == null
      || caseDestination.no_case == caseDepart.no_case + 32 && caseDepart.no_case.+24.piece == null && caseDepart.no_case.+16.piece == null && caseDepart.no_case.+8.piece == null
      || caseDestination.no_case == caseDepart.no_case + 40 && caseDepart.no_case.+32.piece == null && caseDepart.no_case.+24.piece == null && caseDepart.no_case.+16.piece == null && caseDepart.no_case.+8.piece == null
      || caseDestination.no_case == caseDepart.no_case + 48 && caseDepart.no_case.+40.piece == null && caseDepart.no_case.+32.piece == null && caseDepart.no_case.+24.piece == null && caseDepart.no_case.+16.piece == null && caseDepart.no_case.+8.piece == null
      || caseDestination.no_case == caseDepart.no_case + 56 && caseDepart.no_case.+48.piece == null && caseDepart.no_case.+40.piece == null && caseDepart.no_case.+32.piece == null && caseDepart.no_case.+24.piece == null && caseDepart.no_case.+16.piece == null && caseDepart.no_case.+8.piece == null
      || caseDestination.no_case == caseDepart.no_case - 8
      || caseDestination.no_case == caseDepart.no_case - 16 && caseDepart.no_case.-8.piece == null
      || caseDestination.no_case == caseDepart.no_case - 24 && caseDepart.no_case.-16.piece == null && caseDepart.no_case.-8.piece == null
      || caseDestination.no_case == caseDepart.no_case - 32 && caseDepart.no_case.-24.piece == null && caseDepart.no_case.-16.piece == null && caseDepart.no_case.-8.piece == null
      || caseDestination.no_case == caseDepart.no_case - 40 && caseDepart.no_case.-32.piece == null && caseDepart.no_case.-24.piece == null && caseDepart.no_case.-16.piece == null && caseDepart.no_case.-8.piece == null
      || caseDestination.no_case == caseDepart.no_case - 48 && caseDepart.no_case.-40.piece == null && caseDepart.no_case.-32.piece == null && caseDepart.no_case.-24.piece == null && caseDepart.no_case.-16.piece == null && caseDepart.no_case.-8.piece == null
      || caseDestination.no_case == caseDepart.no_case - 56 && caseDepart.no_case.-48.piece == null && caseDepart.no_case.-40.piece == null && caseDepart.no_case.-32.piece == null && caseDepart.no_case.-24.piece == null && caseDepart.no_case.-16.piece == null && caseDepart.no_case.-8.piece == null
      )
      {
	    return true;
      }
      */
        return true;
    }



    /**
     * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
     * d'une pièce de type Fou.
     * @return
     * @throws Exception
     */
    public boolean deplacementFou(Case caseDepart, Case caseDestination, List<Case> echiquier) throws Exception
    {
        // Récupération des numéros des cases :
        int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
        int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());
        // Déplacements possibles :
    /*
    If (
      // Contrôle diagonale (droite/gauche/haut/bas) :
      caseDestination.no_case == caseDepart.no_case + 9
      || caseDestination.no_case == caseDepart.no_case + 18 && caseDepart.no_case.+ 9.piece == null
      || caseDestination.no_case == caseDepart.no_case + 27 && caseDepart.no_case.+ 18.piece == null && caseDepart.no_case.+ 9.piece == null
      || caseDestination.no_case == caseDepart.no_case + 36 && caseDepart.no_case.+ 27.piece == null && caseDepart.no_case.+ 18.piece == null && caseDepart.no_case.+ 9.piece == null
      || caseDestination.no_case == caseDepart.no_case + 45 && caseDepart.no_case.+ 36.piece == null && caseDepart.no_case.+ 27.piece == null && caseDepart.no_case.+ 18.piece == null && caseDepart.no_case.+ 9.piece == null
      || caseDestination.no_case == caseDepart.no_case + 54 && caseDepart.no_case.+ 45.piece == null  && caseDepart.no_case.+ 36.piece == null && caseDepart.no_case.+ 27.piece == null && caseDepart.no_case.+ 18.piece == null && caseDepart.no_case.+ 9.piece == null
      || caseDestination.no_case == caseDepart.no_case + 63 && caseDepart.no_case.+ 54.piece == null && caseDepart.no_case.+ 45.piece == null  && caseDepart.no_case.+ 36.piece == null && caseDepart.no_case.+ 27.piece == null && caseDepart.no_case.+ 18.piece == null && caseDepart.no_case.+ 9.piece == null
      || caseDestination.no_case == caseDepart.no_case + 72 && caseDepart.no_case.+ 63.piece == null && caseDepart.no_case.+ 54.piece == null && caseDepart.no_case.+ 45.piece == null  && caseDepart.no_case.+ 36.piece == null && caseDepart.no_case.+ 27.piece == null && caseDepart.no_case.+ 18.piece == null && caseDepart.no_case.+ 9.piece == null
      || caseDestination.no_case == caseDepart.no_case + 7
      || caseDestination.no_case == caseDepart.no_case + 14 && caseDepart.no_case.+ 7.piece == null
      || caseDestination.no_case == caseDepart.no_case + 21 && caseDepart.no_case.+ 14.piece == null && caseDepart.no_case.+ 7.piece == null
      || caseDestination.no_case == caseDepart.no_case + 28 && caseDepart.no_case.+ 21.piece == null && caseDepart.no_case.+ 14.piece == null && caseDepart.no_case.+ 7.piece == null
      || caseDestination.no_case == caseDepart.no_case + 35 && caseDepart.no_case.+ 28.piece == null && caseDepart.no_case.+ 21.piece == null && caseDepart.no_case.+ 14.piece == null && caseDepart.no_case.+ 7.piece == null
      || caseDestination.no_case == caseDepart.no_case + 42 && caseDepart.no_case.+ 35.piece == null && caseDepart.no_case.+ 28.piece == null && caseDepart.no_case.+ 21.piece == null && caseDepart.no_case.+ 14.piece == null && caseDepart.no_case.+ 7.piece == null
      || caseDestination.no_case == caseDepart.no_case + 49 && caseDepart.no_case.+ 42.piece == null && caseDepart.no_case.+ 35.piece == null && caseDepart.no_case.+ 28.piece == null && caseDepart.no_case.+ 21.piece == null && caseDepart.no_case.+ 14.piece == null && caseDepart.no_case.+ 7.piece == null
      || caseDestination.no_case == caseDepart.no_case + 56 && caseDepart.no_case.+ 49.piece == null && caseDepart.no_case.+ 42.piece == null && caseDepart.no_case.+ 35.piece == null && caseDepart.no_case.+ 28.piece == null && caseDepart.no_case.+ 21.piece == null && caseDepart.no_case.+ 14.piece == null && caseDepart.no_case.+ 7.piece == null

      || caseDestination.no_case == caseDepart.no_case - 9
      || caseDestination.no_case == caseDepart.no_case - 18 && caseDepart.no_case.-9.piece == null
      || caseDestination.no_case == caseDepart.no_case - 27 && caseDepart.no_case.-18.piece == null && caseDepart.no_case.-9.piece == null
      || caseDestination.no_case == caseDepart.no_case - 36 && caseDepart.no_case.-27.piece == null && caseDepart.no_case.-18.piece == null && caseDepart.no_case.-9.piece == null
      || caseDestination.no_case == caseDepart.no_case - 45 && caseDepart.no_case.-36.piece == null && caseDepart.no_case.-27.piece == null && caseDepart.no_case.-18.piece == null && caseDepart.no_case.-9.piece == null
      || caseDestination.no_case == caseDepart.no_case - 54 && caseDepart.no_case.-45.piece == null && caseDepart.no_case.-36.piece == null && caseDepart.no_case.-27.piece == null && caseDepart.no_case.-18.piece == null && caseDepart.no_case.-9.piece == null
      || caseDestination.no_case == caseDepart.no_case - 63 && caseDepart.no_case.-54.piece == null && caseDepart.no_case.-45.piece == null&& caseDepart.no_case.-36.piece == null && caseDepart.no_case.-27.piece == null && caseDepart.no_case.-18.piece == null && caseDepart.no_case.-9.piece == null
      || caseDestination.no_case == caseDepart.no_case - 72 && caseDepart.no_case.-63.piece == null && caseDepart.no_case.-54.piece == null && caseDepart.no_case.-45.piece == null && caseDepart.no_case.-36.piece == null && caseDepart.no_case.-27.piece == null && caseDepart.no_case.-18.piece == null && caseDepart.no_case.-9.piece == null
      || caseDestination.no_case == caseDepart.no_case - 7
      || caseDestination.no_case == caseDepart.no_case - 14 && caseDepart.no_case.- 7.piece == null
      || caseDestination.no_case == caseDepart.no_case - 21 && caseDepart.no_case.- 14.piece == null && caseDepart.no_case.- 7.piece == null
      || caseDestination.no_case == caseDepart.no_case - 28 && caseDepart.no_case.- 21.piece == null && caseDepart.no_case.- 14.piece == null && caseDepart.no_case.- 7.piece == null
      || caseDestination.no_case == caseDepart.no_case - 35 && caseDepart.no_case.- 28.piece == null && caseDepart.no_case.- 21.piece == null && caseDepart.no_case.- 14.piece == null && caseDepart.no_case.- 7.piece == null
      || caseDestination.no_case == caseDepart.no_case - 42 && caseDepart.no_case.- 35.piece == null && caseDepart.no_case.- 28.piece == null && caseDepart.no_case.- 21.piece == null && caseDepart.no_case.- 14.piece == null && caseDepart.no_case.- 7.piece == null
      || caseDestination.no_case == caseDepart.no_case - 49 && caseDepart.no_case.- 42.piece == null && caseDepart.no_case.- 35.piece == null && caseDepart.no_case.- 28.piece == null && caseDepart.no_case.- 21.piece == null && caseDepart.no_case.- 14.piece == null && caseDepart.no_case.- 7.piece == null
      || caseDestination.no_case == caseDepart.no_case - 56 && caseDepart.no_case.- 49.piece == null && caseDepart.no_case.- 42.piece == null && caseDepart.no_case.- 35.piece == null && caseDepart.no_case.- 28.piece == null && caseDepart.no_case.- 21.piece == null && caseDepart.no_case.- 14.piece == null && caseDepart.no_case.- 7.piece == null
      )
      {
	    return true;
      }
      */
        return true;
    }



    /**
     * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
     * d'une pièce de type Cavalier.
     * @return
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
            return true;
        } else {
            return false;
        }
    }



    /**
     * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
     * d'une pièce de type Pion.
     * @return
     * @throws Exception
     */
    public boolean deplacementPion(Case caseDepart, Case caseDestination, List<Case> echiquier) throws Exception
    {
        // VERSION TEMPORAIRE :COMPLETER LES CONDITIONS AVEC LA CASE ORIGINE.
        // Récupération des numéros des cases :
        int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
        int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());
        // Contrôle des déplacements possibles :
        if(noCaseDestination == noCaseDepart + 1 && caseDestination.getPiece() == null
           || noCaseDestination == noCaseDepart - 1 && caseDestination.getPiece() == null
           || noCaseDestination == noCaseDepart + 2 && caseDestination.getPiece()== null // && noCaseDepart = case_origine && caseDestination.getPiece()== null
           || noCaseDestination == noCaseDepart - 2 && caseDestination.getPiece()== null // && noCaseDepart = case_origine && caseDestination.getPiece()== null
           || noCaseDestination == noCaseDepart + 9 && caseDestination.getPiece() != null && caseDestination.getPiece().getCouleur().getCouleur() != caseDepart.getPiece().getCouleur().getCouleur()
           || noCaseDestination == noCaseDepart - 9 && caseDestination.getPiece() != null && caseDestination.getPiece().getCouleur().getCouleur() != caseDepart.getPiece().getCouleur().getCouleur()
           || noCaseDestination == noCaseDepart +7 && caseDestination.getPiece() != null && caseDestination.getPiece().getCouleur().getCouleur() != caseDepart.getPiece().getCouleur().getCouleur()
           || noCaseDestination == noCaseDepart -7 && caseDestination.getPiece() != null && caseDestination.getPiece().getCouleur().getCouleur() != caseDepart.getPiece().getCouleur().getCouleur()
        )
        {
            return true;
        } else {
            return false;
        }
    }




















    /********************* Méthodes qui contrôlent les déplacements spécifiques et les mises en échec *********************/



    /**
     * Méthode qui vérifie si le roi est en échec :
     * @return
     * @throws Exception
     */
    public boolean echecAuRoi() throws Exception
    {
        // REGLES DE L'ALGORITHME :
        /*
               // 1- Récupération la position de toutes les pièces du camp adverse.
	              Stocker dans un array toutes les cases de l’échiquier ou piece.couleur != roi.couleur

              // 2- Simuler pour chacune de ces pièces si elle peut se rendre sur la case du roi.

	            1- Boucler sur ce tableau :
		            A chaque tour de boucle le List<Case> casesDeplacement sera composé de :
			        —> La case de la pièce comme case de départ.
			        —> La case du roi comme case de destination.
	            2- Selon le type de pièce :
			        —> appeler la méthode deplacementRoi, deplacementReine, deplacementTour, deplacementFou, deplacementCavalier, deplacementPion.
	            3- Si une de ces méthodes renvoie true :
			        —> On break la boucle.
			        —> On renvoie la pop-up : « Echec au roi ».
         */
        return true;
    }



    /**
     * Méthode qui vérifie si le roi est échec et mat :
     * @return
     * @throws Exception
     */
    public boolean echecEtMat() throws Exception
    {
        // REGLES DE L'ALGORITHME :
        /*
               // 1- Récupération la position de toutes les pièces du camp adverse.
	              Stocker dans un array toutes les cases de l’échiquier ou piece.couleur != roi.couleur

              // 2- Simuler pour chacune de ces pièces si elle peut se rendre sur la case du roi.

	            1- Boucler sur ce tableau :
		            A chaque tour de boucle le List<Case> casesDeplacement sera composé de :
			        —> La case de la pièce comme case de départ.
			        —> La case du roi comme case de destination.
	            2- Selon le type de pièce :
			        —> appeler la méthode deplacementRoi, deplacementReine, deplacementTour, deplacementFou, deplacementCavalier, deplacementPion.
	            3- Si une de ces méthodes renvoie true :
			        —> On break la boucle.
			        —> On appelle la méthode de déplacement du roi : deplacementRoi().
			           Si la méthode de déplacement du roi renvoie false (Roi en échec + Roi ne peut se déplacer) :
			                —> On renvoie la pop-up : « Partie terminée : Echec et mat ».
         */
        return true;
    }



    /**
     * Méthode qui contrôle si le roi a fait 3 allers/retours sur sa case.
     * Si ce cas est vérifiée, la partie est terminée.
     * @return
     * @throws Exception
     */
    public boolean echecEtPat() throws Exception
    {
        // REGLES DE L'ALGORITHME :
        /*
		1- Charger dans un ArrayList les cases du roi dont c’est le tour depuis la BDD.
		2- Boucler l’ArrayList.
		3- A chaque tour de boucle, exécuter la condition suivante. Si est est vérifiée : Renvoyer « true ».
			if( ArrayList.get(0).no_case == ArrayList.get(2).no_case )
			{
				true;
			}
		4- Si true est renvoyé : Renvoyer une pop-up qui indique « Partie Terminée : Echec et Pat ».
         */
        return true;
    }



    /**
     * Méthode qui vérifie si le Grand Roque ou le Petit Roque est correct :
     * @return
     * @throws Exception
     */
    public boolean Roque() throws Exception
    {
        // Règles de contrôles pour le Petit Roque blanc :
        /*
        if (caseDestination.no_case == caseDepart.no_case + 16
            && case.no_case(64).piece == "tour"
            && case.no_case(48).piece == null
            && case.no_case(56).piece == null)
        {
                // Récupérer la tour de la case 64 et la placer dans la case 48.
                // Récupérer le roi de la case 40 et le placer dans la case 56.
                return true;
        }
        // Règles de contrôles pour le Petit Roque noir :
        else if (caseDestination.no_case == caseDepart.no_case - 16
                && case.no_case(8).piece == "tour"
                && case.no_case(16).piece == null
                && case.no_case(24).piece == null)
        {
                // Récupérer la tour de la case 8 et la placer dans la case 24.
                // Récupérer le roi de la case 32 et le placer dans la case 16.
                return true;
        }
        // Règles de contrôles pour le Grand Roque blanc :
        else if(caseDestination.no_case ==caseDepart.no_case - 24
                && case.no_case(8).piece == "tour"
                && case.no_case(16).piece == null
                && case.no_case(24).piece == null
                && case.no_case(32).piece == null)
        {
                // Récupérer la tour de la case 8 et la placer dans la case 32.
                // Récupérer le roi de la case 40 et le placer dans la case 16.
                return true;
        }
        // Règles de contrôles pour le Grand Roque noir :
        else if(caseDestination.no_case == caseDepart.no_case + 24
                && case.no_case(64).piece == "tour"
                && case.no_case(40).piece == null
                && case.no_case(48).piece == null
                && case.no_case(56).piece == null)
        {
                // Récupérer la tour de la case 64 et la placer dans la case 40.
                // Récupérer le roi de la case 32 et le placer dans la case 56.
                return true;
        }
        */
        // Règles de contrôles pour le petit Roque.
        return true;
    }



    /**
     * Méthode qui transforme un pion quand il atteint le bout de l'échiquier :
     * @return
     * @throws Exception
     */
    public boolean transformationPion() throws Exception
    {
        // Règles de contrôle pour transformer un pion
        /*
        1- Méthode 1 : Vérifier que le pion est arrivé au bout de l’échiquier.
				—> Si oui : Ouvrir une pop-up.
				—> Si non : Ne rien faire.
        2- Méthode 2 : Récupérer la pièce choisi par le joueur et transformer le pion :
				—> Passer en paramètre la pièce choisie par le joueur :
						reine, tour, fou, cavalier, pion.
				—> Utiliser un Switch Case : Selon la pièce choisi, la valeur valeur suivante sera assignée :
						reine, tour, fou, cavalier, pion.
        */
        return true;
    }



    /**
     * Méthode qui permet au joueur d'abandoner la partie.
     * @return
     * @throws Exception
     */
    public void abandon() throws Exception
    {
        // IMPLEMENTER DES REGLES QUI APPELLENT LE SERVICE QUI REINITIALISE L'ECHIQUIER.
        // UN MESSAGE D'ABANDON DEVRA ETRE RENVOYE AU USER.
    }
























    /********************* Méthodes relatives à la gestion de la partie *********************/

    // COMMENTAIRE :
    /*
    Le bon fonctionnement des 2 méthodes qui suivent mplique que la valeur nombreDeTour doit être stockée en BDD.
    Il faut créer un objet et une table partie. Cet objet disposera des attributs suivants :
    -int id;
	-int nombreDeTour;
	-User userBlanc;
	-User userNoir;
	-User userGagnant;
    */



    /**
     * Méthode qui récupère les positions de toutes les pièces du camp adverse.
     * @return
     */

    public List<Case> positionsCampsAdverse() {

        // 1- Identification du camp qui joue :
        String campQuiJoue = campQuiJoue();

        // 2- Récupérer toutes les cases et les trier par id de case :
        List<Case> echiquier = caseRepository.findAll();
        echiquier.sort(Comparator.comparing(Case::getNo_case));
        // 3- Récupérer toutes les cases du camp adverse :
        List<Case> positionsCampAdverse = new ArrayList<Case>();
        for(int i = 0; i<=echiquier.size() ; i++)
        {
            if(!echiquier.get(i).getPiece().getCouleur().getCouleur().equals(campQuiJoue)){
                positionsCampAdverse.add(echiquier.get(i));
            }
        }
        // 4- Renvoie des positions du camp adverse :
        return positionsCampAdverse;
    }




    /**
     * Cette méthode renvoie la couleur du camp qui est entrain de jouer.
     * La valeur renvoyée est une string nommée "camp".
     * @return
     */

    public String campQuiJoue()
    {
        Long id = 1L;
        Optional<Partie> partie = partieRepository.findById(id);
        int nombreDeTour = partie.get().getNombreDeTour();
                String campQuiJoue;
        // Identification du camp qui joue et mise à jour du nombre de tour :
        if(nombreDeTour % 2 == 0){
            campQuiJoue = "noir";
            nombreDeTour++;
            partie.get().setNombreDeTour(nombreDeTour);
            partieRepository.save(partie);
        } else {
            campQuiJoue = "blanc";
            nombreDeTour++;
        }
        return campQuiJoue;
    }




}
