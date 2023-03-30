package com.Applications.EchecsBackend.service.connexion;

import com.Applications.EchecsBackend.models.connexion.Role;
import com.Applications.EchecsBackend.repository.connexion.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;





/**
 * Service qui contient les fonctionnalités qui gèrent les rôles des users.
 */
@Service
public class RoleServiceImpl implements RoleService {





    // ********************* Dépendances *********************

    @Autowired
    RoleRepository roleRepository;





    // ********************* Méthode *********************

    /**
     * Méthode qui charge tous les rôles.
     * @return
     * @throws Exception
     */
    public List<Role> loadAllRoles() throws Exception {
        return roleRepository.findAll();
    }





}
