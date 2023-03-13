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
     * @return caseRepository.saveAll(echiquier)
     */
    public List<Case> ReinitialiserEchequier(String camp) {

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
        if(camp.equals("noir")) {
            System.out.println("Le camp est noir");

            // Initialisation des cases :
            Case caseH1 = new Case(1L,"H",1,blanc,tourBlanche2);
            Case caseH2 = new Case(2L,"H",2,noir,pionBlanc8);
            Case caseH3 = new Case(3L,"H",3,blanc,null);
            Case caseH4 = new Case(4L,"H",4,noir,null);
            Case caseH5 = new Case(5L,"H",5,blanc,null);
            Case caseH6 = new Case(6L,"H",6,noir,null);
            Case caseH7 = new Case(7L,"H",7,blanc,pionNoir8);
            Case caseH8 = new Case(8L,"H",8,noir,tourNoire2);

            Case caseG1 = new Case(9L,"G",1,noir,cavalierBlanc2);
            Case caseG2 = new Case(10L,"G",2,blanc,pionBlanc7);
            Case caseG3 = new Case(11L,"G",3,noir,null);
            Case caseG4 = new Case(12L,"G",4,blanc,null);
            Case caseG5 = new Case(13L,"G",5,noir,null);
            Case caseG6 = new Case(14L,"G",6,blanc,null);
            Case caseG7 = new Case(15L,"G",7,noir,pionNoir7);
            Case caseG8 = new Case(16L,"G",8,blanc,cavalierNoir2);

            Case caseF1 = new Case(17L,"F",1,blanc,fouBlanc2);
            Case caseF2 = new Case(18L,"F",2,noir,pionBlanc6);
            Case caseF3 = new Case(19L,"F",3,blanc,null);
            Case caseF4 = new Case(20L,"F",4,noir,null);
            Case caseF5 = new Case(21L,"F",5,blanc,null);
            Case caseF6 = new Case(22L,"F",6,noir,null);
            Case caseF7 = new Case(23L,"F",7,blanc,pionNoir6);
            Case caseF8 = new Case(24L,"F",8,noir,fouNoir2);

            Case caseE1 = new Case(25L,"E",1,noir,roiBlanc);
            Case caseE2 = new Case(26L,"E",2,blanc,pionBlanc5);
            Case caseE3 = new Case(27L,"E",3,noir,null);
            Case caseE4 = new Case(28L,"E",4,blanc,null);
            Case caseE5 = new Case(29L,"E",5,noir,null);
            Case caseE6 = new Case(30L,"E",6,blanc,null);
            Case caseE7 = new Case(31L,"E",7,noir,pionNoir5);
            Case caseE8 = new Case(32L,"E",8,blanc,roiNoir);

            Case caseD1 = new Case(33L,"D",1,blanc,reineBlanc);
            Case caseD2 = new Case(34L,"D",2,noir,pionBlanc4);
            Case caseD3 = new Case(35L,"D",3,blanc,null);
            Case caseD4= new Case(36L,"D",4,noir,null);
            Case caseD5 = new Case(37L,"D",5,blanc,null);
            Case caseD6 = new Case(38L,"D",6,noir,null);
            Case caseD7 = new Case(39L,"D",7,blanc,pionNoir4);
            Case caseD8 = new Case(40L,"D",8,noir,reineNoir);

            Case caseC1 = new Case(41L,"C",1,noir,fouBlanc1);
            Case caseC2 = new Case(42L,"C",2,blanc,pionBlanc3);
            Case caseC3 = new Case(43L,"C",3,noir,null);
            Case caseC4 = new Case(44L,"C",4,blanc,null);
            Case caseC5 = new Case(45L,"C",5,noir,null);
            Case caseC6 = new Case(46L,"C",6,blanc,null);
            Case caseC7 = new Case(47L,"C",7,noir,pionNoir3);
            Case caseC8 = new Case(48L,"C",8,blanc,fouNoir1);

            Case caseB1 = new Case(49L,"B",1,blanc,cavalierBlanc1);
            Case caseB2 = new Case(50L,"B",2,noir,pionBlanc2);
            Case caseB3 = new Case(51L,"B",3,blanc,null);
            Case caseB4 = new Case(52L,"B",4,noir,null);
            Case caseB5 = new Case(53L,"B",5,blanc,null);
            Case caseB6 = new Case(54L,"B",6,noir,null);
            Case caseB7 = new Case(55L,"B",7,blanc,pionNoir2);
            Case caseB8 = new Case(56L,"B",8,noir,cavalierNoir1);

            Case caseA1 = new Case(57L, "A",1, blanc,tourBlanche1);
            Case caseA2 = new Case(58L, "A",2,blanc,pionBlanc1);
            Case caseA3 = new Case(59L, "A",3,noir,null);
            Case caseA4 = new Case(60L,"A",4,blanc,null);
            Case caseA5 = new Case(61L,"A",5,noir,null);
            Case caseA6 = new Case(62L,"A",6,blanc,null);
            Case caseA7 = new Case(63L,"A",7,noir,pionNoir1);
            Case caseA8 = new Case(64L,"A",8,blanc,tourNoire1);

            // Ajout des cases dans l'échiquier :
            echiquier.add(caseH1);
            echiquier.add(caseH2);
            echiquier.add(caseH3);
            echiquier.add(caseH4);
            echiquier.add(caseH5);
            echiquier.add(caseH6);
            echiquier.add(caseH7);
            echiquier.add(caseH8);
            echiquier.add(caseG1);
            echiquier.add(caseG2);
            echiquier.add(caseG3);
            echiquier.add(caseG4);
            echiquier.add(caseG5);
            echiquier.add(caseG6);
            echiquier.add(caseG7);
            echiquier.add(caseG8);
            echiquier.add(caseF1);
            echiquier.add(caseF2);
            echiquier.add(caseF3);
            echiquier.add(caseF4);
            echiquier.add(caseF5);
            echiquier.add(caseF6);
            echiquier.add(caseF7);
            echiquier.add(caseF8);
            echiquier.add(caseE1);
            echiquier.add(caseE2);
            echiquier.add(caseE3);
            echiquier.add(caseE4);
            echiquier.add(caseE5);
            echiquier.add(caseE6);
            echiquier.add(caseE7);
            echiquier.add(caseE8);
            echiquier.add(caseD1);
            echiquier.add(caseD2);
            echiquier.add(caseD3);
            echiquier.add(caseD4);
            echiquier.add(caseD5);
            echiquier.add(caseD6);
            echiquier.add(caseD7);
            echiquier.add(caseD8);
            echiquier.add(caseC1);
            echiquier.add(caseC2);
            echiquier.add(caseC3);
            echiquier.add(caseC4);
            echiquier.add(caseC5);
            echiquier.add(caseC6);
            echiquier.add(caseC7);
            echiquier.add(caseC8);
            echiquier.add(caseB1);
            echiquier.add(caseB2);
            echiquier.add(caseB3);
            echiquier.add(caseB4);
            echiquier.add(caseB5);
            echiquier.add(caseB6);
            echiquier.add(caseB7);
            echiquier.add(caseB8);
            echiquier.add(caseA1);
            echiquier.add(caseA2);
            echiquier.add(caseA3);
            echiquier.add(caseA4);
            echiquier.add(caseA5);
            echiquier.add(caseA6);
            echiquier.add(caseA7);
            echiquier.add(caseA8);

            // 4- Renvoie de l'échiquier vers le Front :
            return caseRepository.saveAll(echiquier);
        }
        else
        {
            System.out.println("Le camp est blanc");

            // Initialisation des cases :
            Case caseA8 = new Case(1L,"A",8,blanc,tourNoire1);
            Case caseA7 = new Case(2L,"A",7,noir,pionNoir1);
            Case caseA6 = new Case(3L,"A",6,blanc,null);
            Case caseA5 = new Case(4L,"A",5,noir,null);
            Case caseA4 = new Case(5L,"A",4,blanc,null);
            Case caseA3 = new Case(6L, "A",3,noir,null);
            Case caseA2 = new Case(7L, "A",2,blanc,pionBlanc1);
            Case caseA1 = new Case(8L, "A",1, blanc,tourBlanche1);

            Case caseB8 = new Case(9L,"B",8,noir,cavalierNoir1);
            Case caseB7 = new Case(10L,"B",7,blanc,pionNoir2);
            Case caseB6 = new Case(11L,"B",6,noir,null);
            Case caseB5 = new Case(12L,"B",5,blanc,null);
            Case caseB4 = new Case(13L,"B",4,noir,null);
            Case caseB3 = new Case(14L,"B",3,blanc,null);
            Case caseB2 = new Case(15L,"B",2,noir,pionBlanc2);
            Case caseB1 = new Case(16L,"B",1,blanc,cavalierBlanc1);

            Case caseC8 = new Case(17L,"C",8,blanc,fouNoir1);
            Case caseC7 = new Case(18L,"C",7,noir,pionNoir3);
            Case caseC6 = new Case(19L,"C",6,blanc,null);
            Case caseC5 = new Case(20L,"C",5,noir,null);
            Case caseC4 = new Case(21L,"C",4,blanc,null);
            Case caseC3 = new Case(22L,"C",3,noir,null);
            Case caseC2 = new Case(23L,"C",2,blanc,pionBlanc3);
            Case caseC1 = new Case(24L,"C",1,noir,fouBlanc1);

            Case caseD8 = new Case(25L,"D",8,noir,reineNoir);
            Case caseD7 = new Case(26L,"D",7,blanc,pionNoir4);
            Case caseD6 = new Case(27L,"D",6,noir,null);
            Case caseD5 = new Case(28L,"D",5,blanc,null);
            Case caseD4= new Case(29L,"D",4,noir,null);
            Case caseD3 = new Case(30L,"D",3,blanc,null);
            Case caseD2 = new Case(31L,"D",2,noir,pionBlanc4);
            Case caseD1 = new Case(32L,"D",1,blanc,reineBlanc);

            Case caseE8 = new Case(33L,"E",8,blanc,roiNoir);
            Case caseE7 = new Case(34L,"E",7,noir,pionNoir5);
            Case caseE6 = new Case(35L,"E",6,blanc,null);
            Case caseE5 = new Case(36L,"E",5,noir,null);
            Case caseE4 = new Case(37L,"E",4,noir,null);
            Case caseE3 = new Case(38L,"E",3,noir,null);
            Case caseE2 = new Case(39L,"E",2,blanc,pionBlanc5);
            Case caseE1 = new Case(40L,"E",1,noir,roiBlanc);

            Case caseF8 = new Case(41L,"F",8,noir,fouNoir2);
            Case caseF7 = new Case(42L,"F",7,blanc,pionNoir6);
            Case caseF6 = new Case(43L,"F",6,noir,null);
            Case caseF5 = new Case(44L,"F",5,blanc,null);
            Case caseF4 = new Case(45L,"F",4,noir,null);
            Case caseF3 = new Case(46L,"F",3,blanc,null);
            Case caseF2 = new Case(47L,"F",2,noir,pionBlanc6);
            Case caseF1 = new Case(48L,"F",1,blanc,fouBlanc2);

            Case caseG8 = new Case(49L,"G",8,blanc,cavalierNoir2);
            Case caseG7 = new Case(50L,"G",7,noir,pionNoir7);
            Case caseG6 = new Case(51L,"G",6,blanc,null);
            Case caseG5 = new Case(52L,"G",5,noir,null);
            Case caseG4 = new Case(53L,"G",4,blanc,null);
            Case caseG3 = new Case(54L,"G",3,noir,null);
            Case caseG2 = new Case(55L,"G",2,blanc,pionBlanc7);
            Case caseG1 = new Case(56L,"G",1,noir,cavalierBlanc2);

            Case caseH8 = new Case(57L,"H",8,noir,tourNoire2);
            Case caseH7 = new Case(58L,"H",7,blanc,pionNoir8);
            Case caseH6 = new Case(59L,"H",6,noir,null);
            Case caseH5 = new Case(60L,"H",5,blanc,null);
            Case caseH4 = new Case(61L,"H",4,noir,null);
            Case caseH3 = new Case(62L,"H",3,blanc,null);
            Case caseH2 = new Case(63L,"H",2,noir,pionBlanc8);
            Case caseH1 = new Case(64L,"H",1,blanc,tourBlanche2);

            // Ajour des cases dans l'échiquier
            echiquier.add(caseA8);
            echiquier.add(caseA7);
            echiquier.add(caseA6);
            echiquier.add(caseA5);
            echiquier.add(caseA4);
            echiquier.add(caseA3);
            echiquier.add(caseA2);
            echiquier.add(caseA1);
            echiquier.add(caseB8);
            echiquier.add(caseB7);
            echiquier.add(caseB6);
            echiquier.add(caseB5);
            echiquier.add(caseB4);
            echiquier.add(caseB3);
            echiquier.add(caseB2);
            echiquier.add(caseB1);
            echiquier.add(caseC8);
            echiquier.add(caseC7);
            echiquier.add(caseC6);
            echiquier.add(caseC5);
            echiquier.add(caseC4);
            echiquier.add(caseC3);
            echiquier.add(caseC2);
            echiquier.add(caseC1);
            echiquier.add(caseD8);
            echiquier.add(caseD7);
            echiquier.add(caseD6);
            echiquier.add(caseD5);
            echiquier.add(caseD4);
            echiquier.add(caseD3);
            echiquier.add(caseD2);
            echiquier.add(caseD1);
            echiquier.add(caseE8);
            echiquier.add(caseE7);
            echiquier.add(caseE6);
            echiquier.add(caseE5);
            echiquier.add(caseE4);
            echiquier.add(caseE3);
            echiquier.add(caseE2);
            echiquier.add(caseE1);
            echiquier.add(caseF8);
            echiquier.add(caseF7);
            echiquier.add(caseF6);
            echiquier.add(caseF5);
            echiquier.add(caseF4);
            echiquier.add(caseF3);
            echiquier.add(caseF2);
            echiquier.add(caseF1);
            echiquier.add(caseG8);
            echiquier.add(caseG7);
            echiquier.add(caseG6);
            echiquier.add(caseG5);
            echiquier.add(caseG4);
            echiquier.add(caseG3);
            echiquier.add(caseG2);
            echiquier.add(caseG1);
            echiquier.add(caseH8);
            echiquier.add(caseH7);
            echiquier.add(caseH6);
            echiquier.add(caseH5);
            echiquier.add(caseH4);
            echiquier.add(caseH3);
            echiquier.add(caseH2);
            echiquier.add(caseH1);

            // 4- Renvoie de l'échiquier vers le Front :
            return caseRepository.saveAll(echiquier);
        }
    }



    /**
     * Méthode qui affiche l'échiquier
     * @return echiquierInitialise
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
