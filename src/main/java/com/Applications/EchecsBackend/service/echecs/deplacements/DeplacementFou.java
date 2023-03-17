package com.Applications.EchecsBackend.service.echecs.deplacements;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Piece;
import com.Applications.EchecsBackend.repository.echecs.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;





/**
 * Service qui gère les fonctionnalités de déplacement des Fous.
 */
@Service
public class DeplacementFou {





    // ********************* Dépendances *********************
    Piece fou = new Piece();
    private final CaseRepository caseRepository;





    // ********************* Constructeur ******************** :
    @Autowired
    public DeplacementFou(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }





    // ********************* Méthodes ******************** :
    /**
     * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
     * d'une pièce de type Fou.
     * @return boolean
     * @throws Exception
     */
    public boolean deplacementFou(Case caseDepart, Case caseDestination, List<Case> echiquier) throws Exception
    {
        // Attributs :
        boolean deplacementFouOk;

        // Si déplacements Ok :
        if(deplacementFouDeGaucheADroite(caseDepart, caseDestination, echiquier) || deplacementFouDeDroiteAGauche(caseDepart, caseDestination, echiquier)) {
            // Si le Fou ne dépasse pas les frontières de l'échiquier affiché :
            if (borduresFou(caseDepart, caseDestination)) {
                // Si la pièce sur la case de Destination est de la même couleur que la pièce déplacée :
                if (fou.verificationCampPieceCaseDestination(caseDepart, caseDestination))
                {
                    deplacementFouOk = true;
                }
                else
                {
                    deplacementFouOk = false;
                }
            // Si le fou dépasse tières de l'échiquier affiché :
            }
            else
            {
                deplacementFouOk = false;
            }
        }
        // Si déplacement Nok:
        else
        {
            deplacementFouOk = false;
        }
        return deplacementFouOk;
    }
    // ANCIENNE VERSION DEL A FONCTIONNALITE :
    /*
            // Récupération des numéros des cases :
        int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
        int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());
        String plus = "plus";
        String moins = "moins";
        Case caseDepartPlus9 = fou.calculCaseIntermediaire(caseDepart, 8L, echiquier, plus);
        Case caseDepartPlus18 = fou.calculCaseIntermediaire(caseDepart, 17L, echiquier, plus);
        Case caseDepartPlus27 = fou.calculCaseIntermediaire(caseDepart, 26L, echiquier, plus);
        Case caseDepartPlus36 = fou.calculCaseIntermediaire(caseDepart, 35L, echiquier, plus);
        Case caseDepartPlus45 = fou.calculCaseIntermediaire(caseDepart, 44L, echiquier, plus);
        Case caseDepartPlus54 = fou.calculCaseIntermediaire(caseDepart, 53L, echiquier, plus);
        Case caseDepartPlus63 = fou.calculCaseIntermediaire(caseDepart, 62L, echiquier, plus);

        Case caseDepartMoins9 = fou.calculCaseIntermediaire(caseDepart, 10L, echiquier, moins);
        Case caseDepartMoins18 = fou.calculCaseIntermediaire(caseDepart, 19L, echiquier, moins);
        Case caseDepartMoins27 = fou.calculCaseIntermediaire(caseDepart, 28L, echiquier, moins);
        Case caseDepartMoins36 = fou.calculCaseIntermediaire(caseDepart, 37L, echiquier, moins);
        Case caseDepartMoins45 = fou.calculCaseIntermediaire(caseDepart, 46L, echiquier, moins);
        Case caseDepartMoins54 = fou.calculCaseIntermediaire(caseDepart, 55L, echiquier, moins);
        Case caseDepartMoins63 = fou.calculCaseIntermediaire(caseDepart, 64L, echiquier, moins);

        Case caseDepartPlus7 = fou.calculCaseIntermediaire(caseDepart, 6L, echiquier, plus);
        Case caseDepartPlus14 = fou.calculCaseIntermediaire(caseDepart, 13L, echiquier, plus);
        Case caseDepartPlus21 = fou.calculCaseIntermediaire(caseDepart, 20L, echiquier, plus);
        Case caseDepartPlus28 = fou.calculCaseIntermediaire(caseDepart, 27L, echiquier, plus);
        Case caseDepartPlus35 = fou.calculCaseIntermediaire(caseDepart, 34L, echiquier, plus);
        Case caseDepartPlus42 = fou.calculCaseIntermediaire(caseDepart, 41L, echiquier, plus);
        Case caseDepartPlus49 = fou.calculCaseIntermediaire(caseDepart, 48L, echiquier, plus);

        Case caseDepartMoins7 = fou.calculCaseIntermediaire(caseDepart, 8L, echiquier, moins);
        Case caseDepartMoins14 = fou.calculCaseIntermediaire(caseDepart, 15L, echiquier, moins);
        Case caseDepartMoins21 = fou.calculCaseIntermediaire(caseDepart, 22L, echiquier, moins);
        Case caseDepartMoins28 = fou.calculCaseIntermediaire(caseDepart, 29L, echiquier, moins);
        Case caseDepartMoins35 = fou.calculCaseIntermediaire(caseDepart, 36L, echiquier, moins);
        Case caseDepartMoins42 = fou.calculCaseIntermediaire(caseDepart, 43L, echiquier, moins);
        Case caseDepartMoins49 = fou.calculCaseIntermediaire(caseDepart, 50L, echiquier, moins);

        // Déplacements possibles :
        // Contrôle diagonale (droite/gauche/haut/bas) :

        // De gauche à droite
        if (caseDestination.getNo_case() == caseDepart.getNo_case() + 9
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 18 && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 27 && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 36 && caseDepartPlus27.getPiece() == null && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 45 && caseDepartPlus36.getPiece()== null && caseDepartPlus27.getPiece() == null && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 54 && caseDepartPlus45.getPiece() == null  && caseDepartPlus36.getPiece() == null && caseDepartPlus27.getPiece() == null && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 63 && caseDepartPlus54.getPiece() == null && caseDepartPlus45.getPiece() == null  && caseDepartPlus36.getPiece() == null && caseDepartPlus27.getPiece() == null && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 72 && caseDepartPlus63.getPiece() == null && caseDepartPlus54.getPiece() == null && caseDepartPlus45.getPiece() == null  && caseDepartPlus36.getPiece() == null && caseDepartPlus27.getPiece() == null && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 7
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 14 && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 21 && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 28 && caseDepartPlus21.getPiece() == null && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 35 && caseDepartPlus28.getPiece() == null && caseDepartPlus21.getPiece() == null && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 42 && caseDepartPlus35.getPiece() == null && caseDepartPlus28.getPiece() == null && caseDepartPlus21.getPiece() == null && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 49 && caseDepartPlus42.getPiece() == null && caseDepartPlus35.getPiece() == null && caseDepartPlus28.getPiece() == null && caseDepartPlus21.getPiece() == null && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 56 && caseDepartPlus49.getPiece() == null && caseDepartPlus42.getPiece() == null && caseDepartPlus35.getPiece() == null && caseDepartPlus28.getPiece() == null && caseDepartPlus21.getPiece() == null && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null


                // De droite à gauche :
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 9
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 18 && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 27 && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 36 && caseDepartMoins27.getPiece() == null && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 45 && caseDepartMoins36.getPiece() == null && caseDepartMoins27.getPiece() == null && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 54 && caseDepartMoins45.getPiece() == null && caseDepartMoins36.getPiece() == null && caseDepartMoins27.getPiece() == null && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 63 && caseDepartMoins54.getPiece() == null && caseDepartMoins45.getPiece() == null&& caseDepartMoins36.getPiece() == null && caseDepartMoins27.getPiece() == null && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 72 && caseDepartMoins63.getPiece() == null && caseDepartMoins54.getPiece() == null && caseDepartMoins45.getPiece() == null && caseDepartMoins36.getPiece() == null && caseDepartMoins27.getPiece() == null && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 7
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 14 && caseDepartMoins7.getPiece()== null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 21 && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 28 && caseDepartMoins21.getPiece()== null && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 35 && caseDepartMoins28.getPiece() == null && caseDepartMoins21.getPiece() == null && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 42 && caseDepartMoins35.getPiece() == null && caseDepartMoins28.getPiece() == null && caseDepartMoins21.getPiece() == null && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 49 && caseDepartMoins42.getPiece() == null && caseDepartMoins35.getPiece() == null && caseDepartMoins28.getPiece() == null && caseDepartMoins21.getPiece() == null && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 56 && caseDepartMoins49.getPiece() == null && caseDepartMoins42.getPiece() == null && caseDepartMoins35.getPiece() == null && caseDepartMoins28.getPiece() == null && caseDepartMoins21.getPiece() == null && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
        )
        {
            if (fou.verificationCampPieceCaseDestination(caseDepart, caseDestination))
            {
                return true;
            }else
            {
                return false;
            }
        } else
        {
            return false;
        }
    */




