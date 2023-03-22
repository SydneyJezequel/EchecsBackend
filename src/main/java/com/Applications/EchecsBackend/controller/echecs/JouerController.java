package com.Applications.EchecsBackend.controller.echecs;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.service.echecs.JouerService;
import com.Applications.EchecsBackend.service.echecs.coupSpeciaux.TransformationPion;
import com.Applications.EchecsBackend.service.echecs.deplacements.DeplacementPion;
import com.Applications.EchecsBackend.service.echecs.gestionPartie.GestionDesParties;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


/**
 * Controller qui gère les fonctionnalités pour jouer une partie d'échecs.
 */
@RestController
@RequestMapping("/api")
public class JouerController {





    // ********************* Attributs *********************
    private final JouerService jouerService;
    private final TransformationPion transformationPion;
    private final GestionDesParties gestionDesParties;




    // ********************* Constructeur *********************
    @Autowired
    public JouerController (JouerService jouerService,
                            TransformationPion transformationPion,
                            GestionDesParties gestionDesParties) {
        this.jouerService= jouerService;
        this.transformationPion=transformationPion;
        this.gestionDesParties=gestionDesParties;
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
        transformationPion.transformationPion(caseDestination);
    }




    /**
     * Méthode qui vérifie si le roi est en échec (Echec au Roi, Echec et Mat).
     * @return boolean echecAuRoi
     * @throws Exception
     */
    @PutMapping("/echec_au_roi")
    @PreAuthorize("hasRole('USER')")
    public boolean echecAuRoi(@RequestBody List<Case> casesDeplacement) throws Exception
    {
        return jouerService.echecAuRoiPopUp(casesDeplacement);
    }




    /**
     * Méthode qui contrôle quel camp est entrain de jouer.
     * @return String campQuiJoue
     * @throws Exception
     */
    @RequestMapping(value = "/camp_qui_joue", method = GET)
    @PreAuthorize("hasRole('USER')")
    public boolean campQuiJoue() throws Exception
    {
        String camp = gestionDesParties.campQuiJoue();
        boolean campRenvoye;
        if(camp.equals("blanc"))
        {
            campRenvoye = true;
        }
        else
        {
            campRenvoye = false;
        }
        return campRenvoye;
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
