package cogent.recruitment.service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cogent.recruitment.dao.UserDao;
import cogent.recruitment.jwt.JwtUtils;
import cogent.recruitment.dao.ApplicationDao;
import cogent.recruitment.entities.User;
import cogent.recruitment.exception.ResourceNotFoundException;
import cogent.recruitment.payload.request.LoginRequest;
import cogent.recruitment.payload.request.SignupRequest;
import cogent.recruitment.payload.response.JwtResponse;
import cogent.recruitment.util.UserUtil;
import cogent.recruitment.entities.ARole;
import cogent.recruitment.entities.Application;
import cogent.recruitment.entities.MailMessage;
import cogent.recruitment.entities.Role;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	ApplicationDao appDao;
	
	@Autowired
	private MailService notificationService;
	
	@Autowired
	private MailMessage message;

	@Autowired
	RoleService roleService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UserUtil util;

	@Override
	@Transactional
	public String addUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDao.save(user);
		User newUser = userDao.getById(user.getId());
		message.setEmailAddress(newUser.getEmail());
		message.setSubject("Cogent Recruitment Account Signup");
		message.setBodyText("Your account has successfully been registered");
		try {
			notificationService.sendEmail(message);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		return user.toString();
	}

	@Override
	public Set<User> getApplicants() {
		return userDao.findByrolesId(1);
	}
	
	@Override
	public User getApplicantfromUsers(Set<User> users) {
		Role role = new Role();
		role.setId(1); role.setName(ARole.USER);
		for(User user:users) {
			user= getUserById(user.getId());
			if (user.getRoles().iterator().next().toString().equals(role.toString())) { return user; }
		}
		return null;

	}

	@Override
	public Set<User> getAdmins() {
		Set<User> admins = userDao.findByrolesId(4);
		admins.addAll(getRecruiters());
		admins.addAll(getManagers());
		return admins;
	}

	@Override
	public Set<User> getRecruiters() {
		return userDao.findByrolesId(2);
	}

	@Override
	public Set<User> getManagers() {
		return userDao.findByrolesId(3);
	}

	@Override
	public User getUserById(int id) {
		return userDao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User with ID: " + id + " not found."));
	}

	@Override
	public Set<User> getUserByName(String name) {
		return userDao.findByname(name)
				.orElseThrow(() -> new ResourceNotFoundException("No users with name containing: " + name + " found."));
	}

	@Override
	@Transactional
	public String assignRecruiter(int applicationId, int recruiterId) {

		Optional<Application> app = appDao.findById(applicationId);
		Optional<User> rec = userDao.findById(recruiterId);
		if (app.isPresent() && rec.isPresent()) {
			if (rec.get().getRoles().contains(roleService.getRoleById(2))) {
				Set<User> users = app.get().getUsers();
				users.add(rec.get());
				app.get().setUsers(users);
				appDao.save(app.get());
				message.setEmailAddress(rec.get().getEmail());
				message.setSubject("New Application Assignment");
				message.setBodyText("You have been assigned a new Application");
				try {
					notificationService.sendEmail(message);
				} catch (MailException mailException) {
					System.out.println(mailException);
				}
				return "Successfully assigned Recruiter to Application";
			} else {
				return "Admin is not a recruiter";
			}
		} else {
			return "Application Id or Recruiter Id is not valid";
		}

	}

	@Override
	@Transactional
	public String unassignRecruiter(int applicationId, int recruiterId) {

		Optional<Application> app = appDao.findById(applicationId);
		Optional<User> rec = userDao.findById(recruiterId);
		if (app.isPresent() && rec.isPresent()) {
			if (rec.get().getRoles().contains(roleService.getRoleById(2))) {
				Set<User> users = app.get().getUsers();
				if (users.contains(rec.get())) {
					users.remove(rec.get());
					app.get().setUsers(users);
					appDao.save(app.get());
					message.setEmailAddress(rec.get().getEmail());
					message.setSubject("Unassigned from Application");
					message.setBodyText("You are no more in charge of handling this Application:\n"
							+ "\nJob: "+app.get().getJob()+ "\nApplicant: "+getApplicantfromUsers(app.get().getUsers()).getEmail());
					try {
						notificationService.sendEmail(message);
					} catch (MailException mailException) {
						System.out.println(mailException);
					}
					return "Successfully unassigned Recruiter from Application";
				} else {
					return "Recruiter not assigned to Application";
				}
			} else {
				return "Admin is not a recruiter";
			}
		} else {
			return "Application Id or Recruiter Id is not valid";
		}

	}

	@Override
	@Transactional
	public String updateUser(User user) {
		userDao.save(user);
		User newUser = userDao.getById(user.getId());
		message.setEmailAddress(newUser.getEmail());
		message.setSubject("Account Updated");
		message.setBodyText("Your account has successfully been udpated");
		try {
			notificationService.sendEmail(message);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		return user.toString();
	}

	@Override
	public User getUserByEmail(String email) {
		User user = userDao.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("No user with the specified email exists"));
		return user;
	}

	@Override
	public boolean doesUserExist(Integer id) {
		return userDao.existsById(id);
	}
	
	@Override
	public boolean doesUserExistByEmail(String email) {
		return userDao.existsByEmail(email);
	}

	@Override
	public JwtResponse userSignin(LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return new JwtResponse(jwt, userDetails.getId(), userDetails.getFirstName(), userDetails.getLastName(),
				userDetails.getEmail(), userDetails.getPhone(), userDetails.getLinkedin(), userDetails.isActive(),
				roles);
	}

	@Override
	@Transactional
	public String userSignup(SignupRequest signupRequest) {
		
		if(doesUserExistByEmail(signupRequest.getEmail())) {
			return "User with this email already exists.";
		}
		
		User user = new User(signupRequest.getFirstName(), signupRequest.getLastName(),
				signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()),
				signupRequest.getPhone(), signupRequest.getLinkedin());
		
		user.setActive(true);
		
		Set<Role> userRoles = signupRequest.getRoles();
		Set<Role>roles = new HashSet<>();
		
		if(userRoles == null) {
			Role userRole = roleService.getRoleByName(ARole.USER);
			roles.add(userRole);
		} else {
			userRoles.forEach(role -> {
				switch (roleService.getRoleById(role.getId()).getName().toString().toLowerCase()) {
				
				case "recruiter":
					Role adminRole = roleService.getRoleByName(ARole.RECRUITER);
					roles.add(adminRole);
					break;
					
				case "manager":
					Role managerRole = roleService.getRoleByName(ARole.MANAGER);
					roles.add(managerRole);
					break;
					
				case "owner":
					Role ownerRole = roleService.getRoleByName(ARole.OWNER);
					roles.add(ownerRole);
					break;

				default:
					Role userRole = roleService.getRoleByName(ARole.USER);
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userDao.save(user);
		User newUser = userDao.getById(user.getId());
		message.setEmailAddress(newUser.getEmail());
		message.setSubject("Cogent Recruitment Signup");
		message.setBodyText("You have successfully registered with Cogent Infotech Reruitment Service");
		try {
			notificationService.sendEmail(message);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		return "User registered successfully";
	}

}
