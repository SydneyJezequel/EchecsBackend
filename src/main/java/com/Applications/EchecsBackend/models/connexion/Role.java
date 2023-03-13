package com.Applications.EchecsBackend.models.connexion;

import jakarta.persistence.*;





/**
 * Classe qui représente un Rôle.
 */
@Entity
@Table(name = "roles")
public class Role {





    /********************** Attributs ********************/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;





    /********************** Constructeurs ********************/
    public Role() {}

    public Role(ERole name) {
        this.name = name;
    }





    /********************** Getters & Setters ********************/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }





}