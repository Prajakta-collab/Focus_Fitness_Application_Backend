package com.project.attendance.security;

import com.project.attendance.Exception.ResourceNotFoundException;
import com.project.attendance.Model.User;
import com.project.attendance.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /*Loading User From Database By Username*/
        User loadeduser = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User" , "email : " + username , 0 ))  ;

        return loadeduser ;
    }
}
