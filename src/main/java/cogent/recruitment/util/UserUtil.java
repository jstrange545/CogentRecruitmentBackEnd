package cogent.recruitment.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import cogent.recruitment.entities.User;

@Component
public class UserUtil {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public void mergeUser(User newUser, User oldUser) {
		
		if(newUser.getFirstName() !=null) {
			oldUser.setFirstName(newUser.getFirstName());
		}
		
		if(newUser.getLastName() !=null) {
			oldUser.setLastName(newUser.getLastName());
		}
		
		if(newUser.getPassword() !=null) {
			oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		}
		
		if(newUser.getPhone() !=null) {
			oldUser.setPhone(newUser.getPhone());
		}
		
		if(newUser.getLinkedin() !=null) {
			oldUser.setLinkedin(newUser.getLinkedin());
		}
		
		if(newUser.getActive()) {
			oldUser.setActive(newUser.getActive());
		}
		
		if(!newUser.getActive()) {
			oldUser.setActive(false);
		}
		
		if(!newUser.getRoles().isEmpty()) {
			oldUser.setRoles(newUser.getRoles());
		}
	}

}