package com.Applications.EchecsBackend.models.echecs;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;




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
    Couleur couleur;

    @Column(nullable = false, name="statut")
    char statut;




    // ********************* Constructeur ******************** :

    // TEST :
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




}
