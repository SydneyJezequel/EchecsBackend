package com.Applications.EchecsBackend.controller.echecs;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.service.echecs.gestionPartie.serviceImpl.DemarrerUnePartieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;





/**
 * Controller qui gère les fonctionnalités pour initialiser une partie d'échecs.
 */
@RestController
@RequestMapping("/api")
public class DemarrerPartieController {





    /****************************** Attributs ******************************/

    private final DemarrerUnePartieService demarerPartie;





    /****************************** Constructeur ******************************/

    @Autowired
    public DemarrerPartieController (DemarrerUnePartieService demarerPartie) {
        this.demarerPartie= demarerPartie;
    }





    /****************************** Méthodes ******************************/

    /**
     * Controller pour ré-initialiser l'échiquier et créé une nouvelle partie.
     * @return
     */
    @GetMapping("/nouvelle_partie/{camp}")
    @PreAuthorize("hasRole('USER')")
    public List<Case> ReinitialiserEchiquier(@PathVariable("camp") String camp) {
        List<Case> echiquier = demarerPartie.ReinitialiserEchequier(camp);
        // TEST :
        System.out.println("le camp est : "+camp);
        for (int i = 0; i<echiquier.size();i++)
        {
            System.out.println("numéro de case : "+echiquier.get(i).getNo_case());
            System.out.println("case et colonne : "+echiquier.get(i).getColonne() + echiquier.get(i).getLigne());
        }
        // TEST :
        return echiquier;
    }




    /**
     * Controller pour renvoyer l'échiquier.
     * @return
     */
    @GetMapping("/echiquier/{camp}")
    @PreAuthorize("hasRole('USER')")
    public List<Case> getEchiquier(@PathVariable("camp") String camp){
        List<Case> echiquier = demarerPartie.ReinitialiserEchequier(camp);
        return echiquier;
    }













    /******************************* NOUVEAU FLUX D'AFFICHAGE *******************************/

    /**
     * VERSION 2 : Controller pour ré-initialiser l'échiquier et créé une nouvelle partie.
     * @return
     */
    @GetMapping("/nouvelle_partie")
    @PreAuthorize("hasRole('USER')")
    public List<Case> ReinitialiserEchiquier2() {
        List<Case> echiquier = demarerPartie.ReinitialiserEchequier2();
        return echiquier;
    }




    /**
     * VERSION 2 : Controller pour afficher l'échiquier et créé une nouvelle partie.
     * @return
     */
    @GetMapping("/echiquier")
    @PreAuthorize("hasRole('USER')")
    public List<Case> getEchiquier() {
        List<Case> echiquier = demarerPartie.getEchequier();
        // TEST :
        for (int i = 0; i<echiquier.size();i++)
        {
            System.out.println("numéro de case : "+echiquier.get(i).getNo_case());
            System.out.println("case et colonne : "+echiquier.get(i).getColonne() + echiquier.get(i).getLigne());
        }
        // TEST :
        return echiquier;
    }


    /******************************* NOUVEAU FLUX D'AFFICHAGE *******************************/







}



