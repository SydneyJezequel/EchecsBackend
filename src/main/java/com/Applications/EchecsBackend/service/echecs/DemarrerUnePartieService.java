package com.Applications.EchecsBackend.service.echecs;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Couleur;
import com.Applications.EchecsBackend.models.echecs.Piece;
import com.Applications.EchecsBackend.repository.echecs.CaseRepository;
import com.Applications.EchecsBackend.repository.echecs.CouleurRepository;
import com.Applications.EchecsBackend.repository.echecs.PieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;




/**
 * Service qui gère les fonctionnalités pour initialiser une partie d'échecs.
 */
@Service
public class DemarrerUnePartieService {




    // ********************* Dépendances *********************

    @Autowired
    private final PieceRepository pieceRepository;
    @Autowired
    private final CaseRepository caseRepository;
    @Autowired
    private final CouleurRepository couleurRepository;




    // ********************* Constructeur *********************

    @Autowired
    public DemarrerUnePartieService (PieceRepository pieceRepository, CaseRepository caseRepository, CouleurRepository couleurRepository) {
        this.pieceRepository = pieceRepository;
        this.caseRepository = caseRepository;
        this.couleurRepository = couleurRepository;
    }




    /*************************************** Méthodes **************************************/

    /**
     * Méthode qui Ré-initialise l'Echequier :
     */
    public List<Case> ReinitialiserEchequier() {

        // 1- Suppression de l'Echiquier en BDD de la partie en cours :
        pieceRepository.deleteAll();
        caseRepository.deleteAll();

        // 2- Création des camps :
        Couleur blanc = couleurRepository.getReferenceById(1L);
        Couleur noir = couleurRepository.getReferenceById(2L);

        // 3- Création de l'échiquier (cases et cellules) :
        // 3.1- Initialisation des pièces dans la BDD :
        List<Piece> listePiece = new ArrayList<Piece>();
        Piece pionBlanc1 = new Piece(1L,"pion blanc", blanc, 'A');
        listePiece.add(pionBlanc1);
        Piece pionBlanc2 = new Piece(2L,"pion blanc", blanc, 'A');
        listePiece.add(pionBlanc2);
        Piece pionBlanc3 = new Piece(3L,"pion blanc", blanc, 'A');
        listePiece.add(pionBlanc3);
        Piece pionBlanc4 = new Piece(4L, "pion blanc", blanc, 'A');
        listePiece.add(pionBlanc4);
        Piece pionBlanc5 = new Piece(5L, "pion blanc", blanc, 'A');
        listePiece.add(pionBlanc5);
        Piece pionBlanc6 = new Piece(6L, "pion blanc", blanc, 'A');
        listePiece.add(pionBlanc6);
        Piece pionBlanc7 = new Piece(7L, "pion blanc", blanc, 'A');
        listePiece.add(pionBlanc7);
        Piece pionBlanc8 = new Piece(8L, "pion blanc", blanc, 'A');
        listePiece.add(pionBlanc8);
        Piece pionNoir1 = new Piece(9L, "pion noir", noir,'A');
        listePiece.add(pionNoir1);
        Piece pionNoir2 = new Piece(10L, "pion noir", noir,'A');
        listePiece.add(pionNoir2);
        Piece pionNoir3 = new Piece(11L, "pion noir", noir,'A');
        listePiece.add(pionNoir3);
        Piece pionNoir4 = new Piece(12L, "pion noir", noir,'A');
        listePiece.add(pionNoir4);
        Piece pionNoir5 = new Piece(13L, "pion noir", noir,'A');
        listePiece.add(pionNoir5);
        Piece pionNoir6 = new Piece(14L, "pion noir", noir,'A');
        listePiece.add(pionNoir6);
        Piece pionNoir7 = new Piece(15L, "pion noir", noir,'A');
        listePiece.add(pionNoir7);
        Piece pionNoir8 = new Piece(16L, "pion noir", noir,'A');
        listePiece.add(pionNoir8);
        Piece tourBlanche1 = new Piece(17L, "tour blanc", blanc, 'A');
        listePiece.add(tourBlanche1);
        Piece tourBlanche2 = new Piece(18L, "tour blanc", blanc, 'A');
        listePiece.add(tourBlanche2);
        Piece tourNoire1 = new Piece(19L, "tour noir", noir, 'A');
        listePiece.add(tourNoire1);
        Piece tourNoire2 = new Piece(20L, "tour noir", noir, 'A');
        listePiece.add(tourNoire2);
        Piece cavalierBlanc1 = new Piece(21L, "cavalier blanc", blanc, 'A');
        listePiece.add(cavalierBlanc1);
        Piece cavalierBlanc2 = new Piece(22L, "cavalier blanc", blanc, 'A');
        listePiece.add(cavalierBlanc2);
        Piece cavalierNoir1 = new Piece(23L, "cavalier noir", noir, 'A');
        listePiece.add(cavalierNoir1);
        Piece cavalierNoir2 = new Piece(24L, "cavalier noir", noir, 'A');
        listePiece.add(cavalierNoir2);
        Piece fouBlanc1 = new Piece(25L, "fou blanc", blanc, 'A');
        listePiece.add(fouBlanc1);
        Piece fouBlanc2 = new Piece(26L, "fou blanc", blanc, 'A');
        listePiece.add(fouBlanc2);
        Piece fouNoir1 = new Piece(27L, "fou noir", noir, 'A');
        listePiece.add(fouNoir1);
        Piece fouNoir2 = new Piece(28L, "fou noir", noir, 'A');
        listePiece.add(fouNoir2);
        Piece roiBlanc = new Piece(29L, "roi blanc", blanc, 'A');
        listePiece.add(roiBlanc);
        Piece roiNoir = new Piece(30L, "roi noir", noir, 'A');
        listePiece.add(roiNoir);
        Piece reineBlanc = new Piece(31L, "reine blanc", blanc, 'A');
        listePiece.add(reineBlanc);
        Piece reineNoir = new Piece(32L, "reine noir", noir, 'A');
        listePiece.add(reineNoir);
        pieceRepository.saveAll(listePiece);

        //3.2- Initialisation des Cases :
        List<Case> echiquier = new ArrayList<Case>();
        Case case1 = new Case(1L, "A",1, blanc,tourBlanche1);
        echiquier.add(case1);
        Case case2 = new Case(2L, "A",2,blanc,pionBlanc1);
        echiquier.add(case2);
        Case case3 = new Case(3L, "A",3,noir,null);
        echiquier.add(case3);
        Case case4 = new Case(4L,"A",4,blanc,null);
        echiquier.add(case4);
        Case case5 = new Case(5L,"A",5,noir,null);
        echiquier.add(case5);
        Case case6 = new Case(6L,"A",6,blanc,null);
        echiquier.add(case6);
        Case case7 = new Case(7L,"A",7,noir,pionNoir1);
        echiquier.add(case7);
        Case case8 = new Case(8L,"A",8,blanc,tourNoire1);
        echiquier.add(case8);
        Case case9 = new Case(9L,"B",1,blanc,cavalierBlanc1);
        echiquier.add(case9);
        Case case10 = new Case(10L,"B",2,noir,pionBlanc2);
        echiquier.add(case10);
        Case case11 = new Case(11L,"B",3,blanc,null);
        echiquier.add(case11);
        Case case12 = new Case(12L,"B",4,noir,null);
        echiquier.add(case12);
        Case case13 = new Case(13L,"B",5,blanc,null);
        echiquier.add(case13);
        Case case14 = new Case(14L,"B",6,noir,null);
        echiquier.add(case14);
        Case case15 = new Case(15L,"B",7,blanc,pionNoir2);
        echiquier.add(case15);
        Case case16 = new Case(16L,"B",8,noir,cavalierNoir1);
        echiquier.add(case16);
        Case case17 = new Case(17L,"C",1,noir,fouBlanc1);
        echiquier.add(case17);
        Case case18 = new Case(18L,"C",2,blanc,pionBlanc3);
        echiquier.add(case18);
        Case case19 = new Case(19L,"C",3,noir,null);
        echiquier.add(case19);
        Case case20 = new Case(20L,"C",4,blanc,null);
        echiquier.add(case20);
        Case case21 = new Case(21L,"C",5,noir,null);
        echiquier.add(case21);
        Case case22 = new Case(22L,"C",6,blanc,null);
        echiquier.add(case22);
        Case case23 = new Case(23L,"C",7,noir,pionNoir3);
        echiquier.add(case23);
        Case case24 = new Case(24L,"C",8,blanc,fouNoir1);
        echiquier.add(case24);
        Case case25 = new Case(25L,"D",1,blanc,roiBlanc);
        echiquier.add(case25);
        Case case26 = new Case(26L,"D",2,noir,pionBlanc4);
        echiquier.add(case26);
        Case case27 = new Case(27L,"D",3,blanc,null);
        echiquier.add(case27);
        Case case28 = new Case(28L,"D",4,noir,null);
        echiquier.add(case28);
        Case case29 = new Case(29L,"D",5,blanc,null);
        echiquier.add(case29);
        Case case30 = new Case(30L,"D",6,noir,null);
        echiquier.add(case30);
        Case case31 = new Case(31L,"D",7,blanc,pionNoir4);
        echiquier.add(case31);
        Case case32 = new Case(32L,"D",8,noir,roiNoir);
        echiquier.add(case32);
        Case case33 = new Case(33L,"E",1,noir,reineBlanc);
        echiquier.add(case33);
        Case case34 = new Case(34L,"E",2,blanc,pionBlanc5);
        echiquier.add(case34);
        Case case35 = new Case(35L,"E",3,noir,null);
        echiquier.add(case35);
        Case case36 = new Case(36L,"E",4,blanc,null);
        echiquier.add(case36);
        Case case37 = new Case(37L,"E",5,noir,null);
        echiquier.add(case37);
        Case case38 = new Case(38L,"E",6,blanc,null);
        echiquier.add(case38);
        Case case39 = new Case(39L,"E",7,noir,pionNoir5);
        echiquier.add(case39);
        Case case40 = new Case(40L,"E",8,blanc,reineNoir);
        echiquier.add(case40);
        Case case41 = new Case(41L,"F",1,blanc,fouBlanc2);
        echiquier.add(case41);
        Case case42 = new Case(42L,"F",2,noir,pionBlanc6);
        echiquier.add(case42);
        Case case43 = new Case(43L,"F",3,blanc,null);
        echiquier.add(case43);
        Case case44 = new Case(44L,"F",4,noir,null);
        echiquier.add(case44);
        Case case45 = new Case(45L,"F",5,blanc,null);
        echiquier.add(case45);
        Case case46 = new Case(46L,"F",6,noir,null);
        echiquier.add(case46);
        Case case47 = new Case(47L,"F",7,blanc,pionNoir6);
        echiquier.add(case47);
        Case case48 = new Case(48L,"F",8,noir,fouNoir2);
        echiquier.add(case48);
        Case case49 = new Case(49L,"G",1,noir,cavalierBlanc2);
        echiquier.add(case49);
        Case case50 = new Case(50L,"G",2,blanc,pionBlanc7);
        echiquier.add(case50);
        Case case51 = new Case(51L,"G",3,noir,null);
        echiquier.add(case51);
        Case case52 = new Case(52L,"G",4,blanc,null);
        echiquier.add(case52);
        Case case53 = new Case(53L,"G",5,noir,null);
        echiquier.add(case53);
        Case case54 = new Case(54L,"G",6,blanc,null);
        echiquier.add(case54);
        Case case55 = new Case(55L,"G",7,noir,pionNoir7);
        echiquier.add(case55);
        Case case56 = new Case(56L,"G",8,blanc,cavalierNoir2);
        echiquier.add(case56);
        Case case57 = new Case(57L,"H",1,blanc,tourBlanche2);
        echiquier.add(case57);
        Case case58 = new Case(58L,"H",2,noir,pionBlanc8);
        echiquier.add(case58);
        Case case59 = new Case(59L,"H",3,blanc,null);
        echiquier.add(case59);
        Case case60 = new Case(60L,"H",4,noir,null);
        echiquier.add(case60);
        Case case61 = new Case(61L,"H",5,blanc,null);
        echiquier.add(case61);
        Case case62 = new Case(62L,"H",6,noir,null);
        echiquier.add(case62);
        Case case63 = new Case(63L,"H",7,blanc,pionNoir8);
        echiquier.add(case63);
        Case case64 = new Case(64L,"H",8,noir,tourNoire2);
        echiquier.add(case64);
        return caseRepository.saveAll(echiquier);
    }



