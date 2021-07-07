package cogent.recruitment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cogent.recruitment.entities.Role;
import cogent.recruitment.service.RoleService;

@CrossOrigin
@RestController
public class RoleController {
	
	@Autowired
	RoleService service;
	
	@PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@GetMapping(value = "/roles/{id}")
	public ResponseEntity<?> getRole(@PathVariable("id") int id) {
		Role role = service.getRoleById(id);
		ResponseEntity<?> resp = new ResponseEntity<Role>(role, HttpStatus.OK);
		return resp;
	}
	
	@PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@GetMapping(value="/roles")
	public ResponseEntity<?> getRoles() {
		List<Role> roles = service.getRoles();
		ResponseEntity<?> resp = new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
		return resp;
	}
	
}
