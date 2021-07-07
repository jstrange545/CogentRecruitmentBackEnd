package cogent.recruitment.controller;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cogent.recruitment.entities.User;
import cogent.recruitment.payload.request.LoginRequest;
import cogent.recruitment.payload.request.SignupRequest;
import cogent.recruitment.payload.response.JwtResponse;
import cogent.recruitment.service.UserService;
import cogent.recruitment.util.UserUtil;

@CrossOrigin
@RestController
public class UserController {

	@Autowired
	UserService service;

	@Autowired
	UserUtil util;

	@PreAuthorize("hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") int id) {
		User user = service.getUserById(id);
		ResponseEntity<?> resp = new ResponseEntity<User>(user, HttpStatus.OK);
		return resp;
	}

	@PreAuthorize("hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@GetMapping(value = "/users/name/{name}")
	public ResponseEntity<?> getUserByName(@PathVariable("name") String name) {
		Set<User> users = service.getUserByName(name);
		ResponseEntity<?> resp = new ResponseEntity<Set<User>>(users, HttpStatus.OK);
		return resp;
	}

	@PreAuthorize("hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@GetMapping(value = "/users/email/{email}")
	public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email) {
		User users = service.getUserByEmail(email);
		ResponseEntity<?> resp = new ResponseEntity<User>(users, HttpStatus.OK);
		return resp;
	}

	@PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@GetMapping(value = "/users/applicants")
	public ResponseEntity<?> getApplicants() {
		Set<User> users = service.getApplicants();
		ResponseEntity<?> resp = new ResponseEntity<Set<User>>(users, HttpStatus.OK);
		return resp;
	}

	@PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@GetMapping(value = "/users/admins")
	public ResponseEntity<?> getAdmins() {
		Set<User> users = service.getAdmins();
		ResponseEntity<?> resp = new ResponseEntity<Set<User>>(users, HttpStatus.OK);
		return resp;
	}

	@PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@GetMapping(value = "/users/recruiters")
	public ResponseEntity<?> getRecruiters() {
		Set<User> users = service.getRecruiters();
		ResponseEntity<?> resp = new ResponseEntity<Set<User>>(users, HttpStatus.OK);
		return resp;
	}

	@PreAuthorize("hasAuthority('OWNER')")
	@GetMapping(value = "/users/managers")
	public ResponseEntity<?> getManagers() {
		Set<User> users = service.getManagers();
		ResponseEntity<?> resp = new ResponseEntity<Set<User>>(users, HttpStatus.OK);
		return resp;
	}

	@PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@PostMapping(value = "/users")
	public ResponseEntity<String> saveUser(@Valid @RequestBody User user) {
		String userDets = "Created User: " + service.addUser(user);
		ResponseEntity<String> resp = new ResponseEntity<String>(userDets, HttpStatus.CREATED);
		return resp;
	}

	@PostMapping(value = "/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
		String msg = service.userSignup(signupRequest);
		ResponseEntity<String> resp = new ResponseEntity<String>(msg, HttpStatus.CREATED);
		return resp;
	}

	@PostMapping(value = "/signin")
	public ResponseEntity<?> signin(@Valid @RequestBody LoginRequest loginRequest) {
		JwtResponse resp = service.userSignin(loginRequest);
		return ResponseEntity.ok(resp);
	}

	@PreAuthorize("hasAuthority('USER') or hasAuthority('RECRUITER') or hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@PutMapping(value = "/users/{id}")
	public ResponseEntity<String> updateUser(@PathVariable int id, @Valid @RequestBody User user) {
		User dbUser = service.getUserById(id);
		util.mergeUser(user, dbUser);
		String userDets = "Updated User: " + service.updateUser(dbUser);
		ResponseEntity<String> resp = new ResponseEntity<String>(userDets, HttpStatus.OK);
		return resp;
	}

	@PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@PostMapping(value = "users/assignRecruiter/{Application_Id}/{Recruiter_Id}")
	public ResponseEntity<String> assignRecruiter(@PathVariable("Application_Id") int appId,
			@PathVariable("Recruiter_Id") int recId) {
		String proc = service.assignRecruiter(appId, recId);
		ResponseEntity<String> resp = new ResponseEntity<String>(proc, HttpStatus.OK);
		return resp;
	}

	@PreAuthorize("hasAuthority('MANAGER') or hasAuthority('OWNER')")
	@PostMapping(value = "users/unassignRecruiter/{Application_Id}/{Recruiter_Id}")
	public ResponseEntity<String> unassignRecruiter(@PathVariable("Application_Id") int appId,
			@PathVariable("Recruiter_Id") int recId) {
		String proc = service.unassignRecruiter(appId, recId);
		ResponseEntity<String> resp = new ResponseEntity<String>(proc, HttpStatus.OK);
		return resp;
	}

}
