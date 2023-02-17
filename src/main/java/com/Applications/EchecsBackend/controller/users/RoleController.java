package com.Applications.EchecsBackend.controller.users;

import com.Applications.EchecsBackend.service.connexion.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.Applications.EchecsBackend.models.connexion.Role;
import java.util.List;




/**
 * Controller qui contient les fonctionnalités qui gèrent les rôles des users.
 */
@RestController
@RequestMapping("/api")
public class RoleController {




    /****************************** Dépendances ******************************/

    @Autowired
    RoleServiceImpl roleServiceImpl;




    /****************************** Méthodes ******************************/

    @GetMapping("/role/all")
    // @Secured("ROLE_ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Role>> getAllRoles() throws Exception {
        List<Role> listeRoles = roleServiceImpl.loadAllRoles();
        return new ResponseEntity<>(listeRoles, HttpStatus.OK);
    }




}



