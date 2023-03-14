package com.Applications.EchecsBackend.service.echecs.deplacements;

import com.Applications.EchecsBackend.models.echecs.Case;
import com.Applications.EchecsBackend.models.echecs.Couleur;
import com.Applications.EchecsBackend.models.echecs.Piece;
import com.Applications.EchecsBackend.repository.echecs.CaseRepository;
import com.Applications.EchecsBackend.repository.echecs.CouleurRepository;
import com.Applications.EchecsBackend.repository.echecs.PieceRepository;
import com.Applications.EchecsBackend.service.echecs.gestionPartie.GestionDesParties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service qui gère les fonctionnalités de déplacement des Roi.
 */
@Service
public class DeplacementRoi {





    // ********************* Attributs ******************** :
    Piece roi = new Piece();
    private final CaseRepository caseRepository;
    private final PieceRepository pieceRepository;

    private final CouleurRepository couleurRepository;
    private final GestionDesParties gestionDesParties;
    private final DeplacementDame deplacementDame;
    private final DeplacementTour deplacementTour;
    private final DeplacementFou deplacementFou;
    private final DeplacementCavalier deplacementCavalier;
    private final DeplacementPion deplacementPion;



    // ********************* Constructeur ******************** :
    @Autowired
    public DeplacementRoi(CaseRepository caseRepository,
                          PieceRepository pieceRepository,
                          CouleurRepository couleurRepository,
                          GestionDesParties gestionDesParties,
                          DeplacementDame deplacementDame,
                          DeplacementTour deplacementTour,
                          DeplacementFou deplacementFou,
                          DeplacementCavalier deplacementCavalier,
                          DeplacementPion deplacementPion) {
        this.caseRepository = caseRepository;
        this.pieceRepository = pieceRepository;
        this.couleurRepository = couleurRepository;
        this.gestionDesParties = gestionDesParties;
        this.deplacementDame = deplacementDame;
        this.deplacementTour = deplacementTour;
        this.deplacementFou = deplacementFou;
        this.deplacementCavalier = deplacementCavalier;
        this.deplacementPion = deplacementPion;
    }





    // ********************* Méthodes ******************** :

