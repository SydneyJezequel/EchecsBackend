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
     * Méthode qui déplace les pièces.
     * Cette méthode va appeler les méthodes qui réalisent les contrôles
     * pour les déplacements de chaque pièce (ex : deplacementRoi(), deplacementReine(), etc.) :
     * Si les conditions sont vérifiés, la méthode : "echiquierMaj(List<Case> casesDeplacement)"
     * est appelée pour déplacer la pièce et renvoyer l'échiquier mis à jour.
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
                if (deplacementRoi(caseDepart, caseDestination, echiquier) /*&& borduresRoi(caseDepart, caseDestination)*/) {
                    // Contrôles à exécuter sur le Roi :
                        //echecAuRoi();
                        // echecEtPat();
                    echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                }
                else if (Roque(caseDepart, caseDestination, echiquier)) {
                    if (caseDestination.getNo_case() == 56L) {
                        petitRoqueBlanc(caseDepart, caseDestination, piece, echiquier);
                    }else if(caseDestination.getNo_case()==49L)
                    {
                        petitRoqueNoir(caseDepart, caseDestination, piece, echiquier);
                    }else if(caseDestination.getNo_case()==24L)
                    {
                        grandRoqueBlanc(caseDepart, caseDestination, piece, echiquier);

                    }else if(caseDestination.getNo_case()==17L)
                    {
                        grandRoqueNoir(caseDepart, caseDestination, piece, echiquier);
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
                if(deplacementDame(caseDepart, caseDestination, echiquier) /*&& borduresDame(caseDepart, caseDestination)*/)
                {
                    // Contrôles à executer sur la Reine :
                    echecAuRoi();
                    echecEtMat();
                    echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                } else {
                    echiquierMaj = caseRepository.findAll();
                }
                break;
            case "tour":
                // Déplacement de la pièce :
                if(deplacementTour(caseDepart, caseDestination, echiquier) /*&& borduresTour(caseDepart, caseDestination)*/)
                {
                    // Contrôles à exécuter sur la tour :
                    echecAuRoi();
                    echecEtMat();
                    echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                } else {
                    echiquierMaj = caseRepository.findAll();
                }
                break;
            case "fou":
                // Déplacement de la pièce :
                if(deplacementFou(caseDepart, caseDestination, echiquier) /*&& borduresFou(caseDepart, caseDestination)*/)
                {
                    // Contrôles à exécuter sur le Fou :
                    // echecAuRoi();
                    // echecEtMat();
                    echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                } else {
                    echiquierMaj = caseRepository.findAll();
                }
                break;
            case "cavalier":
                // Déplacement de la pièce :
                if(deplacementCavalier(caseDepart, caseDestination, echiquier) /*&& borduresCavalier(caseDepart, caseDestination)*/) {
                    // Contrôles à exécuter sur le Cavalier :
                    // echecAuRoi();
                    // echecEtMat();
                    echiquierMaj = miseAJourEchiquier(caseDepart, caseDestination, piece);
                } else {
                    echiquierMaj = caseRepository.findAll();
                }
                break;
            case "pion":
                // Déplacement de la pièce :
                if(deplacementPion(caseDepart, caseDestination, piece, echiquier) /*&& borduresPion(caseDepart, caseDestination)*/) {
                    // Contrôles à exécuter sur le Pion :
                    // echecAuRoi();
                    // echecEtMat();
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
    public boolean deplacementDame(Case caseDepart, Case caseDestination, List<Case> echiquier) throws Exception
    {
        if(deplacementTour(caseDepart, caseDestination, echiquier) || deplacementFou(caseDepart, caseDestination, echiquier))
        {
            return true;
        } else
        {
            return false;
        }
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
        String plus = "plus";
        String moins = "moins";
        Case caseDepartPlus9 = calculCaseIntermediaire(caseDepart, 8L, echiquier, plus);
        Case caseDepartPlus18 = calculCaseIntermediaire(caseDepart, 17L, echiquier, plus);
        Case caseDepartPlus27 = calculCaseIntermediaire(caseDepart, 26L, echiquier, plus);
        Case caseDepartPlus36 = calculCaseIntermediaire(caseDepart, 35L, echiquier, plus);
        Case caseDepartPlus45 = calculCaseIntermediaire(caseDepart, 44L, echiquier, plus);
        Case caseDepartPlus54 = calculCaseIntermediaire(caseDepart, 53L, echiquier, plus);
        Case caseDepartPlus63 = calculCaseIntermediaire(caseDepart, 62L, echiquier, plus);

        Case caseDepartMoins9 = calculCaseIntermediaire(caseDepart, 10L, echiquier, moins);
        Case caseDepartMoins18 = calculCaseIntermediaire(caseDepart, 19L, echiquier, moins);
        Case caseDepartMoins27 = calculCaseIntermediaire(caseDepart, 28L, echiquier, moins);
        Case caseDepartMoins36 = calculCaseIntermediaire(caseDepart, 37L, echiquier, moins);
        Case caseDepartMoins45 = calculCaseIntermediaire(caseDepart, 46L, echiquier, moins);
        Case caseDepartMoins54 = calculCaseIntermediaire(caseDepart, 55L, echiquier, moins);
        Case caseDepartMoins63 = calculCaseIntermediaire(caseDepart, 64L, echiquier, moins);

        Case caseDepartPlus7 = calculCaseIntermediaire(caseDepart, 6L, echiquier, plus);
        Case caseDepartPlus14 = calculCaseIntermediaire(caseDepart, 13L, echiquier, plus);
        Case caseDepartPlus21 = calculCaseIntermediaire(caseDepart, 20L, echiquier, plus);
        Case caseDepartPlus28 = calculCaseIntermediaire(caseDepart, 27L, echiquier, plus);
        Case caseDepartPlus35 = calculCaseIntermediaire(caseDepart, 34L, echiquier, plus);
        Case caseDepartPlus42 = calculCaseIntermediaire(caseDepart, 41L, echiquier, plus);
        Case caseDepartPlus49 = calculCaseIntermediaire(caseDepart, 48L, echiquier, plus);

        Case caseDepartMoins7 = calculCaseIntermediaire(caseDepart, 8L, echiquier, moins);
        Case caseDepartMoins14 = calculCaseIntermediaire(caseDepart, 15L, echiquier, moins);
        Case caseDepartMoins21 = calculCaseIntermediaire(caseDepart, 22L, echiquier, moins);
        Case caseDepartMoins28 = calculCaseIntermediaire(caseDepart, 29L, echiquier, moins);
        Case caseDepartMoins35 = calculCaseIntermediaire(caseDepart, 36L, echiquier, moins);
        Case caseDepartMoins42 = calculCaseIntermediaire(caseDepart, 43L, echiquier, moins);
        Case caseDepartMoins49 = calculCaseIntermediaire(caseDepart, 50L, echiquier, moins);

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

















    /********************* Méthodes qui définissent les bordures pour toutes les pièces *********************/



    /**
     * Méthode qui définit les limites de l'échiquier pour un Roi.
     * @return
     */
    public boolean borduresRoi(Case caseDepart, Case caseDestination)
    {
        Piece piece = caseDepart.getPiece();
        List<String> nomPiece = List.of(piece.getType().split(" "));
        String typeDePiece = nomPiece.get(0);
        if(typeDePiece.equals("roi")
            && caseDepart.getNo_case() == 40L
            || caseDepart.getNo_case() == 33L
            && caseDestination.getNo_case() == 33L
            || caseDestination.getNo_case() == 41L
            || caseDestination.getNo_case() == 49L
            || caseDestination.getNo_case() == 24L
            || caseDestination.getNo_case() == 32L
            || caseDestination.getNo_case() == 40L
            )
        {
            return false;
        }
        else
        {
            return true;
        }
    }



    /**
     * Méthode qui définit les limites de l'échiquier pour une Dame.
     * @return
     */
    public boolean borduresDame(Case caseDepart, Case caseDestination)
    {
        Piece piece = caseDepart.getPiece();
        List<String> nomPiece = List.of(piece.getType().split(" "));
        String typeDePiece = nomPiece.get(0);
        if(typeDePiece.equals("reine")
           && caseDepart.getNo_case() == 32L
           || caseDepart.getNo_case() == 25L
           && caseDestination.getNo_case() == 25L
           || caseDestination.getNo_case() == 33L
           || caseDestination.getNo_case() == 41L
           || caseDestination.getNo_case() == 18L
           || caseDestination.getNo_case() == 50L
           || caseDestination.getNo_case() == 11L
           || caseDestination.getNo_case() == 4L
           || caseDestination.getNo_case() == 16L
           || caseDestination.getNo_case() == 24L
           || caseDestination.getNo_case() == 32L
           || caseDestination.getNo_case() == 7L
           || caseDestination.getNo_case() == 23L
           || caseDestination.getNo_case() == 39L
        )
        {
            return false;
        }
        else
        {
            return true;
        }
    }



    /**
     * Méthode qui définit les limites de l'échiquier pour une Tour.
     * @return
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



    /**
     * Méthode qui définit les limites de l'échiquier pour un Fou.
     * @return
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



    /**
     * Méthode qui définit les limites de l'échiquier pour un Cavalier.
     * @return
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



    /**
     * Méthode qui définit les limites de l'échiquier pour un Pion.
     * @return
     */
    public boolean borduresPion(Case caseDepart, Case caseDestination)
    {
       boolean deplacementAutorise = true;
        Piece piece = caseDepart.getPiece();
        List<String> nomPiece = List.of(piece.getType().split(" "));
        String typeDePiece = nomPiece.get(0);
        if(typeDePiece.equals("pion")) {
            // Pion Blanc :
            if (caseDepart.getNo_case() == 1L && caseDestination.getNo_case() == 16 || caseDestination.getNo_case() == 24) {
                deplacementAutorise = false;
            }
            if (caseDepart.getNo_case() == 9L && caseDestination.getNo_case() == 8 || caseDestination.getNo_case() == 16 || caseDestination.getNo_case() == 24) {
                deplacementAutorise = false;
            }
            if (caseDepart.getNo_case() == 17L && caseDestination.getNo_case() == 16 || caseDestination.getNo_case() == 24 || caseDestination.getNo_case() == 32) {
                deplacementAutorise = false;
            }
            if (caseDepart.getNo_case() == 25L && caseDestination.getNo_case() == 24 || caseDestination.getNo_case() == 32 || caseDestination.getNo_case() == 340) {
                deplacementAutorise = false;
            }
            if (caseDepart.getNo_case() == 33L && caseDestination.getNo_case() == 32 || caseDestination.getNo_case() == 40 || caseDestination.getNo_case() == 48) {
                deplacementAutorise = false;
            }
            if (caseDepart.getNo_case() == 41L && caseDestination.getNo_case() == 40 || caseDestination.getNo_case() == 48 || caseDestination.getNo_case() == 46) {
                deplacementAutorise = false;
            }
            if (caseDepart.getNo_case() == 49L && caseDestination.getNo_case() == 48 || caseDestination.getNo_case() == 56 || caseDestination.getNo_case() == 64) {
                deplacementAutorise = false;
            }
            if (caseDepart.getNo_case() == 57L && caseDestination.getNo_case() == 56 || caseDestination.getNo_case() == 64) {
                deplacementAutorise = false;
            }

            // Pion Noir :
            if (caseDepart.getNo_case() == 8L && caseDestination.getNo_case() == 9 || caseDestination.getNo_case() == 17 || caseDestination.getNo_case() == 32) {
                deplacementAutorise = false;
            }
            if (caseDepart.getNo_case() == 16L && caseDestination.getNo_case() == 1 || caseDestination.getNo_case() == 9 || caseDestination.getNo_case() == 17) {
                deplacementAutorise = false;
            }
            if (caseDepart.getNo_case() == 24L && caseDestination.getNo_case() == 9 || caseDestination.getNo_case() == 17 || caseDestination.getNo_case() == 25) {
                deplacementAutorise = false;
            }
            if (caseDepart.getNo_case() == 32L && caseDestination.getNo_case() == 17 || caseDestination.getNo_case() == 25 || caseDestination.getNo_case() == 33) {
                deplacementAutorise = false;
            }
            if (caseDepart.getNo_case() == 40L && caseDestination.getNo_case() == 25 || caseDestination.getNo_case() == 33 || caseDestination.getNo_case() == 41) {
                deplacementAutorise = false;
            }
            if (caseDepart.getNo_case() == 48L && caseDestination.getNo_case() == 33 || caseDestination.getNo_case() == 41 || caseDestination.getNo_case() == 49) {
                deplacementAutorise = false;
            }
            if (caseDepart.getNo_case() == 56L && caseDestination.getNo_case() == 41 || caseDestination.getNo_case() == 49 || caseDestination.getNo_case() == 57) {
                deplacementAutorise = false;
            }
        }
        return deplacementAutorise;
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
     * Méthode qui vérifie si le Grand Roque ou le Petit Roque est correct.
     * @return
     * @throws Exception
     */
    public boolean Roque(Case caseDepart, Case caseDestination, List<Case> echiquier) throws Exception
    {
        // Vérification de la validité du Petit Roque Blanc :
        if(echiquier.get(63).getPiece()!=null)
        {
            List<String> nomTourBlanche2 = List.of(echiquier.get(63).getPiece().getType().split(" "));
            String tourBlancheH1 = nomTourBlanche2.get(0);
            // Règle de contrôle pour le petit Roque Blanc
            if (caseDestination.getNo_case() == caseDepart.getNo_case() + 16L
                    && tourBlancheH1.equals("tour")
                    && echiquier.get(55).getPiece() == null
                    && echiquier.get(47).getPiece() == null)
            {
                return true;
            }
        }
        // Vérification de la validité du Grand Roque Blanc :
        if(echiquier.get(7).getPiece()!=null)
        {
            List<String> nomTourBlanche1 = List.of(echiquier.get(7).getPiece().getType().split(" "));
            String tourBlancheA1 = nomTourBlanche1.get(0);
            // Règles de contrôles pour le Grand Roque Blanc :
            if (caseDestination.getNo_case() == caseDepart.getNo_case() - 16L
                && tourBlancheA1.equals("tour")
                && echiquier.get(15).getPiece() == null
                && echiquier.get(23).getPiece() == null
                && echiquier.get(31).getPiece() == null)
            {
                return true;
            }
        }
        // Vérification de la validité du petit Roque Noir :
        if(echiquier.get(56).getPiece()!=null)
        {
            List<String> nomTourNoire2 = List.of(echiquier.get(56).getPiece().getType().split(" "));
            String tourNoireH8 = nomTourNoire2.get(0);
            // Règles de contrôles pour le petit Roque noir :
            if(caseDestination.getNo_case() == caseDepart.getNo_case() + 16L
                && tourNoireH8.equals("tour")
                && echiquier.get(48).getPiece() == null
                && echiquier.get(40).getPiece() == null)
            {
            return true;
            }
        }
        // Vérification de la validité du Grand Roque Noire:
        if(echiquier.get(0).getPiece()!=null)
        {
            List<String> nomTourNoire1 = List.of(echiquier.get(0).getPiece().getType().split(" "));
            String tourNoireA8 = nomTourNoire1.get(0);
            // Règles de contrôles pour le Grand Roque Noire:
            if(caseDestination.getNo_case() ==caseDepart.getNo_case() - 16L
                    && tourNoireA8.equals("tour")
                    && echiquier.get(24).getPiece() == null
                    && echiquier.get(16).getPiece() == null
                    && echiquier.get(8).getPiece() == null)
            {
                return true;
            }
        }
        return false;
    }




    /**
     * Cette méthode exécute le petit Roque Blanc.
     * @param caseDepart
     * @param caseDestination
     * @param piece
     * @param echiquier
     */
    public void petitRoqueBlanc(Case caseDepart, Case caseDestination, Piece piece, List<Case> echiquier)
    {
            caseDestination.setPiece(piece);
            caseDepart.setPiece(null);
            caseRepository.save(caseDepart);
            caseRepository.save(caseDestination);
            Case caseDepartTour = echiquier.get(63);
            Case caseDestinationTour = echiquier.get(47);
            caseDestinationTour.setPiece(echiquier.get(63).getPiece());
            caseDepartTour.setPiece(null);
            caseRepository.save(caseDepartTour);
            caseRepository.save(caseDestinationTour);
    }




    /**
     * Cette méthode exécute le petit Roque Noir.
     */
    public void petitRoqueNoir(Case caseDepart, Case caseDestination, Piece piece, List<Case> echiquier)
    {
            caseDestination.setPiece(piece);
            caseDepart.setPiece(null);
            caseRepository.save(caseDepart);
            caseRepository.save(caseDestination);
            Case caseDepartTour = echiquier.get(56);
            Case caseDestinationTour = echiquier.get(40);
            caseDestinationTour.setPiece(echiquier.get(56).getPiece());
            caseDepartTour.setPiece(null);
            caseRepository.save(caseDepartTour);
            caseRepository.save(caseDestinationTour);
    }




    /**
     * Cette méthode exécute le grand Roque Blanc.
     */
    public void grandRoqueBlanc(Case caseDepart, Case caseDestination, Piece piece, List<Case> echiquier)
    {
            caseDestination.setPiece(piece);
            caseDepart.setPiece(null);
            caseRepository.save(caseDepart);
            caseRepository.save(caseDestination);
            Case caseDepartTour = echiquier.get(7);
            Case caseDestinationTour = echiquier.get(31);
            caseDestinationTour.setPiece(echiquier.get(7).getPiece());
            caseDepartTour.setPiece(null);
            caseRepository.save(caseDepartTour);
            caseRepository.save(caseDestinationTour);
    }




    /**
     * Cette méthode exécute le grand Roque Noir.
     */
    public void grandRoqueNoir(Case caseDepart, Case caseDestination, Piece piece, List<Case> echiquier)
    {
            caseDestination.setPiece(piece);
            caseDepart.setPiece(null);
            caseRepository.save(caseDepart);
            caseRepository.save(caseDestination);
            Case caseDepartTour = echiquier.get(0);
            Case caseDestinationTour = echiquier.get(24);
            caseDestinationTour.setPiece(echiquier.get(0).getPiece());
            caseDepartTour.setPiece(null);
            caseRepository.save(caseDepartTour);
            caseRepository.save(caseDestinationTour);
    }




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
