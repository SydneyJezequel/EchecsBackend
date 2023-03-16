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

        // Contrôles :
        if(deplacementFouDeGaucheADroite(caseDepart, caseDestination, echiquier) || deplacementFouDeDroiteAGauche(caseDepart, caseDestination, echiquier))
        {
            if (fou.verificationCampPieceCaseDestination(caseDepart, caseDestination))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
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
            if(borduresFou(caseDepart, caseDestination))
            {
                deplacementAutorise = false;
            }
            else
            {
                deplacementAutorise = true;
            }
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
            if(borduresFou(caseDepart, caseDestination))
            {
                deplacementAutorise = false;
            }
            else
            {
                deplacementAutorise = true;
            }
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
        boolean bordureDepasse;
        List<Case> echiquier = caseRepository.findAll();
        echiquier.sort(Comparator.comparing(Case::getNo_case));
        // Diagonale GaucheDroite :
        if (borduresFouDiagonaleGaucheDroiteDeHautEnBas(caseDepart, caseDestination, echiquier) || borduresFouDiagonaleGaucheADroiteDeBasEnHaut(caseDepart, caseDestination, echiquier)) {
            bordureDepasse = true;
        } else {
            bordureDepasse = false;
        }
        return bordureDepasse;
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
    public boolean borduresFouDiagonaleGaucheDroiteDeHautEnBas(Case caseDepart, Case caseDestination, List<Case> echiquier)
    {
        // Attributs Diagonale GaucheDroite Noir :
        boolean bordureDepasse = true;
        List<Case>diagonaleGaucheDroiteDeHautEnBasNoir1 = new ArrayList<Case>();
        List<Case>diagonaleGaucheDroiteDeHautEnBasNoir2 = new ArrayList<Case>();
        List<Case>diagonaleGaucheDroiteDeHautEnBasNoir3 = new ArrayList<Case>();
        List<Case>diagonaleGaucheDroiteDeHautEnBasNoir4 = new ArrayList<Case>();
        List<Case>diagonaleGaucheDroiteDeHautEnBasNoir5 = new ArrayList<Case>();
        List<Case>diagonaleGaucheDroiteDeHautEnBasNoir6 = new ArrayList<Case>();
        List<Case>diagonaleGaucheDroiteDeHautEnBasNoir7 = new ArrayList<Case>();
        diagonaleGaucheDroiteDeHautEnBasNoir1.add(echiquier.get(6));
        diagonaleGaucheDroiteDeHautEnBasNoir1.add(echiquier.get(15));
        diagonaleGaucheDroiteDeHautEnBasNoir2.add(echiquier.get(4));
        diagonaleGaucheDroiteDeHautEnBasNoir2.add(echiquier.get(13));
        diagonaleGaucheDroiteDeHautEnBasNoir2.add(echiquier.get(22));
        diagonaleGaucheDroiteDeHautEnBasNoir2.add(echiquier.get(31));
        diagonaleGaucheDroiteDeHautEnBasNoir3.add(echiquier.get(2));
        diagonaleGaucheDroiteDeHautEnBasNoir3.add(echiquier.get(11));
        diagonaleGaucheDroiteDeHautEnBasNoir3.add(echiquier.get(20));
        diagonaleGaucheDroiteDeHautEnBasNoir3.add(echiquier.get(29));
        diagonaleGaucheDroiteDeHautEnBasNoir3.add(echiquier.get(38));
        diagonaleGaucheDroiteDeHautEnBasNoir3.add(echiquier.get(47));
        diagonaleGaucheDroiteDeHautEnBasNoir4.add(echiquier.get(0));
        diagonaleGaucheDroiteDeHautEnBasNoir4.add(echiquier.get(9));
        diagonaleGaucheDroiteDeHautEnBasNoir4.add(echiquier.get(18));
        diagonaleGaucheDroiteDeHautEnBasNoir4.add(echiquier.get(27));
        diagonaleGaucheDroiteDeHautEnBasNoir4.add(echiquier.get(36));
        diagonaleGaucheDroiteDeHautEnBasNoir4.add(echiquier.get(45));
        diagonaleGaucheDroiteDeHautEnBasNoir4.add(echiquier.get(54));
        diagonaleGaucheDroiteDeHautEnBasNoir4.add(echiquier.get(63));
        diagonaleGaucheDroiteDeHautEnBasNoir5.add(echiquier.get(16));
        diagonaleGaucheDroiteDeHautEnBasNoir5.add(echiquier.get(25));
        diagonaleGaucheDroiteDeHautEnBasNoir5.add(echiquier.get(34));
        diagonaleGaucheDroiteDeHautEnBasNoir5.add(echiquier.get(43));
        diagonaleGaucheDroiteDeHautEnBasNoir5.add(echiquier.get(52));
        diagonaleGaucheDroiteDeHautEnBasNoir5.add(echiquier.get(61));
        diagonaleGaucheDroiteDeHautEnBasNoir6.add(echiquier.get(32));
        diagonaleGaucheDroiteDeHautEnBasNoir6.add(echiquier.get(41));
        diagonaleGaucheDroiteDeHautEnBasNoir6.add(echiquier.get(50));
        diagonaleGaucheDroiteDeHautEnBasNoir6.add(echiquier.get(59));
        diagonaleGaucheDroiteDeHautEnBasNoir7.add(echiquier.get(48));
        diagonaleGaucheDroiteDeHautEnBasNoir7.add(echiquier.get(57));

        // Attributs Diagonale GaucheDroite Blanc :
        List<Case>diagonaleGaucheDroiteDeHautEnBasBlanc1 = new ArrayList<Case>();
        List<Case>diagonaleGaucheDroiteDeHautEnBasBlanc2 = new ArrayList<Case>();
        List<Case>diagonaleGaucheDroiteDeHautEnBasBlanc3 = new ArrayList<Case>();
        List<Case>diagonaleGaucheDroiteDeHautEnBasBlanc4 = new ArrayList<Case>();
        List<Case>diagonaleGaucheDroiteDeHautEnBasBlanc5 = new ArrayList<Case>();
        List<Case>diagonaleGaucheDroiteDeHautEnBasBlanc6 = new ArrayList<Case>();
        List<Case>diagonaleGaucheDroiteDeHautEnBasBlanc7 = new ArrayList<Case>();
        List<Case>diagonaleGaucheDroiteDeHautEnBasBlanc8 = new ArrayList<Case>();
        diagonaleGaucheDroiteDeHautEnBasBlanc1.add(echiquier.get(7));
        diagonaleGaucheDroiteDeHautEnBasBlanc2.add(echiquier.get(5));
        diagonaleGaucheDroiteDeHautEnBasBlanc2.add(echiquier.get(14));
        diagonaleGaucheDroiteDeHautEnBasBlanc2.add(echiquier.get(23));
        diagonaleGaucheDroiteDeHautEnBasBlanc3.add(echiquier.get(3));
        diagonaleGaucheDroiteDeHautEnBasBlanc3.add(echiquier.get(12));
        diagonaleGaucheDroiteDeHautEnBasBlanc3.add(echiquier.get(21));
        diagonaleGaucheDroiteDeHautEnBasBlanc3.add(echiquier.get(30));
        diagonaleGaucheDroiteDeHautEnBasBlanc3.add(echiquier.get(39));
        diagonaleGaucheDroiteDeHautEnBasBlanc4.add(echiquier.get(1));
        diagonaleGaucheDroiteDeHautEnBasBlanc4.add(echiquier.get(10));
        diagonaleGaucheDroiteDeHautEnBasBlanc4.add(echiquier.get(19));
        diagonaleGaucheDroiteDeHautEnBasBlanc4.add(echiquier.get(28));
        diagonaleGaucheDroiteDeHautEnBasBlanc4.add(echiquier.get(37));
        diagonaleGaucheDroiteDeHautEnBasBlanc4.add(echiquier.get(46));
        diagonaleGaucheDroiteDeHautEnBasBlanc4.add(echiquier.get(55));
        diagonaleGaucheDroiteDeHautEnBasBlanc5.add(echiquier.get(8));
        diagonaleGaucheDroiteDeHautEnBasBlanc5.add(echiquier.get(17));
        diagonaleGaucheDroiteDeHautEnBasBlanc5.add(echiquier.get(26));
        diagonaleGaucheDroiteDeHautEnBasBlanc5.add(echiquier.get(35));
        diagonaleGaucheDroiteDeHautEnBasBlanc5.add(echiquier.get(44));
        diagonaleGaucheDroiteDeHautEnBasBlanc5.add(echiquier.get(53));
        diagonaleGaucheDroiteDeHautEnBasBlanc5.add(echiquier.get(62));
        diagonaleGaucheDroiteDeHautEnBasBlanc6.add(echiquier.get(24));
        diagonaleGaucheDroiteDeHautEnBasBlanc6.add(echiquier.get(33));
        diagonaleGaucheDroiteDeHautEnBasBlanc6.add(echiquier.get(42));
        diagonaleGaucheDroiteDeHautEnBasBlanc6.add(echiquier.get(51));
        diagonaleGaucheDroiteDeHautEnBasBlanc6.add(echiquier.get(60));
        diagonaleGaucheDroiteDeHautEnBasBlanc7.add(echiquier.get(40));
        diagonaleGaucheDroiteDeHautEnBasBlanc7.add(echiquier.get(49));
        diagonaleGaucheDroiteDeHautEnBasBlanc7.add(echiquier.get(58));
        diagonaleGaucheDroiteDeHautEnBasBlanc8.add(echiquier.get(56));

        // Contrôles des bordures sur les diagonales de Gauche à Droite :
        // Contrôles sur les cases noires :
        if(diagonaleGaucheDroiteDeHautEnBasBlanc1.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasBlanc1.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasNoir2.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasNoir2.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasNoir3.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasNoir3.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasNoir4.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasNoir4.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasNoir5.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasNoir5.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasNoir6.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasNoir6.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasNoir7.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasNoir7.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }

        // Contrôles sur les cases blanches:
        else if(diagonaleGaucheDroiteDeHautEnBasBlanc1.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasBlanc1.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasBlanc2.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasBlanc2.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasBlanc3.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasBlanc3.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasBlanc4.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasBlanc4.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasBlanc5.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasBlanc5.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasBlanc6.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasBlanc6.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleGaucheDroiteDeHautEnBasBlanc7.contains(caseDepart.getNo_case()) && diagonaleGaucheDroiteDeHautEnBasBlanc7.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        return bordureDepasse;
    }




    /**
     * Méthode qui contrôle les bordures des diagonales qui vont de droite à gauche.
     * @param caseDepart
     * @param caseDestination
     * @return
     */
    public boolean borduresFouDiagonaleGaucheADroiteDeBasEnHaut(Case caseDepart, Case caseDestination, List<Case> echiquier)
    {
        // Attributs :
        boolean bordureDepasse = true;
        List<Case>diagonaleDroiteGaucheDeBasEnHautNoir1 = new ArrayList<Case>();
        List<Case>diagonaleDroiteGaucheDeBasEnHautNoir2 = new ArrayList<Case>();
        List<Case>diagonaleDroiteGaucheDeBasEnHautNoir3 = new ArrayList<Case>();
        List<Case>diagonaleDroiteGaucheDeBasEnHautNoir4 = new ArrayList<Case>();
        List<Case>diagonaleDroiteGaucheDeBasEnHautNoir5 = new ArrayList<Case>();
        List<Case>diagonaleDroiteGaucheDeBasEnHautNoir6 = new ArrayList<Case>();
        List<Case>diagonaleDroiteGaucheDeBasEnHautNoir7 = new ArrayList<Case>();
        List<Case>diagonaleDroiteGaucheDeBasEnHautNoir8 = new ArrayList<Case>();
        diagonaleDroiteGaucheDeBasEnHautNoir1.add(echiquier.get(0));
        diagonaleDroiteGaucheDeBasEnHautNoir2.add(echiquier.get(2));
        diagonaleDroiteGaucheDeBasEnHautNoir2.add(echiquier.get(9));
        diagonaleDroiteGaucheDeBasEnHautNoir2.add(echiquier.get(16));
        diagonaleDroiteGaucheDeBasEnHautNoir3.add(echiquier.get(4));
        diagonaleDroiteGaucheDeBasEnHautNoir3.add(echiquier.get(11));
        diagonaleDroiteGaucheDeBasEnHautNoir3.add(echiquier.get(18));
        diagonaleDroiteGaucheDeBasEnHautNoir3.add(echiquier.get(25));
        diagonaleDroiteGaucheDeBasEnHautNoir3.add(echiquier.get(32));
        diagonaleDroiteGaucheDeBasEnHautNoir4.add(echiquier.get(6));
        diagonaleDroiteGaucheDeBasEnHautNoir4.add(echiquier.get(13));
        diagonaleDroiteGaucheDeBasEnHautNoir4.add(echiquier.get(20));
        diagonaleDroiteGaucheDeBasEnHautNoir4.add(echiquier.get(27));
        diagonaleDroiteGaucheDeBasEnHautNoir4.add(echiquier.get(34));
        diagonaleDroiteGaucheDeBasEnHautNoir4.add(echiquier.get(41));
        diagonaleDroiteGaucheDeBasEnHautNoir4.add(echiquier.get(48));
        diagonaleDroiteGaucheDeBasEnHautNoir5.add(echiquier.get(15));
        diagonaleDroiteGaucheDeBasEnHautNoir5.add(echiquier.get(22));
        diagonaleDroiteGaucheDeBasEnHautNoir5.add(echiquier.get(29));
        diagonaleDroiteGaucheDeBasEnHautNoir5.add(echiquier.get(36));
        diagonaleDroiteGaucheDeBasEnHautNoir5.add(echiquier.get(43));
        diagonaleDroiteGaucheDeBasEnHautNoir5.add(echiquier.get(50));
        diagonaleDroiteGaucheDeBasEnHautNoir5.add(echiquier.get(57));
        diagonaleDroiteGaucheDeBasEnHautNoir6.add(echiquier.get(31));
        diagonaleDroiteGaucheDeBasEnHautNoir6.add(echiquier.get(38));
        diagonaleDroiteGaucheDeBasEnHautNoir6.add(echiquier.get(45));
        diagonaleDroiteGaucheDeBasEnHautNoir6.add(echiquier.get(52));
        diagonaleDroiteGaucheDeBasEnHautNoir6.add(echiquier.get(59));
        diagonaleDroiteGaucheDeBasEnHautNoir7.add(echiquier.get(47));
        diagonaleDroiteGaucheDeBasEnHautNoir7.add(echiquier.get(54));
        diagonaleDroiteGaucheDeBasEnHautNoir7.add(echiquier.get(61));
        diagonaleDroiteGaucheDeBasEnHautNoir8.add(echiquier.get(63));

        List<Case>diagonaleDroiteGaucheDeBasEnHautBlanc1 = new ArrayList<Case>();
        List<Case>diagonaleDroiteGaucheDeBasEnHautBlanc2 = new ArrayList<Case>();
        List<Case>diagonaleDroiteGaucheDeBasEnHautBlanc3 = new ArrayList<Case>();
        List<Case>diagonaleDroiteGaucheDeBasEnHautBlanc4 = new ArrayList<Case>();
        List<Case>diagonaleDroiteGaucheDeBasEnHautBlanc5 = new ArrayList<Case>();
        List<Case>diagonaleDroiteGaucheDeBasEnHautBlanc6 = new ArrayList<Case>();
        List<Case>diagonaleDroiteGaucheDeBasEnHautBlanc7 = new ArrayList<Case>();
        diagonaleDroiteGaucheDeBasEnHautBlanc1.add(echiquier.get(1));
        diagonaleDroiteGaucheDeBasEnHautBlanc1.add(echiquier.get(8));
        diagonaleDroiteGaucheDeBasEnHautBlanc2.add(echiquier.get(3));
        diagonaleDroiteGaucheDeBasEnHautBlanc2.add(echiquier.get(10));
        diagonaleDroiteGaucheDeBasEnHautBlanc2.add(echiquier.get(17));
        diagonaleDroiteGaucheDeBasEnHautBlanc2.add(echiquier.get(24));
        diagonaleDroiteGaucheDeBasEnHautBlanc3.add(echiquier.get(5));
        diagonaleDroiteGaucheDeBasEnHautBlanc3.add(echiquier.get(12));
        diagonaleDroiteGaucheDeBasEnHautBlanc3.add(echiquier.get(19));
        diagonaleDroiteGaucheDeBasEnHautBlanc3.add(echiquier.get(26));
        diagonaleDroiteGaucheDeBasEnHautBlanc3.add(echiquier.get(33));
        diagonaleDroiteGaucheDeBasEnHautBlanc3.add(echiquier.get(40));
        diagonaleDroiteGaucheDeBasEnHautBlanc4.add(echiquier.get(7));
        diagonaleDroiteGaucheDeBasEnHautBlanc4.add(echiquier.get(14));
        diagonaleDroiteGaucheDeBasEnHautBlanc4.add(echiquier.get(21));
        diagonaleDroiteGaucheDeBasEnHautBlanc4.add(echiquier.get(28));
        diagonaleDroiteGaucheDeBasEnHautBlanc4.add(echiquier.get(35));
        diagonaleDroiteGaucheDeBasEnHautBlanc4.add(echiquier.get(42));
        diagonaleDroiteGaucheDeBasEnHautBlanc4.add(echiquier.get(49));
        diagonaleDroiteGaucheDeBasEnHautBlanc4.add(echiquier.get(56));
        diagonaleDroiteGaucheDeBasEnHautBlanc5.add(echiquier.get(23));
        diagonaleDroiteGaucheDeBasEnHautBlanc5.add(echiquier.get(30));
        diagonaleDroiteGaucheDeBasEnHautBlanc5.add(echiquier.get(37));
        diagonaleDroiteGaucheDeBasEnHautBlanc5.add(echiquier.get(44));
        diagonaleDroiteGaucheDeBasEnHautBlanc5.add(echiquier.get(51));
        diagonaleDroiteGaucheDeBasEnHautBlanc5.add(echiquier.get(58));
        diagonaleDroiteGaucheDeBasEnHautBlanc6.add(echiquier.get(39));
        diagonaleDroiteGaucheDeBasEnHautBlanc6.add(echiquier.get(46));
        diagonaleDroiteGaucheDeBasEnHautBlanc6.add(echiquier.get(53));
        diagonaleDroiteGaucheDeBasEnHautBlanc6.add(echiquier.get(60));
        diagonaleDroiteGaucheDeBasEnHautBlanc7.add(echiquier.get(55));
        diagonaleDroiteGaucheDeBasEnHautBlanc7.add(echiquier.get(62));

        // Contrôles des bordures sur les diagonales de Droite à Gauche :
        // Contrôles sur les cases noires :
        if(diagonaleDroiteGaucheDeBasEnHautNoir1.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautNoir1.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautNoir2.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautNoir2.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautNoir3.contains(caseDepart.getNo_case()) && !diagonaleDroiteGaucheDeBasEnHautNoir3.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautNoir4.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautNoir4.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautNoir5.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautNoir5.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautNoir6.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautNoir6.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautNoir7.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautNoir7.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautNoir8.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautNoir8.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }

        // Contrôles sur les cases blanches:
        else if(diagonaleDroiteGaucheDeBasEnHautBlanc1.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautBlanc1.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautBlanc2.contains(caseDepart.getNo_case()) && !diagonaleDroiteGaucheDeBasEnHautBlanc2.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautBlanc3.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautBlanc3.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautBlanc4.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautBlanc4.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautBlanc5.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautBlanc5.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautBlanc6.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautBlanc6.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }
        else if(diagonaleDroiteGaucheDeBasEnHautBlanc7.contains(caseDepart.getNo_case()) && diagonaleDroiteGaucheDeBasEnHautBlanc7.contains(caseDestination.getNo_case()))
        {
            bordureDepasse = false;
        }



        return bordureDepasse;
    }






}
