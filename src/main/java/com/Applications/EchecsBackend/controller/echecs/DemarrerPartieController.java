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





}