    /**
     * Méthode qui gère le déplacement du fou en diagonale de gauche à droite.
     * @param caseDepart
     * @param caseDestination
     * @param echiquier
     * @return
     * @throws Exception
     */
    public boolean deplacementFouDeGaucheADroite(Case caseDepart, Case caseDestination, List<Case> echiquier) throws Exception
    {
        // Attributs :
        boolean deplacementAutorise;
        List<Case> echiquierBordure = caseRepository.findAll();
        echiquier.sort(Comparator.comparing(Case::getNo_case));
        int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
        int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());
        String plus = "plus";
        String moins = "moins";
        Case caseDepartPlus9 = fou.calculCaseIntermediaire(caseDepart, 8L, echiquier, plus);
        Case caseDepartPlus18 = fou.calculCaseIntermediaire(caseDepart, 17L, echiquier, plus);
        Case caseDepartPlus27 = fou.calculCaseIntermediaire(caseDepart, 26L, echiquier, plus);
        Case caseDepartPlus36 = fou.calculCaseIntermediaire(caseDepart, 35L, echiquier, plus);
        Case caseDepartPlus45 = fou.calculCaseIntermediaire(caseDepart, 44L, echiquier, plus);
        Case caseDepartPlus54 = fou.calculCaseIntermediaire(caseDepart, 53L, echiquier, plus);
        Case caseDepartPlus63 = fou.calculCaseIntermediaire(caseDepart, 62L, echiquier, plus);

