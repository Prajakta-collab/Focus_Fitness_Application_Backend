package com.project.attendance.Utilities;

import com.project.attendance.Config.AppConstants;
import com.project.attendance.Exception.ResourceNotFoundException;
import com.project.attendance.Model.User;
import com.project.attendance.Repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


@Configuration
public class Utility {

    @Autowired
    UserRepository userRepository ;

    public void validateUser(String token , String email , Collection<? extends GrantedAuthority> authorities) {

        String actualToken = token.substring(7); // Remove "Bearer " prefix
        String username = Jwts.parser().setSigningKey(String.valueOf(AppConstants.JWT_SECRET)).parseClaimsJws(actualToken).getBody().getSubject();


        /* Admin can access anything so returning same id to show same person is accessing */
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                return;
            }
        }

        if(!username.equals(email)){
            System.out.println("Check :- token user" + username + " --  owner" + email );
            throw new AccessDeniedException("Unauthorized access to data") ;
        }
    }
}
