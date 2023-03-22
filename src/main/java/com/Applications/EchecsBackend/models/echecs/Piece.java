package com.Applications.EchecsBackend.models.echecs;

import com.Applications.EchecsBackend.service.gestionException.DeplacementCavalierException;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import java.util.List;





/**
 * Classe qui représente les pièces du jeu d'échecs.
 */
@Entity
@Table(name="Piece")
public class Piece {





    // ********************* Attributs ******************** :
    @Id
    @Column(nullable = false, name="id_piece")
    Long no_piece;

    @Column(nullable = false, name="nom")
    String type;

    @ManyToOne
    // @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name="couleur_id", nullable = false, referencedColumnName = "id_couleur")
    com.Applications.EchecsBackend.models.echecs.Couleur couleur;

    @Column(nullable = false, name="statut")
    char statut;





    // ********************* Constructeur ******************** :
    public Piece() {}

    public Piece(Long no_piece, String type, Couleur couleur, char statut) {
        this.no_piece = no_piece;
        this.type = type;
        this.couleur = couleur;
        this.statut = statut;
    }





    // ********************* Getter & Setter ******************** :
    public Long getNo_piece() {
        return no_piece;
    }
    public void setNo_piece(Long no_piece) {
        this.no_piece = no_piece;
    }
    public String getType() {
        return type;
    }
    public void setType(String nom) {
        this.type = type;
    }
    public Couleur getCouleur() {
        return couleur;
    }
    public void setCouleur(Couleur couleur) {this.couleur = couleur;}
    public char getStatut() { return statut;}
    public void setStatut(char statut) {this.statut = statut;}





    // ********************* Méthodes ******************** :

    /**
     * Cette méthode affecte les cases intermediaire par lesquels va passer une pièce lorsqu'elle se déplace.
     * @param caseDepart
     * @param nbCaseSup
     * @param echiquier
     * @return caseIntermediaire
     * @throws Exception
     */
    public Case calculCaseIntermediaire(Case caseDepart, Long nbCaseSup, List<Case> echiquier, String operateur) throws Exception
    {
        Case caseIntermediaire;
        if(operateur.equals("plus"))
        {
            if(caseDepart.getNo_case()+nbCaseSup>echiquier.size())
            {
                caseIntermediaire = null;
            }else if(caseDepart.getNo_case()+nbCaseSup==echiquier.size())
            {
                caseIntermediaire = echiquier.get(Math.toIntExact(caseDepart.getNo_case()+nbCaseSup-1));
            }else
            {
                int test = Math.toIntExact(caseDepart.getNo_case()+nbCaseSup);
                caseIntermediaire = echiquier.get(Math.toIntExact(caseDepart.getNo_case()+nbCaseSup));
            }
        }else{
            if(caseDepart.getNo_case()-nbCaseSup<0)
            {
                caseIntermediaire = null;
            }else
            {
                caseIntermediaire = echiquier.get(Math.toIntExact(caseDepart.getNo_case()-nbCaseSup));
            }
        }
        return caseIntermediaire;
    }



    /**
     * Cette méthode vérifie si la pièce sur la case de destination fait partie du même camp.
     * @param caseDepart
     * @param caseDestination
     * @return boolean
     * @throws Exception
     */
    public boolean verificationCampPieceCaseDestination(Case caseDepart, Case caseDestination) throws DeplacementCavalierException {
        if (caseDestination.getPiece() == null || caseDestination.getPiece() != null && !caseDestination.getPiece().getCouleur().getCouleur().equals(caseDepart.getPiece().getCouleur().getCouleur())) {
            return true;
        } else {
            return false;
        }
    }





}
