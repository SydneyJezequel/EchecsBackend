package com.Applications.EchecsBackend.controller.echecs;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.service.echecs.JouerService;
import com.Applications.EchecsBackend.service.echecs.deplacements.DeplacementPion;
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
    private final DeplacementPion deplacementPion;
    private List<Case> casesDeplacement;





    // ********************* Constructeur *********************
    @Autowired
    public JouerController (JouerService jouerService, DeplacementPion deplacementPion) {
        this.jouerService= jouerService;
        this.deplacementPion=deplacementPion;
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
    public List<Case> deplacerPiece(@RequestBody List<Case> casesDeplacement) throws Exception {
        List<Case> echiquierMaj = jouerService.deplacerPiece(casesDeplacement);
        return echiquierMaj;
    }



    /**
     * Méthode qui déplace transforme les pions quand ils arrivent au bout de l'échiquier.
     */
    @PutMapping("/transformer")
    @PreAuthorize("hasRole('USER')")
    public void transformationPion(@RequestBody Case caseDestination) throws Exception {
        deplacementPion.transformationPion(caseDestination);
    }


    /**
     * Méthode qui vérifie si le roi est en échec (Echec au Roi, Echec et Mat).
     * @return boolean echecAuRoi
     * @throws Exception
     */
    @GetMapping("/echec_au_roi")
    @PreAuthorize("hasRole('USER')")
    public int echecAuRoi() throws Exception
    {
        return jouerService.echecAuRoi();
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
