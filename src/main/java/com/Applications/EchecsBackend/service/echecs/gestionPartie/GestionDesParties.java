package com.Applications.EchecsBackend.service.echecs.gestionPartie;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Partie;
import com.Applications.EchecsBackend.models.echecs.Piece;
import com.Applications.EchecsBackend.repository.echecs.CaseRepository;
import com.Applications.EchecsBackend.repository.echecs.PartieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;





/**
 * Service qui gère les parties.
 */
@Service
public class GestionDesParties {





    // ********************* Dépendances *********************

    Piece tour = new Piece();
    private final PartieRepository partieRepository;
    private final CaseRepository caseRepository;





    // ********************* Constructeur ******************** :
    @Autowired
    public GestionDesParties(PartieRepository partieRepository, CaseRepository caseRepository) {
        this.partieRepository = partieRepository;
        this.caseRepository = caseRepository;
    }





    // ********************* Méthodes ******************** :

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
     * @return positionsCampAdverse
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
     * @return campQuiJoue
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