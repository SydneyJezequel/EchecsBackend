package com.Applications.EchecsBackend.controller.echecs;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.service.echecs.JouerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;




/**
 * Controller qui gère les fonctionnalités pour jouer une partie d'échecs.
 */
@RestController
@RequestMapping("/api")
public class JouerController {




    // ********************* Attributs *********************

    private final JouerService jouerService;
    private List<Case> casesDeplacement;




    // ********************* Constructeur *********************

    @Autowired
    public JouerController (JouerService jouerService) {
        this.jouerService= jouerService;
    }




    /****************************** Méthodes ******************************/

    /**
     * Méthode qui renvoie toutes les cases de l'échiquier.
     */
    @GetMapping("/case/all")
    @PreAuthorize("hasRole('USER')")
    public List<Case> findAllCases() {
        return jouerService.findAllCases();
    }



    /**
     * Méthode qui déplace les pièces
     */
    @PutMapping("/deplacer")
    @PreAuthorize("hasRole('USER')")
    public List<Case> addCase(@RequestBody List<Case> casesDeplacement) throws Exception {
        List<Case> echiquierMaj = jouerService.deplacerPiece(casesDeplacement);
        return echiquierMaj;
    }



    /**
     * Méthode qui déplace transforme les pions quand ils arrivent au bout de l'échiquier.
     */
    @PutMapping("/transformer")
    @PreAuthorize("hasRole('USER')")
    public List<Case> addCase(@RequestBody Case caseDestination, @RequestBody String typeNouvellePiece) throws Exception {
        List<Case> echiquierMaj = jouerService.transformationPion(caseDestination, typeNouvellePiece);
        return echiquierMaj;
    }



    /**
     * Méthode qui déclare l'abandon de la partie.
     */
    /*
    public void abandon() throws Exception
    {
        // IMPLEMENTER DES REGLES QUI APPELLENT LE SERVICE QUI REINITIALISE L'ECHIQUIER.
        // UN MESSAGE D'ABANDON DEVRA ETRE RENVOYE AU USER.
    }
    */




}
