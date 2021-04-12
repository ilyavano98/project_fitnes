package com.fitnes2.demo2.service;

import com.fitnes2.demo2.model.Role;
import com.fitnes2.demo2.model.User;
import com.fitnes2.demo2.repositoryes.RoleRepository;
import com.fitnes2.demo2.repositoryes.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public User ad_findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> ad_allUsers() {
        return userRepository.findAll();
    }

    public boolean ad_saveUser(User user, String role_user) {
        //User userFromDB = userRepository.findByUsername(user.getUsername());

       /* if (userFromDB != null) {
            return false;
        }*/
        Role role_User_1;
        Role role_User_2;
        System.out.println("Здеся");
            if (role_user == "ROLE_USER")
            {
                System.out.println("вощел юзер");
                role_User_1 = new Role(1L, role_user);
                user.setRoles(Collections.singleton(role_User_1));
            }
            if(role_user == "ROLE_ADMIN"){
                System.out.println("вощел админ");
                role_User_2 = new Role(2L, role_user);
                user.setRoles(Collections.singleton(role_User_2));
            }
            if(role_user == "ROLE_USER, ROLE_ADMIN"){
                System.out.println("вощел");
                role_User_1 = new Role(1L, "ROLE_USER");
                role_User_2 = new Role(2L, "ROLE_ADMIN");
                Set<Role> roles = null;
                roles.add(role_User_1);
                roles.add(role_User_2);
                for(Role x : roles){
                    System.out.println(x.getName());
                }
                user.setRoles(roles);
            }
        //roleRepository.save(role);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
}
