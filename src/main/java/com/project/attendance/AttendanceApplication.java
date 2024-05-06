package com.project.attendance;

import com.project.attendance.Config.AppConstants;
import com.project.attendance.Model.Role;
import com.project.attendance.Repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AttendanceApplication implements CommandLineRunner {

	@Autowired
	PasswordEncoder passwordEncoder ;

	@Autowired
	private RoleRepository roleRepository ;

	@Bean
	ModelMapper modelMapper(){
		return new ModelMapper() ;
	}

	@Override
	public void run(String... args) throws Exception {

		try{
			Role role1 = new Role() ;
			role1.setId(AppConstants.ADMIN_USER);
			role1.setRoleName("ROLE_ADMIN");

			Role role2 = new Role() ;
			role2.setId(AppConstants.NORMAL_USER);
			role2.setRoleName("NORMAL_USER");

			roleRepository.save(role1) ;
			roleRepository.save(role2) ;

		}catch (Exception e){
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(AttendanceApplication.class, args);
	}
}
