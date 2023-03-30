package com.Applications.EchecsBackend.service.echecs.gestionPartie.serviceImpl;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Partie;
import com.Applications.EchecsBackend.models.echecs.Piece;
import com.Applications.EchecsBackend.repository.echecs.CaseRepository;
import com.Applications.EchecsBackend.repository.echecs.PartieRepository;
import com.Applications.EchecsBackend.repository.echecs.PieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;





/**
 * Service qui gère les parties.
 */
@Service
public class GestionDesParties {





    // ********************* Dépendances *********************

    Piece tour = new Piece();

    private final PieceRepository pieceRepository;
    private final PartieRepository partieRepository;
    private final CaseRepository caseRepository;





    // ********************* Constructeur ******************** :
    @Autowired
    public GestionDesParties(PieceRepository pieceRepository, PartieRepository partieRepository, CaseRepository caseRepository) {
        this.pieceRepository = pieceRepository;
        this.partieRepository = partieRepository;
        this.caseRepository = caseRepository;
    }





    // ********************* Méthodes ******************** :


    /**
     * Méthode qui récupère les positions de toutes les pièces du camp adverse
     * au camp passé en paramètre
     * C'est la méthode inverse de positionsPiecesCamp(String camp).
     *
     * @return positionsCampAdverse
     */
    public List<Case> positionsPiecesCampAdverse(String camp) {

        // Attributs :
        List<Case> positionsCampAdverse = new ArrayList<Case>();

        // 1- Récupérer toutes les cases du camp adverse :
        if (camp.equals("blanc")) {
            positionsCampAdverse = caseRepository.RecupererCasesCamp("noir");
        }
        else
        {
            positionsCampAdverse = caseRepository.RecupererCasesCamp("blanc");
        }

        //2- Gestion des cases qui tombent en NULL :
        positionsCampAdverse = gestionCasesNull(positionsCampAdverse);

        // 3- Renvoie des positions du camp adverse :
        return positionsCampAdverse;
    }




    /**
     * Cette méthode renvoie les pièces du camp passé en paramètre.
     * C'est la méthode inverse de positionsPiecesCampAdverse(String camp).
     */
    public List<Case> positionsPiecesCamp(String camp) {

        // Attributs :
        List<Case> positionsCamp = new ArrayList<Case>();

        // 1- Récupérer toutes les cases du camp adverse :
        if (camp.equals("blanc")) {
            positionsCamp = caseRepository.RecupererCasesCamp("blanc");
        }
        else
        {
            positionsCamp = caseRepository.RecupererCasesCamp("noir");
        }

        //2- Gestion des cases qui tombent en NULL :
        positionsCamp = gestionCasesNull(positionsCamp);

        // 3- Renvoie des positions du camp adverse :
        return positionsCamp;
    }




    /**
     * Cette méthode gère les cases qui peuvent être NULL lors de la récupération
     * des cases sur lesquelles se trouvent les pièces du camp adverse.
     */
    public List<Case> gestionCasesNull(List<Case> positionsCampAdverse)
    {
        // 1- Initialisation des listes de contrôle :
        String campQuiJoue = campQuiJoue();
        // Liste des pièces du camp blanc :
        List<Long> listTypeBlanc = new ArrayList<Long>();
        listTypeBlanc.add(1L);
        listTypeBlanc.add(2L);
        listTypeBlanc.add(3L);
        listTypeBlanc.add(4L);
        listTypeBlanc.add(5L);
        listTypeBlanc.add(6L);
        listTypeBlanc.add(7L);
        listTypeBlanc.add(8L);
        listTypeBlanc.add(17L);
        listTypeBlanc.add(18L);
        listTypeBlanc.add(21L);
        listTypeBlanc.add(22L);
        listTypeBlanc.add(25L);
        listTypeBlanc.add(26L);
        listTypeBlanc.add(29L);
        listTypeBlanc.add(31L);
        // Liste des pièces du camp noir :
        List<Long> listTypeNoir = new ArrayList<Long>();
        listTypeNoir.add(9L);
        listTypeNoir.add(10L);
        listTypeNoir.add(11L);
        listTypeNoir.add(12L);
        listTypeNoir.add(13L);
        listTypeNoir.add(14L);
        listTypeNoir.add(15L);
        listTypeNoir.add(16L);
        listTypeNoir.add(19L);
        listTypeNoir.add(20L);
        listTypeNoir.add(23L);
        listTypeNoir.add(24L);
        listTypeNoir.add(27L);
        listTypeNoir.add(28L);
        listTypeNoir.add(30L);
        listTypeNoir.add(32L);

        //2- Récupération des cases non NULL :
        List<Long> listIdPieceAControler = new ArrayList<Long>();
        for(int i = 0; i<positionsCampAdverse.size();i++)
        {
            if(positionsCampAdverse.get(i).getPiece()!=null) {
                listIdPieceAControler.add(positionsCampAdverse.get(i).getPiece().getNo_piece());
            }
        }
        Long idMissingPiece;
        Piece missingPiece;

        // 3- Correction des cases NULL :
        for(int i = 0; i<positionsCampAdverse.size();i++)
        {
            if (positionsCampAdverse.get(i).getPiece() == null) {
                if (campQuiJoue.equals("blanc")) {
                    listTypeNoir.removeAll(listIdPieceAControler);
                    idMissingPiece = listTypeNoir.get(0);
                    missingPiece = pieceRepository.getReferenceById(idMissingPiece);
                    positionsCampAdverse.get(i).setPiece(missingPiece);
                } else {
                    listTypeBlanc.removeAll(listIdPieceAControler);
                    idMissingPiece = listTypeBlanc.get(0);
                    missingPiece = pieceRepository.getReferenceById(idMissingPiece);
                    positionsCampAdverse.get(i).setPiece(missingPiece);
                }
            }
        }

        // 4- Renvoie des pièces corrigées :
        return positionsCampAdverse;
    }




    /**
     * Cette méthode renvoie la couleur du camp qui est entrain de jouer.
     * La valeur renvoyée est une string nommée "camp".
     * @return campQuiJoue
     */
    public String campQuiJoue()
    {
        // Attributs :
        String campQuiJoue;
        String noir = "noir";
        String blanc = "blanc";
        Long id = 1L;
        Partie partie = partieRepository.findById(id).orElse(null);
        int nombreDeTour = partie.getNombreDeTour();

        // Contrôles :
        if(nombreDeTour % 2 == 0)
        {
            campQuiJoue = noir;
        }
        else
        {
            campQuiJoue = blanc;
        }
        return campQuiJoue;
    }




    /**
     * Cette méthode met à jour le nombre de tour après le coup
     * de chaque joueur.
     * @return nombreDeTour
     */
    public void miseAJourDuNombreDeTour()
    {
        // Attributs :
        Long id = 1L;
        Partie partie = partieRepository.findById(id).orElse(null);
        int nombreDeTour = partie.getNombreDeTour();

        // Mise à jour du nombre de tours :
         nombreDeTour = nombreDeTour+1;
         partie.setNombreDeTour(nombreDeTour);
         partieRepository.save(partie);

    }




    /**
     * Cette méthode initialise le nombre de tour en fin de partie.
     */
    public void reinitialisationDuNombreDeTour()
    {
        // Attributs :
        Long id = 1L;
        Partie partie = partieRepository.findById(id).orElse(null);
        int nombreDeTour = partie.getNombreDeTour();

        // Mise à jour du nombre de tours :
        nombreDeTour = 1;
        partie.setNombreDeTour(nombreDeTour);
        partieRepository.save(partie);
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
