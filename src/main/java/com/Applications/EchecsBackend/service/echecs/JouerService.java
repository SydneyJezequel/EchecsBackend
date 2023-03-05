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
                deplacementPion(caseDepart, caseDestination, piece, echiquier);
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
                if(deplacementPion(caseDepart, caseDestination, piece, echiquier)) {
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
            if (verificationCampPieceCaseDestination(caseDepart, caseDestination))
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
        String plus = "plus";
        String moins = "moins";

      // Case horizontales et verticales :
        Case caseDepartPlus1 = calculCaseIntermediaire(caseDepart, 1L, echiquier, plus);
        Case caseDepartPlus2 = calculCaseIntermediaire(caseDepart, 2L, echiquier, plus);
        Case caseDepartPlus3 = calculCaseIntermediaire(caseDepart, 3L, echiquier, plus);
        Case caseDepartPlus4 = calculCaseIntermediaire(caseDepart, 4L, echiquier, plus);
        Case caseDepartPlus5 = calculCaseIntermediaire(caseDepart, 5L, echiquier, plus);
        Case caseDepartPlus6 = calculCaseIntermediaire(caseDepart, 6L, echiquier, plus);
        Case caseDepartPlus8 = calculCaseIntermediaire(caseDepart, 7L, echiquier, plus);

        Case caseDepartMoins1 = calculCaseIntermediaire(caseDepart, 2L, echiquier, moins);
        Case caseDepartMoins2 = calculCaseIntermediaire(caseDepart, 3L, echiquier, moins);
        Case caseDepartMoins3 = calculCaseIntermediaire(caseDepart, 4L, echiquier, moins);
        Case caseDepartMoins4 = calculCaseIntermediaire(caseDepart, 5L, echiquier, moins);
        Case caseDepartMoins5 = calculCaseIntermediaire(caseDepart, 6L, echiquier, moins);
        Case caseDepartMoins6 = calculCaseIntermediaire(caseDepart, 7L, echiquier, moins);
        Case caseDepartMoins8 = calculCaseIntermediaire(caseDepart, 8L, echiquier, moins);

        Case caseDepartPlus16 = calculCaseIntermediaire(caseDepart, 15L, echiquier, plus);
        Case caseDepartPlus24 = calculCaseIntermediaire(caseDepart, 23L, echiquier, plus);
        Case caseDepartPlus32 = calculCaseIntermediaire(caseDepart, 31L, echiquier, plus);
        Case caseDepartPlus40 = calculCaseIntermediaire(caseDepart, 39L, echiquier, plus);
        Case caseDepartPlus48 = calculCaseIntermediaire(caseDepart, 47L, echiquier, plus);
        Case caseDepartPlus56 = calculCaseIntermediaire(caseDepart, 55L, echiquier, plus);

        Case caseDepartMoins8Hor = calculCaseIntermediaire(caseDepart, 9L, echiquier, moins);
        Case caseDepartMoins16 = calculCaseIntermediaire(caseDepart, 17L, echiquier, moins);
        Case caseDepartMoins24 = calculCaseIntermediaire(caseDepart, 25L, echiquier, moins);
        Case caseDepartMoins32 = calculCaseIntermediaire(caseDepart, 33L, echiquier, moins);
        Case caseDepartMoins40 = calculCaseIntermediaire(caseDepart, 41L, echiquier, moins);
        Case caseDepartMoins48 = calculCaseIntermediaire(caseDepart, 49L, echiquier, moins);
        Case caseDepartMoins56 = calculCaseIntermediaire(caseDepart, 57L, echiquier, moins);

        // Case diagonales :
        Case caseDepartPlus9 = echiquier.get((int) (caseDepart.getNo_case()+9));
        Case caseDepartPlus18 = echiquier.get((int) (caseDepart.getNo_case()+18));
        Case caseDepartPlus27 = echiquier.get((int) (caseDepart.getNo_case()+27));
        Case caseDepartPlus36 = echiquier.get((int) (caseDepart.getNo_case()+36));
        Case caseDepartPlus45 = echiquier.get((int) (caseDepart.getNo_case()+45));
        Case caseDepartPlus54 = echiquier.get((int) (caseDepart.getNo_case()+54));
        Case caseDepartPlus63 = echiquier.get((int) (caseDepart.getNo_case()+63));
        Case caseDepartMoins9 = echiquier.get((int) (caseDepart.getNo_case()-9));
        Case caseDepartMoins18 = echiquier.get((int) (caseDepart.getNo_case()-18));
        Case caseDepartMoins27 = echiquier.get((int) (caseDepart.getNo_case()-27));
        Case caseDepartMoins36 = echiquier.get((int) (caseDepart.getNo_case()-36));
        Case caseDepartMoins45 = echiquier.get((int) (caseDepart.getNo_case()-45));
        Case caseDepartMoins54 = echiquier.get((int) (caseDepart.getNo_case()-54));
        Case caseDepartMoins63 = echiquier.get((int) (caseDepart.getNo_case()-63));
        Case caseDepartPlus7 = echiquier.get((int) (caseDepart.getNo_case()+7));
        Case caseDepartPlus14 = echiquier.get((int) (caseDepart.getNo_case()+14));
        Case caseDepartPlus21 = echiquier.get((int) (caseDepart.getNo_case()+21));
        Case caseDepartPlus28 = echiquier.get((int) (caseDepart.getNo_case()+28));
        Case caseDepartPlus35 = echiquier.get((int) (caseDepart.getNo_case()+35));
        Case caseDepartPlus42 = echiquier.get((int) (caseDepart.getNo_case()+42));
        Case caseDepartPlus49 = echiquier.get((int) (caseDepart.getNo_case()+49));
        Case caseDepartMoins7 = echiquier.get((int) (caseDepart.getNo_case()-7));
        Case caseDepartMoins14 = echiquier.get((int) (caseDepart.getNo_case()-14));
        Case caseDepartMoins21 = echiquier.get((int) (caseDepart.getNo_case()-21));
        Case caseDepartMoins28 = echiquier.get((int) (caseDepart.getNo_case()-28));
        Case caseDepartMoins35 = echiquier.get((int) (caseDepart.getNo_case()-35));
        Case caseDepartMoins42 = echiquier.get((int) (caseDepart.getNo_case()-42));
        Case caseDepartMoins49 = echiquier.get((int) (caseDepart.getNo_case()-49));
      // Déplacements possibles :
        // Déplacement ligne droite verticale :
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
         || caseDestination.getNo_case() == caseDepart.getNo_case() - 16 && caseDepartMoins8Hor.getPiece() == null
         || caseDestination.getNo_case() == caseDepart.getNo_case() - 24 && caseDepartMoins16.getPiece()== null && caseDepartMoins8Hor.getPiece() == null
         || caseDestination.getNo_case() == caseDepart.getNo_case() - 32 && caseDepartMoins24.getPiece() == null && caseDepartMoins16.getPiece() == null && caseDepartMoins8Hor.getPiece() == null
         || caseDestination.getNo_case() == caseDepart.getNo_case() - 40 && caseDepartMoins32.getPiece() == null && caseDepartMoins24.getPiece() == null && caseDepartMoins16.getPiece() == null && caseDepartMoins8Hor.getPiece()== null
         || caseDestination.getNo_case() == caseDepart.getNo_case() - 48 && caseDepartMoins40.getPiece() == null && caseDepartMoins32.getPiece() == null && caseDepartMoins24.getPiece() == null && caseDepartMoins16.getPiece() == null && caseDepartMoins8Hor.getPiece() == null
         || caseDestination.getNo_case() == caseDepart.getNo_case() - 56 && caseDepartMoins48.getPiece() == null && caseDepartMoins40.getPiece() == null && caseDepartMoins32.getPiece() == null && caseDepartMoins24.getPiece() == null && caseDepartMoins16.getPiece() == null && caseDepartMoins8Hor.getPiece() == null

         // Déplacement en diagonales :
         || caseDestination.getNo_case() == caseDepart.getNo_case() + 9
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
          if (verificationCampPieceCaseDestination(caseDepart, caseDestination))
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
      // Version à reprendre :
      /*
       // Récupération des numéros des cases :
      int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
      int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());

      // Case horizontales et verticales :
        Case caseDepartPlus1 = echiquier.get((int) (caseDepart.getNo_case()+1));
        Case caseDepartPlus2 = echiquier.get((int) (caseDepart.getNo_case()+2));
        Case caseDepartPlus3 = echiquier.get((int) (caseDepart.getNo_case()+3));
        Case caseDepartPlus4 = echiquier.get((int) (caseDepart.getNo_case()+4));
        Case caseDepartPlus5 = echiquier.get((int) (caseDepart.getNo_case()+5));
        Case caseDepartPlus6 = echiquier.get((int) (caseDepart.getNo_case()+6));
        Case caseDepartMoins1 = echiquier.get((int) (caseDepart.getNo_case()-1));
        Case caseDepartMoins2 = echiquier.get((int) (caseDepart.getNo_case()-2));
        Case caseDepartMoins3 = echiquier.get((int) (caseDepart.getNo_case()-3));
        Case caseDepartMoins4 = echiquier.get((int) (caseDepart.getNo_case()-4));
        Case caseDepartMoins5 = echiquier.get((int) (caseDepart.getNo_case()-5));
        Case caseDepartMoins6 = echiquier.get((int) (caseDepart.getNo_case()-6));
        Case caseDepartPlus8 = echiquier.get((int) (caseDepart.getNo_case()+8));
        Case caseDepartPlus16 = echiquier.get((int) (caseDepart.getNo_case()+16));
        Case caseDepartPlus24 = echiquier.get((int) (caseDepart.getNo_case()+24));
        Case caseDepartPlus32 = echiquier.get((int) (caseDepart.getNo_case()+32));
        Case caseDepartPlus40 = echiquier.get((int) (caseDepart.getNo_case()+40));
        Case caseDepartPlus48 = echiquier.get((int) (caseDepart.getNo_case()+48));
        Case caseDepartMoins8 = echiquier.get((int) (caseDepart.getNo_case()-8));
        Case caseDepartMoins16 = echiquier.get((int) (caseDepart.getNo_case()-16));
        Case caseDepartMoins24 = echiquier.get((int) (caseDepart.getNo_case()-24));
        Case caseDepartMoins32 = echiquier.get((int) (caseDepart.getNo_case()-32));
        Case caseDepartMoins40 = echiquier.get((int) (caseDepart.getNo_case()-40));
        Case caseDepartMoins48 = echiquier.get((int) (caseDepart.getNo_case()-48));
        // Case diagonales :
        Case caseDepartPlus9 = echiquier.get((int) (caseDepart.getNo_case()+9));
        Case caseDepartPlus18 = echiquier.get((int) (caseDepart.getNo_case()+18));
        Case caseDepartPlus27 = echiquier.get((int) (caseDepart.getNo_case()+27));
        Case caseDepartPlus36 = echiquier.get((int) (caseDepart.getNo_case()+36));
        Case caseDepartPlus45 = echiquier.get((int) (caseDepart.getNo_case()+45));
        Case caseDepartPlus54 = echiquier.get((int) (caseDepart.getNo_case()+54));
        Case caseDepartPlus63 = echiquier.get((int) (caseDepart.getNo_case()+63));
        Case caseDepartMoins9 = echiquier.get((int) (caseDepart.getNo_case()-9));
        Case caseDepartMoins18 = echiquier.get((int) (caseDepart.getNo_case()-18));
        Case caseDepartMoins27 = echiquier.get((int) (caseDepart.getNo_case()-27));
        Case caseDepartMoins36 = echiquier.get((int) (caseDepart.getNo_case()-36));
        Case caseDepartMoins45 = echiquier.get((int) (caseDepart.getNo_case()-45));
        Case caseDepartMoins54 = echiquier.get((int) (caseDepart.getNo_case()-54));
        Case caseDepartMoins63 = echiquier.get((int) (caseDepart.getNo_case()-63));
        Case caseDepartPlus7 = echiquier.get((int) (caseDepart.getNo_case()+7));
        Case caseDepartPlus14 = echiquier.get((int) (caseDepart.getNo_case()+14));
        Case caseDepartPlus21 = echiquier.get((int) (caseDepart.getNo_case()+21));
        Case caseDepartPlus28 = echiquier.get((int) (caseDepart.getNo_case()+28));
        Case caseDepartPlus35 = echiquier.get((int) (caseDepart.getNo_case()+35));
        Case caseDepartPlus42 = echiquier.get((int) (caseDepart.getNo_case()+42));
        Case caseDepartPlus49 = echiquier.get((int) (caseDepart.getNo_case()+49));
        Case caseDepartMoins7 = echiquier.get((int) (caseDepart.getNo_case()-7));
        Case caseDepartMoins14 = echiquier.get((int) (caseDepart.getNo_case()-14));
        Case caseDepartMoins21 = echiquier.get((int) (caseDepart.getNo_case()-21));
        Case caseDepartMoins28 = echiquier.get((int) (caseDepart.getNo_case()-28));
        Case caseDepartMoins35 = echiquier.get((int) (caseDepart.getNo_case()-35));
        Case caseDepartMoins42 = echiquier.get((int) (caseDepart.getNo_case()-42));
        Case caseDepartMoins49 = echiquier.get((int) (caseDepart.getNo_case()-49));
      // Déplacements possibles :
        if (
         // Contrôle ligne droite verticale :
         caseDestination.getNo_case() == caseDepart.getNo_case()+ 1
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

         // Déplacement en diagonales :
         || caseDestination.getNo_case() == caseDepart.getNo_case() + 9
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
	    return true;
      } else {
        return false;
      }
      */




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
        String plus = "plus";
        String moins = "moins";
        Case caseDepartPlus1 = calculCaseIntermediaire(caseDepart, 1L, echiquier, plus);
        Case caseDepartPlus2 = calculCaseIntermediaire(caseDepart, 2L, echiquier, plus);
        Case caseDepartPlus3 = calculCaseIntermediaire(caseDepart, 3L, echiquier, plus);
        Case caseDepartPlus4 = calculCaseIntermediaire(caseDepart, 4L, echiquier, plus);
        Case caseDepartPlus5 = calculCaseIntermediaire(caseDepart, 5L, echiquier, plus);
        Case caseDepartPlus6 = calculCaseIntermediaire(caseDepart, 6L, echiquier, plus);

        Case caseDepartMoins1 = calculCaseIntermediaire(caseDepart, 2L, echiquier, moins);
        Case caseDepartMoins2 = calculCaseIntermediaire(caseDepart, 3L, echiquier, moins);
        Case caseDepartMoins3 = calculCaseIntermediaire(caseDepart, 4L, echiquier, moins);
        Case caseDepartMoins4 = calculCaseIntermediaire(caseDepart, 5L, echiquier, moins);
        Case caseDepartMoins5 = calculCaseIntermediaire(caseDepart, 6L, echiquier, moins);
        Case caseDepartMoins6 = calculCaseIntermediaire(caseDepart, 7L, echiquier, moins);

        Case caseDepartPlus8 = calculCaseIntermediaire(caseDepart, 9L, echiquier, plus);
        Case caseDepartPlus16 = calculCaseIntermediaire(caseDepart, 15L, echiquier, plus);
        Case caseDepartPlus24 = calculCaseIntermediaire(caseDepart, 23L, echiquier, plus);
        Case caseDepartPlus32 = calculCaseIntermediaire(caseDepart, 31L, echiquier, plus);
        Case caseDepartPlus40 = calculCaseIntermediaire(caseDepart, 39L, echiquier, plus);
        Case caseDepartPlus48 = calculCaseIntermediaire(caseDepart, 47L, echiquier, plus);

        Case caseDepartMoins8 = calculCaseIntermediaire(caseDepart, 9L, echiquier, moins);
        Case caseDepartMoins16 = calculCaseIntermediaire(caseDepart, 17L, echiquier, moins);
        Case caseDepartMoins24 = calculCaseIntermediaire(caseDepart, 25L, echiquier, moins);
        Case caseDepartMoins32 = calculCaseIntermediaire(caseDepart, 33L, echiquier, moins);
        Case caseDepartMoins40 = calculCaseIntermediaire(caseDepart, 41L, echiquier, moins);
        Case caseDepartMoins48 = calculCaseIntermediaire(caseDepart, 49L, echiquier, moins);

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
          if (verificationCampPieceCaseDestination(caseDepart, caseDestination))
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
        Case caseDepartPlus9 = echiquier.get((int) (caseDepart.getNo_case()+9));
        Case caseDepartPlus18 = echiquier.get((int) (caseDepart.getNo_case()+18));
        Case caseDepartPlus27 = echiquier.get((int) (caseDepart.getNo_case()+27));
        Case caseDepartPlus36 = echiquier.get((int) (caseDepart.getNo_case()+36));
        Case caseDepartPlus45 = echiquier.get((int) (caseDepart.getNo_case()+45));
        Case caseDepartPlus54 = echiquier.get((int) (caseDepart.getNo_case()+54));
        Case caseDepartPlus63 = echiquier.get((int) (caseDepart.getNo_case()+63));
        Case caseDepartMoins9 = echiquier.get((int) (caseDepart.getNo_case()-9));
        Case caseDepartMoins18 = echiquier.get((int) (caseDepart.getNo_case()-18));
        Case caseDepartMoins27 = echiquier.get((int) (caseDepart.getNo_case()-27));
        Case caseDepartMoins36 = echiquier.get((int) (caseDepart.getNo_case()-36));
        Case caseDepartMoins45 = echiquier.get((int) (caseDepart.getNo_case()-45));
        Case caseDepartMoins54 = echiquier.get((int) (caseDepart.getNo_case()-54));
        Case caseDepartMoins63 = echiquier.get((int) (caseDepart.getNo_case()-63));
        Case caseDepartPlus7 = echiquier.get((int) (caseDepart.getNo_case()+7));
        Case caseDepartPlus14 = echiquier.get((int) (caseDepart.getNo_case()+14));
        Case caseDepartPlus21 = echiquier.get((int) (caseDepart.getNo_case()+21));
        Case caseDepartPlus28 = echiquier.get((int) (caseDepart.getNo_case()+28));
        Case caseDepartPlus35 = echiquier.get((int) (caseDepart.getNo_case()+35));
        Case caseDepartPlus42 = echiquier.get((int) (caseDepart.getNo_case()+42));
        Case caseDepartPlus49 = echiquier.get((int) (caseDepart.getNo_case()+49));
        Case caseDepartMoins7 = echiquier.get((int) (caseDepart.getNo_case()-7));
        Case caseDepartMoins14 = echiquier.get((int) (caseDepart.getNo_case()-14));
        Case caseDepartMoins21 = echiquier.get((int) (caseDepart.getNo_case()-21));
        Case caseDepartMoins28 = echiquier.get((int) (caseDepart.getNo_case()-28));
        Case caseDepartMoins35 = echiquier.get((int) (caseDepart.getNo_case()-35));
        Case caseDepartMoins42 = echiquier.get((int) (caseDepart.getNo_case()-42));
        Case caseDepartMoins49 = echiquier.get((int) (caseDepart.getNo_case()-49));

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
            if (verificationCampPieceCaseDestination(caseDepart, caseDestination))
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
            if (verificationCampPieceCaseDestination(caseDepart, caseDestination))
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
     * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
     * d'une pièce de type Pion.
     * @return
     * @throws Exception
     */
    public boolean deplacementPion(Case caseDepart, Case caseDestination, Piece piece, List<Case> echiquier) throws Exception
    {
        // VERSION TEMPORAIRE :COMPLETER LES CONDITIONS AVEC LA CASE ORIGINE.
        // Récupération des numéros des cases :
        int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
        int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());
        Case caseIntermediaire;
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
            if (verificationCampPieceCaseDestination(caseDepart, caseDestination))
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
     * Cette méthode affecte les cases intermediaire par lesquels va passer une pièce lorsqu'elle se déplace.
     * @param caseDepart
     * @param nbCaseSup
     * @param echiquier
     * @return
     * @throws Exception
     */
    public Case calculCaseIntermediaire(Case caseDepart, Long nbCaseSup, List<Case> echiquier, String operateur) throws Exception
    {
        Case caseIntermediaire;
        if(operateur.equals("plus"))
        {
            if(caseDepart.getNo_case()+nbCaseSup>echiquier.size())
            {
                caseIntermediaire = null;
            }else if(caseDepart.getNo_case()+nbCaseSup==echiquier.size())
            {
                caseIntermediaire = echiquier.get(Math.toIntExact(caseDepart.getNo_case()+nbCaseSup-1));
            }else
            {
                int test = Math.toIntExact(caseDepart.getNo_case()+nbCaseSup);
                caseIntermediaire = echiquier.get(Math.toIntExact(caseDepart.getNo_case()+nbCaseSup));
            }
        }else{
            if(caseDepart.getNo_case()-nbCaseSup<0)
            {
                caseIntermediaire = null;
            }else
            {
                caseIntermediaire = echiquier.get(Math.toIntExact(caseDepart.getNo_case()-nbCaseSup));
            }
        }
        return caseIntermediaire;
    }




    /**
     * Cette méthode vérifie si la pièce sur la case de destination fait partie du même camp.
     * @param caseDepart
     * @param caseDestination
     * @return
     * @throws Exception
     */
    public boolean verificationCampPieceCaseDestination(Case caseDepart, Case caseDestination) throws Exception {
        if (caseDestination.getPiece() == null || caseDestination.getPiece() != null && !caseDestination.getPiece().getCouleur().getCouleur().equals(caseDepart.getPiece().getCouleur().getCouleur())) {
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
