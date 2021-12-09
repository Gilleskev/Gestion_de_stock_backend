package com.gilles.gestionDeStock.model;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "entreprise")
public class Entreprise extends AbstractEntity{

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Embedded
    private Adresse adresse;

    @Column(name = "siret")
    private String siret;

    @Column(name = "codefiscal")
    private String codeFisscal;

    @Column(name = "photo")
    private  String photo;


    @Column(name = "email")
    private String email;

    @Column(name = "numerotel")
    private String numTel;

    @Column(name = "siteweb")
    private  String siteWeb;

    @OneToMany(mappedBy = "entreprise")
    private List<Utilisateur> utilisateurs;
}