        Case caseDepartPlus7 = fou.calculCaseIntermediaire(caseDepart, 6L, echiquier, plus);
        Case caseDepartPlus14 = fou.calculCaseIntermediaire(caseDepart, 13L, echiquier, plus);
        Case caseDepartPlus21 = fou.calculCaseIntermediaire(caseDepart, 20L, echiquier, plus);
        Case caseDepartPlus28 = fou.calculCaseIntermediaire(caseDepart, 27L, echiquier, plus);
        Case caseDepartPlus35 = fou.calculCaseIntermediaire(caseDepart, 34L, echiquier, plus);
        Case caseDepartPlus42 = fou.calculCaseIntermediaire(caseDepart, 41L, echiquier, plus);
        Case caseDepartPlus49 = fou.calculCaseIntermediaire(caseDepart, 48L, echiquier, plus);

        // Contrôles :
        if (caseDestination.getNo_case() == caseDepart.getNo_case() + 9
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 18 && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 27 && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 36 && caseDepartPlus27.getPiece() == null && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 45 && caseDepartPlus36.getPiece()== null && caseDepartPlus27.getPiece() == null && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 54 && caseDepartPlus45.getPiece() == null  && caseDepartPlus36.getPiece() == null && caseDepartPlus27.getPiece() == null && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 63 && caseDepartPlus54.getPiece() == null && caseDepartPlus45.getPiece() == null  && caseDepartPlus36.getPiece() == null && caseDepartPlus27.getPiece() == null && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 72 && caseDepartPlus63.getPiece() == null && caseDepartPlus54.getPiece() == null && caseDepartPlus45.getPiece() == null  && caseDepartPlus36.getPiece() == null && caseDepartPlus27.getPiece() == null && caseDepartPlus18.getPiece() == null && caseDepartPlus9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 7
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 14 && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 21 && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 28 && caseDepartPlus21.getPiece() == null && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 35 && caseDepartPlus28.getPiece() == null && caseDepartPlus21.getPiece() == null && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 42 && caseDepartPlus35.getPiece() == null && caseDepartPlus28.getPiece() == null && caseDepartPlus21.getPiece() == null && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 49 && caseDepartPlus42.getPiece() == null && caseDepartPlus35.getPiece() == null && caseDepartPlus28.getPiece() == null && caseDepartPlus21.getPiece() == null && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() + 56 && caseDepartPlus49.getPiece() == null && caseDepartPlus42.getPiece() == null && caseDepartPlus35.getPiece() == null && caseDepartPlus28.getPiece() == null && caseDepartPlus21.getPiece() == null && caseDepartPlus14.getPiece() == null && caseDepartPlus7.getPiece() == null)
        {
            deplacementAutorise = true;
        }
        else
        {
            deplacementAutorise = false;
        }
        return deplacementAutorise;
    }



    /**
     * Méthode qui gère le déplacement du fou en diagonale de doite à gauche.
     * @param caseDepart
     * @param caseDestination
     * @param echiquier
     * @return
     * @throws Exception
     */
    public boolean deplacementFouDeDroiteAGauche(Case caseDepart, Case caseDestination, List<Case> echiquier) throws Exception
    {
        // Attributs :
        boolean deplacementAutorise;
        List<Case> echiquierBordure = caseRepository.findAll();
        echiquier.sort(Comparator.comparing(Case::getNo_case));
        int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
        int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());
        String plus = "plus";
        String moins = "moins";
        Case caseDepartMoins9 = fou.calculCaseIntermediaire(caseDepart, 10L, echiquier, moins);
        Case caseDepartMoins18 = fou.calculCaseIntermediaire(caseDepart, 19L, echiquier, moins);
        Case caseDepartMoins27 = fou.calculCaseIntermediaire(caseDepart, 28L, echiquier, moins);
        Case caseDepartMoins36 = fou.calculCaseIntermediaire(caseDepart, 37L, echiquier, moins);
        Case caseDepartMoins45 = fou.calculCaseIntermediaire(caseDepart, 46L, echiquier, moins);
        Case caseDepartMoins54 = fou.calculCaseIntermediaire(caseDepart, 55L, echiquier, moins);
        Case caseDepartMoins63 = fou.calculCaseIntermediaire(caseDepart, 64L, echiquier, moins);

        Case caseDepartMoins7 = fou.calculCaseIntermediaire(caseDepart, 8L, echiquier, moins);
        Case caseDepartMoins14 = fou.calculCaseIntermediaire(caseDepart, 15L, echiquier, moins);
        Case caseDepartMoins21 = fou.calculCaseIntermediaire(caseDepart, 22L, echiquier, moins);
        Case caseDepartMoins28 = fou.calculCaseIntermediaire(caseDepart, 29L, echiquier, moins);
        Case caseDepartMoins35 = fou.calculCaseIntermediaire(caseDepart, 36L, echiquier, moins);
        Case caseDepartMoins42 = fou.calculCaseIntermediaire(caseDepart, 43L, echiquier, moins);
        Case caseDepartMoins49 = fou.calculCaseIntermediaire(caseDepart, 50L, echiquier, moins);

        // Contrôles :
        if (caseDestination.getNo_case() == caseDepart.getNo_case() - 9
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 18 && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 27 && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 36 && caseDepartMoins27.getPiece() == null && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 45 && caseDepartMoins36.getPiece() == null && caseDepartMoins27.getPiece() == null && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 54 && caseDepartMoins45.getPiece() == null && caseDepartMoins36.getPiece() == null && caseDepartMoins27.getPiece() == null && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 63 && caseDepartMoins54.getPiece() == null && caseDepartMoins45.getPiece() == null&& caseDepartMoins36.getPiece() == null && caseDepartMoins27.getPiece() == null && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 72 && caseDepartMoins63.getPiece() == null && caseDepartMoins54.getPiece() == null && caseDepartMoins45.getPiece() == null && caseDepartMoins36.getPiece() == null && caseDepartMoins27.getPiece() == null && caseDepartMoins18.getPiece() == null && caseDepartMoins9.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 7
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 14 && caseDepartMoins7.getPiece()== null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 21 && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 28 && caseDepartMoins21.getPiece()== null && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 35 && caseDepartMoins28.getPiece() == null && caseDepartMoins21.getPiece() == null && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 42 && caseDepartMoins35.getPiece() == null && caseDepartMoins28.getPiece() == null && caseDepartMoins21.getPiece() == null && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 49 && caseDepartMoins42.getPiece() == null && caseDepartMoins35.getPiece() == null && caseDepartMoins28.getPiece() == null && caseDepartMoins21.getPiece() == null && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
                || caseDestination.getNo_case() == caseDepart.getNo_case() - 56 && caseDepartMoins49.getPiece() == null && caseDepartMoins42.getPiece() == null && caseDepartMoins35.getPiece() == null && caseDepartMoins28.getPiece() == null && caseDepartMoins21.getPiece() == null && caseDepartMoins14.getPiece() == null && caseDepartMoins7.getPiece() == null
        )
        {
            deplacementAutorise = true;
        }
        else
        {
            deplacementAutorise = false;
        }
        return deplacementAutorise;
    }




    /**
     * Méthode qui définit les limites de l'échiquier pour un Fou.
     * @return boolean
     */
    public boolean borduresFou(Case caseDepart, Case caseDestination) {
        // Attributs :
        boolean bordurePasDepasse = false;
        List<Case> echiquier = caseRepository.findAll();
        echiquier.sort(Comparator.comparing(Case::getNo_case));
        // Diagonale GaucheDroite :
        if (ControleBorduresPasDepasseFouDiagonaleGaucheDroiteDeHautEnBas(caseDepart, caseDestination, echiquier) || ControleBorduresPasDepasseFouDiagonaleGaucheADroiteDeBasEnHaut(caseDepart, caseDestination, echiquier))
        {
            bordurePasDepasse= true;
        }
        return bordurePasDepasse;
    }
    // ANCIENNE VERSION DE LA METHODE :
    /*
        public boolean borduresFou(Case caseDepart, Case caseDestination)
    {
        Piece piece = caseDepart.getPiece();
        List<String> nomPiece = List.of(piece.getType().split(" "));
        String typeDePiece = nomPiece.get(0);
        if(typeDePiece.equals("fou")
                && caseDepart.getNo_case() == 24L
                || caseDepart.getNo_case() == 48L
                || caseDepart.getNo_case() == 17L
                || caseDepart.getNo_case() == 41L
                && caseDestination.getNo_case() == 17L
                || caseDestination.getNo_case() == 10L
                || caseDestination.getNo_case() == 3L
                || caseDestination.getNo_case() == 33L
                || caseDestination.getNo_case() == 57L
                || caseDestination.getNo_case() == 41L
                || caseDestination.getNo_case() == 34L
                || caseDestination.getNo_case() == 27L
                || caseDestination.getNo_case() == 20L
                || caseDestination.getNo_case() == 13L
                || caseDestination.getNo_case() == 6L
                || caseDestination.getNo_case() == 24L
                || caseDestination.getNo_case() == 31L
                || caseDestination.getNo_case() == 38L
                || caseDestination.getNo_case() == 45L
                || caseDestination.getNo_case() == 52L
                || caseDestination.getNo_case() == 59L
                || caseDestination.getNo_case() == 8L
                || caseDestination.getNo_case() == 48L
                || caseDestination.getNo_case() == 55L
                || caseDestination.getNo_case() == 62L
                || caseDestination.getNo_case() == 32L
                || caseDestination.getNo_case() == 23L
                || caseDestination.getNo_case() == 14L
                || caseDestination.getNo_case() == 5L
        )
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    */




    /**
     * Méthode qui contrôle les bordures des diagonales qui vont de gauche à droite.
     * @param caseDepart
     * @param caseDestination
     * @return
     */
    public boolean ControleBorduresPasDepasseFouDiagonaleGaucheDroiteDeHautEnBas(Case caseDepart, Case caseDestination, List<Case> echiquier)
    {
        // Attributs Diagonale GaucheDroite Noir :
        boolean bordurePasDepasse = false;
        List<Long>diagonaleGaucheDroiteDeHautEnBasNoir1 = new ArrayList<Long>();
        List<Long>diagonaleGaucheDroiteDeHautEnBasNoir2 = new ArrayList<Long>();
        List<Long>diagonaleGaucheDroiteDeHautEnBasNoir3 = new ArrayList<Long>();
        List<Long>diagonaleGaucheDroiteDeHautEnBasNoir4 = new ArrayList<Long>();
        List<Long>diagonaleGaucheDroiteDeHautEnBasNoir5 = new ArrayList<Long>();
        List<Long>diagonaleGaucheDroiteDeHautEnBasNoir6 = new ArrayList<Long>();
        List<Long>diagonaleGaucheDroiteDeHautEnBasNoir7 = new ArrayList<Long>();
        diagonaleGaucheDroiteDeHautEnBasNoir1.add(echiquier.get(6).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir1.add(echiquier.get(15).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir2.add(echiquier.get(4).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir2.add(echiquier.get(13).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir2.add(echiquier.get(22).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir2.add(echiquier.get(31).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir3.add(echiquier.get(2).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir3.add(echiquier.get(11).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir3.add(echiquier.get(20).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir3.add(echiquier.get(29).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir3.add(echiquier.get(38).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir3.add(echiquier.get(47).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir4.add(echiquier.get(0).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir4.add(echiquier.get(9).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir4.add(echiquier.get(18).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir4.add(echiquier.get(27).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir4.add(echiquier.get(36).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir4.add(echiquier.get(45).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir4.add(echiquier.get(54).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir4.add(echiquier.get(63).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir5.add(echiquier.get(16).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir5.add(echiquier.get(25).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir5.add(echiquier.get(34).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir5.add(echiquier.get(43).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir5.add(echiquier.get(52).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir5.add(echiquier.get(61).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir6.add(echiquier.get(32).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir6.add(echiquier.get(41).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir6.add(echiquier.get(50).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir6.add(echiquier.get(59).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir7.add(echiquier.get(48).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasNoir7.add(echiquier.get(57).getNo_case());

        // Attributs Diagonale GaucheDroite Blanc :
        List<Long>diagonaleGaucheDroiteDeHautEnBasBlanc1 = new ArrayList<Long>();
        List<Long>diagonaleGaucheDroiteDeHautEnBasBlanc2 = new ArrayList<Long>();
        List<Long>diagonaleGaucheDroiteDeHautEnBasBlanc3 = new ArrayList<Long>();
        List<Long>diagonaleGaucheDroiteDeHautEnBasBlanc4 = new ArrayList<Long>();
        List<Long>diagonaleGaucheDroiteDeHautEnBasBlanc5 = new ArrayList<Long>();
        List<Long>diagonaleGaucheDroiteDeHautEnBasBlanc6 = new ArrayList<Long>();
        List<Long>diagonaleGaucheDroiteDeHautEnBasBlanc7 = new ArrayList<Long>();
        List<Long>diagonaleGaucheDroiteDeHautEnBasBlanc8 = new ArrayList<Long>();
        diagonaleGaucheDroiteDeHautEnBasBlanc1.add(echiquier.get(7).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc2.add(echiquier.get(5).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc2.add(echiquier.get(14).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc2.add(echiquier.get(23).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc3.add(echiquier.get(3).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc3.add(echiquier.get(12).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc3.add(echiquier.get(21).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc3.add(echiquier.get(30).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc3.add(echiquier.get(39).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc4.add(echiquier.get(1).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc4.add(echiquier.get(10).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc4.add(echiquier.get(19).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc4.add(echiquier.get(28).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc4.add(echiquier.get(37).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc4.add(echiquier.get(46).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc4.add(echiquier.get(55).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc5.add(echiquier.get(8).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc5.add(echiquier.get(17).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc5.add(echiquier.get(26).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc5.add(echiquier.get(35).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc5.add(echiquier.get(44).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc5.add(echiquier.get(53).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc5.add(echiquier.get(62).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc6.add(echiquier.get(24).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc6.add(echiquier.get(33).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc6.add(echiquier.get(42).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc6.add(echiquier.get(51).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc6.add(echiquier.get(60).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc7.add(echiquier.get(40).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc7.add(echiquier.get(49).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc7.add(echiquier.get(58).getNo_case());
        diagonaleGaucheDroiteDeHautEnBasBlanc8.add(echiquier.get(56).getNo_case());

        // Contrôles des bordures sur les diagonales de Gauche à Droite :
        // Contrôles sur les cases noires :
        if(diagonaleGaucheDroiteDeHautEnBasBlanc1.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasBlanc1.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasNoir2.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasNoir2.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        // TEST QUI DEVRAIT VALIDER :
        else if(diagonaleGaucheDroiteDeHautEnBasNoir3.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasNoir3.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        // TEST QUI DEVRAIT VALIDER :
        else if(diagonaleGaucheDroiteDeHautEnBasNoir4.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasNoir4.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasNoir5.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasNoir5.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasNoir6.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasNoir6.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasNoir7.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasNoir7.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }

        // Contrôles sur les cases blanches:
        else if(diagonaleGaucheDroiteDeHautEnBasBlanc1.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasBlanc1.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasBlanc2.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasBlanc2.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasBlanc3.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasBlanc3.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasBlanc4.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasBlanc4.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasBlanc5.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasBlanc5.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasBlanc6.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasBlanc6.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasBlanc7.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasBlanc7.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        return bordurePasDepasse;
    }




    /**
     * Méthode qui contrôle les bordures des diagonales qui vont de droite à gauche.
     * @param caseDepart
     * @param caseDestination
     * @return
     */
    public boolean ControleBorduresPasDepasseFouDiagonaleGaucheADroiteDeBasEnHaut(Case caseDepart, Case caseDestination, List<Case> echiquier)
    {
        // Attributs :
        boolean bordurePasDepasse = false;
        List<Long>diagonaleDroiteGaucheDeBasEnHautNoir1 = new ArrayList<Long>();
        List<Long>diagonaleDroiteGaucheDeBasEnHautNoir2 = new ArrayList<Long>();
        List<Long>diagonaleDroiteGaucheDeBasEnHautNoir3 = new ArrayList<Long>();
        List<Long>diagonaleDroiteGaucheDeBasEnHautNoir4 = new ArrayList<Long>();
        List<Long>diagonaleDroiteGaucheDeBasEnHautNoir5 = new ArrayList<Long>();
        List<Long>diagonaleDroiteGaucheDeBasEnHautNoir6 = new ArrayList<Long>();
        List<Long>diagonaleDroiteGaucheDeBasEnHautNoir7 = new ArrayList<Long>();
        List<Long>diagonaleDroiteGaucheDeBasEnHautNoir8 = new ArrayList<Long>();
        diagonaleDroiteGaucheDeBasEnHautNoir1.add(echiquier.get(0).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir2.add(echiquier.get(2).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir2.add(echiquier.get(9).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir2.add(echiquier.get(16).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir3.add(echiquier.get(4).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir3.add(echiquier.get(11).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir3.add(echiquier.get(18).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir3.add(echiquier.get(25).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir3.add(echiquier.get(32).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir4.add(echiquier.get(6).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir4.add(echiquier.get(13).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir4.add(echiquier.get(20).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir4.add(echiquier.get(27).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir4.add(echiquier.get(34).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir4.add(echiquier.get(41).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir4.add(echiquier.get(48).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir5.add(echiquier.get(15).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir5.add(echiquier.get(22).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir5.add(echiquier.get(29).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir5.add(echiquier.get(36).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir5.add(echiquier.get(43).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir5.add(echiquier.get(50).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir5.add(echiquier.get(57).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir6.add(echiquier.get(31).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir6.add(echiquier.get(38).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir6.add(echiquier.get(45).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir6.add(echiquier.get(52).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir6.add(echiquier.get(59).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir7.add(echiquier.get(47).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir7.add(echiquier.get(54).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir7.add(echiquier.get(61).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautNoir8.add(echiquier.get(63).getNo_case());

        List<Long>diagonaleDroiteGaucheDeBasEnHautBlanc1 = new ArrayList<Long>();
        List<Long>diagonaleDroiteGaucheDeBasEnHautBlanc2 = new ArrayList<Long>();
        List<Long>diagonaleDroiteGaucheDeBasEnHautBlanc3 = new ArrayList<Long>();
        List<Long>diagonaleDroiteGaucheDeBasEnHautBlanc4 = new ArrayList<Long>();
        List<Long>diagonaleDroiteGaucheDeBasEnHautBlanc5 = new ArrayList<Long>();
        List<Long>diagonaleDroiteGaucheDeBasEnHautBlanc6 = new ArrayList<Long>();
        List<Long>diagonaleDroiteGaucheDeBasEnHautBlanc7 = new ArrayList<Long>();
        diagonaleDroiteGaucheDeBasEnHautBlanc1.add(echiquier.get(1).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc1.add(echiquier.get(8).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc2.add(echiquier.get(3).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc2.add(echiquier.get(10).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc2.add(echiquier.get(17).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc2.add(echiquier.get(24).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc3.add(echiquier.get(5).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc3.add(echiquier.get(12).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc3.add(echiquier.get(19).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc3.add(echiquier.get(26).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc3.add(echiquier.get(33).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc3.add(echiquier.get(40).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc4.add(echiquier.get(7).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc4.add(echiquier.get(14).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc4.add(echiquier.get(21).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc4.add(echiquier.get(28).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc4.add(echiquier.get(35).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc4.add(echiquier.get(42).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc4.add(echiquier.get(49).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc4.add(echiquier.get(56).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc5.add(echiquier.get(23).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc5.add(echiquier.get(30).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc5.add(echiquier.get(37).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc5.add(echiquier.get(44).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc5.add(echiquier.get(51).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc5.add(echiquier.get(58).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc6.add(echiquier.get(39).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc6.add(echiquier.get(46).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc6.add(echiquier.get(53).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc6.add(echiquier.get(60).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc7.add(echiquier.get(55).getNo_case());
        diagonaleDroiteGaucheDeBasEnHautBlanc7.add(echiquier.get(62).getNo_case());

        // Contrôles des bordures sur les diagonales de Droite à Gauche :
        // Contrôles sur les cases noires :
        if(diagonaleDroiteGaucheDeBasEnHautNoir1.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautNoir1.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautNoir2.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautNoir2.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautNoir3.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautNoir3.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautNoir4.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautNoir4.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautNoir5.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautNoir5.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautNoir6.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautNoir6.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        // TEST QUI DEVRAIT MARCHER :
        else if(diagonaleDroiteGaucheDeBasEnHautNoir7.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautNoir7.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        // TEST QUI DEVRAIT MARCHER :
        else if(diagonaleDroiteGaucheDeBasEnHautNoir8.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautNoir8.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }

        // Contrôles sur les cases blanches:
        else if(diagonaleDroiteGaucheDeBasEnHautBlanc1.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautBlanc1.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautBlanc2.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautBlanc2.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautBlanc3.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautBlanc3.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautBlanc4.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautBlanc4.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautBlanc5.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautBlanc5.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautBlanc6.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautBlanc6.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautBlanc7.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautBlanc7.contains(caseDestination.getNo_case()))
        {
            bordurePasDepasse = true;
        }
        return bordurePasDepasse;
    }






}
