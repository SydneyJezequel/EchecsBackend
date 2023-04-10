package com.Applications.EchecsBackend.controller.echecs;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.service.echecs.coupSpeciaux.serviceImpl.Echec;
import com.Applications.EchecsBackend.service.echecs.gestionPartie.serviceImpl.JouerService;
import com.Applications.EchecsBackend.service.echecs.coupSpeciaux.serviceImpl.TransformationPion;
import com.Applications.EchecsBackend.service.echecs.gestionPartie.serviceImpl.GestionDesParties;
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
    private final Echec echec;





    // ********************* Constructeur *********************
    @Autowired
    public JouerController (JouerService jouerService,
                            TransformationPion transformationPion,
                            GestionDesParties gestionDesParties,
                            Echec echec) {
        this.jouerService= jouerService;
        this.transformationPion=transformationPion;
        this.gestionDesParties=gestionDesParties;
        this.echec=echec;
    }





    /****************************** Méthodes ******************************/

    /**
     * Controller pour renvoyer toutes les cases de l'échiquier.
     */
    @GetMapping("/case/all")
    @PreAuthorize("hasRole('USER')")
    public List<Case> findAllCases() {
        return jouerService.findAllCases();
    }




    /**
     * Controller pour déplacer les pièces
     */
    @PutMapping("/deplacer")
    @PreAuthorize("hasRole('USER')")
    public List<Case> deplacerPiece(@RequestBody List<Case> casesDeplacement) throws Exception {
        List<Case> echiquierMaj = jouerService.deplacerPiece(casesDeplacement);
        return echiquierMaj;
    }




    /**
     * Controller pour déplacer transforme les pions quand ils arrivent au bout de l'échiquier.
     */
    @PutMapping("/transformer")
    @PreAuthorize("hasRole('USER')")
    public void transformationPion(@RequestBody Case caseDestination) throws Exception {
        transformationPion.transformationPion(caseDestination);
    }




    /**
     * Controller pour vérifier si le roi est en échec (Echec au Roi, Echec et Mat).
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
     * Controller qui vérifie si le roi est échec et mat.
     * @return boolean echecEtMat
     * @throws Exception
     */
    @PutMapping("/echec_et_mat")
    @PreAuthorize("hasRole('USER')")
    public boolean echecEtMat(@RequestBody List<Case> casesDeplacement) throws Exception
    {
        return echec.echecEtMat(casesDeplacement);
    }




    /**
     * Controller pour contrôler quel camp est en train de jouer.
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
     * Controller pour déclarer l'abandon de la partie.
     */
    /*
    public void abandon() throws Exception
    {
        // IMPLEMENTER DES REGLES QUI APPELLENT LE SERVICE QUI REINITIALISE L'ECHIQUIER.
        // UN MESSAGE D'ABANDON DEVRA ETRE RENVOYE AU USER.
    }
    */





}
