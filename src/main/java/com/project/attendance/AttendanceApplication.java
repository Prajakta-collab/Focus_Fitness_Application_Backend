package com.project.attendance;

import com.project.attendance.Config.AppConstants;
import com.project.attendance.Model.Batch;
import com.project.attendance.Model.Role;
import com.project.attendance.Repository.BatchRepository;
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

	@Autowired
	private BatchRepository batchRepository ;

	@Bean
	ModelMapper modelMapper(){
		return new ModelMapper() ;
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println(passwordEncoder.encode("admin"));

		try{
			Role role1 = new Role() ;
			role1.setId(AppConstants.ADMIN_USER);
			role1.setRoleName("ROLE_ADMIN");

			Role role2 = new Role() ;
			role2.setId(AppConstants.NORMAL_USER);
			role2.setRoleName("ROLE_USER");

			Role role3 = new Role() ;
			role3.setId(AppConstants.STAFF_USER);
			role3.setRoleName("ROLE_STAFF");

			roleRepository.save(role1) ;
			roleRepository.save(role2) ;
			roleRepository.save(role3) ;

			Batch morningBatch = new Batch() ;
			morningBatch.setId(1);
			morningBatch.setBatchName("Morning");

			Batch eveningBatch = new Batch() ;
			eveningBatch.setId(2);
			eveningBatch.setBatchName("Evening");

			batchRepository.save(morningBatch) ;
			batchRepository.save(eveningBatch) ;

		}catch (Exception e){
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(AttendanceApplication.class, args);
	}
}
