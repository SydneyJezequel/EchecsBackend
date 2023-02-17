package com.Applications.EchecsBackend.service.echecs;

import com.Applications.EchecsBackend.models.connexion.User;
import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Couleur;
import com.Applications.EchecsBackend.models.echecs.Piece;
import com.Applications.EchecsBackend.repository.echecs.CaseRepository;
import com.Applications.EchecsBackend.repository.echecs.PieceRepository;
import jakarta.persistence.SynchronizationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        // ****************************** TEST ****************************** :
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
        // ****************************** TEST ****************************** :

        System.out.println("Service déclenché");
        System.out.println("Cases de départ :");
        System.out.println("Infos case de départ : " +
                caseDepart.getNo_case()
                + " "
                + caseDepart.getColonne()
                + " "
                + caseDepart.getLigne()
                + " "
                + caseDestination.getCouleur().getId()
                + " "
                + caseDepart.getCouleur().getCouleur());
        System.out.println("Pièce Case départ : "
                + caseDepart.getPiece());

        System.out.println("Cases de d'arrivée :");
        System.out.println("Infos case d'arrivée' : "
                + caseDestination.getNo_case()
                + " "
                + caseDestination.getColonne()
                + " "
                + caseDestination.getLigne()
                + " "
                + caseDestination.getCouleur().getId()
                + " "
                + caseDestination.getCouleur().getCouleur());
        System.out.println("Pièce case d'arrivée : "
                + " n° de la pièce : "
                + caseDestination.getPiece().getNo_piece()
                + ", type de pièce : "
                + caseDestination.getPiece().getType()
                + ", couleur de la pièce : "
                + caseDestination.getPiece().getCouleur());

        // ****************************** TEST ****************************** :
        // 3- Enregistrement des cases mises à jour :
        caseRepository.save(caseDepart);
        caseRepository.save(caseDestination);
        // 4- Récupération de la BDD Mise à jour :
        List<Case> echiquierMaj = demarrerUnePartieService.getEchequier();
        return echiquierMaj;
    }
/*
    public List<Case> echiquierMaj(List<Case> casesDeplacement) throws Exception {
            // 1- Récupération des objets mis à jour :
                // Piece piece = casesDeplacement.get(0).getPiece();
                Long pieceId = casesDeplacement.get(0).getPiece().getNo_piece();
                Piece piece = pieceRepository.getReferenceById(pieceId);
                // Mise à jour de la couleur de la case de Départ :
                Case caseDepart = casesDeplacement.get(0);
                if(caseDepart.getCouleur().getCouleur().equals("blanc"))
                {
                    caseDepart.setCouleur(new Couleur(1L,"blanc"));
                } else {
                    caseDepart.setCouleur(new Couleur(2L,"noir"));
                }
                // Mise à jour de la couleur de la case de Destination :
                Case caseDestination = casesDeplacement.get(1);
                if(caseDestination.getCouleur().getCouleur().equals("blanc"))
                {
                    caseDestination.setCouleur(new Couleur(1L,"blanc"));
                } else {
                    caseDestination.setCouleur(new Couleur(2L,"noir"));
                }
             // 2- Modification des objets :
                caseDepart.setPiece(null);
                caseDestination.setPiece(piece);
                 // ****************************** TEST ****************************** :
                System.out.println("Service déclenché");
                System.out.println("Cases de départ :");
                System.out.println("Infos case de départ : " + caseDepart.getNo_case() + " " + caseDepart.getColonne() + " " + caseDepart.getLigne() + " " + caseDepart.getCouleur().getCouleur());
                System.out.println("Pièce Case départ : " + caseDepart.getPiece());

                System.out.println("Cases de d'arrivée :");
                System.out.println("Infos case d'arrivée' : " + caseDestination.getNo_case() + " " + caseDestination.getColonne() + " " + caseDestination.getLigne() + " " + caseDestination.getCouleur().getCouleur());
                System.out.println("Pièce case d'arrivée : " + " n° de la pièce : "+ caseDestination.getPiece().getNo_piece() + ", type de pièce : " + caseDestination.getPiece().getType() + ", couleur de la pièce : "+ caseDestination.getPiece().getCouleur());
                 // ****************************** TEST ****************************** :
             // 3- Sauvegarde des objets dans la BDD :
                caseRepository.save(caseDepart);
                caseRepository.save(caseDestination);
                // TEST :
                Optional<Case> caseDepartTest = caseRepository.findById(caseDepart.getNo_case());
                System.out.println("Infos case de départ : " + caseDepartTest.get().getNo_case() + ", colonne : " + caseDepartTest.get().getColonne() + ", ligne : " + caseDepartTest.get().getLigne() + ", couleur : " + caseDepartTest.get().getCouleur().getCouleur() );
                Optional<Case> caseDestinationTest = caseRepository.findById(caseDestination.getNo_case());
                System.out.println("Infos case d'arrivée' : " + caseDestinationTest.get().getNo_case() + ", colonne : " + caseDestinationTest.get().getColonne() + ", ligne : " + caseDestinationTest.get().getLigne() + ", couleur : " + ", pièce : " + caseDepartTest.get().getCouleur().getCouleur() );
                // TEST :
            // 4- Récupération de la BDD Mise à jour :
                List<Case> echiquierMaj = demarrerUnePartieService.getEchequier();
                return echiquierMaj;
    }
*/



}
