package com.gilles.gestionDeStock.dto;

import com.gilles.gestionDeStock.model.MvtStock;
import com.gilles.gestionDeStock.model.TypeMvtStk;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class MvtStockDto {

    private Integer id;

    private Instant dateMvt;

    private BigDecimal quantite;

    private ArticleDto article;

    private TypeMvtStk typeMvt;

    private Integer idEntreprise;

    public static MvtStockDto fromEntity(MvtStock mvtStock){
        if (mvtStock == null){
            return null;
        }
        return MvtStockDto.builder()
                .id(mvtStock.getId())
                .dateMvt(mvtStock.getDateMvt())
                .quantite(mvtStock.getQuantite())
                .idEntreprise(mvtStock.getIdEntreprise())
                .article(ArticleDto.fromEntity(mvtStock.getArticle()))
                .typeMvt(mvtStock.getTypeMvt())
                .build();
    }

    public static MvtStock toEntity(MvtStockDto mvtStkStkDto){
        if (mvtStkStkDto == null){
            return null;
        }
        MvtStock mvtStock = new MvtStock();
        mvtStock.setId(mvtStkStkDto.getId());
        mvtStock.setDateMvt(mvtStkStkDto.getDateMvt());
        mvtStock.setQuantite(mvtStkStkDto.getQuantite());
        mvtStock.setIdEntreprise(mvtStkStkDto.getIdEntreprise());
        mvtStock.setArticle(mvtStock.getArticle());
        mvtStock.setTypeMvt(mvtStkStkDto.getTypeMvt());
        return mvtStock;
    }

}


