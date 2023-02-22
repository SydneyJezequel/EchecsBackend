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
        // Règles de contrôles.
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
        return true;
    }



    /**
     * Méthode qui vérifie si le roi est en échec :
     * @return
     * @throws Exception
     */
    public boolean echecAuRoi() throws Exception
    {
        // Règles de contrôles.
        return true;
    }



    /**
     * Méthode qui vérifie si le roi est échec et mat :
     * @return
     * @throws Exception
     */
    public boolean echecEtMat() throws Exception
    {
        // Règles de contrôles.
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
        // Règles de contrôles.
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
        // Règles de contrôles pour le Grand Roque.
        // Règles de contrôles pour le petit Roque.
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
