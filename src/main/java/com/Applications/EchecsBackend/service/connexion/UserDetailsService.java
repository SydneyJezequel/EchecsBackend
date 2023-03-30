package com.Applications.EchecsBackend.service.connexion;

import com.Applications.EchecsBackend.models.connexion.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;





/**
 * Interface du Service qui contient les fonctionnalités qui contient les fonctionnalités qui gèrent les UserDetails.
 */
public interface UserDetailsService {


    // Méthode qui charge un user via son username.
    UserDetails loadUserByUsername(String username) throws Exception;

    // Méthode qui charge un user via son id.
    User loadUserById(Long id) throws Exception;

    // Méthode qui charge tous les users.
    List<UserDetails> loadAllUsers() throws Exception;

    // Méthode qui modifie un user.
    User updateUser(Long user_id, User user) throws Exception;

    // Méthode qui supprime un user.
    void deleteUser(Long userId) throws Exception;

    // Méthode qui créer un user.
    User addUser(User user) throws Exception;




}
