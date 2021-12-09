package com.gilles.gestionDeStock.dto;

import com.gilles.gestionDeStock.model.Roles;
import com.gilles.gestionDeStock.model.Utilisateur;
import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class RolesDto {

    private Integer id;

    private String roleName;

    private UtilisateurDto utilisateur;

    public static RolesDto fromEntity(Roles roles){
        if (roles == null){
            return null;
        }
        return RolesDto.builder()
                .id(roles.getId())
                .roleName(roles.getRoleName())
                .utilisateur(UtilisateurDto.fromEntity(roles.getUtilisateur()))
                .build();
    }

    public static Roles toEntity(RolesDto rolesDto){
        if (rolesDto == null){
            return null;
        }
        Roles roles = new Roles();
        roles.setId(rolesDto.getId());
        roles.setRoleName(rolesDto.getRoleName());
        roles.setUtilisateur(roles.getUtilisateur());

        return roles;
    }
}
