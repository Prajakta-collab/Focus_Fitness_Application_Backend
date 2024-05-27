package com.project.attendance.Config;

import com.project.attendance.Exception.ResourceNotFoundException;
import com.project.attendance.Model.User;
import com.project.attendance.Repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;


@Configuration
public class Utility {

    @Autowired
    UserRepository userRepository ;

    public void validateUser(String token , Integer id) {
        String actualToken = token.substring(7); // Remove "Bearer " prefix
        String username = Jwts.parser().setSigningKey(String.valueOf(AppConstants.JWT_SECRET)).parseClaimsJws(actualToken).getBody().getSubject();

        User user = userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User" , "Email :- " + username , 0));


        /* Admin can access anything so returning same id to show same person is accessing */
        if(user.getRoles().contains("ROLE_ADMIN")){
            return ;
        }

        if(!user.getId().equals(id)){
            System.out.println("Check :- token user" + user.getId() + " --  owner" + id );
            throw new AccessDeniedException("Unauthorized access to data") ;
        }
    }
}
