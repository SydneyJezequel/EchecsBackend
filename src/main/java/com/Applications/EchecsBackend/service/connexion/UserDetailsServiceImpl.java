package com.Applications.EchecsBackend.service.connexion;

import com.Applications.EchecsBackend.models.connexion.User;
import com.Applications.EchecsBackend.repository.connexion.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;




/**
 * Service qui contient les fonctionnalités qui contient les fonctionnalités qui gèrent les UserDetails.
 *
 * UserDetailsService interface has a method to load User by username and returns
 * a UserDetails object that Spring Security can use for authentication and validation.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService, org.springframework.security.core.userdetails.UserDetailsService {




    // ********************* Dépendances *********************

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;




    // ********************* Méthodes *********************

    /**
     * Méthode qui charge un user via son Username.
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }



    /**
     * Méthode de récupération de tous les users.
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public List<UserDetails> loadAllUsers() throws Exception {
        List<User> listUsers = new ArrayList<User>();
        List<UserDetails> listUserDetails = new ArrayList<UserDetails>();
        listUsers = userRepository.findAll();
        for (User user : listUsers) {
            UserDetails userDetails = UserDetailsImpl.build(user);
            listUserDetails.add(userDetails);
        }
        return listUserDetails;
    }



    /**
     * Méthode qui récupère un User via son Id.
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // @Secured("ROLE_ADMIN")
   //  @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User loadUserById(Long id) throws Exception {
        return userRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found by id: " + id));
    }



    /**
     * Méthode de modification du user.
     * @param id
     * @param userMaj
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public User updateUser(Long id, User userMaj) throws Exception
    {
        User userData = userRepository.findById(id)
                .orElseThrow(() -> new Exception("Employee not exist with id: " + id));
        userData.setUsername(userMaj.getUsername());
        userData.setPassword(encoder.encode(userMaj.getPassword()));
        userData.setEmail(userMaj.getEmail());
        userData.setRoles(userMaj.getRoles());
        return userRepository.save(userData);
    }



    /**
     * Méthode qui supprime un user.
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public void deleteUser(Long userId) throws Exception
    {
        userRepository.deleteById(userId);
    }



    /**
     * Méthode qui ajoute un user.
     * @param user
     * @return
     */
    @Override
    @Transactional
    public User addUser(User user) throws Exception {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }




}