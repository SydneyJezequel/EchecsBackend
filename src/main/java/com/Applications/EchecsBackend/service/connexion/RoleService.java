package com.Applications.EchecsBackend.service.connexion;

import com.Applications.EchecsBackend.models.connexion.Role;
import java.util.List;




/**
 * Interface du Service qui contient les fonctionnalités qui gère les rôles des users.
 */
public interface RoleService {


    // Méthode qui charge tous les rôles.
    public List<Role> loadAllRoles() throws Exception;




}
