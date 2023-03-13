package com.Applications.EchecsBackend.models.echecs;

import com.Applications.EchecsBackend.models.connexion.User;
import jakarta.persistence.*;





/**
 * Classe qui représente les cases de l'échiquier
 */
@Entity
@Table(name="partie")
public class Partie {





        // ********************* Attributs ******************** :
        @Id
        @Column(nullable = false, name="id_partie")
        Long no_partie;

        @Column(nullable = false, name="nombre_tour")
        int nombreDeTour;

        @ManyToOne
        @JoinColumn(name="user_camp_blanc", nullable = false, referencedColumnName = "id")
        User userCampBlanc;

        @ManyToOne
        @JoinColumn(name="user_camp_noir", nullable = false, referencedColumnName = "id")
        User userCampNoir;

        @ManyToOne
        @JoinColumn(name="user_gagnant", referencedColumnName = "id")
        User userGagnant;





    // ********************* Constructeur ******************** :

    public Partie() {}

    public Partie(Long no_partie, int nombreDeTour, User userCampBlanc, User userCampNoir, User userGagnant) {
        this.no_partie = no_partie;
        this.nombreDeTour = nombreDeTour;
        this.userCampBlanc = userCampBlanc;
        this.userCampNoir = userCampNoir;
        this.userGagnant = userGagnant;
    }





    // *********************** Getter & Setter ***********************

    public Long getNo_partie() {
        return no_partie;
    }

    public void setNo_partie(Long no_partie) {
        this.no_partie = no_partie;
    }

    public int getNombreDeTour() {
        return nombreDeTour;
    }

    public void setNombreDeTour(int nombreDeTour) {
        this.nombreDeTour = nombreDeTour;
    }





}
