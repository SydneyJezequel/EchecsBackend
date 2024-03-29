package com.Applications.EchecsBackend.controller.users;

import com.Applications.EchecsBackend.models.connexion.User;
import com.Applications.EchecsBackend.service.connexion.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;





/**
 * Controller qui contient les fonctionnalités pour gérer les users de l'application.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {





    /****************************** Dépendances ******************************/

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;





    /****************************** Méthodes ******************************/

    /**
     * Controller pour renvoyer tous les users.
     * @return
     * @throws Exception
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDetails>> getAllUsersDetails() throws Exception {
        List<UserDetails> usersDetailsList = userDetailsServiceImpl.loadAllUsers();
        return new ResponseEntity<>(usersDetailsList, HttpStatus.OK);
    }




    /**
     * Controller pour renvoyer un user via son Id.
     */
    @GetMapping("/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserDetails(@PathVariable("id") Long id) throws Exception {
        User user = userDetailsServiceImpl.loadUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }




    /**
     * Controller pour modifier un user.
     */
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User userMaj) throws Exception {
        User updateUser = userDetailsServiceImpl.updateUser(id, userMaj);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }




    /**
     * Controller pour supprimer un user.
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) throws Exception {
        userDetailsServiceImpl.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




    /**
     * Controller pour créér un nouveau user.
     */
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@RequestBody User user) throws Exception {
        User nouveauUser = userDetailsServiceImpl.addUser(user);
        return new ResponseEntity<>(nouveauUser, HttpStatus.OK);
    }





}