    /**
     * Méthode qui contrôle que le déplacement correspond aux règles de déplacement
     * d'une pièce de type Roi.
     * @return boolean
     * @throws Exception
     */
    public boolean deplacementRoi(Case caseDepart, Case caseDestination) throws Exception
    {
        // VERSION TEMPORAIRE : AJOUTER UN BLOCAGE DE DEPLACEMENT SUR LES PIECES DU MEME CAMP.
        // Récupération des numéros des cases :
        int noCaseDepart = Math.toIntExact(caseDepart.getNo_case());
        int noCaseDestination = Math.toIntExact(caseDestination.getNo_case());
        // Déplacements possibles :
        if(noCaseDestination == noCaseDepart+1
                || noCaseDestination == noCaseDepart-1
                || noCaseDestination == noCaseDepart+8
                || noCaseDestination == noCaseDepart-8
                || noCaseDestination == noCaseDepart+7
                || noCaseDestination == noCaseDepart-7
                || noCaseDestination == noCaseDepart+9
                || noCaseDestination == noCaseDepart-9
        )
        {
            if (roi.verificationCampPieceCaseDestination(caseDepart, caseDestination))
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
    }



    /**
     * Méthode qui définit les limites de l'échiquier pour un Roi.
     * @return boolean
     */
    public boolean borduresRoi(Case caseDepart, Case caseDestination)
    {
        Piece piece = caseDepart.getPiece();
        List<String> nomPiece = List.of(piece.getType().split(" "));
        String typeDePiece = nomPiece.get(0);
        if(typeDePiece.equals("roi")
                && caseDepart.getNo_case() == 40L
                || caseDepart.getNo_case() == 33L
                && caseDestination.getNo_case() == 33L
                || caseDestination.getNo_case() == 41L
                || caseDestination.getNo_case() == 49L
                || caseDestination.getNo_case() == 24L
                || caseDestination.getNo_case() == 32L
                || caseDestination.getNo_case() == 40L
        )
        {
            return false;
        }
        else
        {
            return true;
        }
    }



    /**
     * Méthode qui vérifie si le roi est en échec :
     * @return boolean
     * @throws Exception
     */
    public boolean echecAuRoi() throws Exception
    {
        // VERSION DE LA FONCTIONNALITE A TESTER :
        // Attributs :
        List<Case> echiquier = caseRepository.findAll();
        List<Case> positionsCampAdverse;
        Case caseDepart;
        Case caseDestination;
        Piece piece;
        boolean echecAuRoi = false;

        // 1- Récupération du roi du camp adverse :
        caseDestination = recuperationCaseDuRoiAdverse();
        piece = caseDestination.getPiece();

        // 2- Récupération des cases où se trouvent les pièces du camp adverse :
        positionsCampAdverse = gestionDesParties.positionsCampsAdverse();

        // 3- Contrôle pour savoir si le roi est en echec :
        for(int i = 0; i<positionsCampAdverse.size(); i++)
        {
            // Récupération du type des pièces du camp adverse :
            caseDepart = positionsCampAdverse.get(i);
            List<String> nomPiece = List.of(positionsCampAdverse.get(i).getPiece().getType().split(" "));
            String typeDePiece = nomPiece.get(0);

            // Contrôle du type de pièce :
            if(typeDePiece.equals("reine")) {
                if (deplacementDame.deplacementDame(caseDepart, caseDestination, echiquier)) {
                    echecAuRoi = true;
                    //************* TEST *************
                    System.out.println("echec au roi");
                    //************* TEST *************
                    break;
                }
            }else if(typeDePiece.equals("tour")) {
                if(deplacementTour.deplacementTour(caseDepart, caseDestination, echiquier))
                {
                    echecAuRoi = true;
                    //************* TEST *************
                    System.out.println("echec au roi");
                    //************* TEST *************
                    break;
                }
            }else if(typeDePiece.equals("fou")) {
                if(deplacementFou.deplacementFou(caseDepart, caseDestination, echiquier))
                {
                    echecAuRoi = true;
                    //************* TEST *************
                    System.out.println("echec au roi");
                    //************* TEST *************
                    break;
                }
            }else if(typeDePiece.equals("cavalier")) {
                if(deplacementCavalier.deplacementCavalier(caseDepart, caseDestination, echiquier))
                {
                    echecAuRoi = true;
                    //************* TEST *************
                    System.out.println("echec au roi");
                    //************* TEST *************
                    break;
                }
            }else if(typeDePiece.equals("pion")) {
                if(deplacementPion.deplacementPion(caseDepart, caseDestination, piece, echiquier))
                {
                    echecAuRoi = true;
                    //************* TEST *************
                    System.out.println("echec au roi");
                    //************* TEST *************
                    break;
                }
            }
        }
        return echecAuRoi;
        // return true;
    }
    // REGLES DE L'ALGORITHME :
        /*
               // 1- Récupération la position de toutes les pièces du camp adverse.
	              Stocker dans un array toutes les cases de l’échiquier ou piece.couleur != roi.couleur

              // 2- Simuler pour chacune de ces pièces si elle peut se rendre sur la case du roi.

	            1- Boucler sur ce tableau :
		            A chaque tour de boucle le List<Case> casesDeplacement sera composé de :
			        —> La case de la pièce comme case de départ.
			        —> La case du roi comme case de destination.
	            2- Selon le type de pièce :
			        —> appeler la méthode deplacementRoi, deplacementReine, deplacementTour, deplacementFou, deplacementCavalier, deplacementPion.
	            3- Si une de ces méthodes renvoie true :
			        —> On break la boucle.
			        —> On renvoie la pop-up : « Echec au roi ».
         */



    /**
     * Méthode qui vérifie si le roi est échec et mat :
     * @return boolean
     * @throws Exception
     */
    public boolean echecEtMat() throws Exception
    {
        // Attributs :
        boolean echecEtMat = true;
        Case caseDepartDuRoi = recuperationCaseDuRoiAdverse();
        List<Case> casesAutourDuRoi = casesAutourDuRoi(caseDepartDuRoi);

        // Exécution du contrôle :
        for(int i=0; i<casesAutourDuRoi.size(); i++)
        {
            if(deplacementRoi(caseDepartDuRoi, casesAutourDuRoi.get(i)))
            {
                echecEtMat = false;
                break;
            }
        }
         //************* TEST *************
        if(echecEtMat==true)
        {
               System.out.println("echec au et mat");
        }
        //************* TEST *************
        return echecEtMat;
    }
    // REGLES DE L'ALGORITHME :
        /*
               // 1- Récupération la position de toutes les pièces du camp adverse.
	              Stocker dans un array toutes les cases de l’échiquier ou piece.couleur != roi.couleur

              // 2- Simuler pour chacune de ces pièces si elle peut se rendre sur la case du roi.

	            1- Boucler sur ce tableau :
		            A chaque tour de boucle le List<Case> casesDeplacement sera composé de :
			        —> La case de la pièce comme case de départ.
			        —> La case du roi comme case de destination.
	            2- Selon le type de pièce :
			        —> appeler la méthode deplacementRoi, deplacementReine, deplacementTour, deplacementFou, deplacementCavalier, deplacementPion.
	            3- Si une de ces méthodes renvoie true :
			        —> On break la boucle.
			        —> On appelle la méthode de déplacement du roi : deplacementRoi().
			           Si la méthode de déplacement du roi renvoie false (Roi en échec + Roi ne peut se déplacer) :
			                —> On renvoie la pop-up : « Partie terminée : Echec et mat ».
         */



    /**
     * Méthode qui récupère les cases autour du roi :
     * @return casesAutourDuRoi
     */
    public List<Case> casesAutourDuRoi(Case caseDepartDuRoi)
    {
        // Attributs :
        List<Case> casesAutourDuRoi = new ArrayList<>();

        // Ajout des cases autour du roi dans le tableau :
        Case caseAutourDuRoi1 =  caseRepository.RecupererCasesAutourDuRoi(caseDepartDuRoi.getNo_case() + 1L);
        casesAutourDuRoi.add(caseAutourDuRoi1);
        Case caseAutourDuRoi2 = caseRepository.RecupererCasesAutourDuRoi(caseDepartDuRoi.getNo_case() - 1L);
        casesAutourDuRoi.add(caseAutourDuRoi2);
        Case caseAutourDuRoi3 = caseRepository.RecupererCasesAutourDuRoi(caseDepartDuRoi.getNo_case() + 8L);
        casesAutourDuRoi.add(caseAutourDuRoi3);
        Case caseAutourDuRoi4 = caseRepository.RecupererCasesAutourDuRoi(caseDepartDuRoi.getNo_case() - 8L);
        casesAutourDuRoi.add(caseAutourDuRoi4);
        Case caseAutourDuRoi5 = caseRepository.RecupererCasesAutourDuRoi(caseDepartDuRoi.getNo_case() + 7L);
        casesAutourDuRoi.add(caseAutourDuRoi5);
        Case caseAutourDuRoi6 = caseRepository.RecupererCasesAutourDuRoi(caseDepartDuRoi.getNo_case() - 7L);
        casesAutourDuRoi.add(caseAutourDuRoi6);
        Case caseAutourDuRoi7 = caseRepository.RecupererCasesAutourDuRoi(caseDepartDuRoi.getNo_case() + 9L);
        casesAutourDuRoi.add(caseAutourDuRoi7);
        Case caseAutourDuRoi8 = caseRepository.RecupererCasesAutourDuRoi(caseDepartDuRoi.getNo_case() - 9L);
        casesAutourDuRoi.add(caseAutourDuRoi8);

        // Renvoie du tableau :
        return casesAutourDuRoi;
    }



    /**
     * Méthode qui renvoie la case sur laquelle se trouve le roi du camp adverse.
     * @return caseDestination
     */
    public Case recuperationCaseDuRoiAdverse()
    {
        // Attributs :
        String campQuiJoue = gestionDesParties.campQuiJoue();
        Case caseDestination;
        String roiBlanc = "roi blanc";
        String roiNoir = "roi noir";
        // Piece roiBlanc = new Piece(29L, "roi blanc", campBlanc, 'A');
        // Piece roiNoir = new Piece(30L, "roi noir", campNoir, 'A');

        // Récupération du roi du camp adverse :
        if (campQuiJoue.equals("blanc")) {
            caseDestination = caseRepository.recupererLaCaseDuRoi(roiNoir);
            /*
            for (int i = 0; i <= echiquier.size(); i++) {
                if (echiquier.get(i).getPiece().getType().equals("roi noir")) {
                    caseDestination = echiquier.get(i);
                    break;
                }
            }
            */
        } else {
            caseDestination = caseRepository.recupererLaCaseDuRoi(roiBlanc);
            /*
            for (int i = 0; i <= echiquier.size(); i++) {
                if (echiquier.get(i).getPiece().getType().equals("roi blanc")) {
                    caseDestination = echiquier.get(i);
                    break;
                }
            }
            */
        }
        return caseDestination;
    }



    /**
     * Méthode qui contrôle si le roi a fait 3 allers/retours sur sa case.
     * Si ce cas est vérifiée, la partie est terminée.
     * @return boolean
     * @throws Exception
     */
    public boolean echecEtPat() throws Exception
    {
        // REGLES DE L'ALGORITHME :
        /*
		1- Charger dans un ArrayList les cases du roi dont c’est le tour depuis la BDD.
		2- Boucler l’ArrayList.
		3- A chaque tour de boucle, exécuter la condition suivante. Si est est vérifiée : Renvoyer « true ».
			if( ArrayList.get(0).no_case == ArrayList.get(2).no_case )
			{
				true;
			}
		4- Si true est renvoyé : Renvoyer une pop-up qui indique « Partie Terminée : Echec et Pat ».
         */
        return true;
    }



    /**
     * Méthode qui vérifie si le Grand Roque ou le Petit Roque est correct.
     * @return boolean
     * @throws Exception
     */
    public boolean Roque(Case caseDepart, Case caseDestination, List<Case> echiquier) throws Exception
    {
        // Vérification de la validité du Petit Roque Blanc :
        if(echiquier.get(63).getPiece()!=null)
        {
            List<String> nomTourBlanche2 = List.of(echiquier.get(63).getPiece().getType().split(" "));
            String tourBlancheH1 = nomTourBlanche2.get(0);
            // Règle de contrôle pour le petit Roque Blanc
            if (caseDestination.getNo_case() == caseDepart.getNo_case() + 16L
                    && tourBlancheH1.equals("tour")
                    && echiquier.get(55).getPiece() == null
                    && echiquier.get(47).getPiece() == null)
            {
                return true;
            }
        }
        // Vérification de la validité du Grand Roque Blanc :
        if(echiquier.get(7).getPiece()!=null)
        {
            List<String> nomTourBlanche1 = List.of(echiquier.get(7).getPiece().getType().split(" "));
            String tourBlancheA1 = nomTourBlanche1.get(0);
            // Règles de contrôles pour le Grand Roque Blanc :
            if (caseDestination.getNo_case() == caseDepart.getNo_case() - 16L
                    && tourBlancheA1.equals("tour")
                    && echiquier.get(15).getPiece() == null
                    && echiquier.get(23).getPiece() == null
                    && echiquier.get(31).getPiece() == null)
            {
                return true;
            }
        }
        // Vérification de la validité du petit Roque Noir :
        if(echiquier.get(56).getPiece()!=null)
        {
            List<String> nomTourNoire2 = List.of(echiquier.get(56).getPiece().getType().split(" "));
            String tourNoireH8 = nomTourNoire2.get(0);
            // Règles de contrôles pour le petit Roque noir :
            if(caseDestination.getNo_case() == caseDepart.getNo_case() + 16L
                    && tourNoireH8.equals("tour")
                    && echiquier.get(48).getPiece() == null
                    && echiquier.get(40).getPiece() == null)
            {
                return true;
            }
        }
        // Vérification de la validité du Grand Roque Noire:
        if(echiquier.get(0).getPiece()!=null)
        {
            List<String> nomTourNoire1 = List.of(echiquier.get(0).getPiece().getType().split(" "));
            String tourNoireA8 = nomTourNoire1.get(0);
            // Règles de contrôles pour le Grand Roque Noire:
            if(caseDestination.getNo_case() ==caseDepart.getNo_case() - 16L
                    && tourNoireA8.equals("tour")
                    && echiquier.get(24).getPiece() == null
                    && echiquier.get(16).getPiece() == null
                    && echiquier.get(8).getPiece() == null)
            {
                return true;
            }
        }
        return false;
    }



    /**
     * Cette méthode exécute le petit Roque Blanc.
     * @param caseDepart
     * @param caseDestination
     * @param piece
     * @param echiquier
     */
    public void petitRoqueBlanc(Case caseDepart, Case caseDestination, Piece piece, List<Case> echiquier)
    {
        caseDestination.setPiece(piece);
        caseDepart.setPiece(null);
        caseRepository.save(caseDepart);
        caseRepository.save(caseDestination);
        Case caseDepartTour = echiquier.get(63);
        Case caseDestinationTour = echiquier.get(47);
        caseDestinationTour.setPiece(echiquier.get(63).getPiece());
        caseDepartTour.setPiece(null);
        caseRepository.save(caseDepartTour);
        caseRepository.save(caseDestinationTour);
    }



    /**
     * Cette méthode exécute le petit Roque Noir.
     */
    public void petitRoqueNoir(Case caseDepart, Case caseDestination, Piece piece, List<Case> echiquier)
    {
        caseDestination.setPiece(piece);
        caseDepart.setPiece(null);
        caseRepository.save(caseDepart);
        caseRepository.save(caseDestination);
        Case caseDepartTour = echiquier.get(56);
        Case caseDestinationTour = echiquier.get(40);
        caseDestinationTour.setPiece(echiquier.get(56).getPiece());
        caseDepartTour.setPiece(null);
        caseRepository.save(caseDepartTour);
        caseRepository.save(caseDestinationTour);
    }



    /**
     * Cette méthode exécute le grand Roque Blanc.
     */
    public void grandRoqueBlanc(Case caseDepart, Case caseDestination, Piece piece, List<Case> echiquier)
    {
        caseDestination.setPiece(piece);
        caseDepart.setPiece(null);
        caseRepository.save(caseDepart);
        caseRepository.save(caseDestination);
        Case caseDepartTour = echiquier.get(7);
        Case caseDestinationTour = echiquier.get(31);
        caseDestinationTour.setPiece(echiquier.get(7).getPiece());
        caseDepartTour.setPiece(null);
        caseRepository.save(caseDepartTour);
        caseRepository.save(caseDestinationTour);
    }



    /**
     * Cette méthode exécute le grand Roque Noir.
     */
    public void grandRoqueNoir(Case caseDepart, Case caseDestination, Piece piece, List<Case> echiquier)
    {
        caseDestination.setPiece(piece);
        caseDepart.setPiece(null);
        caseRepository.save(caseDepart);
        caseRepository.save(caseDestination);
        Case caseDepartTour = echiquier.get(0);
        Case caseDestinationTour = echiquier.get(24);
        caseDestinationTour.setPiece(echiquier.get(0).getPiece());
        caseDepartTour.setPiece(null);
        caseRepository.save(caseDepartTour);
        caseRepository.save(caseDestinationTour);
    }





}