    /**
     * Méthode qui affiche l'échiquier
     * @return
     */
    public List<Case> getEchequier() {

        // 1- Chargement des tableaux de pièces et cases :
        List<Case> echiquierInitialise = new ArrayList<>();
        List<Piece> listePiece = new ArrayList<>();
        listePiece = pieceRepository.findAll();
        listePiece.sort(Comparator.comparing(Piece::getNo_piece));
        echiquierInitialise = caseRepository.findAll();
        echiquierInitialise.sort(Comparator.comparing(Case::getNo_case));

        // 2- Ajout des pièces sur l'échiquier :
        // Colonne A :
        echiquierInitialise.get(0).setPiece(echiquierInitialise.get(0).getPiece());
        echiquierInitialise.get(1).setPiece(echiquierInitialise.get(1).getPiece());
        echiquierInitialise.get(6).setPiece(echiquierInitialise.get(6).getPiece());
        echiquierInitialise.get(7).setPiece(echiquierInitialise.get(7).getPiece());
        // Colonne B :
        echiquierInitialise.get(8).setPiece(echiquierInitialise.get(8).getPiece());
        echiquierInitialise.get(9).setPiece(echiquierInitialise.get(9).getPiece());
        echiquierInitialise.get(14).setPiece(echiquierInitialise.get(14).getPiece());
        echiquierInitialise.get(15).setPiece(echiquierInitialise.get(15).getPiece());
        // Colonne C :
        echiquierInitialise.get(16).setPiece(echiquierInitialise.get(16).getPiece());
        echiquierInitialise.get(17).setPiece(echiquierInitialise.get(17).getPiece());
        echiquierInitialise.get(22).setPiece(echiquierInitialise.get(22).getPiece());
        echiquierInitialise.get(23).setPiece(echiquierInitialise.get(23).getPiece());
        // Colonne D :
        echiquierInitialise.get(24).setPiece(echiquierInitialise.get(24).getPiece());
        echiquierInitialise.get(25).setPiece(echiquierInitialise.get(25).getPiece());
        echiquierInitialise.get(30).setPiece(echiquierInitialise.get(30).getPiece());
        echiquierInitialise.get(31).setPiece(echiquierInitialise.get(31).getPiece());
        // Colonne E :
        echiquierInitialise.get(32).setPiece(echiquierInitialise.get(32).getPiece());
        echiquierInitialise.get(33).setPiece(echiquierInitialise.get(33).getPiece());
        echiquierInitialise.get(38).setPiece(echiquierInitialise.get(38).getPiece());
        echiquierInitialise.get(39).setPiece(echiquierInitialise.get(39).getPiece());

        // Colonne F :
        echiquierInitialise.get(40).setPiece(echiquierInitialise.get(40).getPiece());
        echiquierInitialise.get(41).setPiece(echiquierInitialise.get(41).getPiece());
        echiquierInitialise.get(46).setPiece(echiquierInitialise.get(46).getPiece());
        echiquierInitialise.get(47).setPiece(echiquierInitialise.get(47).getPiece());

        // Colonne G :
        echiquierInitialise.get(48).setPiece(echiquierInitialise.get(48).getPiece());
        echiquierInitialise.get(49).setPiece(echiquierInitialise.get(49).getPiece());
        echiquierInitialise.get(54).setPiece(echiquierInitialise.get(54).getPiece());
        echiquierInitialise.get(55).setPiece(echiquierInitialise.get(55).getPiece());

        // Colonne H :
        echiquierInitialise.get(56).setPiece(echiquierInitialise.get(56).getPiece());
        echiquierInitialise.get(57).setPiece(echiquierInitialise.get(57).getPiece());
        echiquierInitialise.get(62).setPiece(echiquierInitialise.get(62).getPiece());
        echiquierInitialise.get(63).setPiece(echiquierInitialise.get(63).getPiece());

        return echiquierInitialise;
    }




}
