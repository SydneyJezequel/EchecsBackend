package com.Applications.EchecsBackend.models.echecs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;





/**
 * Classe qui représente les cases de l'échiquier
 */
@Entity
@Table(name = "case")
public class Case {





    // *********************** Attributs ***********************
    @Id
    @Column(nullable = false, name="id_case")
    private Long no_case;
    @Column(nullable = false, updatable = false, name="colonne")
    private String colonne;
    @Column(nullable = false, updatable = false, name="ligne")
    private int ligne;
    @ManyToOne
    //@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name="couleur_id", nullable = false, referencedColumnName = "id_couleur")
    private Couleur couleur;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="piece_id", referencedColumnName = "id_piece")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Piece piece;





    // *********************** Constructeur ***********************
    public Case() {}

    public Case(String colonne, int ligne, Couleur couleur) {
        this.colonne = colonne;
        this.ligne = ligne;
        this.couleur = couleur;
    }

    public Case(String colonne, int ligne, Couleur couleur, Piece piece) {
        this.colonne = colonne;
        this.ligne = ligne;
        this.couleur = couleur;
        this.piece = piece;

    }

    public Case(Long no_case, String colonne, int ligne, Couleur couleur) {
        this.no_case = no_case;
        this.colonne = colonne;
        this.ligne = ligne;
        this.couleur = couleur;
    }

    public Case(Long no_case, String colonne, int ligne, Couleur couleur, Piece piece) {
        this.no_case = no_case;
        this.colonne = colonne;
        this.ligne = ligne;
        this.couleur = couleur;
        this.piece = piece;
    }





    // *********************** Getter & Setter ***********************
    public Long getNo_case() {
        return no_case;
    }
    public void setNo_case(Long no_case) {
        this.no_case = no_case;
    }
    public String getColonne() {
        return colonne;
    }
    public void setColonne(String colonne) {
        this.colonne = colonne;
    }
    public int getLigne() {
        return ligne;
    }
    public void setLigne(int ligne) {
        this.ligne = ligne;
    }
    public Couleur getCouleur() {return couleur;}
    public void setCouleur(Couleur couleur) {this.couleur = couleur;}
    public Piece getPiece() {return piece;}
    public void setPiece(Piece piece) {this.piece = piece;}





}
