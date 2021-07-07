package cogent.recruitment.service;

import java.util.Set;

import cogent.recruitment.entities.User;
import cogent.recruitment.payload.request.LoginRequest;
import cogent.recruitment.payload.request.SignupRequest;
import cogent.recruitment.payload.response.JwtResponse;

public interface UserService {
	
	public JwtResponse userSignin(LoginRequest loginRequest);
	
	public String userSignup(SignupRequest signupRequest);
	
	public String addUser(User user);
	
	public String updateUser(User user);
	
	public String assignRecruiter(int applicationId, int recruiterId);
	
	public String unassignRecruiter(int applicationId, int recruiterId);
	
	public Set<User> getApplicants();
	
	public User getApplicantfromUsers(Set<User> users);
	
	public Set<User> getAdmins();
	
	public Set<User> getRecruiters();
	
	public Set<User> getManagers();
	
	public User getUserById(int id);

	public Set<User> getUserByName(String name);
	
	public User getUserByEmail(String name);
	
	boolean doesUserExist(Integer id);
	
	boolean doesUserExistByEmail(String email);
	
}
