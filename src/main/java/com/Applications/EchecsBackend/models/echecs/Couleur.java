package com.Applications.EchecsBackend.models.echecs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;





/**
 * Classe qui représente les couleurs des pièces
 */
@Entity
@Table(name = "couleur")
public class Couleur {





    // *********************** Attributs ***********************
    @Id
    @Column(nullable = false, name="id_couleur")
    // @OneToMany(mappedBy = "couleur_id", cascade = CascadeType.ALL)
    private Long no_couleur;

    @Column(nullable = false, updatable = false, name="couleur")
    private String couleur;





    // *********************** Constructeur ***********************
    public Couleur() {
    }

    public Couleur(String camp) {
        this.couleur = couleur;
    }

    public Couleur(Long no_couleur, String couleur) {
        this.no_couleur = no_couleur;
        this.couleur = couleur;
    }





    // *********************** Getter & Setter ***********************

    public void setId(Long no_camp) {
        this.no_couleur = no_couleur;
    }
    public Long getId() {
        return no_couleur;
    }
    public String getCouleur() {
        return couleur;
    }
    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }





}
