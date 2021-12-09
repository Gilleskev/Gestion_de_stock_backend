package com.gilles.gestionDeStock.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "mouvementStk")
public class MvtStock extends AbstractEntity{


    @Column(name = "datemouvement")
    private Instant dateMvt;

    @Column(name = "quantite")
    private BigDecimal quantite;

    @ManyToOne
    @JoinColumn(name = "idarticle")
    private Article article;

    @Column(name = "typemouvement")
    private TypeMvtStk typeMvt;

    @Column(name = "identreprise")
    private Integer idEntreprise;
}
