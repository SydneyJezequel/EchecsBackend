package com.Applications.EchecsBackend.service.echecs;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Couleur;
import com.Applications.EchecsBackend.models.echecs.Piece;
import com.Applications.EchecsBackend.repository.echecs.CaseRepository;
import com.Applications.EchecsBackend.repository.echecs.PieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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




    // ********************* Constructeur *********************

    @Autowired
    public JouerService(CaseRepository caseRepository, DemarrerUnePartieService demarrerUnePartieService, PieceRepository pieceRepository) {
        this.caseRepository = caseRepository;
        this.demarrerUnePartieService = demarrerUnePartieService;
        this.pieceRepository = pieceRepository;
    }




    // ********************* Méthodes *********************

    /**
     * Méthode qui renvoie toutes les cases
     */
    public List<Case> findAllCases() {
        return caseRepository.findAll();
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
                deplacementRoi();
                Roque();
                echecEtPat();
                System.out.println("Déplacement roi");
                break;
            case "reine":
                // Déplacement de la pièce :
                deplacementReine();
                // Contrôles :
                echecAuRoi();
                echecEtMat();
                System.out.println("Déplacement reine");
                break;
            case "tour":
                // Déplacement de la pièce :
                deplacementTour();
                // Contrôles :
                echecAuRoi();
                echecEtMat();
                System.out.println("Déplacement tour");
                break;
            case "fou":
                // Déplacement de la pièce :
                deplacementFou();
                // Contrôles :
                echecAuRoi();
                echecEtMat();
                System.out.println("Déplacement fou");
                break;
            case "cavalier":
                // Déplacement de la pièce :
                deplacementCavalier();
                // Contrôles :
                echecAuRoi();
                echecEtMat();
                System.out.println("Déplacement cavalier");
                break;
            default: // Pion
                // Déplacement de la pièce :
                deplacementPion();
                // Contrôles :
                echecAuRoi();
                echecEtMat();
                transformationPion();
                System.out.println("Déplacement pion");
        }
        // **************************  TEST : A SUPPRIMER **************************

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



    /**
     * Méthode qui identifie la pièce.
     * Cette méthode va appeler les méthodes qui réalisent les contrôles
     * pour les déplacements de chaque pièce (deplacementRoi(), deplacementReine, etc.) :
     * Si les conditions sont vérifiés, la méthode : "echiquierMaj(List<Case> casesDeplacement)"
     * est appelée pour déplacer la pièce.
     */
    public List<Case> deplacerPiece(List<Case> casesDeplacement) throws Exception {

        // Attributs :
        boolean deplacementRoi = false;
        boolean deplacementReine = false;
        boolean deplacementTour = false;
        boolean deplacementFou = false;
        boolean deplacementCavalier = false;
        boolean deplacementPion = false;

        // 1- Récupération de la pièce :
        Piece piece = pieceRepository.findById(casesDeplacement.get(0).getPiece().getNo_piece()).
                orElseThrow(() -> new Exception("Piece not exist with id: " + casesDeplacement.get(0).getPiece().getNo_piece()));

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
                deplacementRoi = deplacementRoi();
                Roque();
                echecEtPat();
                System.out.println("Déplacement roi");
                break;
            case "reine":
                // Déplacement de la pièce :
                deplacementReine = deplacementReine();
                // Contrôles :
                echecAuRoi();
                echecEtMat();
                System.out.println("Déplacement reine");
                break;
            case "tour":
                // Déplacement de la pièce :
                deplacementTour = deplacementTour();
                // Contrôles :
                echecAuRoi();
                echecEtMat();
                System.out.println("Déplacement tour");
                break;
            case "fou":
                // Déplacement de la pièce :
                deplacementFou = deplacementFou();
                // Contrôles :
                echecAuRoi();
                echecEtMat();
                System.out.println("Déplacement fou");
                break;
            case "cavalier":
                // Déplacement de la pièce :
                deplacementCavalier = deplacementCavalier();
                // Contrôles :
                echecAuRoi();
                echecEtMat();
                System.out.println("Déplacement cavalier");
                break;
            default: // Pion
                // Déplacement de la pièce :
                deplacementPion = deplacementPion();
                // Contrôles :
                echecAuRoi();
                echecEtMat();
                transformationPion();
                System.out.println("Déplacement pion");
        }

        // 2- Appelle de la méthode qui déplace les pièces :
        if( deplacementRoi || deplacementReine || deplacementTour
            || deplacementFou || deplacementCavalier || deplacementPion)
        {
            echiquierMaj(casesDeplacement);
        }

        // 3- Renvoie de l'échiquier (mis à jour avec le déplacement ou non) :
        List<Case> echiquierMaj = demarrerUnePartieService.getEchequier();
        return echiquierMaj;
    }



        /**
         * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
         * d'une pièce de type Roi.
         * @return
         * @throws Exception
         */
    public boolean deplacementRoi() throws Exception
    {
        // Règles de contrôles.
        /*
        If (caseDestination.no_case == no_case + 1
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
        return true;
    }



    /**
     * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
     * d'une pièce de type Reine.
     * @return
     * @throws Exception
     */
    public boolean deplacementReine() throws Exception
    {
      // Règles de contrôles.
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
     * d'une pièce de type Tour.
     * @return
     * @throws Exception
     */
    public boolean deplacementTour() throws Exception
    {
        // Règles de contrôles.
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
    public boolean deplacementFou() throws Exception
    {
        // Règles de contrôles :
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
    public boolean deplacementCavalier() throws Exception
    {
        // Règles de contrôles.
    /*
    If (
      // Contrôle des déplacements possibles :
      || caseDestination.no_case == caseDepart.no_case + 6
      || caseDestination.no_case == caseDepart.no_case + 10
      || caseDestination.no_case == caseDepart.no_case + 15
      || caseDestination.no_case == caseDepart.no_case + 17
      || caseDestination.no_case == caseDepart.no_case - 6
      || caseDestination.no_case == caseDepart.no_case - 10
      || caseDestination.no_case == caseDepart.no_case - 15
      || caseDestination.no_case == caseDepart.no_case - 17
    )
    {
	    return true;
    }
    */
        return true;
    }



    /**
     * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
     * d'une pièce de type Pion.
     * @return
     * @throws Exception
     */
    public boolean deplacementPion() throws Exception
    {
        // Règles de contrôles.
        /*
        If ( caseDestination.no_case == caseDepart.no_case + 1 && caseDestination.piece == null
        || caseDestination.no_case == caseDepart.no_case + 2 && caseDepart.no_case = case_origine && caseDestination.piece == null
        || caseDestination.no_case == caseDepart.no_case + 9 && caseDestination.piece != null && caseDestination.piece.couleur != caseDepart.piece.couleur
        || caseDestination.no_case == caseDepart.no_case -7 && caseDestination.piece != null && caseDestination.piece.couleur != caseDepart.piece.couleur
        )
        {
	        return true;
        }
         */
        return true;
    }



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
        // Règles de contrôles pour le Grand Roque.
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




}
